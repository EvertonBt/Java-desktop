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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxmvc.model.dao.ClienteDAO;
import javafxmvc.model.dao.ItemDeConsultaDAO;
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.ItemDeConsulta;
import javafxmvc.model.domain.Produto;
import javafxmvc.model.domain.Consulta;

public class ConsultaInserirController implements Initializable {

    @FXML
    private ComboBox comboBoxConsultaCliente;
    @FXML
    private DatePicker datePickerConsultaData;
    @FXML
    private CheckBox checkBoxConsultaPago;
    @FXML
    private ComboBox comboBoxConsultaProduto;
    @FXML
    private TableView<ItemDeConsulta> tableViewItensDeConsulta;
    @FXML
    private TableColumn<ItemDeConsulta, Produto> tableColumnItemDeConsultaProduto;
    @FXML
    private TableColumn<ItemDeConsulta, Integer> tableColumnItemDeConsultaQuantidade;
    @FXML
    private TableColumn<ItemDeConsulta, Double> tableColumnItemDeConsultaValor;
    @FXML
    private TextField textFieldConsultaValor;
    @FXML
    private TextField textFieldConsultaItemDeConsultaQuantidade;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonAdicionar;

    private List<Cliente> listClientes;
    private List<Produto> listProdutos;
    private ObservableList<Cliente> observableListClientes;
    private ObservableList<Produto> observableListProdutos;
    private ObservableList<ItemDeConsulta> observableListItensDeConsulta;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final ItemDeConsultaDAO itemDAO = new ItemDeConsultaDAO();
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Consulta consulta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        produtoDAO.setConnection(connection);
        itemDAO.setConnection(connection);
        carregarComboBoxClientes();
        carregarComboBoxProdutos();

        tableColumnItemDeConsultaProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        tableColumnItemDeConsultaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnItemDeConsultaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

    }

    public void carregarComboBoxClientes() {

        listClientes = clienteDAO.listar();

        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxConsultaCliente.setItems(observableListClientes);

        //Como não será possível alterar uma Consulta, não precisaremos implementar a seleção do cliente (caso seja uma alteração de consulta)
    }

    public void carregarComboBoxProdutos() {

        listProdutos = produtoDAO.listar();

        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        comboBoxConsultaProduto.setItems(observableListProdutos);
    

        //Como não será possível alterar uma Consulta, não precisaremos implementar a seleção do produto (caso seja uma alteração de consulta)
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Consulta getConsulta() {
        return this.consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
        // comboBoxConsultaCliente.getSelectionModel().selectFirst();
        this.comboBoxConsultaCliente.getSelectionModel().select(consulta.getCliente());
        this.datePickerConsultaData.setValue(consulta.getData());
        this.checkBoxConsultaPago.setSelected(consulta.getPago());
        this.textFieldConsultaValor.setText(String.valueOf(consulta.getValor()));
        observableListItensDeConsulta = FXCollections.observableArrayList(this.consulta.getItensDeConsulta());
        tableViewItensDeConsulta.setItems(observableListItensDeConsulta);
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }


  
    
    @FXML
    public void handleButtonAdicionar() {
        Produto produto;
        ItemDeConsulta itemDeConsulta = new ItemDeConsulta();

        if (comboBoxConsultaProduto.getSelectionModel().getSelectedItem() != null) {
            produto = (Produto) comboBoxConsultaProduto.getSelectionModel().getSelectedItem();

            if (produto.getQuantidade() >= Integer.parseInt(textFieldConsultaItemDeConsultaQuantidade.getText())) {
                itemDeConsulta.setProduto((Produto) comboBoxConsultaProduto.getSelectionModel().getSelectedItem());
                itemDeConsulta.setQuantidade(Integer.parseInt(textFieldConsultaItemDeConsultaQuantidade.getText()));
                itemDeConsulta.setValor(itemDeConsulta.getProduto().getPreco() * itemDeConsulta.getQuantidade());

                this.consulta.getItensDeConsulta().add(itemDeConsulta);
                consulta.setValor(consulta.getValor() + itemDeConsulta.getValor());

                observableListItensDeConsulta = FXCollections.observableArrayList(consulta.getItensDeConsulta());
                tableViewItensDeConsulta.setItems(observableListItensDeConsulta);

                textFieldConsultaValor.setText(String.format("%.2f", consulta.getValor()));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Problemas na escolha do produto!");
                alert.setContentText("Não existe a quantidade de produtos disponíveis no estoque!");
                alert.show();
            }
        }
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            consulta.setCliente((Cliente) comboBoxConsultaCliente.getSelectionModel().getSelectedItem());
            consulta.setPago(checkBoxConsultaPago.isSelected());
            consulta.setData(datePickerConsultaData.getValue());
            
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

        if (comboBoxConsultaCliente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente inválido!\n";
        }
        if (datePickerConsultaData.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }
        if (observableListItensDeConsulta == null) {
            errorMessage += "Itens de Consulta inválidos!\n";
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

}
