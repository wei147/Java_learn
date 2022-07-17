package com.imooc.spring.ioc.entity;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Company {
    private Set<String> rooms;
    private Map<String,Computer> computers;
    private Properties info;

    public Set<String> getRooms() {
        return rooms;
    }

    //运行后，room的类型这里是LinkHashSet 和 普通Set 不是一回事。
    // 在内存中虽然数据是分散存储的。但LinkHashSet基于双向链表，能够保证数据在提取时是按照原先设置的的顺序依次执行（就像List类型运行后这里显示ArrayList）
    public void setRooms(Set<String> rooms) {
        this.rooms = rooms;
    }

    public Map<String, Computer> getComputers() {
        return computers;
    }

    //运行后，computers的类型这里是LinkHashMap。一样采用双向链表，提取顺序按数据存放的顺序进行的
    public void setComputers(Map<String, Computer> computers) {
        this.computers = computers;
    }

    public Properties getInfo() {
        return info;
    }

    public void setInfo(Properties info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Company{" +
                "rooms=" + rooms +
                ", computers=" + computers +
                ", info=" + info +
                '}';
    }
}
