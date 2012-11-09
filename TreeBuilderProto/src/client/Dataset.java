package client;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public interface Dataset <T extends Number> {
	public Iterator<DatasetItem<T>> iterateOverDatasetItems();
	public Normalizer<T> getNormalizer();
}
