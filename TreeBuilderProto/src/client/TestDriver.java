package client;

import clusterer.TreeBuilder;

public class TestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		RandomDataset ds = new RandomDataset();
//		ds.printRandomMatrix();
		
		Dataset ds = new GrouplensDataset(null);
		TreeBuilder<Double> treeBuilder = new TreeBuilder<Double>(ds);
		treeBuilder.cluster();
		

	}

}
