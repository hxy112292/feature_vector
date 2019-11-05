package edu.uta.nlp.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hxy
 */
public class Aggregate<T> implements Cloneable{

    private List<T> list = new ArrayList<T>();

    public void add(T obj) {
        list.add(obj);
    }

    public void addAll(List objectList) {
        list.addAll(objectList);
    }

    public void remove(T obj) {
        list.remove(obj);
    }

    public Iterator<T> getIterator() {
        return (new Iterator(list));
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
