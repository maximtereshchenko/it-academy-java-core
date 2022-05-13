package by.it_academy.lesson18.patterns;

/**
 * @author Maxim Tereshchenko
 */
interface Order {
    int price();
}

class Item implements Order {

    private final int price;

    Item(int price) {
        this.price = price;
    }

    @Override
    public int price() {
        return price;
    }
}

class Box implements Order {

    private final Order[] orders;

    Box(Order... orders) {
        this.orders = orders.clone();
    }

    @Override
    public int price() {
        int total = 0;

        for (Order order : orders) {
            total += order.price();
        }

        return total;
    }
}

class Composite {

    public static void main(String[] args) {
        var box = new Box(
                new Box(
                        new Item(10),
                        new Item(20)
                ),
                new Item(30)
        );

        System.out.println("box.price() = " + box.price());
    }
}
