package client;

import clusterer.TreeBuilder;

public class TestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RandomDataset ds = new RandomDataset();
		ds.printRandomMatrix();
		TreeBuilder<Double> treeBuilder = new TreeBuilder<Double>(ds);

		treeBuilder.printAllOpenUserNodes();
		treeBuilder.printAllOpenMovieNodes();
		treeBuilder.getClosestOpenUserNodes();
		treeBuilder.getClosestOpenMovieNodes();
	}

}
