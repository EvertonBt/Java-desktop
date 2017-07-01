package javafxmvc.controller;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.ServicoDAO;
import javafxmvc.model.domain.Servico;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;


public class ServicoListagemController implements Initializable{

	  @FXML
	    private TableView<Servico> tableViewServicos;
	    @FXML
	    private TableColumn<Servico, String> tableColumnServicoCodigo;
	    @FXML
	    private TableColumn<Servico, String> tableColumnServicoNome;
	    @FXML
	    private Button buttonInserir;
	    @FXML
	    private Button buttonAlterar;
	    @FXML
	    private Button buttonRemover;
	    @FXML
	    private Label labelCodigo;  
	    @FXML
	    private Label labelNome;
	    @FXML
	    private Label labelDescricao;
	    @FXML
	    private Label labelPreco;
	    @FXML
	    private Label labelQuantidade;
	    
	    //Vari·veis para manipular a lista que vem do banco de dados.
	    private List<Servico> listServicos;
	    // lista que preenche a tableview.
	    private ObservableList<Servico> observableListServicos;
	    //Atributos necess·rios para obter a conex„o com o banco e acessar seus dados
	    private final Database database = DatabaseFactory.getDatabase("mysql");
	    private final Connection connection = database.conectar();
	    private final ServicoDAO servicoDAO = new ServicoDAO();
		
	    @Override
		public void initialize(URL location, ResourceBundle resources) {

	    	 //seta a conex„o no servicoDAO
	    	 servicoDAO.setConnection (connection);
	         
	    	 //MÈtodo que busca no banco a lista de servicos e converte para uma lista do tipo observablelist
	         carregarTableViewServicos();

	         // Limpando a exibiÁ„o dos detahes do servico (isso È usado quando o usu·rio n„o escolhe nenhum servico da lista
	         selecionarItemTableViewServicos(null);

	         // Listen acionado diante de quaisquer alteraÁ„o  na seleÁ„o de itens do TableView
	         tableViewServicos.getSelectionModel().selectedItemProperty().addListener(
	                 (observable, oldValue, newValue) -> selecionarItemTableViewServicos(newValue));
	    
			
		}

	    //obtem a lista do banco, converte para observable list e seta na tableview
	    public void carregarTableViewServicos() {
	    	tableColumnServicoCodigo.setCellValueFactory(new PropertyValueFactory<>("cdServico"));
	    	tableColumnServicoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	        listServicos = servicoDAO.listar();
	        observableListServicos = FXCollections.observableArrayList(listServicos);
	        tableViewServicos.setItems(observableListServicos);
	    }
	    
	    //A medida que o usu·rio seleciona um novo item na lista ele exibe no label dos dados referentes ao servico selecionado
	    public void selecionarItemTableViewServicos(Servico servico) {
	        if (servico != null) {
	            labelCodigo.setText(String.valueOf(servico.getCdServico()));
	            labelNome.setText(servico.getNome());
	            labelDescricao.setText(servico.getDescricao());
	            labelPreco.setText(String.valueOf(servico.getPreco()));
	           
	        } else {
	            labelCodigo.setText("");
	            labelNome.setText("");
	            labelDescricao.setText("");
	            labelPreco.setText("");
	        
	        }
	    }
	    
	    
	    @FXML
	    public void handleButtonInserir() throws IOException {
	      
	    	Servico servico = new Servico();
	        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosservicosDialog(servico);
	        if (buttonConfirmarClicked) {
	            servicoDAO.inserir(servico);
	            carregarTableViewServicos();
	        }
	    
	    }

	    @FXML
	    public void handleButtonAlterar() throws IOException {
	      
	    	Servico servico = tableViewServicos.getSelectionModel().getSelectedItem();//Obtendo servico selecionado
	        if (servico != null) {
	            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosservicosDialog(servico);
	            if (buttonConfirmarClicked) {
	                servicoDAO.alterar(servico);
	                carregarTableViewServicos();
	            }
	        } else {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setContentText("Por favor, escolha um servico na Tabela!");
	            alert.show();
	        }
	   
	    }

	    @FXML
	    public void handleButtonRemover() throws IOException {
	   
	    	Servico servico = tableViewServicos.getSelectionModel().getSelectedItem();
	        if (servico != null) {
	            servicoDAO.remover(servico);
	            carregarTableViewServicos();
	        } else {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setContentText("Por favor, escolha um servico na Tabela!");
	            alert.show();
	        }
	 
	    }
	    public boolean showFXMLAnchorPaneCadastrosservicosDialog(Servico servico) throws IOException {
	      
	    	//Chama a tela de inserÁ„o de um novo servico
	    	FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(ServicoInserirController.class.getResource("/javafxmvc/view/ServicoInserir.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Criando um Est·gio de di·logo(Stage Dialog)
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Cadastro de servicos");
	        //Especifica a modalidade para esta fase . Isso deve ser feito antes de fazer o est√°gio vis√≠vel. A modalidade pode ser: Modality.NONE , Modality.WINDOW_MODAL , ou Modality.APPLICATION_MODAL 
	        //dialogStage.initModality(Modality.WINDOW_MODAL);//WINDOW_MODAL (possibilita minimizar)
	        
	        //Especifica a janela do propriet√°rio para esta p√°gina, ou null para um n√≠vel superior.
	        //dialogStage.initOwner(null); //null deixa a Tela Principal livre para ser movida
	        //dialogStage.initOwner(this.tableViewservicos.getScene().getWindow()); //deixa a tela de Preenchimento dos dados como priorit√°ria
	        
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Setando o servico no Controller.
	        ServicoInserirController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setServico(servico);

	        // Mostra o Dialog e espera atÈ o usu·rio fechar
	        dialogStage.showAndWait();

	        return controller.isButtonConfirmarClicked();


	    }
	    
	    
}