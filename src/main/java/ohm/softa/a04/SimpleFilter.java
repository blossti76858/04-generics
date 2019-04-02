package ohm.softa.a04;

/**
 * Functional interface to model a boolean predicate
 *
 * @author Peter Kurfer
 * Created on 10/6/17.
 */
@FunctionalInterface
public interface SimpleFilter<T> {

	/**
	 * Determines if a item matches a condition
	 *
	 * @param item Object to evaluate
	 * @return true if the referenced object should be included.
	 */
	boolean include(T item);
}