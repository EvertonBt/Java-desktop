package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxmvc.model.dao.ItemDeConsultaDAO;
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.dao.ConsultaDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.ItemDeConsulta;
import javafxmvc.model.domain.Produto;
import javafxmvc.model.domain.Consulta;

public class ConsultaListagemController implements Initializable {

    @FXML
    private TableView<Consulta> tableViewConsultas;
    @FXML
    private TableColumn<Consulta, Integer> tableColumnConsultaCodigo;
    @FXML
    private TableColumn<Consulta, Date> tableColumnConsultaData;
    @FXML
    private TableColumn<Consulta, Consulta> tableColumnConsultaCliente;
    @FXML
    private TableColumn<Consulta, Double> tableColumnConsultaValor;
    @FXML
    private TableColumn  tableColumnConsultaPago;
    @FXML
    private DatePicker dataPesquisa;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelConsultaCodigo;
    @FXML
    private Label labelConsultaData;
    @FXML
    private Label labelConsultaValor;
    @FXML
    private Label labelConsultaPago;
    @FXML
    private Label labelConsultaCliente;

    private List<Consulta> listConsultas;
    private ObservableList<Consulta> observableListConsultas;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ConsultaDAO consultaDAO = new ConsultaDAO();
    private final ItemDeConsultaDAO itemDeConsultaDAO = new ItemDeConsultaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        consultaDAO.setConnection(connection);

        carregarTableViewConsultas();

 /*
        // Limpando a exibição dos detalhes da consulta
        selecionarItemTableViewConsultas(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewConsultas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewConsultas(newValue));
  */
    }

    public void carregarTableViewConsultas() {
       
    	tableColumnConsultaCodigo.setCellValueFactory(new PropertyValueFactory<>("cdConsulta"));
        tableColumnConsultaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnConsultaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        tableColumnConsultaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tableColumnConsultaPago.setCellValueFactory(new PropertyValueFactory<>("estado"));
       
        if(dataPesquisa.getValue() == null ){
        listConsultas = consultaDAO.listar();
        }
        
        else {
        	dataPesquisa.getValue();
        	listConsultas = consultaDAO.listarPorData(dataPesquisa.getValue());
        }
        
        observableListConsultas = FXCollections.observableArrayList(listConsultas);
        tableViewConsultas.setItems(observableListConsultas);
    }
/*
    public void selecionarItemTableViewConsultas(Consulta consulta) {
        if (consulta != null) {
            labelConsultaCodigo.setText(String.valueOf(consulta.getCdConsulta()));
            labelConsultaData.setText(String.valueOf(consulta.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelConsultaValor.setText(String.format("%.2f", consulta.getValor()));
            labelConsultaPago.setText(String.valueOf(consulta.getPago()));
            labelConsultaCliente.setText(consulta.getCliente().toString());
        } else {
            labelConsultaCodigo.setText("");
            labelConsultaData.setText("");
            labelConsultaValor.setText("");
            labelConsultaPago.setText("");
            labelConsultaCliente.setText("");
        }
    }
*/
    @FXML
    public void handleButtonInserir() throws IOException {
        Consulta consulta = new Consulta();
        List<ItemDeConsulta> listItensDeConsulta = new ArrayList<>();
        consulta.setItensDeConsulta(listItensDeConsulta);
        boolean buttonConfirmarClicked = showFXMLAnchorPaneProcessosConsultasDialog(consulta);
        if (buttonConfirmarClicked) {
            try {
                connection.setAutoCommit(false);
                consultaDAO.setConnection(connection);
                consultaDAO.inserir(consulta);
                itemDeConsultaDAO.setConnection(connection);
                produtoDAO.setConnection(connection);
                for (ItemDeConsulta listItemDeConsulta : consulta.getItensDeConsulta()) {
                    Produto produto = listItemDeConsulta.getProduto();
                    listItemDeConsulta.setConsulta(consultaDAO.buscarUltimaConsulta());
                    itemDeConsultaDAO.inserir(listItemDeConsulta);
                    produto.setQuantidade(produto.getQuantidade() - listItemDeConsulta.getQuantidade());
                    produtoDAO.alterar(produto);
                }
                connection.commit();
                carregarTableViewConsultas();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(ConsultaListagemController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(ConsultaListagemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
      
     //Obtem consulta selecionada
     Consulta consulta  = tableViewConsultas.getSelectionModel().getSelectedItem();
        if (consulta != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneProcessosConsultasDialog(consulta);
   
            if (buttonConfirmarClicked) {
                try {
                    connection.setAutoCommit(false);
                    consultaDAO.setConnection(connection);
                    consultaDAO.alterar(consulta);
                    itemDeConsultaDAO.setConnection(connection);
                    produtoDAO.setConnection(connection);
                    for (ItemDeConsulta listItemDeConsulta : consulta.getItensDeConsulta()) {
                        Produto produto = listItemDeConsulta.getProduto();
                        listItemDeConsulta.setConsulta(consultaDAO.buscarUltimaConsulta());
                        produto.setQuantidade(produto.getQuantidade() - listItemDeConsulta.getQuantidade());
                        produtoDAO.alterar(produto);
                       ItemDeConsulta item =  itemDeConsultaDAO.buscar(listItemDeConsulta);	
                       System.out.println(item.getCdItemDeConsulta());
                        if(item.getCdItemDeConsulta() == 0) { 
                       
                               itemDeConsultaDAO.inserir(listItemDeConsulta);
                        }
                        
                    }
                    connection.commit();
                    carregarTableViewConsultas();
                } catch (SQLException ex) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(ConsultaListagemController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    Logger.getLogger(ConsultaListagemController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma consulta na Tabela!");
            alert.show();
        }
   
    }

    @FXML
    public void handleButtonRemover() throws IOException, SQLException {
        Consulta consulta = tableViewConsultas.getSelectionModel().getSelectedItem();
        if (consulta != null) {
            connection.setAutoCommit(false);
            consultaDAO.setConnection(connection);
            itemDeConsultaDAO.setConnection(connection);
            produtoDAO.setConnection(connection);
            for (ItemDeConsulta listItemDeConsulta : consulta.getItensDeConsulta()) {
                Produto produto = listItemDeConsulta.getProduto();
                produto.setQuantidade(produto.getQuantidade() + listItemDeConsulta.getQuantidade());
                produtoDAO.alterar(produto);
                itemDeConsultaDAO.remover(listItemDeConsulta);
            }
            consultaDAO.remover(consulta);
            connection.commit();
            carregarTableViewConsultas();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma consulta na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneProcessosConsultasDialog(Consulta consulta) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ConsultaInserirController.class.getResource("/javafxmvc/view/ConsultaInserir.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registro de Consultas");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando a Consulta no Controller.
        ConsultaInserirController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setConsulta(consulta);

        // Mostra o Dialog e espera at� que o usu�rio feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

}
