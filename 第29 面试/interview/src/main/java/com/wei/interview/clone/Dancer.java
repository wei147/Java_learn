package com.wei.interview.clone;

import java.io.*;

//舞者  需要搭档
public class Dancer implements Cloneable,Serializable {

    private String name;
    private Dancer partner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dancer getPartner() {
        return partner;
    }

    public void setPartner(Dancer partner) {
        this.partner = partner;
    }

    public Dancer deepClone() throws IOException, ClassNotFoundException {
        // 序列化,将内存中的对象序列化为字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        // 反序列化,将字节数组转回为Dancer对象。 这个过程本身就是深层复制
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (Dancer) ois.readObject();
    }

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        Dancer d1 = new Dancer();
        d1.setName("小美");

        Dancer d2 = new Dancer();
        d2.setName("小明");
        // 在d1中持有了d2的对象
        d1.setPartner(d2);
        // hashCode 是内存地址转化后的一个整数
        System.out.println("Partner: " + d2.hashCode());
        // 浅复制 shallow  (一个对象要克隆的话,要实现Cloneable 接口)
        Dancer d2Clone = (Dancer) d1.clone();
        System.out.println("浅复制: " + d2Clone.getPartner().hashCode()+"   "+d2Clone.getPartner().getName());

        //深复制  (只有实现了Serializable接口,才能序列化和反序列化)
        Dancer deep = (Dancer) d1.deepClone();
        System.out.println("深复制: " +deep.getPartner().hashCode()+"  "+deep.getPartner().getName());
        System.out.println();

    }
}
