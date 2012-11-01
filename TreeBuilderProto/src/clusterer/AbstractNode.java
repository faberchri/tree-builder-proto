package clusterer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public abstract class AbstractNode implements Node, PrintableNode{
	
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