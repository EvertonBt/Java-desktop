package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafxmvc.model.dao.ConsultaDAO;
import javafxmvc.model.dao.Procedimento;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Servico;

//Essa classe trabalha em conjunto com a SceneLoginApp
//Initializable é uma interface do JavaFX para gerenciamento de componentes
//e suas propriedades e ações
public class GraficoServicosController implements Initializable{

	@FXML
	private AnchorPane panelPrincipal;
	@FXML
	private ComboBox comboBoxAno;
	private PieChart graficoPizza;
	private List<Servico> listServicos;
	
	 //Atributos necessários para obter a conexão com o banco e acessar seus dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ConsultaDAO  consultaDAO = new ConsultaDAO();
   
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 this.consultaDAO.setConnection(connection);
		
		List<Integer> anos = new ArrayList<Integer>();
		for (int i = 2015; i < 2050; i++){
		   
			anos.add(i);
		}
		
		ObservableList observableListAnos = FXCollections.observableArrayList(anos);
		comboBoxAno.setItems(observableListAnos);
		comboBoxAno.getSelectionModel().selectFirst();
		carregaGrafico();
		
		
		
	}
 public void carregaGrafico(){
		this.panelPrincipal.getChildren().remove(graficoPizza);
	  
	   //Pega o ano selecionado
	    int ano =  (int)comboBoxAno.getSelectionModel().getSelectedItem();
	 	//Faz consulta no banco de acordo com o ano selecionado
	    List<Procedimento> procedimentos = this.consultaDAO.listarProcedimentosPorAno(ano);
	    this.graficoPizza = new PieChart();
	    graficoPizza.setTitle("Preferências");
	    graficoPizza.setLayoutX(250);
	    graficoPizza.setLayoutY(10);
	 	graficoPizza.setPrefSize(300, 350);
		for(Procedimento procedimento : procedimentos){
	 
			graficoPizza.getData().addAll (new PieChart.Data(procedimento.getNome(), procedimento.getTotal()));
		
		}
	
	
		this.panelPrincipal.getChildren().add(graficoPizza);

 }
		
	
/*outra forma de fazer um comboBox diretamente na tela (.fxml)

<ComboBox>
    <items>
        <FXCollections fx:factory="observableArrayList">
            <String fx:value="NVT" />
            <String fx:value="Bezig" />
            <String fx:value="Positief" />
            <String fx:value="Negatief" />
        </FXCollections>
    </items>
    <value>
        <String fx:value="NVT" />
    </value>
</ComboBox>
		
	 */
		

	//Método que fecha a aplicação ao clicar no botão sair
	private void fecharAplicacao() {
		System.exit(0);
		}
	
	


}
