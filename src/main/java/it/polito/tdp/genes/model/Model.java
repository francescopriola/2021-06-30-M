package it.polito.tdp.genes.model;

//import java.util.LinkedList;
//import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private GenesDao dao;
	private Graph<Integer, DefaultWeightedEdge> graph;
//	private List<Integer> best;
	
	public Model() {
		this.dao = new GenesDao();
	}
	
//	public List<Integer> calcolaPercorso(){
//		best = new LinkedList<>();
//		List<String> parziale = new LinkedList<>();
//		parziale.add(sorgente);
//		cerca(parziale);
//		return best;
//	}
//	
//	private void cerca(List<Integer> parziale) {
////		Condizione di terminazione
//		if(parziale.get(parziale.size()-1).equals(destinazione)) {
////			E' la soluzione migliore?
//			if(parziale.size() > best.size())
//				best = new LinkedList<>(parziale);
//			return;
//		}
//		
////		Scorro i vicini dell'ultimo inserito e provo le varie strade
//		for(Integer v : Graphs.neighborListOf(this.graph, parziale.get(parziale.size()-1))) {
//			if(!parziale.contains(v)) {
//				parziale.add(v);
//				cerca(parziale, destinazione);
//				parziale.remove(parziale.size()-1);
//			}
//		}
//		
//	}
	
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