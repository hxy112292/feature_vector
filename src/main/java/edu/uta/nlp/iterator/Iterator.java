package edu.uta.nlp.iterator;

import java.util.List;

/**
 * @author hxy
 */
public class Iterator<T> {

    private List<T> list = null;
    private int index = -1;

    public Iterator(List<T> list) {
        this.list = list;
    }

    public boolean hasNext() {
        if (index < list.size() - 1) {
            return true;
        } else {
            return false;
        }
    }

    public Object first() {
        index = 0;
        Object obj = list.get(index);
        ;
        return obj;
    }

    public Object next() {
        Object obj = null;
        if (this.hasNext()) {
            obj = list.get(++index);
        }
        return obj;
    }
}
