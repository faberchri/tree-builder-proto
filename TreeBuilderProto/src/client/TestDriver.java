package client;

import clusterer.ParallelClosestNodesSearcher;
import clusterer.SimpleNodeDistanceCalculator;
import clusterer.SimpleNodeUpdater;
import clusterer.TreeBuilder;

public class TestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		RandomDataset ds = new RandomDataset();
//		ds.printRandomMatrix();

		Dataset<Integer> ds = new GrouplensDataset(null);
		TreeBuilder<Integer> treeBuilder = new TreeBuilder<Integer>(
				ds,
				new SimpleNodeDistanceCalculator(),
				new SimpleNodeDistanceCalculator(),
				new SimpleNodeUpdater(),
				new ParallelClosestNodesSearcher());
		treeBuilder.cluster();
		

	}

}
