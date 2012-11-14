package client;

<<<<<<< HEAD
import clusterer.CachedParallelClosestNodesSearcher;
import clusterer.SimpleNodeDistanceCalculator;
import clusterer.SimpleNodeUpdater;
=======
import java.awt.Container;
import java.util.Set;

import javax.swing.JFrame;

import visualization.VisualizationBuilder;
import clusterer.Node;
>>>>>>> 9f6796bd9f3d344830ba5372778b3d2820cbb08b
import clusterer.TreeBuilder;

public class TestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
<<<<<<< HEAD
//		RandomDataset ds = new RandomDataset();
//		ds.printRandomMatrix();

		Dataset<Integer> ds = new GrouplensDataset(null);
		TreeBuilder<Integer> treeBuilder = new TreeBuilder<Integer>(
				ds,
				new SimpleNodeDistanceCalculator(),
				new SimpleNodeDistanceCalculator(),
				new SimpleNodeUpdater(),
				new CachedParallelClosestNodesSearcher());
		treeBuilder.cluster();
		

=======
		
		// Get Data
		RandomDataset ds = new RandomDataset();
		ds.printRandomMatrix();
		
		// Build Cluster
		TreeBuilder<Double> treeBuilder = new TreeBuilder<Double>(ds);
		treeBuilder.cluster();
		treeBuilder.visualize();
>>>>>>> 9f6796bd9f3d344830ba5372778b3d2820cbb08b
	}

}
