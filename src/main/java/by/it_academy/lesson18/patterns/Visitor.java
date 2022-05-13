package by.it_academy.lesson18.patterns;

import java.util.stream.Stream;

/**
 * @author Maxim Tereshchenko
 */
interface Shape {
    void visit(Visitor visitor);
}

class Circle implements Shape {
    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }
}

class Square implements Shape {
    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }
}

class Visitor {

    void visit(Shape shape) {
        System.out.println("shape");
    }

    void visit(Circle circle) {
        System.out.println("circle");
    }

    void visit(Square square) {
        System.out.println("square");
    }
}

class VisitorExample {

    public static void main(String[] args) {
        var visitor = new Visitor();
        Stream.of(
                        new Circle(),
                        new Square()
                )
                //.forEach(visitor::visit);
                .forEach(shape -> shape.visit(visitor));
    }
}
