package com.dsf.comicspider;

import javax.sound.midi.Soundbank;

/**
 * @author 戴少峰
 * @version 1.0
 * @className Person
 * @date 2021/4/18-22:29
 */
public class Person implements Human{

    protected String name = "hello";
    public Person hello() {
        System.out.println("person");
        return this;
    }

    public Person next() {
        System.out.println("person+");
        this.hello();
        return this;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
    private void num() {
        System.out.println("num");
    }

    public void test1() {
        System.out.println("test1");
    }
    public void test2() {
        System.out.println("test2");
    }

    @Override
    public void test() {

    }
}
