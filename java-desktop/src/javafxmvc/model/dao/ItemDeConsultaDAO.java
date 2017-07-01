package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.ItemDeConsulta;
import javafxmvc.model.domain.Produto;
import javafxmvc.model.domain.Consulta;

public class ItemDeConsultaDAO{

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ItemDeConsulta itemDeConsulta) {
        String sql = "INSERT INTO itensdeconsulta(quantidade, valor, cdProduto, cdConsulta) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeConsulta.getQuantidade());
            stmt.setDouble(2, itemDeConsulta.getValor());
            stmt.setInt(3, itemDeConsulta.getProduto().getCdProduto());
            stmt.setInt(4, itemDeConsulta.getConsulta().getCdConsulta());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDeConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(ItemDeConsulta itemDeConsulta) {
        return true;
    }

    public boolean remover(ItemDeConsulta itemDeConsulta) {
        String sql = "DELETE FROM itensDeConsulta WHERE cdItemDeConsulta=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeConsulta.getCdItemDeConsulta());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDeConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<ItemDeConsulta> listar() {
        String sql = "SELECT * FROM itensDeConsulta";
        List<ItemDeConsulta> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ItemDeConsulta itemDeConsulta = new ItemDeConsulta();
                Produto produto = new Produto();
                Consulta consulta = new Consulta();
                itemDeConsulta.setCdItemDeConsulta(resultado.getInt("cdItemDeConsulta"));
                itemDeConsulta.setQuantidade(resultado.getInt("quantidade"));
                itemDeConsulta.setValor(resultado.getDouble("valor"));
                
                produto.setCdProduto(resultado.getInt("cdProduto"));
                consulta.setCdConsulta(resultado.getInt("cdConsulta"));
                
                //Obtendo os dados completos do Produto associado ao Item de Consulta
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.setConnection(connection);
                produto = produtoDAO.buscar(produto);
                
                ConsultaDAO consultaDAO = new ConsultaDAO();
                consultaDAO.setConnection(connection);
                consulta = consultaDAO.buscar(consulta);
                
                itemDeConsulta.setProduto(produto);
                itemDeConsulta.setConsulta(consulta);
                
                retorno.add(itemDeConsulta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemDeConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<ItemDeConsulta> listarPorConsulta(Consulta consulta) {
        String sql = "SELECT * FROM itensDeConsulta WHERE cdConsulta=?";
        List<ItemDeConsulta> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, consulta.getCdConsulta());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ItemDeConsulta itemDeConsulta = new ItemDeConsulta();
                Produto produto = new Produto();
                Consulta v = new Consulta();
                itemDeConsulta.setCdItemDeConsulta(resultado.getInt("cdItemDeConsulta"));
                itemDeConsulta.setQuantidade(resultado.getInt("quantidade"));
                itemDeConsulta.setValor(resultado.getDouble("valor"));
                
                produto.setCdProduto(resultado.getInt("cdProduto"));
                v.setCdConsulta(resultado.getInt("cdConsulta"));
                
                //Obtendo os dados completos do Produto associado ao Item de Consulta
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.setConnection(connection);
                produto = produtoDAO.buscar(produto);
                
                itemDeConsulta.setProduto(produto);
                itemDeConsulta.setConsulta(v);
                
                retorno.add(itemDeConsulta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemDeConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public ItemDeConsulta buscar(ItemDeConsulta itemDeConsulta) {
        String sql = "SELECT * FROM itensDeConsulta WHERE cdItemDeConsulta=?";
        ItemDeConsulta retorno = new ItemDeConsulta();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeConsulta.getCdItemDeConsulta());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Produto produto = new Produto();
                Consulta consulta = new Consulta();
                itemDeConsulta.setCdItemDeConsulta(resultado.getInt("cdItemDeConsulta"));
                itemDeConsulta.setQuantidade(resultado.getInt("quantidade"));
                itemDeConsulta.setValor(resultado.getDouble("valor"));
                
                produto.setCdProduto(resultado.getInt("cdProduto"));
                consulta.setCdConsulta(resultado.getInt("cdConsulta"));
                
                //Obtendo os dados completos do Cliente associado à Consulta
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.setConnection(connection);
                produto = produtoDAO.buscar(produto);
                
                ConsultaDAO consultaDAO = new ConsultaDAO();
                consultaDAO.setConnection(connection);
                consulta = consultaDAO.buscar(consulta);
                
                itemDeConsulta.setProduto(produto);
                itemDeConsulta.setConsulta(consulta);
                
                retorno = itemDeConsulta;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
