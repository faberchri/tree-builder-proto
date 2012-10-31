package clusterer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class TreeBuilder {
	
	private static final int NUM_OF_USERS = 15;
	private static final int NUM_OF_MOVIES = 10;

	Double[][] randomMatrix = new Double[NUM_OF_MOVIES][NUM_OF_USERS];
	
	Set<Node> userNodes = new HashSet<Node>();	
	Set<Node> movieNodes = new HashSet<Node>();
	
	private static int userNodeId = 0;
	private static int movieNodeId = 0;
	
	
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
	
	private void printRandomMatrix() {
		for (int i = 0; i < randomMatrix.length; i++) {
			for (int j = 0; j < randomMatrix[0].length; j++) {
				System.out.print(randomMatrix[i][j] +  "\t| ");
			}
			System.out.println();
		}
	}
	
	private void initNodes() {
		List<UserNode> users = new ArrayList<UserNode>();
		for (int i = 0; i < randomMatrix[0].length; i++) {
			users.add(new UserNode());
		}
		List<MovieNode> movies = new ArrayList<MovieNode>();
		for (int i = 0; i < randomMatrix.length; i++) {
			movies.add(new MovieNode());
		}
		
		// set movieNodes to userNodes
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
		
		// set userNodes to movieNodes
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
	
	public Node[] getClosestNodes(Set<Node> openNodes) {
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
	
	private void printAllNodesInSet(Set<Node> set, String nodeNames){
		System.out.println("-----------------------");
		System.out.println(nodeNames);
		for (Node node : set) {
			System.out.println(node.toString()+"|\t"+node.getAttributesString());
		}
		System.out.println("-----------------------");
	}
	
	public static void main(String[] args) {
		TreeBuilder treeBuilder = new TreeBuilder();
		treeBuilder.printRandomMatrix();
		treeBuilder.printAllNodesInSet(treeBuilder.userNodes, "User Nodes:");
		treeBuilder.printAllNodesInSet(treeBuilder.movieNodes, "MovieNodes:");
		treeBuilder.getClosestNodes(treeBuilder.userNodes);
	}
	
	private class UserNode extends AbstractNode implements Node {
		
		Map<MovieNode, Double> movies;
		private final int id = userNodeId++;
		
		public UserNode() {		}
		
		public void setMovies(Map<MovieNode, Double> movies) {
			this.movies = movies;
		}
		
		public Set<MovieNode> getAttributeKeys() {
			return movies.keySet();
		}
		
		public Double getAttributeValue(Node node) {
			return movies.get(node);
		}
				
		public String getAttributesString() {
			return getAttributesString(movies);
		}
		
		@Override
		public String toString() {
			return "UserNode".concat(" ").concat(String.valueOf(id));
		}
		
	}
	
	private class MovieNode extends AbstractNode implements Node{
		
		Map<UserNode, Double> users;
		private final int id = movieNodeId++;
		
		public MovieNode() { }
		
		public void setUsers(Map<UserNode, Double> users) {
			this.users = users;
		}
		
		public Set<UserNode> getAttributeKeys() {
			return users.keySet();
		}
		
		public Double getAttributeValue(Node node) {
			return users.get(node);
		}
				
		public String getAttributesString() {
			return getAttributesString(users);
		}
		
		@Override
		public String toString() {
			return "MovieNode".concat(" ").concat(String.valueOf(id));
		}
	}
	
	public abstract class AbstractNode implements Node{
		
		public double getDistance(Node otherNode) {
			Set<Node> union = new HashSet<Node>(this.getAttributeKeys());
			union.addAll(otherNode.getAttributeKeys());
			Set<Node> intersect = new HashSet<Node>(this.getAttributeKeys());
			intersect.retainAll(otherNode.getAttributeKeys());
			double summedRatingDiffs = 0.0;
			for (Node userNode : intersect) {
				summedRatingDiffs = Math.abs(this.getAttributeValue(userNode) - otherNode.getAttributeValue(userNode));
			}
			return 1.0 / (double)union.size() * summedRatingDiffs + ((double)union.size() - (double)intersect.size()) / (double)union.size();
		}
		
		public String getAttributesString(Map<? extends AbstractNode, Double> map) {
			String s = "";
			for (Map.Entry<? extends AbstractNode, Double> entry : map.entrySet()) {
				s = s.concat(entry.getKey().toString()).concat(": ").concat(entry.getValue().toString()).concat(";\t");
			}
			if (s.length() == 0) {
				return "no_attributes";
			} else {
				return s.substring(0, s.length()-1);
			}
		}
		
	}
	
	public interface Node {
		public double getDistance(Node otherNode);
		public Double getAttributeValue(Node node);
		public Set<? extends AbstractNode> getAttributeKeys();
		public String getAttributesString();
	}
	

}
