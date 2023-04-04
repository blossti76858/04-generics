package ohm.softa.a04;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Peter Kurfer
 * Created on 10/23/17.
 */
public abstract class CollectionsUtility {

	private CollectionsUtility() {
	}

	/**
	 * Sort a SimpleList with by applying the MergeSort algorithm
	 *
	 * @param cmp Comparator instance to be able to sort any type
	 * @return sorted SimpleList
	 */
	public static <T> SimpleList<T> sort(SimpleList<T> list, Comparator<T> cmp) {
		if (list.size() == 1)
			return list;

		// split list in halves
		SimpleList<T> left = createNew(list.getClass());
		SimpleList<T> right = createNew(list.getClass());

		split(list, left, right);
		left = sort(left, cmp);
		right = sort(right, cmp);
		return merge(left, right, cmp);
	}

	/**
	 * Merge two list parts into one by sorting them with the given Comparator
	 *
	 * @param left  left part of a list
	 * @param right right part of a list
	 * @param cmp   Comparator to merge the two lists in a sorted way
	 * @param <T>   type of the lists
	 * @return sorted, merged list
	 */
	private static <T> SimpleList<T> merge(SimpleList<T> left, SimpleList<T> right, Comparator<T> cmp) {
		// special cases
		if (left.size() == 0)
			return right;
		if (right.size() == 0)
			return left;

		SimpleList<T> out = createNew(left.getClass());

		/* create iterators */
		Iterator<T> li = left.iterator(), ri = right.iterator();
		T le = null, re = null;

		while (li.hasNext() || ri.hasNext() || re != null || le != null) {
			// advance iterators
			if (le == null && li.hasNext())
				le = li.next();
			if (re == null && ri.hasNext())
				re = ri.next();

			if (re == null && le == null)
				break;

			/* if both elements have values, compare */
			if (le == null) {
				out.add(re);
				re = null;
			} else if (re == null) {
				out.add(le);
				le = null;
			} else {
				int c = cmp.compare(le, re);

				if (c >= 0) {
					out.add(re);
					re = null;
				}
				if (c <= 0) {
					out.add(le);
					le = null;
				}
			}
		}

		return out;
	}

	private static <T> void split(SimpleList<T> in, SimpleList<T> outLeft, SimpleList<T> outRight) {
		int i = 0, h = in.size() / 2;
		SimpleList<T> out = outLeft;
		for (T t : in) {
			if (i == h)
				out = outRight;
			out.add(t);
			i++;
		}
	}

	/**
	 * Create a new instance of the current list implementation
	 *
	 * @param clazz Class of the list implementation
	 * @param <T>   runtime type of the list to sort
	 * @return new instance of the SimpleList implementation
	 * @implNote fallback to SimpleListImpl if no new instance can be created
	 */
	@SuppressWarnings("unchecked")
	private static <T> SimpleList<T> createNew(Class<? extends SimpleList> clazz) {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			return new SimpleListImpl<>();
		}
	}
}
