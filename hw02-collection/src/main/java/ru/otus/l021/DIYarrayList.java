package ru.otus.l021;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by Alexandr Byankin on 14.04.2019
 */
public class DIYarrayList<T> implements List<T> {

    private Object[] data; //массив для хранения
    private int size; //количество хранимых объектов в массиве
    private int workLenght; //длина массива
    private int defaultWorkLenght = 3; //длина массива по дефолту
    private static int multiplier = 3; //множитель, когда необходимо увеличить размер массива в связи с добавлением объектов

    public DIYarrayList(){
        clear();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        add(size, t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        data = new Object[workLenght];
        size = 0;
        workLenght = defaultWorkLenght;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return (T) data[index];
    }

    @Override
    public T set(int index, T element) {
        Objects.checkIndex(index, size);
        data[index] = element;
        return element;
    }

    @Override
    public void add(int index, T element) {
        Objects.checkIndex(index, size + 1);

        Object[] resultData;
        if (size == workLenght){
            workLenght*=multiplier;
        }
        resultData = new Object[workLenght];

        System.arraycopy(data, 0, resultData, 0, index);

        System.arraycopy(data, index, resultData, index + 1, size - index);

        resultData[index] = element;
        data = resultData;
        size++;
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    static <T> T elementAt(Object[] es, int index) {
        return (T) es[index];
    }

    public void sort(Comparator<? super T> c) {
        Arrays.sort((T[]) data, 0, size, c);
    }

    private class Itr implements Iterator<T> {
        int cursor;
        int lastRet = -1;

        Itr() {}

        public boolean hasNext() {
            return cursor != size;
        }

        public T next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = DIYarrayList.this.data;
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                DIYarrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            final int size = DIYarrayList.this.size;
            int i = cursor;
            if (i < size) {
                final Object[] es = data;
                for (; i < size; i++)
                    action.accept(elementAt(es, i));
                cursor = i;
                lastRet = i - 1;
            }
        }
    }

    private class ListItr extends Itr implements ListIterator<T>{

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public T previous() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(T t) {
            if (lastRet < 0)
                throw new IllegalStateException();
            DIYarrayList.this.set(lastRet, t);
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }
    }

}
