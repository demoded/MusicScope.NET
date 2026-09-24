/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SortedList<T>
extends ArrayList<T> {
    private static final long serialVersionUID = 1L;
    private Comparator<? super T> INavigationControlListener;

    public SortedList() {
        this((Comparator<T>)null);
    }

    public SortedList(Comparator<T> comparator) {
        super(0);
        this.INavigationControlListener = comparator;
    }

    public synchronized Comparator<? super T> INavigationControlListener() {
        return this.INavigationControlListener;
    }

    public synchronized void INavigationControlListener(Comparator<? super T> comparator) {
        this.INavigationControlListener = comparator;
    }

    public synchronized void LraControl() {
        this.LraControl(this.INavigationControlListener);
    }

    public void LraControl(Comparator<? super T> c) {
        Collections.sort(this, c);
    }

    @Override
    public synchronized boolean addAll(Collection<? extends T> c) {
        boolean add = super.addAll(c);
        if (add) {
            this.LraControl();
        }
        return add;
    }

    @Override
    public synchronized boolean add(T e) {
        boolean add = super.add(e);
        if (add) {
            this.LraControl();
        }
        return add;
    }

    @Override
    public synchronized boolean addAll(int index, Collection<? extends T> c) {
        boolean add = super.addAll(index, c);
        if (add) {
            this.LraControl();
        }
        return add;
    }

    @Override
    public synchronized void add(int index, T element) {
        super.add(index, element);
        this.LraControl();
    }

    @Override
    public synchronized List<T> subList(int fromIndex, int toIndex) {
        return super.subList(fromIndex, toIndex);
    }

    @Override
    public Iterator<T> iterator() {
        return super.iterator();
    }

    @Override
    public synchronized ListIterator<T> listIterator() {
        return super.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return super.listIterator(index);
    }

    @Override
    public synchronized boolean retainAll(Collection<?> c) {
        return super.retainAll(c);
    }

    @Override
    public synchronized boolean removeAll(Collection<?> c) {
        return super.removeAll(c);
    }

    @Override
    protected synchronized void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
    }

    @Override
    public synchronized void clear() {
        super.clear();
    }

    @Override
    public synchronized boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public synchronized T remove(int index) {
        return (T)super.remove(index);
    }

    @Override
    public synchronized T set(int index, T element) {
        return super.set(index, element);
    }

    @Override
    public synchronized T get(int index) {
        return (T)super.get(index);
    }

    @Override
    public synchronized <T> T[] toArray(T[] a) {
        return super.toArray(a);
    }

    @Override
    public synchronized Object[] toArray() {
        return super.toArray();
    }

    @Override
    public synchronized Object clone() {
        return super.clone();
    }

    @Override
    public synchronized int lastIndexOf(Object o) {
        return super.lastIndexOf(o);
    }

    @Override
    public synchronized int indexOf(Object o) {
        return super.indexOf(o);
    }

    @Override
    public synchronized boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public synchronized int size() {
        return super.size();
    }

    @Override
    public synchronized void ensureCapacity(int minCapacity) {
        super.ensureCapacity(minCapacity);
    }

    @Override
    public synchronized void trimToSize() {
        super.trimToSize();
    }
}

