package clusterer;

import java.util.Map;
import java.util.Set;


final class UserNode extends AbstractNode implements Node {
	
	private static int userNodeId = 0;
	
	private Map<MovieNode, Double> movies;
	private final int id = userNodeId++;
	
	public UserNode() {		}
	
	public void setMovies(Map<MovieNode, Double> movies) {
		this.movies = movies;
	}
	
	public Set<MovieNode> getAttributeKeys() {
		return movies.keySet();
	}
	
	public Double getAttributeValue(Node node) {
		return movies.get(node);
	}
			
	public String getAttributesString() {
		return getAttributesString(movies);
	}
	
	@Override
	public String toString() {
		return "UserNode".concat(" ").concat(String.valueOf(id));
	}
	
}