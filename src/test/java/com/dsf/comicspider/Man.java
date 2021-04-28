package com.dsf.comicspider;

/**
 * @author 戴少峰
 * @version 1.0
 * @className Man
 * @date 2021/4/18-22:30
 */
public class Man extends Person{
    private static int a = 0;
    static class hello {
        private int b = a;
    }

    {
        a = 2;
    }
    @Override
    public Person hello() {
        System.out.println("student");
        return this;
    }

    @Override
    public Person next() {
        System.out.println("student+");
        System.out.println(super.name);
        this.hello();
        return this;
    }

    @Override
    public void test() {

    }
}
