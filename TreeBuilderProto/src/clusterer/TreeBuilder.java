package clusterer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public final class TreeBuilder {
	
	private static final int NUM_OF_USERS = 15;
	private static final int NUM_OF_MOVIES = 10;

	Double[][] randomMatrix = new Double[NUM_OF_MOVIES][NUM_OF_USERS];
	
	private Set<UserNode> userNodes = new HashSet<UserNode>();	
	private Set<MovieNode> movieNodes = new HashSet<MovieNode>();
	
	
	public TreeBuilder() {
		initRandomMatrix();
		initNodes();
	}
	
	private void initRandomMatrix() {
	    Random randomGenerator = new Random();
		for (int i = 0; i < randomMatrix.length; i++) {
			for (int j = 0; j < randomMatrix[0].length; j++) {
				// high chance for null values
				if (randomGenerator.nextInt() % 4 == 0) {
					// we want to have rating values in the interval [0.0, 1.0] 
					// rounded to one decimal digit.
					randomMatrix[i][j] = ((double)randomGenerator.nextInt(11) / 10.0);

				} else {
					randomMatrix[i][j] = null;
				}
			}
		}
	}
	
	public void printRandomMatrix() {
		for (int i = 0; i < randomMatrix.length; i++) {
			for (int j = 0; j < randomMatrix[0].length; j++) {
				System.out.print(randomMatrix[i][j] +  "\t| ");
			}
			System.out.println();
		}
	}
	
	private void initNodes() {
		// create UserNode objects
		List<UserNode> users = new ArrayList<UserNode>();
		for (int i = 0; i < randomMatrix[0].length; i++) {
			users.add(new UserNode());
		}
		
		// create MovieNode objects
		List<MovieNode> movies = new ArrayList<MovieNode>();
		for (int i = 0; i < randomMatrix.length; i++) {
			movies.add(new MovieNode());
		}
		
		// add movieNodes to userNodes
		for (int i = 0; i < randomMatrix[0].length; i++) {
			Map<MovieNode, Double> attributes = new HashMap<MovieNode, Double>();
			for (int j = 0; j < randomMatrix.length; j++) {
				if (randomMatrix[j][i] != null) {
					attributes.put(movies.get(j), randomMatrix[j][i]);
				}
			}
			users.get(i).setMovies(attributes);
			userNodes.add(users.get(i));
		}
		
		// add userNodes to movieNodes
		for (int i = 0; i < randomMatrix.length; i++) {
			Map<UserNode, Double> attributes = new HashMap<UserNode, Double>();
			for (int j = 0; j < randomMatrix[0].length; j++) {
				if (randomMatrix[i][j] != null) {
					attributes.put(users.get(j), randomMatrix[i][j]);
				}
			}
			movies.get(i).setUsers(attributes);
			movieNodes.add(movies.get(i));
		}
	}
	
	private Node[] getClosestNodes(Set<? extends Node> openNodes) {
		double closesDistance = Double.MAX_VALUE;
		Node[] closesNodes = new Node[2];
		Set<Node> subSet = new HashSet<Node>(openNodes);
		for (Node node : openNodes) {
			subSet.remove(node);
			for (Node node2 : subSet) {
				double tmpDi = node.getDistance(node2);
				if (tmpDi < closesDistance){
					closesDistance = tmpDi;
					closesNodes[0] = node;
					closesNodes[1] = node2;
				}
				System.out.println("calculation: "+node+", "+node2+" ("+tmpDi+")");
			}
		}
		System.out.println("Closest nodes: "+closesNodes[0]+", "+closesNodes[1]+" ("+closesDistance+")");
		return closesNodes;
	}
	
	private void printAllNodesInSet(Set<? extends PrintableNode> set, String nodeNames){
		System.out.println("-----------------------");
		System.out.println(nodeNames);
		for (PrintableNode node : set) {
			System.out.println(node.toString()+"|\t"+node.getAttributesString());
		}
		System.out.println("-----------------------");
	}
	
	public void printAllOpenUserNodes() {
		printAllNodesInSet(userNodes, "User Nodes:");
	}
	
	public void printAllOpenMovieNodes() {
		printAllNodesInSet(movieNodes, "MovieNodes:");
	}
	
	public Node[] getClosestOpenUserNodes() {
		return getClosestNodes(userNodes);
	}
	
	public Node[] getClosestOpenMovieNodes() {
		return getClosestNodes(movieNodes);
	}
	
		
}