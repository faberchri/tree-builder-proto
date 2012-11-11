package clusterer;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface Node {
	public double getDistance(Node otherNode);
	public void setAttributes(Map<Node, Attribute> attributes);
	public Attribute getAttributeValue(Node node);
	public Set<Node> getAttributeKeys();
	public int getId();
	public void addChild (Node child);
	public boolean isChild(Node possibleChild);
	public Iterator<Node> getChildren();
	public Set<Node> getChildrenSet();
	public void setParent(Node parent);
	public Node getParent();
	public boolean isLeaf();
	public boolean isRoot();
	public Factory getNodeFactory();
}