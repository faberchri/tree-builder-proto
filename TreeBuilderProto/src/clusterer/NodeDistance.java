package clusterer;

import java.util.List;

public interface NodeDistance {
	public double getDistance();
	public Node getOtherNode(Node n);
	public List<Node> getBothNode();
}
