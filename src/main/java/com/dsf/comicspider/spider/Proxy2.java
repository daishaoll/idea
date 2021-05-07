package com.dsf.comicspider.spider;

import org.aspectj.weaver.ast.Var;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 戴少峰
 * @version 1.0
 * @className Proxy
 * @date 2021/5/5-18:46
 */
public interface Proxy2 {

    void test1();

    void test2(String name);
}

class Human implements Proxy2, Cloneable  {
    public int a = 4;

    public Human test3() throws CloneNotSupportedException {
        return (Human)clone();
    }
    @Override
    public void test1() {
        System.out.println("human");
    }

    @Override
    public void test2(String name) {
        System.out.println(name);
    }
}

class ProxyFactory {
    public static Object proxyFactory(final Object obj) {

        InvocationHandler myHandeler = (proxy, method, args) -> {
            System.out.println("hello");
            method.invoke(obj, args);
            System.out.println("我是代理类");
            return null;
        };
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), myHandeler);
    }

    public static void main(String[] args) {
        Human human = new Human();
        Proxy2 o = (Proxy2)ProxyFactory.proxyFactory(human);
        o.test1();
        o.test2("o");

    }
}
