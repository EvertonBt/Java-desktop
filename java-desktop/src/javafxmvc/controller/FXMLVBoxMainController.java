package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class FXMLVBoxMainController implements Initializable {

    @FXML
    private MenuItem menuItemCadastrosClientes;
    
    @FXML
    private MenuItem menuItemProdutos;
    
    @FXML
    private MenuItem menuItemServicos;
    
    @FXML
    private MenuItem menuItemFinanceiro;
    
    @FXML
    private MenuItem menuItemPersonalizar;
    
    @FXML
    private MenuItem menuItemProcessosVendas;
    
    @FXML
    private MenuItem menuItemConsultas; 
    
    @FXML
    private MenuItem menuItemGraficosVendasPorMes;
    
    @FXML
    private MenuItem menuItemRelatoriosQuantidadeProdutos;

    
    
    @FXML
    private AnchorPane anchorPane;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    //invoca tela de listagem de clientes
    @FXML
    public void handleMenuItemCadastrosClientes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosClientes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    //invoca tela de listagem de produtos
    @FXML
    public void handleMenuItemCadastrosProdutos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/ProdutoListagem.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    
    //invoca tela de listagem de Serviços
    @FXML
    public void handleMenuItemCadastrosServicos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/ServicoListagem.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    //invoca tela de listagem de Consultas
    @FXML
    public void handleMenuItemCadastrosConsultas() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/ConsultaListagem.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    
    //Exibe dados gráficos da quantidade de vendas por mês
    @FXML
    public void handleMenuItemProcessosVendas() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneProcessosVendas.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    //Exibe dados gráficos da quantidade de Consultas por mês
    @FXML
    public void handleMenuConsultasPorMes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/ConsultasPorMes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    //Exibe dados gráficos da quantidade de Serviços
    @FXML
    public void handleMenuGraficoServicos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/GraficoServicos.fxml"));
        anchorPane.getChildren().setAll(a);
    }
 
    @FXML
    public void handleMenuItemGraficosVendasPorMes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneGraficosVendasPorMes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    //Criando relatórios
    
    @FXML
    public void handleMenuItemRelatoriosQuantidadeProdutos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneRelatoriosQuantidadeProdutos.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    
    @FXML
    public void handleMenuItemRelatorioServicos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/RelatorioServicos.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuSair() throws IOException {
       System.exit(0);
    }

    
    
}
