package model;

public enum Difficulty {
    EASY(250),
    MEDIUM(150),
    HARD(80);

    private final int tickMs;

    Difficulty(int tickMs) {
        this.tickMs = tickMs;
    }

    public int getTickMs() {
        return tickMs;
    }

    public Difficulty next() {
        Difficulty[] values = values();
        return values[(ordinal() + 1) % values.length];
    }
}
