package it.polito.tdp.genes.model;

import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Archi {
	
	public Integer c1;
	public Integer c2;
	public double peso;
	
	public Archi(Integer c1, Integer c2, double peso) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.peso = peso;
	}

	public Integer getC1() {
		return c1;
	}

	public void setC1(Integer c1) {
		this.c1 = c1;
	}

	public Integer getC2() {
		return c2;
	}

	public void setC2(Integer c2) {
		this.c2 = c2;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

}
