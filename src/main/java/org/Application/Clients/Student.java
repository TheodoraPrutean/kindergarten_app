package org.Application.Clients;
import org.Application.Organization.Group;


public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private Group group;
    private Parent parent1;
    private Parent parent2;
    private int groupId;
    private int parent1Id;
    private int parent2Id;

    public Student(String firstName, String lastName, int age, Group group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.group = group;
    }
    public Student(String firstName, String lastName, int age, Group group, Parent parent1, Parent parent2) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.group = group;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }
    public Student(String firstName, String lastName, int age, Group group, Parent parent1) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.group = group;
        this.parent1 = parent1;
    }

    public int getParent1Id() {
        return parent1Id;
    }

    public void setParent1Id(int parent1Id) {
        this.parent1Id = parent1Id;
    }

    public int getParent2Id() {
        return parent2Id;
    }

    public void setParent2Id(int parent2Id) {
        this.parent2Id = parent2Id;
    }
    public int getGroupId() {
        return groupId;
    }
    public Parent getParent1() {
        return parent1;
    }
    public void setParent1(Parent parent1) {
        this.parent1 = parent1;
    }
    public Parent getParent2() {
        return parent2;
    }
    public void setParent2(Parent parent2) {
        this.parent2 = parent2;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Group getGroup() {
        return group;
    }
    public void setGroup(Group studentGroup) {
        this.group = studentGroup;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Student() {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Student: id = " + id + ", " +
                "Name = " + firstName + " " + lastName + ", " +
                "age = " + age + ", " +
                "group = " + group);

        if (parent1 != null) {
            builder.append(", " + "parent1 = ").append(parent1.getFirstName()).append(" ").append(parent1.getLastName());
        }

        if (parent2 != null) {
            builder.append(", " + "parent2 = ").append(parent2.getFirstName()).append(" ").append(parent2.getLastName());
        }

        builder.append("\n");
        return builder.toString();
    }
}

