package it.polito.tdp.genes.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private GenesDao dao;
	private Graph<Integer, DefaultWeightedEdge> graph;
	private List<Integer> best;
	
	public Model() {
		this.dao = new GenesDao();
	}
	
	public List<Integer> calcolaPercorso(double s){
		best = null;
		List<Integer> parziale = new LinkedList<>();
		cerca(0, parziale, s);
		return best;
	}
	
	private void cerca(int livello, List<Integer> parziale, double s) {	
//		Condizione di terminazione
		if(condizioneDiTerminazione(parziale, s)) {
//			E' la soluzione migliore?
			double lunghezza = calcolaLunghezza(parziale);
			if(best == null || calcolaLunghezza(best) < lunghezza)
				best = new LinkedList<>(parziale);
			return;
		}
		
//		Scorro i vicini dell'ultimo inserito e provo le varie strade
		for(Integer v : this.graph.vertexSet()) {
			if(aggiuntaValida(v, parziale, s)) {
				parziale.add(v);
				cerca(livello + 1, parziale, s);
				parziale.remove(parziale.size()-1);
			}
		}	
	}
	
	private boolean aggiuntaValida(Integer v, List<Integer> parziale, double s) {
		if(parziale.size() == 0)
			return true;
		else {
			int ultimoVertice = parziale.get(parziale.size()-1);
			if(this.graph.containsEdge(ultimoVertice, v) && this.graph.getEdgeWeight(this.graph.getEdge(ultimoVertice, v)) > s && !parziale.contains(v))
				return true;
		}
		return false;
	}

	private double calcolaLunghezza(List<Integer> parziale) {
		double l = 0;
		for(int i = 0; i < parziale.size()-1; i++)
			l += this.graph.getEdgeWeight(this.graph.getEdge(parziale.get(i), parziale.get(i+1)));
		return l;
	}

	private boolean condizioneDiTerminazione(List<Integer> parziale, double s) {
		if(parziale.size() > 0) {
//			int ultimoVertice = parziale.get(parziale.size()-1);
			int conta = 0;	//conta i vertici non inseriti
			for(int v : this.graph.vertexSet())
				if(!aggiuntaValida(v, parziale, s))
					conta++;
			
			if(conta == this.graph.vertexSet().size())
				return true;
		}
		return false;
	}

	public void creaGrafo() {
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.graph, this.dao.getAllChromosome());
		
		for(Archi a : dao.getArchi())
			Graphs.addEdgeWithVertices(this.graph, a.getC1(), a.getC2(), a.getPeso());
		
		System.out.println("#Vertici: " + this.graph.vertexSet().size());
		System.out.println("#Archi: " + this.graph.edgeSet().size());
	}
	
	public int getVertici() {
		return this.graph.vertexSet().size();
	}
	
	public int getNumArchi() {
		return this.graph.edgeSet().size();
	}
	
	public double calcolaPesoMin() {
		double min = 10000000;
		
		for(DefaultWeightedEdge edge : this.graph.edgeSet()) {
			if(this.graph.getEdgeWeight(edge) < min)
				min = this.graph.getEdgeWeight(edge);
		}
		
		return min;
	}
	
	public double calcolaPesoMax() {
		double max = 0;
		
		for(DefaultWeightedEdge edge : this.graph.edgeSet()) {
			if(this.graph.getEdgeWeight(edge) > max)
				max = this.graph.getEdgeWeight(edge);
		}
		
		return max;
	}
	
	public Set<DefaultWeightedEdge> getArchi(){
		return this.graph.edgeSet();
	}
	
	public Graph<Integer, DefaultWeightedEdge> getGraph(){
		return this.graph;
	}
	

}