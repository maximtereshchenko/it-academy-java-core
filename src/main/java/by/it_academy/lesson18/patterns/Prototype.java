package by.it_academy.lesson18.patterns;

import java.util.Objects;

/**
 * @author Maxim Tereshchenko
 */
class Parent implements Cloneable {

    private final int a;

    Parent(int a) {
        this.a = a;
    }

    protected Parent(Parent parent) {
        this(parent.a);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parent parent = (Parent) o;
        return a == parent.a;
    }

    @Override
    public Parent clone() {
        return new Parent(this);
    }
}

class Child extends Parent {

    private final int b;

    Child(int a, int b) {
        super(a);
        this.b = b;
    }

    private Child(Child child) {
        super(child);
        this.b = child.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Child child = (Child) o;
        return b == child.b;
    }

    @Override
    public Parent clone() {
        return new Child(this);
    }
}

class Prototype {

    public static void main(String[] args) {
        Parent parent = new Parent(1);
        Parent child = new Child(1, 2);

        System.out.println("parent == parent.clone() = " + (parent == parent.clone()));
        System.out.println("parent.equals(parent.clone()) = " + (parent.equals(parent.clone())));

        System.out.println("child == child.clone() = " + (child == child.clone()));
        System.out.println("child.equals(child.clone()) = " + (child.equals(child.clone())));
    }
}

