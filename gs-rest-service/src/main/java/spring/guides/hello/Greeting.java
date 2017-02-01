package spring.guides.hello;

/**
 * 问候表示。
 *
 * <p>Create a resource representation class (资源表示类).
 *
 * <p>不可变类，线程安全。
 *
 * @author dannong
 * @since 2017年01月30日 00:47
 */
public class Greeting {

    /**
     * 标识
     */
    private final long id;

    /**
     * 内容
     */
    private final String content;


    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }


    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
