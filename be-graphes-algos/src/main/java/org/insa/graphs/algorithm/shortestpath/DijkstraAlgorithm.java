package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Label[] tab_label=new Label[data.getGraph().size()];
        BinaryHeap<Label> Tas=new BinaryHeap<Label>();
     
        // Initialize array of predecessors.
        Arc[] predecessorArcs = new Arc[data.getGraph().size()];
        
        //initialisation
        for(Node node : data.getGraph().getNodes()) {
        	tab_label[node.getId()]=new Label(node,false,Float.MAX_VALUE,null);
        }
        tab_label[data.getOrigin().getId()].cout=0;
        Label x;
        boolean Trouve=false;        
        //récupérer ce qu'on veut avec : data.getDestination()
        
        //Iterations
	    while(Trouve==false && !Tas.isEmpty()) {
	        	//extract min
	        	x=Tas.deleteMin();
	        	tab_label[x.sommet_courant.getId()].marque=true;
	        	if (tab_label[data.getDestination().getId()].marque==true) {
	        		Trouve=true;
	        	}
	        	for(Arc arc : tab_label[x.sommet_courant.getId()].sommet_courant.getSuccessors()) {
	        		predecessorArcs[arc.getDestination().getId()] = arc;
	        		if(!tab_label[arc.getDestination().getId()].marque) {
	        			if (tab_label[arc.getDestination().getId()].cout > (tab_label[x.sommet_courant.getId()].cout+arc.getLength())) {
	        				//arc.getDestination()==y
		        			if(tab_label[arc.getDestination().getId()].cout!=Float.MAX_VALUE) {//déjà dans le tas
		        				//Update(y,Tas)
		        				Tas.remove(tab_label[arc.getDestination().getId()]);
		        			}
		        			tab_label[arc.getDestination().getId()].cout = tab_label[x.sommet_courant.getId()].cout+arc.getLength();
		        			Tas.insert(tab_label[arc.getDestination().getId()]);
	        			}
	        		}
	        	}
	    }
        // Create the final solution.
	    //pb solution
	 
	    // Destination has no predecessor, the solution is infeasible...
        if (predecessorArcs[data.getDestination().getId()] == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = predecessorArcs[data.getDestination().getId()];
            while (arc != null) {
                arcs.add(arc);
                arc = predecessorArcs[arc.getOrigin().getId()];
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), arcs));
        }
	    
	    
	    return solution;
    }

}
