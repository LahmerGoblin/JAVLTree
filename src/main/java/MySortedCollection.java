interface MySortedCollection<T extends Comparable<T>> {
    boolean isElement(T t);
    void insert(T t);
    void printSorted();
}
	

