package client;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public interface Dataset <T extends Number> {
	public ListIterator<List<T>> iterateOverUsers();
	public ListIterator<List<T>> iterateOverContentItems();
	public int getNumberOfUsers();
	public int getNumberOfContentItems();
}
