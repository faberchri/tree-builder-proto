package clusterer;

import java.util.Map;
import java.util.Set;


final class MovieNode extends AbstractNode implements Node{
	
	private static int movieNodeId = 0;
	
	private Map<UserNode, Double> users;
	private final int id = movieNodeId++;
	
	public MovieNode() { }
	
	public void setUsers(Map<UserNode, Double> users) {
		this.users = users;
	}
	
	public Set<UserNode> getAttributeKeys() {
		return users.keySet();
	}
	
	public Double getAttributeValue(Node node) {
		return users.get(node);
	}
			
	public String getAttributesString() {
		return getAttributesString(users);
	}
	
	@Override
	public String toString() {
		return "MovieNode".concat(" ").concat(String.valueOf(id));
	}
}