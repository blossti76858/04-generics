package ohm.softa.a04;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public interface SimpleList<T> extends Iterable<T> {
	/**
	 * Add a given object to the back of the list.
	 */
	void add(T o);

	/**
	 * @return current size of the list
	 */
	int size();

	/**
	 * Generate a new list using the given filter instance.
	 * @return a new, filtered list
	 */
	SimpleList<T> filter(SimpleFilter<T> filter);

	default void addDefault() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		add(((Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getDeclaredConstructor().newInstance());
	}
}
