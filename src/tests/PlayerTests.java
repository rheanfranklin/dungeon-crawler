package tests;

import game.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTests {
    private Player player;
    @Before
    public void setup() {

    }

    @Test
    public void difficultyEasy() {
        player = new Player(Player.Difficulty.EASY);

        assertEquals(player.getMoney(), 500);
        assertEquals(player.getHealth(), 150);
        assertEquals(player.getMana(), 150);
        assertEquals(player.getAttackDamage(), 20);
    }

    @Test
    public void difficultyNormal() {
        player = new Player(Player.Difficulty.NORMAL);

        assertEquals(player.getMoney(), 100);
        assertEquals(player.getHealth(), 100);
        assertEquals(player.getMana(), 100);
        assertEquals(player.getAttackDamage(), 15);
    }

    @Test
    public void difficultyInsane() {
        player = new Player(Player.Difficulty.INSANE);

        assertEquals(player.getMoney(), 0);
        assertEquals(player.getHealth(), 50);
        assertEquals(player.getMana(), 50);
        assertEquals(player.getAttackDamage(), 10);
    }
}
