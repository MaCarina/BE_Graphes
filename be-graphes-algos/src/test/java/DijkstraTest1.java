import static org.junit.Assert.*;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.algorithm.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import org.insa.graphs.model.*;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Test;

public class DijkstraTest1 {

	private static Path oneNodePath;
    @Test
    public void testCheminSimple() throws Exception {
    	
    	String nom_carte = "C:/Users/Carin/BE_Graphes/Maps/carre.mapgr";
    	GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(nom_carte))));
    	Graph graph = reader.read();
    	
    	float cout = 0;
    	for (int i=5;i<9;i++) {
    		for (Arc arc : graph.getNodes().get(i).getSuccessors() ) {
    			if(arc.getDestination() == graph.getNodes().get(i+1)) {
    				cout = cout + arc.getLength();
    				break;
    			}
    		}
    	}
    	Node origine = graph.getNodes().get(5);
    	Node destination = graph.getNodes().get(9);
    	ShortestPathData Data = new ShortestPathData(graph,origine,destination,ArcInspectorFactory.getAllFilters().get(0));
        DijkstraAlgorithm d = new DijkstraAlgorithm(Data);
        ShortestPathSolution sd = d.doRun();
    	
        assertTrue(Math.abs(cout-sd.getPath().getLength())<0.01);
    }
    
    @Test
	 public void testOneNode() throws Exception {
		
		String nom_carte = "C:/Users/Carin/BE_Graphes/Maps/carre.mapgr";
		BinaryGraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(nom_carte))));
    	Graph graph = reader.read();
    	
    	Node node = graph.getNodes().get(0);
    	oneNodePath = new Path(graph,node);
    	ShortestPathData Data = new ShortestPathData(graph,node,node,ArcInspectorFactory.getAllFilters().get(0));
        DijkstraAlgorithm d = new DijkstraAlgorithm(Data);
        ShortestPathSolution sd = d.doRun();
	    assertTrue(Math.abs(oneNodePath.getLength()-sd.getPath().getLength())<0.01);
	}
	
    @Test
    public void testMediumPath() throws Exception{
    	
    	String nom_carte = "C:/Users/Carin/BE_Graphes/Maps/haute-garonne.mapgr";
		BinaryGraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(nom_carte))));
    	Graph graph = reader.read();
    	
    	Node origine = graph.getNodes().get(105411);
    	Node destination = graph.getNodes().get(157779);
    	ShortestPathData Data = new ShortestPathData(graph,origine,destination,ArcInspectorFactory.getAllFilters().get(0));
        DijkstraAlgorithm d = new DijkstraAlgorithm(Data);
        ShortestPathSolution sd = d.doRun();
        BellmanFordAlgorithm b = new BellmanFordAlgorithm(Data);
        ShortestPathSolution sb = b.doRun();
	    assertTrue(Math.abs(sb.getPath().getLength()-sd.getPath().getLength())<0.01);
    }

    @Test
    public void testConnexite() throws Exception{
    	
    	String nom_carte = "C:/Users/Carin/BE_Graphes/Maps/french-polynesia.mapgr";
		BinaryGraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(nom_carte))));
    	Graph graph = reader.read();
    	
    	Node origine = graph.getNodes().get(6564);
    	Node destination = graph.getNodes().get(3638);
    	ShortestPathData Data = new ShortestPathData(graph,origine,destination,ArcInspectorFactory.getAllFilters().get(0));
        DijkstraAlgorithm d = new DijkstraAlgorithm(Data);
        ShortestPathSolution sd = d.doRun();
    	assertEquals(sd.getStatus(),AbstractSolution.Status.INFEASIBLE);
    }
}
