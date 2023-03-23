package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class StreamTest {
    private String name;
    private int age;
    private int salary;

    public StreamTest(String name, int age, int salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getSalary() {
        return salary;
    }

    // 构造方法、getter 和 setter 略

    public static void main(String[] args) {
        // 对 Person 对象基于两个字段进行排序
        List<StreamTest> persons = new ArrayList<>();
        persons.add(new StreamTest("232", 25, 10000));
        persons.add(new StreamTest("43", 30, 20000));
        persons.add(new StreamTest("545", 35, 30000));
        Comparator<StreamTest> comparator = Comparator.comparingInt((StreamTest p) -> Integer.parseInt(p.getName()))
                .thenComparingInt(StreamTest::getAge);
        Collections.sort(persons, comparator);

// 输出排序结果
        for (StreamTest person : persons) {
            System.out.println(person.getName() + " " + person.getAge() + " " + person.getSalary());
        }
    }
}
