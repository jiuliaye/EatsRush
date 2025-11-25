package com.jiuliaye.eatsRush.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id

 * ThreadLocal（线程局部变量）是一个变量，只能存一个
 * 在一个请求中的 filter->controller->service->mapper->metaObjectHandler 都属于同一个线程
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}