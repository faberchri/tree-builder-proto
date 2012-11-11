package client;

import java.awt.Container;
import java.util.Set;

import javax.swing.JFrame;

import visualization.VisualizationBuilder;
import clusterer.Node;
import clusterer.TreeBuilder;

public class TestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Get Data
		RandomDataset ds = new RandomDataset();
		ds.printRandomMatrix();
		
		// Build Cluster
		TreeBuilder<Double> treeBuilder = new TreeBuilder<Double>(ds);
		treeBuilder.cluster();
		treeBuilder.visualize();
	}

}
