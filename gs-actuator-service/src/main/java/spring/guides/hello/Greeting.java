package spring.guides.hello;

/**
 * 问候模型表示类。
 *
 * @author dannong
 * @since 2017年02月24日 19:56
 */
public class Greeting {

    private final long id;

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

    @Override
    public String toString() {
        return "Greeting{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

}
