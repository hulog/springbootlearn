package com.fuckSpring.domain.paramBean;

/**
 * Created by upsmart on 17-6-14.
 */
public class UserRequestVO {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserRequestVO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
