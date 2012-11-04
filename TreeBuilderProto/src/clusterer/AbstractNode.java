package clusterer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public abstract class AbstractNode implements Node, PrintableNode{
	
	private NodeDistanceCalculator distanceCalculator;
	
	public AbstractNode(NodeDistanceCalculator ndc) {
		this.distanceCalculator = ndc; 
	}
	
	public double getDistance(Node otherNode) {
		return distanceCalculator.calculateDistance(this, otherNode);
	}
	
	String getAttributesString(Map<? extends AbstractNode, Double> map) {
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