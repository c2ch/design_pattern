package 序列化和反序列化;

import java.io.Serializable;

public class Person implements Serializable {


    private static final long serialVersionUID = -1575061026844889014L;

    private int age;
    private String name;
    //序列化ID

    public Person() {}

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
