package by.it_academy.lesson18.patterns;

/**
 * @author Maxim Tereshchenko
 */
class AbstractFactory {

    FurnitureFactory furnitureFactory(String style) {
        switch (style) {
            case "victorian":
                return new VictorianFurnitureFactory();
            case "modern":
                return new ModernFurnitureFactory();
            default:
                return null;
        }
    }

    interface FurnitureFactory {

        Chair chair();

        Sofa sofa();
    }

    interface Chair {}

    interface Sofa {}

    static final class VictorianFurnitureFactory implements FurnitureFactory {
        @Override
        public Chair chair() {
            return null;
        }

        @Override
        public Sofa sofa() {
            return null;
        }
    }

    static final class ModernFurnitureFactory implements FurnitureFactory {
        @Override
        public Chair chair() {
            return null;
        }

        @Override
        public Sofa sofa() {
            return null;
        }
    }
}
