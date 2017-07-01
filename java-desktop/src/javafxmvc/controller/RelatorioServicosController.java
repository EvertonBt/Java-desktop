package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafxmvc.model.dao.ServicoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Categoria;
import javafxmvc.model.domain.Servico;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class RelatorioServicosController implements Initializable {

	  @FXML
	  private TableView<Servico> tableViewServicos;
	  @FXML
	  private TableColumn<Servico, Integer> tableColumnCdServico;
	  @FXML
	  private TableColumn<Servico, String> tableColumnNome;
	  @FXML
	  private TableColumn<Servico, Double> tableColumnPreco;
	  @FXML
	  private TableColumn<Servico, Integer> tableColumnDescricao;
	  @FXML
	  private Button buttonImprimir;
  
      private List<Servico> listServicos;
      private ObservableList<Servico> observableListServicos;

  //Atributos para manipula√ß√£o de Banco de Dados
  private final Database database = DatabaseFactory.getDatabase("mysql");
  private final Connection connection = database.conectar();
  private final ServicoDAO servicoDAO = new ServicoDAO();
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
      servicoDAO.setConnection (connection); 
      carregarTableViewServicos();
  
  }    
  
  public void carregarTableViewServicos() {
      tableColumnCdServico.setCellValueFactory(new PropertyValueFactory<>("cdServico"));
      tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
      tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
      tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

      listServicos = servicoDAO.listar();

      observableListServicos = FXCollections.observableArrayList(listServicos);
      tableViewServicos.setItems(observableListServicos);
  }

  public void handleImprimir() throws JRException{
      //HashMap filtro = new HashMap();
      //filtro.put("cdCategoria", 1);

	  //Acessa o arquivo .jasper j· compilado
      URL url = getClass().getResource("/javafxmvc/relatorios/RelatorioServicos.jasper");
      //O arquivo .jasper È processado pela biblioteca do jasper reports
      JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
       
      //o jasper print recebe o relatorio gerado pelo jasper fill que recebe a conex„o com o banco de dados (o relatorio precisa ter sido feito usando a conex„o com sql)
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: caso n√£o existam filtros
      //O jasper view serve pra visualizar o relatorio, uma outra alternativa seria exportar para .pdf
      JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: n√£o deixa fechar a aplica√ß√£o principal
      jasperViewer.setVisible(true);
  }
 
  
}
