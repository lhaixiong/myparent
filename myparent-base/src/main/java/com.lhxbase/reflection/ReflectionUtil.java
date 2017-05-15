package com.lhxbase.reflection;

import java.lang.reflect.*;

public class ReflectionUtil {
    public static Class getSupperClassGenericType(Class clazz,int index){
        Type type = clazz.getGenericSuperclass();
        if(!(type instanceof ParameterizedType)){
            return null;
        }
        ParameterizedType parameterizedType= (ParameterizedType) type;
        Type[] params = parameterizedType.getActualTypeArguments();

        if(index>params.length-1||index<0){
            return null;
        }

        if(params[index] instanceof Class){
            return  (Class) params[index];
        }
        return null;
    }

    public static Field getField(String className,String fieldName){
        Field field=null;
        try {
            Class clazz=Class.forName(className);
            return getField(clazz,fieldName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return field;
    }

    public static Field getField(Class clazz,String fieldName){
        Field field=null;
        for(;clazz!=null;clazz=clazz.getSuperclass()){
            try {
                field= clazz.getDeclaredField(fieldName);
                //有找不到field异常时，进入catch代码块，不执行下面两句了，catch不处理，进入下一循环
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
            }
        }

        if(field==null){
            try {
                throw new NoSuchFieldException("当前类以及所有父类中没有该属性>>>"+fieldName);
            }catch (NoSuchFieldException e){
                e.printStackTrace();
            }
        }
       return field;
    }

    /**
     * 调用（当前类或父类）方法，忽略修饰符（private，protected）
     * @param object 类的全路径，传入的对象必须要有一个无参构造函数
     * @param methodName 要调用的方法名
     * @param args 方法参数
     * @return 返回方法调用结果
     */
    public static Object invokeMethod(Object object,String methodName,Object... args)  {
        try {
            Class clazz=object.getClass();
            Class[] parameterTypes=new Class[args.length];
            for (int i=0;i<args.length;i++){
                parameterTypes[i]=args[i].getClass();
            }
            Method method=ReflectionUtil.getMethod(clazz, methodName, parameterTypes);
            //执行method
            return method.invoke(object,args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用（当前类或父类）方法，忽略修饰符（private，protected）
     * @param className 类的全路径，类必须要有一个无参构造函数
     * @param methodName 要调用的方法名
     * @param args 方法参数
     * @return 返回方法调用结果
     */
    public static Object invokeMethod(String className,String methodName,Object... args){
        try {
            Class clazz=Class.forName(className);
            return invokeMethod(clazz.newInstance(),methodName,args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过Class获取method，method可能是私有方法，也可能是父类（私有）方法
     * 如：Method method=ReflectionUtil.getMethod(String.class,"split",String.class);
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethod(Class clazz,String methodName,Class...parameterTypes){
        Method method= null;
        for(;clazz!=null;clazz=clazz.getSuperclass()){
            try {
                method=clazz.getDeclaredMethod(methodName,parameterTypes);
                //有找不到方法异常时，进入catch代码块，不执行下面两句了，catch不处理，进入下一循环
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
            }
        }

        if(method==null){
            try {
                throw new NoSuchMethodException("当前类以及所有父类中找不到方法>>>"+methodName);
            }catch (NoSuchMethodException e){
                e.printStackTrace();
            }
        }

        return method;
    }
    /**
     * 通过对象获取method，method可能是私有方法，也可能是父类（私有）方法
     * 如:Method method=ReflectionUtil.getMethod(new Date(),"setTime",long.class);
     * @param object
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethod(Object object,String methodName,Class...parameterTypes){
        return getMethod(object.getClass(),methodName,parameterTypes);
    }
    /**
     * 通过类的全路径获取method，method可能是私有方法，也可能是父类（私有）方法
     * 如：Method method=ReflectionUtil.getMethod("java.util.Date","setTime",long.class);
     * @param className
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethod(String className,String methodName,Class...parameterTypes){
        Class clazz=null;
        try {
            clazz=Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return getMethod(clazz,methodName,parameterTypes);
    }
}
