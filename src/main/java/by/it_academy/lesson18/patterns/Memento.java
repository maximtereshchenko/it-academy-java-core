package by.it_academy.lesson18.patterns;

/**
 * @author Maxim Tereshchenko
 */
class Label {

    private String text = "initial";

    String getText() {
        return text;
    }

    void setText(String text) {
        this.text = text;
    }

    Memento save() {
        return new Memento(text);
    }

    void restore(Memento memento) {
        text = memento.text;
    }

    static class Memento {

        private final String text;

        Memento(String text) {
            this.text = text;
        }
    }
}

class Memento {

    public static void main(String[] args) {
        Label label = new Label();

        System.out.println("label.getText() = " + label.getText());
        label.setText("remembered text");
        Label.Memento save = label.save();
        //save.text;
        System.out.println("label.getText() = " + label.getText());
        label.setText("next text");
        System.out.println("label.getText() = " + label.getText());
        label.restore(save);
        System.out.println("label.getText() = " + label.getText());
    }
}
