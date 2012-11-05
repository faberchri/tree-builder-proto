package clusterer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import client.Dataset;


public final class TreeBuilder<T> {
		
	private Set<UserNode<T>> userNodes = new HashSet<UserNode<T>>();	
	private Set<MovieNode<T>> movieNodes = new HashSet<MovieNode<T>>();
	
	
	public TreeBuilder(Dataset<T> dataset) {
		initNodes(dataset);
	}
		
	private void initNodes(Dataset<T> dataset) {
		NodeDistanceCalculator ndc = new SimpleNodeDistanceCalculator();
		// create UserNode objects
		List<UserNode<T>> users = new ArrayList<UserNode<T>>();
		for (int i = 0; i < dataset.getNumberOfUsers(); i++) {
			users.add(new UserNode<T>(ndc));
		}
		
		// create MovieNode objects
		List<MovieNode<T>> movies = new ArrayList<MovieNode<T>>();
		for (int i = 0; i < dataset.getNumberOfContentItems(); i++) {
			movies.add(new MovieNode<T>(ndc));
		}
		
		// add movieNodes to userNodes
		ListIterator<List<T>> it = dataset.iterateOverUsers();
		while(it.hasNext()) {
			Map<MovieNode<T>, T> attributes = new HashMap<MovieNode<T>, T>();
			List<T> li = it.next();
			for (int i = 0; i < li.size(); i++) {
				if (li.get(i) != null) {					
					attributes.put(movies.get(i), li.get(i));
				}
			}
			users.get(it.previousIndex()).setMovies(attributes);
			userNodes.add(users.get(it.previousIndex()));
		}
		
		// add userNodes to movieNodes		
		it = dataset.iterateOverContentItems();
		while(it.hasNext()) {
			Map<UserNode<T>, T> attributes = new HashMap<UserNode<T>, T>();
			List<T> li = it.next();
			for (int i = 0; i < li.size(); i++) {
				if (li.get(i) != null) {
					attributes.put(users.get(i), li.get(i));
				}
			}
			movies.get(it.previousIndex()).setUsers(attributes);
			movieNodes.add(movies.get(it.previousIndex()));
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
		PrintableNode[] setArr = set.toArray(new PrintableNode[set.size()]);
		Arrays.sort(setArr);
		for (PrintableNode node : setArr) {
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
