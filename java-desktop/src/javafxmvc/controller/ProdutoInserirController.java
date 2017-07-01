package javafxmvc.controller;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.dao.CategoriaDAO;
import javafxmvc.model.dao.ClienteDAO;
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Categoria;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.Produto;

public class ProdutoInserirController implements Initializable {

    @FXML
    private Label labelProdutoNome;
    @FXML
    private Label labelProdutoCategoria;
    @FXML
    private Label labelProdutoPreco;
    @FXML
    private Label labelProdutoQuantidade;
    @FXML
    private TextField textFieldProdutoNome;
    @FXML
    private ComboBox comboProdutoCategoria;
    @FXML
    private TextField textFieldProdutoPreco;
    @FXML
    private TextField textFieldProdutoQuantidade;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Produto Produto;
    private List<Categoria> listCategorias;
    private ObservableList<Categoria> observableListCategorias;
    
    //Atributos para acesso ao Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    	   categoriaDAO.setConnection(connection);
           carregarComboBoxCategorias();
    	
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Produto getProduto() {
        return this.Produto;
    }

    public void setProduto(Produto Produto) {
        this.Produto = Produto;
 //     this.textFieldProdutoCategoria.setText(Produto.getCategoria());
        this.textFieldProdutoNome.setText(Produto.getNome());
        this.textFieldProdutoPreco.setText(String.valueOf(Produto.getPreco()));
        this.textFieldProdutoQuantidade.setText(String.valueOf(Produto.getQuantidade()));

    }
    
    //Populando o combo box com a lista de categorias
    public void carregarComboBoxCategorias() {

        listCategorias = categoriaDAO.listar();
        observableListCategorias = FXCollections.observableArrayList(listCategorias);
        comboProdutoCategoria.setItems(observableListCategorias);

       
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            Produto.setNome(textFieldProdutoNome.getText());
            Produto.setCategoria((Categoria) comboProdutoCategoria.getSelectionModel().getSelectedItem());
            Produto.setPreco(Double.valueOf(textFieldProdutoPreco.getText()));
            Produto.setQuantidade(Integer.valueOf(textFieldProdutoQuantidade.getText()));     
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    
    
    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }

   
    
    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldProdutoNome.getText() == null || textFieldProdutoNome.getText().length() == 0) {
            errorMessage += "Nome do produto inválido!\n";
        }
      
        if (comboProdutoCategoria.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Escolha uma categoria!\n";
        }
        
        
        if (textFieldProdutoPreco.getText() == null || textFieldProdutoPreco.getText().length() == 0) {
            errorMessage += "Preencha o preço corretamente!\n";
        }
        
        if (textFieldProdutoQuantidade.getText() == null || textFieldProdutoQuantidade.getText().length() == 0) {
            errorMessage += "Preencha a quantidade corretamente!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos invÃ¡lidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

}

