package com.lhxbase.pattern.create;

import java.io.*;

public class Prototype implements Cloneable,Serializable {
    private static final long serialVersionUID=1L;
    private String name;
    private SerialiazeObj serialiazeObj;

    /**
     * 浅复制
     * @return
     * @throws CloneNotSupportedException
     */
    public Prototype simpleClone() throws CloneNotSupportedException {
        return (Prototype) super.clone();
    }

    /**
     * 深复制
     * @return
     */
    public Prototype deepClone() throws IOException, ClassNotFoundException {
        //把当前对象输出到二进制流
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(bos);
        oos.writeObject(this);

        //读入二进制流产生的新对象
        ByteArrayInputStream bis=new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois=new ObjectInputStream(bis);
        return (Prototype) ois.readObject();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SerialiazeObj getSerialiazeObj() {
        return serialiazeObj;
    }

    public void setSerialiazeObj(SerialiazeObj serialiazeObj) {
        this.serialiazeObj = serialiazeObj;
    }
}
class SerialiazeObj implements Serializable{
    private static final long serialVersionUID=1L;
}
