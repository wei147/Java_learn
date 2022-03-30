public class ExtendsTest {
    public static void main(String[] args) {
        Person p1 = new Person();
        p1.name = "wei";
        p1.eat();
        p1.breath();
        System.out.println("==============");

//        p1.sleep();
        System.out.println(p1.name);

        Student s1 = new Student();
        s1.eat();
        s1.setAge(39);
        System.out.println(s1.getAge());

        Creature c1 = new Creature();
        c1.breath();
    }
}
