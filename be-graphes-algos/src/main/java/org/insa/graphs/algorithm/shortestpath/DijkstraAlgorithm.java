package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm{

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected void insert(BinaryHeap<Label> Tas,Node node,boolean marque,float cout,Arc arc_pere, float val_estime) {
    	Label lab = new Label(node,marque,cout,arc_pere);
    	Tas.insert(lab);
    	Label.tab_label[node.getId()]=lab;
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Label.tab_label=new Label[data.getGraph().size()];
        BinaryHeap<Label> Tas=new BinaryHeap<Label>();
     
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        // Initialize array of predecessors.
        Arc[] predecessorArcs = new Arc[data.getGraph().size()];
        
        //initialisation
        for(Node node : data.getGraph().getNodes()) {
        	Label.tab_label[node.getId()]=new Label(node,false,Float.MAX_VALUE,null);
        	//insert(Tas,node,Label.tab_label[node.getId()].marque,Label.tab_label[node.getId()].cout,Label.tab_label[node.getId()].arc_pere,(float) 0.0);
        	
        }
        Label Origin = Label.tab_label[data.getOrigin().getId()];
        Origin.setCost(0);
        Label.tab_label[data.getOrigin().getId()].cout=0;
        Label x;
        //Tas.insert(Origin);
        insert(Tas,Origin.sommet_courant,Label.tab_label[Origin.sommet_courant.getId()].marque,Label.tab_label[Origin.sommet_courant.getId()].cout,Label.tab_label[Origin.sommet_courant.getId()].arc_pere,(float) 0.0);
        boolean Trouve=false;        
        //récupérer ce qu'on veut avec : data.getDestination()
        //int nb_succ = 0;
        //Iterations
	    while(Trouve==false && !Tas.isEmpty()) {
	        	//extract min
	        	x=Tas.deleteMin();
	        	Label.tab_label[x.sommet_courant.getId()].marque=true;
	        	notifyNodeMarked(x.sommet_courant);
	        	//System.out.print(Tas.isValid());
	        	//System.out.print(x.cout + "\n");
	        	//System.out.println(Tas.isValid());
	        	if (Label.tab_label[data.getDestination().getId()].marque==true) {
	        		Trouve=true;
	        	}
	        	for(Arc arc : Label.tab_label[x.sommet_courant.getId()].sommet_courant.getSuccessors()) {
	        		//nb_succ=nb_succ+1;
	        		if(data.isAllowed(arc)) {
	        			notifyNodeReached(arc.getDestination());
		        		if(!Label.tab_label[arc.getDestination().getId()].marque) {
		        			if (Label.tab_label[arc.getDestination().getId()].cout > (Label.tab_label[x.sommet_courant.getId()].cout+arc.getLength())) {
		        				//arc.getDestination()==y
			        			if(Label.tab_label[arc.getDestination().getId()].cout!=Float.MAX_VALUE) {//déjà dans le tas
			        				//Update(y,Tas)
			        				Tas.remove(Label.tab_label[arc.getDestination().getId()]);
			        			}
			        			//System.out.print(arc.getDestination().getNumberOfSuccessors() + " ");
			        			Label.tab_label[arc.getDestination().getId()].cout = Label.tab_label[x.sommet_courant.getId()].cout+arc.getLength();
			        			//Tas.insert(Label.tab_label[arc.getDestination().getId()]);
			        			insert(Tas,arc.getDestination(),Label.tab_label[arc.getDestination().getId()].marque,Label.tab_label[arc.getDestination().getId()].cout,Label.tab_label[arc.getDestination().getId()].arc_pere,(float)arc.getDestination().getPoint().distanceTo(data.getDestination().getPoint()));
			        			predecessorArcs[arc.getDestination().getId()] = arc;
		        			}
		        		}
	        		}
	        	}
	        	//System.out.print(nb_succ +"\n");
	        	//nb_succ=0;
	    }
        // Create the final solution.
	 
	    // Destination has no predecessor, the solution is infeasible...
        if (predecessorArcs[data.getDestination().getId()] == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
        	// The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());
            
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