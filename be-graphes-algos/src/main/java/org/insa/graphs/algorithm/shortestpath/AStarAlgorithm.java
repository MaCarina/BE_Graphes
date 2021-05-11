package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;


public class AStarAlgorithm extends DijkstraAlgorithm {

	@Override
    protected void insert(BinaryHeap<Label> Tas,Node node,boolean marque,float cout,Arc arc_pere,float val_estime) {
    	LabelStar lab = new LabelStar(node,marque,cout,arc_pere,val_estime);
    	Tas.insert(lab);
    	Label.tab_label[node.getId()]=lab;
    }
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    
}
