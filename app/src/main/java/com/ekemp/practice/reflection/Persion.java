package com.ekemp.practice.reflection;

/**
 * Created by Administrator on 15-11-23.
 */
public class Persion {
    private String name;
    private int age;

    public Persion() {

    }

    public Persion(String name, int age) {
        this.name = name;
        this.age = age;
    }


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

    @Override
    public String toString() {
        return "Persion{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
