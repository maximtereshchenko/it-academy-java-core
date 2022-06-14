package by.it_academy.lesson18.patterns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
interface Command {

    void execute();

    void revert();
}

class Wallet {

    private int money = 0;

    int money() {
        return money;
    }

    void add(int amount) {
        money += amount;
    }

    void remove(int amount) {
        money -= amount;
    }
}

class Bank {

    private final List<Command> commands = new ArrayList<>();

    void receive(Command command) {
        command.execute();
        commands.add(command);
    }

    void revertLast() {
        int lastIndex = commands.size() - 1;
        commands.get(lastIndex).revert();
        commands.remove(lastIndex);
    }
}

class AddCommand implements Command {

    private final Wallet wallet;
    private final int amount;

    AddCommand(Wallet wallet, int amount) {
        this.wallet = wallet;
        this.amount = amount;
    }

    @Override
    public void execute() {
        wallet.add(amount);
    }

    @Override
    public void revert() {
        wallet.remove(amount);
    }
}

class CommandExample {

    public static void main(String[] args) {
        Wallet wallet = new Wallet();
        Bank bank = new Bank();
        Command command = new AddCommand(wallet, 10);

        System.out.println("wallet.money() = " + wallet.money());
        bank.receive(command);
        bank.receive(command);
        System.out.println("wallet.money() = " + wallet.money());
        bank.revertLast();
        System.out.println("wallet.money() = " + wallet.money());
    }
}
