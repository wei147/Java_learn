package threadLocal;

/**
 * 演示ThreadLocal用法2: 避免传递参数的麻烦
 */
public class ThreadLocalNormalUsage06 {
    public static void main(String[] args) {
        new Service1().process();
    }
}

class Service1 {
    public void process() {
        User user = new User("王小美");
        //这里ThreadLocal的用法和第一种典型用法有些不一样,不用一开始就赋初始值
        UserContextHolder.holder.set(user);
        //这里调用Service2的方法
        new Service2().process();
    }
}

class Service2 {
    public void process() {
        //由Service1拿到对象就够了,后面就不再需要创建对象或读取了。通过get可以直接拿到这个对象
        User user = UserContextHolder.holder.get();
        System.out.println("Service2: " + user.name);
        new Service3().process();
    }
}

class Service3 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service3: " + user.name);
    }
}

class UserContextHolder {
    //在这边会同样把 ThreadLocal定义为静态的,这是因为我们需要在多个方法中直接取到它,,,
    public static ThreadLocal<User> holder = new ThreadLocal<User>();
}

class User {
    String name;

    public User(String name) {
        this.name = name;
    }
}
