package by.it_academy.lesson18.patterns;

/**
 * @author Maxim Tereshchenko
 */
class Pixel {
    private final int x;
    private final int y;
    private final RgbColour colour;

    Pixel(int x, int y, RgbColour colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }
}

class RgbColour {
    private final int red;
    private final int green;
    private final int blue;

    RgbColour(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
}

class Flyweight {

    public static void main(String[] args) {
//        new Pixel(0,0,new RgbColour(100,100,100));
//        new Pixel(1,1,new RgbColour(100,100,100));
//        new Pixel(2,2,new RgbColour(100,100,100));
        var rgbColour = new RgbColour(100, 100, 100);
        new Pixel(0, 0, rgbColour);
        new Pixel(1, 1, rgbColour);
        new Pixel(2, 2, rgbColour);
    }
}
