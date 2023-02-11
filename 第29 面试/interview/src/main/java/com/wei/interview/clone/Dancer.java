package com.wei.interview.clone;

import java.io.*;

//舞者  需要搭档
public class Dancer implements Cloneable {

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
        // 把内存中的对象输出为字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        // 将字节数组还原为对象
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (Dancer) ois.readObject();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
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
        System.out.println("d2Clone 浅复制: " + d2Clone.getPartner().hashCode());
    }
}
