package javafxmvc;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginApp extends Application{

	//Váriáveis globais da aplicação
	private AnchorPane pane;
	private TextField txLogin;
	private PasswordField txSenha;
	private Button btEntrar, btSair;
	private Label lb;
	private static Stage stage;
	
	@Override
	public void start(Stage stage) throws Exception {
		//Inicializa os componentes
		initComponents();
		
		
		//Inicializa as actions
		initListeners();
		
		//cria uma cena que recebe o painel
		Scene scene = new Scene(pane);
		//Invoca o arquivo css login.css
		scene.getStylesheets().add("application.css");
		// Passa a cena para o stage, a cena é a tela propriamente dita
		 stage.setScene(scene);
		
		// Remove a opção de maximizar a tela
		stage.setResizable(false);
		
		// Dá um título para a tela
		stage.setTitle("Login - Lisianthus Estética");

		//Adiciona Imagem
		//stage.getIcons().add(new Image(getClass().getResourceAsStream("../estetica.jpg")));  
		
		// abre o stage
		stage.show();
		
		//Aplica as configurações de layout
        initLayout();
		
        //injeta o stage do método no stage estático da classe
		LoginApp.stage = stage;
		
	}
	

	// É preciso cria o método main, pois é o ponto de exução da aplicação
	public static void main(String[] args) {
		launch(args);
		}

	
	//Método usado para inicializar os componentes 
	public void initComponents(){
		
		       //Criando painel que conterá os componentes da tela
				pane = new AnchorPane();
				pane.setPrefSize(400, 300);
			
				//Adicionando um css pra deixar o painel mais bonito:
				//pane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, blue 0%, silver 100%);");
				//Outra forma é chamando a class pane do arquivo login.css
				pane.getStyleClass().add("pane");
				
				lb = new Label();
				
			    //Cria um campo de texto e adiciona um texto incial que some ao dar foco
				txLogin = new TextField();
				txLogin.setPromptText("Digite aqui seu login");
				
				//Cria um campo do tipo oculto (password)
				txSenha = new PasswordField();
				txSenha.setPromptText("Digite aqui sua senha");

			    //Cria dois botões, de entrar e sair:
				btEntrar = new Button("Entrar");
				btSair = new Button("Sair");
		
                //Aplicando estilo nos botões:
				btEntrar.getStyleClass().add("btEntrar");
				btSair.getStyleClass().add("btSair");
				
				
				//Agora é preciso adicionar todos os componentes para o painel:
				pane.getChildren().addAll(txLogin, txSenha, btEntrar, btSair);
				
	}
	
	//Método responsável por definir as coordenadas dos componentes:
	private void initLayout() {
		
		        //Para oragnizar os componentes é preciso usar as coordenadas
				//Para localizar o centro da tela subtrai a largura do painel(ou tela)  pela a largura do componente e divide o resultaod por dois
				// as coordenadas devem vir depois do stage.show
				
		 
		        txLogin.setLayoutX((pane.getWidth() - txLogin.getWidth()) / 2);
				txLogin.setLayoutY(50);
				
				txSenha.setLayoutX((pane.getWidth() - txSenha.getWidth()) / 2);
				txSenha.setLayoutY(100);
				
				btEntrar.setLayoutX((pane.getWidth() - btEntrar.getWidth()) / 2 -30);
				btEntrar.setLayoutY(150);
				btSair.setLayoutX((pane.getWidth() - btSair.getWidth()) / 2 +20);
				btSair.setLayoutY(150);
	
	       
	}
	
//Método que reúne as ações dos componenetes:
	private void initListeners() {
		
		// Ação do botão sair
		btSair.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
		fecharAplicacao();
		}
		});
		
		
		//Ação do botão entrar:
		btEntrar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			logar();
			}
			});
	}
	
	
	//Método que fecha a aplicação ao clicar no botão sair
	private void fecharAplicacao() {
		System.exit(0);
		}
	
	
	//Método executado ao pressionar o botão entrar, ele verifica as credenciase e se corretas chama a próxima tela
	private void logar() {
		if (txLogin.getText().equals("everton") &&
		txSenha.getText().equals("123")) {
		try {
		
			  Stage stage = new Stage();
		      Parent root = FXMLLoader.load(getClass().getResource("view/FXMLVBoxMain.fxml"));
		        
		        Scene scene = new Scene(root);
		        
		        stage.setScene(scene);
		        stage.setTitle("Sistema de Vendas (JavaFX MVC)");
		        stage.setResizable(false);
		        stage.show();
		
		
		LoginApp.getStage().close();
		} catch (Exception e) {
		e.printStackTrace();
		}
		} else {
		JOptionPane.showMessageDialog(null, "Login e/ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		}
	
	
	public static Stage getStage(){
		
		return stage;
	}


}
