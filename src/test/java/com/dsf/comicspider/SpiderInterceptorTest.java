package com.dsf.comicspider;

import com.dsf.comicspider.spider.Comic;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.testng.annotations.Test;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.concurrent.Executors;

/**
 * @author 戴少峰
 * @version 1.0
 * @className SpiderInterceptorTest
 * @date 2021/4/18-20:24
 */
public class SpiderInterceptorTest {


    public static void main(String[] args) {


//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\cglibProxyClass");
        Enhancer enhancer = new Enhancer();
        Enhancer enhancer1 = new Enhancer();
//        enhancer.setSuperclass(Spider.class);
        enhancer1.setSuperclass(Person.class);
//        enhancer.setCallback(new SpiderInterceptor());
        enhancer1.setCallback(new SpiderInterceptor());

        Comic comic = new Comic();
//        Spider spider1 = new Spider(comic);
//        Spider spider = (Spider)enhancer.create(new Class[]{PageProcessor.class}, new Object[]{comic});
        Person person = (Person)enhancer1.create();
//        System.out.println(spider);
        System.out.println(person);
//        System.out.println(person.toString());
//        System.out.println(person == null);
        person.next().hello();

    }

    @Test
    public void test1() {
        int a = 1;
        Human human = new Human() {
            @Override
            public void test() {
                System.out.println(a);
            }
        };

        human.test();

    }


}
