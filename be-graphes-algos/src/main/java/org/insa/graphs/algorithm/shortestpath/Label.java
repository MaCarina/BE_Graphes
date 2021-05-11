package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>{

	public Node sommet_courant;
	
	public boolean marque;
	
	public float cout;
	
	public Node pere;
	public Arc arc_pere;
	
	public Label(Node node) {
		this.sommet_courant=node;
		this.marque=false;
		this.cout=Float.MAX_VALUE;
		this.pere=null;
		this.arc_pere=null;
	}
	
	public Label(Node node,boolean marque,float cout,Arc arc_pere) {
		this.sommet_courant=node;
		this.marque=marque;
		this.cout=cout;
		this.pere=null;
		this.arc_pere=arc_pere;
	}
	
	public float setCost(float cost) {
        this.cout=cost;
        return this.cout;
    }
	
	public float getCost() {
        return this.cout;
    }
	
	public int compareTo(Label other) {
        return Float.compare(this.getTotalCost(), other.getTotalCost());
    }
	
	public Arc getPere() {
		return this.arc_pere;
	}

	public float getTotalCost() {
		return this.cout;
	}
	
	public static Label tab_label[];
}
