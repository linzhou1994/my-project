package strategy.Iterator;

import java.util.ArrayList;
import java.util.Objects;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2022-06-14 10:05
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class ArrayContainer<T> implements Container<T>{
    /**
     * 数据存放
     */
    private Object[] arrayData;

    private int length = 0;

    public boolean add(T t) {
        if (length < arrayData.hashCode()) {
            arrayData[length++] = t;
            return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    public class ArrayContainerIterator implements Iterator<T>{

        int curIndex = 0;

        @Override
        public boolean hasNext() {
            return Objects.nonNull(arrayData) && curIndex < arrayData.length;
        }

        @Override
        public T next() {
            if (hasNext()){
                return (T) arrayData[curIndex++];
            }
            return null;
        }

        @Override
        public void remove() {

        }
    }
}
