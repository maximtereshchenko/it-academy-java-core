package by.it_academy.lesson18.patterns;

/**
 * @author Maxim Tereshchenko
 */
interface DocumentState {

    String status();

    DocumentState review();

    DocumentState publish();
}

class Draft implements DocumentState {

    @Override
    public String status() {
        return "DRAFT";
    }

    @Override
    public DocumentState review() {
        return new Reviewed();
    }

    @Override
    public DocumentState publish() {
        throw new IllegalArgumentException("review is required");
    }
}

class Reviewed implements DocumentState {

    @Override
    public String status() {
        return "REVIEW";
    }

    @Override
    public DocumentState review() {
        throw new IllegalArgumentException("already reviewed");
    }

    @Override
    public DocumentState publish() {
        return new Published();
    }
}

class Published implements DocumentState {
    @Override
    public String status() {
        return "PUBLISHED";
    }

    @Override
    public DocumentState review() {
        throw new IllegalArgumentException("already reviewed");
    }

    @Override
    public DocumentState publish() {
        throw new IllegalArgumentException("already published");
    }
}

class Document {
    private DocumentState documentState = new Draft();

    String status() {
        return documentState.status();
    }

    void review() {
        documentState = documentState.review();
    }

    void publish() {
        documentState = documentState.publish();
    }
}

class State {

    public static void main(String[] args) {
        var document = new Document();

        System.out.println("document.status() = " + document.status());
        document.review();
        System.out.println("document.status() = " + document.status());
        document.publish();
        System.out.println("document.status() = " + document.status());
    }
}
