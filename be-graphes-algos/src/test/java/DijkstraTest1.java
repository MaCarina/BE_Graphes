import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.algorithm.utils.BufferedInputStream;
import org.insa.graphs.algorithm.utils.DataInputStream;
import org.insa.graphs.algorithm.utils.FileInputStream;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Test;

public class DijkstraTest1 {

	// Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;
    
    private static Node origine;
    private static Node destination;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;

    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        // Create nodes
        nodes = new Node[5];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 15, speed10, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 15, speed20, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);
        e2d = Node.linkNodes(nodes[4], nodes[0], 10, speed10, null);

        graph = new Graph("ID", "", Arrays.asList(nodes), null);

    }
    
    public void testCheminValide(String nom_carte) {
    	
    	GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(nom_carte))));
    	Graph graph = reader.read();
    	
    	int origine;
    	int destination;
    	final ShortestPathData Data = getInputData();
        DijkstraAlgorithm d = new DijkstraAlgorithm(Data);
    	
        assertEquals(carte,TIME,origine,destination);//scenario1
        assertEquals(carte,LENGTH,origine,destination);//scenario2
    }
	/*
	public void testComparaisonPath() {
		ShortestPathSolution expected;
		expected = DijkstraAlgorithm;
		assertEquals(expected[i], path.getArcs().get(i));
		
	}
	*/
	
	public void testComparaisonBellman(String nom_carte) {
		
		BinaryGraphReader reader = new BinaryGraphReader(new DataInputStream());
    	Graph graph = reader.read();
    	
    	int origine;
    	int destination;
    	final ShortestPathData Data = graph.getInputData();
        DijkstraAlgorithm d = new DijkstraAlgorithm(Data);
        BellmanFordAlgorithm b = new BellmanFordAlgorithm(Data);
        ShortestPathSolution sd = d.doRun();
        ShortestPathSolution sb = b.doRun();
        
        for (int i=0;i<;i++) {
	        if (sd.compareTo(sb)==true) {
	        	assertEquals();
	        	System.out.print("D et B donnent le même résultat \n");
	        }
	        else {
	        	assertEquals();
	        	System.out.print("D et B ne donnent pas le même résultat \n");
	        }
        }
	}
	

}
