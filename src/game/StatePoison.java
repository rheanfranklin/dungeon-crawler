package game;

public class StatePoison {
    private static final int DEFAULT_DAMAGE = 10;
    private static final int DEFAULT_TIME_LENGTH = 100;
    private static final int DEFAULT_LOOP_LENGTH = 20;


    private final int damage;
    private final int timeLength;
    private final Monster poisonedMonster;
    private final int loopLength;


    public StatePoison(Monster monster, int damage, int timeLength, int loopLength) {
        this.poisonedMonster = monster;
        this.damage = damage;
        this.timeLength = timeLength;
        this.loopLength = loopLength;

        poisonedMonster.setState(State.POISONED);
    }

    public StatePoison(Monster monster) {
        this(monster, DEFAULT_DAMAGE, DEFAULT_TIME_LENGTH, DEFAULT_LOOP_LENGTH);
    }

    public void poisonMonster() {
        poisonedMonster.takeDamage(damage);
    }

    public int getDamage() {
        return damage;
    }

    public int getTimeLength() {
        return timeLength;
    }

    public int getLoopLength() {
        return loopLength;
    }

}
