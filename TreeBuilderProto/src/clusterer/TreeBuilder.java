package clusterer;
import java.awt.Container;
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

import javax.swing.JFrame;

import visualization.VisualizationBuilder;

import client.Dataset;


public final class TreeBuilder<T extends Number> {
	
	private Dataset<T> dataset; 
		
	private Set<Node> userNodes = new HashSet<Node>();	
	private Set<Node> movieNodes = new HashSet<Node>();
	private Set<Node> rootNodes = new HashSet<Node>();
		
	private NodeFactory factory;
	private AttributeFactory<T> attributeFactory;
	
	public TreeBuilder(Dataset<T> dataset) {
		this.dataset = dataset;
		this.factory = new NodeFactory(new SimpleNodeDistanceCalculator(), new SimpleNodeDistanceCalculator());
		this.attributeFactory = SimpleAttributeFactory.getInstance();
	}
	
	public Set<Node> cluster() {
		initLeafNodes(dataset);
		while (userNodes.size() > 2 && movieNodes.size() > 2) {
			List<Node> cN = getClosestOpenUserNodes();
			System.out.println("ClosesOpenUserNodes; "+ cN);
			mergeNodes(cN, userNodes);
			printAllOpenUserNodes();
			cN = getClosestOpenMovieNodes();
			System.out.println("ClosesOpenMovieNodes; "+ cN);
			mergeNodes(cN, movieNodes);
			printAllOpenMovieNodes();
		} 
		
		// Root Nodes Set anlegen
		Set<Node> rootNodes = new HashSet<Node>();
		rootNodes.addAll(movieNodes);
		rootNodes.addAll(userNodes);
		this.rootNodes = rootNodes;
		
		return null; // FIXME
	}

	public void visualize() {
		
		// Instantiate VisualizationBuilder
		VisualizationBuilder vb = new VisualizationBuilder();
		vb.setRootNodes(rootNodes);
		
		// Swing Visualization
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.add(vb);
        frame.pack();
        frame.setVisible(true);
	}
	
	private void initLeafNodes(Dataset<T> dataset) {
		// create UserNode objects
		List<UserNode> users = factory.createEmptyUserNodes(dataset.getNumberOfUsers());
		
		// create MovieNode objects
		List<MovieNode> movies = factory.createEmptyContentNodes(dataset.getNumberOfContentItems());

		
		// add movieNodes to userNodes
		ListIterator<List<T>> it = dataset.iterateOverUsers();
		while(it.hasNext()) {
			Map<Node, Attribute> simpleAttributes = new HashMap<Node, Attribute>();
			List<T> li = it.next();
			for (int i = 0; i < li.size(); i++) {
				if (li.get(i) != null) {					
					simpleAttributes.put(movies.get(i), attributeFactory.createAttribute(li.get(i)));
				}
			}
			users.get(it.previousIndex()).setAttributes(simpleAttributes);
			userNodes.add(users.get(it.previousIndex()));
		}
		
		// add userNodes to movieNodes		
		it = dataset.iterateOverContentItems();
		while(it.hasNext()) {
			Map<Node, Attribute> attributes = new HashMap<Node, Attribute>();
			List<T> li = it.next();
			for (int i = 0; i < li.size(); i++) {
				if (li.get(i) != null) {
					attributes.put(users.get(i), attributeFactory.createAttribute(li.get(i)));
				}
			}
			movies.get(it.previousIndex()).setAttributes(attributes);
			movieNodes.add(movies.get(it.previousIndex()));
		}
		
	}
		
//	private void initLeafNodes(Dataset dataset) {
//		NodeDistanceCalculator ndc = new SimpleNodeDistanceCalculator();
//		// create UserNode objects
//		List<UserNode> users = factory.createEmptyUserNodes(dataset.getNumberOfUsers());
//		
//		// create MovieNode objects
//		List<MovieNode> movies = factory.createEmptyContentNodes(dataset.getNumberOfContentItems());
//
//		
//		// add movieNodes to userNodes
//		ListIterator<List> it = dataset.iterateOverUsers();
//		while(it.hasNext()) {
//			Map<Node, T> attributes = new HashMap<Node, T>();
//			List li = it.next();
//			for (int i = 0; i < li.size(); i++) {
//				if (li.get(i) != null) {					
//					attributes.put(movies.get(i), li.get(i));
//				}
//			}
//			users.get(it.previousIndex()).setAttributes(attributes);
//			userNodes.add(users.get(it.previousIndex()));
//		}
//		
//		// add userNodes to movieNodes		
//		it = dataset.iterateOverContentItems();
//		while(it.hasNext()) {
//			Map<Node, T> attributes = new HashMap<Node, T>();
//			List li = it.next();
//			for (int i = 0; i < li.size(); i++) {
//				if (li.get(i) != null) {
//					attributes.put(users.get(i), li.get(i));
//				}
//			}
//			movies.get(it.previousIndex()).setAttributes(attributes);
//			movieNodes.add(movies.get(it.previousIndex()));
//		}
//		
//	}
		
	private List<Node> getClosestNodes(Set<? extends Node> openNodes) {
		double closestDistance = Double.MAX_VALUE;
		List<Node> closestNodes = new ArrayList<Node>();
		Set<Node> subSet = new HashSet<Node>(openNodes);
		for (Node node : openNodes) {
			subSet.remove(node);
			for (Node node2 : subSet) {
				double tmpDi = node.getDistance(node2);
				if (tmpDi < closestDistance){
					closestDistance = tmpDi;
					closestNodes.clear();
					closestNodes.add(node);
					closestNodes.add(node2);
				}
//				System.out.println("calculation: "+node+", "+node2+" ("+tmpDi+")");
			}
		}
		if (closestNodes.size() > 1) {
			System.out.println("Closest nodes: "+closestNodes.get(0)+", "+closestNodes.get(1)+" ("+closestDistance+")");
		}
		return closestNodes;
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
		printAllNodesInSet((Set)userNodes, "User Nodes:");
	}
	
	public void printAllOpenMovieNodes() {
		printAllNodesInSet((Set)movieNodes, "MovieNodes:");
	}
	
	public List<Node> getClosestOpenUserNodes() {
		return getClosestNodes(userNodes);
	}
	
	public List<Node> getClosestOpenMovieNodes() {
		return getClosestNodes(movieNodes);
	}
	
	private void mergeNodes(List<Node> nodes, Set<Node> openSet) {
		if (nodes.size() > 1) {
			Node newNode = nodes.get(0).getNodeFactory().createNode(nodes, attributeFactory);
			openSet.add(newNode);
			for (Node node : nodes) {
				node.setParent(newNode);
				newNode.addChild(node);
				openSet.remove(node);
			}		
		} else {
			System.err.println("merge attempt with 1 or less nodes, " + getClass().getSimpleName());
			System.exit(-1);
		}
	}

}
