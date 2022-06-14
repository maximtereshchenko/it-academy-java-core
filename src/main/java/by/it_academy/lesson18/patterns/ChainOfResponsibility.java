package by.it_academy.lesson18.patterns;

/**
 * @author Maxim Tereshchenko
 */
interface Filter {
    boolean isValid(String str);
}

class AllUpperCase implements Filter {

    private final Filter next;

    AllUpperCase(Filter next) {
        this.next = next;
    }

    @Override
    public boolean isValid(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLowerCase(str.charAt(i))) {
                return false;
            }
        }
        return next.isValid(str);
    }
}

class WithoutA implements Filter {

    private final Filter next;

    WithoutA(Filter next) {
        this.next = next;
    }

    @Override
    public boolean isValid(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'A') {
                return false;
            }
        }
        return next.isValid(str);
    }
}

class AlwaysValid implements Filter {

    @Override
    public boolean isValid(String str) {
        return true;
    }
}

class ChainOfResponsibility {

    public static void main(String[] args) {
        Filter filter = new AllUpperCase(new WithoutA(new AlwaysValid()));

        System.out.println("filter.isValid(\"abc\") = " + filter.isValid("abc"));
        System.out.println("filter.isValid(\"ABC\") = " + filter.isValid("ABC"));
        System.out.println("filter.isValid(\"BCD\") = " + filter.isValid("BCD"));
    }
}
