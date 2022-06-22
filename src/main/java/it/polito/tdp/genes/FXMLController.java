/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model ;
	private int maggiori;
	private int minori;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnContaArchi"
    private Button btnContaArchi; // Value injected by FXMLLoader

    @FXML // fx:id="btnRicerca"
    private Button btnRicerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtSoglia"
    private TextField txtSoglia; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doContaArchi(ActionEvent event) {
    	maggiori = 0;
    	minori = 0;
    	String soglia = this.txtSoglia.getText();
    	
    	try { 
    		double s = Integer.parseInt(soglia);
    		
    		if(s < model.calcolaPesoMin() || s > model.calcolaPesoMax()) {
    			txtResult.appendText("Soglia: " + s + "--> Inserisci un valore compreso tra il peso minimo e il massimo!\n");
    			return;
    		}
    		
    		for(DefaultWeightedEdge edge : this.model.getArchi()) {
    			if(this.model.getGraph().getEdgeWeight(edge) > s)
    				maggiori++;
    			else if(this.model.getGraph().getEdgeWeight(edge) < s)
    				minori++;
    		}
    		
    		txtResult.appendText("Soglia: " + s + "--> Maggiori " + this.maggiori + ", Minori " + this.minori + "\n");
			
		} catch (NumberFormatException e) {
			txtResult.appendText("Inserisci una soglia numerica!\n");
			return;
		}

    }

    @FXML
    void doRicerca(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnContaArchi != null : "fx:id=\"btnContaArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSoglia != null : "fx:id=\"txtSoglia\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model ;
		
		model.creaGrafo();
		txtResult.appendText("GRAFO CREATO: " + model.getVertici() + " vertici, " + model.getNumArchi() + " archi.\n");
		txtResult.appendText("Peso minimo = " + model.calcolaPesoMin() + "; Peso massimo = " + model.calcolaPesoMax() + "\n");
	}
}
