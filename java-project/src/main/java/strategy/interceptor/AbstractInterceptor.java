package strategy.interceptor;

import java.util.Objects;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2022-06-13 20:12
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public abstract class AbstractInterceptor {

    /**
     * 下一个节点
     */
    private AbstractInterceptor next;

    /**
     * 处理消息
     *
     * @param msg
     */
    public void message(String msg) {
        if (!doMessage(msg) && Objects.nonNull(next)) {
            next.message(msg);
        }
    }

    /**
     * 处理消息并返回是否拦截本次请求
     *
     * @param msg 消息
     * @return TRUE：拦截  FALSE：不拦截
     */
    protected abstract boolean doMessage(String msg);
}
