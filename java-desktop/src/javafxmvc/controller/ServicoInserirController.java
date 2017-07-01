package javafxmvc.controller;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.dao.ServicoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Servico;

public class ServicoInserirController implements Initializable {

    @FXML
    private Label labelServicoNome;
    @FXML
    private Label labelServicoPreco;
    @FXML
    private Label labelServicoDescricao;
    @FXML
    private TextField textFieldServicoNome;
    @FXML
    private TextArea textFieldServicoDescricao;
    @FXML
    private TextField textFieldServicoPreco;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Servico Servico;
    
    //Atributos para acesso ao Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ServicoDAO servicoDAO = new ServicoDAO();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Servico getServico() {
        return this.Servico;
    }

    public void setServico(Servico Servico) {
        this.Servico = Servico;
        this.textFieldServicoNome.setText(Servico.getNome());
        this.textFieldServicoPreco.setText(String.valueOf(Servico.getPreco()));
        this.textFieldServicoDescricao.setText(Servico.getDescricao());

    }
    

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            Servico.setNome(textFieldServicoNome.getText());
            Servico.setPreco(Double.valueOf(textFieldServicoPreco.getText()));
            Servico.setDescricao(textFieldServicoDescricao.getText());     
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

        if (textFieldServicoNome.getText() == null || textFieldServicoNome.getText().length() == 0) {
            errorMessage += "Nome do servico inválido!\n";
        }
      
        if (textFieldServicoDescricao.getText() == null || textFieldServicoDescricao.getText().length() == 0) {
            errorMessage += "Descricção inválida!\n";
        }
      
        
        if (textFieldServicoPreco.getText() == null || textFieldServicoPreco.getText().length() == 0 || textFieldServicoPreco.getText().matches("[a-z A-Z]")) {
            errorMessage += "Preencha o preço corretamente!\n";
        }
        

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

      public boolean validacao(){
         
    	 if(textFieldServicoPreco.getText().matches("[a-z A-Z]")){
    		  Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Erro no cadastro");
              alert.setHeaderText("Campos inválidos, por favor, corrija...");
              alert.setContentText("Só é aceita valores númericos");
              textFieldServicoPreco.setText("");
              alert.show();
              return false;
    	 }
    	 else return true;
      }
    
    
}
