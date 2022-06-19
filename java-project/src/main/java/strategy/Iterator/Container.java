package strategy.Iterator;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2022-06-14 10:04
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public interface Container<T> {
    /**
     * 获取迭代器
     *
     * @return
     */
    Iterator<T> iterator();
}
