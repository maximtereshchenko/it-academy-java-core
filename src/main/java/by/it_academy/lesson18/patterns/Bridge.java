package by.it_academy.lesson18.patterns;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

interface RemoteControl {

    void turnUpVolume();

    void turnDownVolume();
}

interface TvSet {

    int currentVolume();

    void setVolume(int target);
}

class Application implements RemoteControl {

    private final TvSet tvSet;

    Application(TvSet tvSet) {
        this.tvSet = tvSet;
    }

    @Override
    public void turnUpVolume() {
        System.out.println("using app");
        tvSet.setVolume(tvSet.currentVolume() + 1);
    }

    @Override
    public void turnDownVolume() {
        System.out.println("using app");
        tvSet.setVolume(tvSet.currentVolume() - 1);
    }
}

class Son implements RemoteControl {

    private final TvSet tvSet;

    Son(TvSet tvSet) {
        this.tvSet = tvSet;
    }

    @Override
    public void turnUpVolume() {
        System.out.println("cry");
        tvSet.setVolume(tvSet.currentVolume() + 1);
    }

    @Override
    public void turnDownVolume() {
        System.out.println("cry");
        tvSet.setVolume(tvSet.currentVolume() - 1);
    }
}

class ModernTv implements TvSet {

    private int volume = 0;

    @Override
    public int currentVolume() {
        return volume;
    }

    @Override
    public void setVolume(int target) {
        volume = target;
    }
}

class OldTv implements TvSet {

    private final Random random = ThreadLocalRandom.current();
    private int volume = 0;

    @Override
    public int currentVolume() {
        return volume;
    }

    @Override
    public void setVolume(int target) {
        volume = target + random.nextInt(3) - 1;
    }
}

/**
 * @author Maxim Tereshchenko
 */
class Bridge {

    public static void main(String[] args) {
        TvSet modernTv = new ModernTv();
        TvSet oldTv = new OldTv();

        RemoteControl application = new Application(modernTv);
        RemoteControl son = new Son(oldTv);

        System.out.println("oldTv.currentVolume() = " + oldTv.currentVolume());
        son.turnUpVolume();
        son.turnUpVolume();
        System.out.println("oldTv.currentVolume() = " + oldTv.currentVolume());

        System.out.println("modernTv.currentVolume() = " + modernTv.currentVolume());
        application.turnUpVolume();
        application.turnUpVolume();
        System.out.println("modernTv.currentVolume() = " + modernTv.currentVolume());
    }
}