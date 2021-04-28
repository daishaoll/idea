package com.dsf.comicspider;



import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author 戴少峰
 * @version 1.0
 * @className SpiderInterceptor
 * @date 2021/4/18-20:11
 */
public class SpiderInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("你好");
        methodProxy.invokeSuper(o, objects);
        return null;

    }
}
