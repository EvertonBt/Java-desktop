package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.ItemDeConsulta;
import javafxmvc.model.domain.Consulta;

public class ConsultaDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Consulta consulta) {
        String sql = "INSERT INTO consultas(data, valor, pago, estado, cdCliente) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(consulta.getData()));
            stmt.setDouble(2, consulta.getValor());
            stmt.setBoolean(3, consulta.getPago());
          
            if(consulta.getPago())
                   consulta.setEstado("Pago");
            else 
            	   consulta.setEstado("Pendente");
            
            stmt.setString(4, consulta.getEstado());
            stmt.setInt(5, consulta.getCliente().getCdCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Consulta consulta) {
        String sql = "UPDATE consultas SET data=?, valor=?, pago=?, estado=?, cdCliente=? WHERE cdConsulta=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(consulta.getData()));
            stmt.setDouble(2, consulta.getValor());
            stmt.setBoolean(3, consulta.getPago());
            if(consulta.getPago())
                consulta.setEstado("Pago");
         else 
         	   consulta.setEstado("Pendente");
            
            stmt.setString(4, consulta.getEstado());
            stmt.setInt(5, consulta.getCliente().getCdCliente());
            stmt.setInt(6, consulta.getCdConsulta());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Consulta consulta) {
        String sql = "DELETE FROM consultas WHERE cdConsulta=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, consulta.getCdConsulta());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Consulta> listar() {
        String sql = "SELECT * FROM consultas ORDER BY cdConsulta DESC ";
        List<Consulta> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Consulta consulta = new Consulta();
                Cliente cliente = new Cliente();
                List<ItemDeConsulta> itensDeConsulta = new ArrayList();

                consulta.setCdConsulta(resultado.getInt("cdConsulta"));
                consulta.setData(resultado.getDate("data").toLocalDate());
                consulta.setValor(resultado.getDouble("valor"));
                consulta.setPago(resultado.getBoolean("pago"));
                consulta.setEstado(resultado.getString("estado"));
                cliente.setCdCliente(resultado.getInt("cdCliente"));

                
                //Obtendo os dados completos do Cliente associado à Consulta
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
                cliente = clienteDAO.buscar(cliente);

                //Obtendo os dados completos dos Itens de Consulta associados à Consulta
                ItemDeConsultaDAO itemDeConsultaDAO = new ItemDeConsultaDAO();
                itemDeConsultaDAO.setConnection(connection);
                itensDeConsulta = itemDeConsultaDAO.listarPorConsulta(consulta);

                consulta.setCliente(cliente);
                consulta.setItensDeConsulta(itensDeConsulta);
                retorno.add(consulta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Consulta buscar(Consulta consulta) {
        String sql = "SELECT * FROM consultas WHERE cdConsulta=?";
        Consulta retorno = new Consulta();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, consulta.getCdConsulta());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Cliente cliente = new Cliente();
                consulta.setCdConsulta(resultado.getInt("cdConsulta"));
                consulta.setData(resultado.getDate("data").toLocalDate());
                consulta.setValor(resultado.getDouble("valor"));
                consulta.setPago(resultado.getBoolean("pago"));
                cliente.setCdCliente(resultado.getInt("cdCliente"));
                consulta.setCliente(cliente);
                retorno = consulta;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    //Quando voc� usa o max � preciso definir um apelido, pois ele retorna o resultado do max com um nome diferente do nome da coluna
    public Consulta buscarUltimaConsulta() {
        String sql = "select max(cdConsulta) cdConsulta from consultas";
        Consulta retorno = new Consulta();
        try {
        	PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();;

            if (resultado.next()) {
            	retorno.setCdConsulta(resultado.getInt("cdConsulta"));
                return retorno;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Map<Integer, ArrayList> listarQuantidadeConsultasPorMes() {
        String sql = "select count(cdConsulta) contador, extract(year from data) as ano, extract(month from data) as mes from consultas group by ano, mes order by ano, mes";
        Map<Integer, ArrayList> retorno = new HashMap();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(resultado.getInt("ano")))
                {
                    linha.add(resultado.getInt("mes"));
                    linha.add(resultado.getInt("contador"));
                    retorno.put(resultado.getInt("ano"), linha);
                }else{
                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
                    linhaNova.add(resultado.getInt("mes"));
                    linhaNova.add(resultado.getInt("contador"));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

	public List<Consulta> listarPorData(LocalDate localDate) {
		
	       String sql = "SELECT * FROM consultas WHERE data=? ORDER BY cdConsulta DESC ";
	        List<Consulta> retorno = new ArrayList<>();
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            stmt.setDate(1, Date.valueOf(localDate));
	            ResultSet resultado = stmt.executeQuery();
	            while (resultado.next()) {
	                Consulta consulta = new Consulta();
	                Cliente cliente = new Cliente();
	                List<ItemDeConsulta> itensDeConsulta = new ArrayList();

	                consulta.setCdConsulta(resultado.getInt("cdConsulta"));
	                consulta.setData(resultado.getDate("data").toLocalDate());
	                consulta.setValor(resultado.getDouble("valor"));
	                consulta.setPago(resultado.getBoolean("pago"));
	                consulta.setEstado(resultado.getString("estado"));
	                cliente.setCdCliente(resultado.getInt("cdCliente"));

	                
	                //Obtendo os dados completos do Cliente associado à Consulta
	                ClienteDAO clienteDAO = new ClienteDAO();
	                clienteDAO.setConnection(connection);
	                cliente = clienteDAO.buscar(cliente);

	                //Obtendo os dados completos dos Itens de Consulta associados à Consulta
	                ItemDeConsultaDAO itemDeConsultaDAO = new ItemDeConsultaDAO();
	                itemDeConsultaDAO.setConnection(connection);
	                itensDeConsulta = itemDeConsultaDAO.listarPorConsulta(consulta);

	                consulta.setCliente(cliente);
	                consulta.setItensDeConsulta(itensDeConsulta);
	                retorno.add(consulta);
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return retorno;

		
		
	}


	 public List<Procedimento> listarProcedimentosPorAno(int ano) {
	        String sql = "SELECT * FROM procedimentos where ano=? ";
	        List<Procedimento> retorno = new ArrayList<Procedimento>();
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            stmt.setInt(1, ano);
	            ResultSet resultado = stmt.executeQuery();
	            while (resultado.next()) {
	               
	            Procedimento procedimento = new Procedimento();
	            procedimento.setCdProcedimento(resultado.getInt("cdProcedimento"));	
	            procedimento.setNome(resultado.getString("nome"));
	            procedimento.setAno(resultado.getInt("ano"));
	            procedimento.setTotal(resultado.getDouble("total")); 
	            retorno.add(procedimento);
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return retorno;
	    }




}