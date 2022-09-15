package by.it_academy.lesson3;

public class Marketing {

    public static void main(String[] args) {
        String desire = "phone";
        switch (desire) {
            case "phone":
                System.out.println("Buy a phone!");
                break;
            case "car":
                System.out.println("Buy a car!");
                break;
            case "house":
                System.out.println("Buy a house!");
                break;
            default:
                System.out.println("Buy something!");
        }
    }
}
