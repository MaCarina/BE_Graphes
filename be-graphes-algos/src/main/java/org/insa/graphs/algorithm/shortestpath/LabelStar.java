package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label {
	
	private float val_estime;
	
	public LabelStar(Node node) {
		super(node);
	}
	
	public LabelStar(Node node,boolean marque,float cout, Arc arc_pere,float val_estime) {
		super(node,marque,cout,arc_pere);
		this.val_estime=val_estime;
	}
	
	public float getTotalCost() {
		return this.val_estime+this.cout;
	}
	

}
