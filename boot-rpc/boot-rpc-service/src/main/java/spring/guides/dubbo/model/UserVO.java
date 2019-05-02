package spring.guides.dubbo.model;

import java.io.Serializable;

/**
 * 用户视图对象。
 *
 * @author dannong
 * @since 2017年10月31日
 */
public class UserVO implements Serializable {

    private static final long serialVersionUID = -1767051477001633837L;

    private String name;

    private String address;

    @Override
    public String toString() {
        return "UserVO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
