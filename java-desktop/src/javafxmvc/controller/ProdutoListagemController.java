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
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.domain.Produto;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;


public class ProdutoListagemController implements Initializable{

	  @FXML
	    private TableView<Produto> tableViewProdutos;
	    @FXML
	    private TableColumn<Produto, String> tableColumnProdutoCodigo;
	    @FXML
	    private TableColumn<Produto, String> tableColumnProdutoNome;
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
	    private Label labelCategoria;
	    @FXML
	    private Label labelPreco;
	    @FXML
	    private Label labelQuantidade;
	    
	    //Vari·veis para manipular a lista que vem do banco de dados.
	    private List<Produto> listProdutos;
	    // lista que preenche a tableview.
	    private ObservableList<Produto> observableListProdutos;
	    //Atributos necess·rios para obter a conex„o com o banco e acessar seus dados
	    private final Database database = DatabaseFactory.getDatabase("mysql");
	    private final Connection connection = database.conectar();
	    private final ProdutoDAO produtoDAO = new ProdutoDAO();
		
	    @Override
		public void initialize(URL location, ResourceBundle resources) {

	    	 //seta a conex„o no produtoDAO
	    	 produtoDAO.setConnection (connection);
	         
	    	 //MÈtodo que busca no banco a lista de produtos e converte para uma lista do tipo observablelist
	         carregarTableViewProdutos();

	         // Limpando a exibiÁ„o dos detahes do produto (isso È usado quando o usu·rio n„o escolhe nenhum produto da lista
	         selecionarItemTableViewProdutos(null);

	         // Listen acionado diante de quaisquer alteraÁ„o  na seleÁ„o de itens do TableView
	         tableViewProdutos.getSelectionModel().selectedItemProperty().addListener(
	                 (observable, oldValue, newValue) -> selecionarItemTableViewProdutos(newValue));
	    
			
		}

	    //obtem a lista do banco, converte para observable list e seta na tableview
	    public void carregarTableViewProdutos() {
	    	tableColumnProdutoCodigo.setCellValueFactory(new PropertyValueFactory<>("cdProduto"));
	    	tableColumnProdutoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

	        listProdutos = produtoDAO.listar();

	        observableListProdutos = FXCollections.observableArrayList(listProdutos);
	        tableViewProdutos.setItems(observableListProdutos);
	    }
	    
	    //A medida que o usu·rio seleciona um novo item na lista ele exibe no label dos dados referentes ao produto selecionado
	    public void selecionarItemTableViewProdutos(Produto produto) {
	        if (produto != null) {
	            labelCodigo.setText(String.valueOf(produto.getCdProduto()));
	            labelNome.setText(produto.getNome());
	            labelCategoria.setText(produto.getCategoria().getDescricao());
	            labelPreco.setText(String.valueOf(produto.getPreco()));
	            labelQuantidade.setText(String.valueOf(produto.getQuantidade()));
	           
	        } else {
	            labelCodigo.setText("");
	            labelNome.setText("");
	            labelCategoria.setText("");
	            labelPreco.setText("");
	            labelQuantidade.setText("");
	        
	        }
	    }
	    
	    
	    @FXML
	    public void handleButtonInserir() throws IOException {
	      
	    	Produto produto = new Produto();
	        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosprodutosDialog(produto);
	        if (buttonConfirmarClicked) {
	            produtoDAO.inserir(produto);
	            carregarTableViewProdutos();
	        }
	    
	    }

	    @FXML
	    public void handleButtonAlterar() throws IOException {
	      
	    	Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();//Obtendo produto selecionado
	        if (produto != null) {
	            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosprodutosDialog(produto);
	            if (buttonConfirmarClicked) {
	                produtoDAO.alterar(produto);
	                carregarTableViewProdutos();
	            }
	        } else {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setContentText("Por favor, escolha um produto na Tabela!");
	            alert.show();
	        }
	   
	    }

	    @FXML
	    public void handleButtonRemover() throws IOException {
	   
	    	Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();
	        if (produto != null) {
	            produtoDAO.remover(produto);
	            carregarTableViewProdutos();
	        } else {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setContentText("Por favor, escolha um produto na Tabela!");
	            alert.show();
	        }
	 
	    }
	    public boolean showFXMLAnchorPaneCadastrosprodutosDialog(Produto produto) throws IOException {
	      
	    	//Chama a tela de inserÁ„o de um novo produto
	    	FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(ProdutoInserirController.class.getResource("/javafxmvc/view/ProdutoInserir.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Criando um Est·gio de di·logo(Stage Dialog)
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Cadastro de produtos");
	        //Especifica a modalidade para esta fase . Isso deve ser feito antes de fazer o est√°gio vis√≠vel. A modalidade pode ser: Modality.NONE , Modality.WINDOW_MODAL , ou Modality.APPLICATION_MODAL 
	        //dialogStage.initModality(Modality.WINDOW_MODAL);//WINDOW_MODAL (possibilita minimizar)
	        
	        //Especifica a janela do propriet√°rio para esta p√°gina, ou null para um n√≠vel superior.
	        //dialogStage.initOwner(null); //null deixa a Tela Principal livre para ser movida
	        //dialogStage.initOwner(this.tableViewprodutos.getScene().getWindow()); //deixa a tela de Preenchimento dos dados como priorit√°ria
	        
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Setando o produto no Controller.
	        ProdutoInserirController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setProduto(produto);

	        // Mostra o Dialog e espera atÈ o usu·rio fechar
	        dialogStage.showAndWait();

	        return controller.isButtonConfirmarClicked();


	    }
	    
	    
}
	    
