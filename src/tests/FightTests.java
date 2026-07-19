package tests;

import game.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FightTests {
    private Player player;
    private Monster monster;


    @Before
    public void setup() {
        player = new Player(Player.Difficulty.EASY);
        monster = new Monster();
    }


    @Test
    public void playerAttacks() {
        int monsterHealth = monster.getHealth();
        int damage = player.getAttackDamage();
        player.attack(monster);
        int newHealth = monsterHealth - damage;
        assertEquals(newHealth, monster.getHealth());
    }
    @Test
    public void monsterDies() {
        while (monster.getHealth() > 0) {
            monster.takeDamage(player);
        }
        assertTrue(monster.isDead());
    }

    @Test
    public void monsterAttacks() {
        int playerHealth = player.getHealth();
        int attackDamage = monster.getAttackDamage();
        monster.attack(player);
        int newHealth = playerHealth - attackDamage;
        assertEquals(newHealth, player.getHealth());
    }

    @Test
    public void playerGetsExperience() {
        int playerExperience = player.getExperience();
        int returnExperience = monster.getReturnExperience();
        while (monster.getHealth() > 0) {
            player.attack(monster);
        }
        monster.giveExperience(player);
        int newExperience = playerExperience + returnExperience;
        assertEquals(newExperience, player.getExperience());
    }


    @Test
    public void monsterGivesExperienceOnce() {
        int playerExperience = player.getExperience();
        int returnExperience = monster.getReturnExperience();
        while (monster.getHealth() > 0) {
            player.attack(monster);
        }
        monster.giveExperience(player);
        monster.giveExperience(player);
        int newExperience = playerExperience + 2 * returnExperience;
        assertNotEquals(newExperience, player.getExperience());

    }

    @Test
    public void roomHasMonster() {
        Room room = new Room(true);
        assertTrue(room.hasMonster());
    }
    @Test
    public void roomsHaveMonster() {
        Floor floor = new Floor();

        for (int i = 0; i < floor.getRowCapacity(); ++i) {
            for (int j = 0; j < floor.getRoomCapacity(); ++j) {
                if (floor.getRoom(i, j) != null
                        && !floor.getRoom(i, j).equals(floor.getStartRoom())) {
                    assertTrue(floor.getRoom(i, j).hasMonster());
                }
            }
        }

    }

    @Test
    public void monstersAlive() {
        Floor floor = new Floor();

        for (int i = 0; i < floor.getRowCapacity(); ++i) {
            for (int j = 0; j < floor.getRoomCapacity(); ++j) {
                if (floor.getRoom(i, j) != null
                        && !floor.getRoom(i, j).equals(floor.getStartRoom())) {
                    assertTrue(!floor.getRoom(i, j).getMonster().isDead());
                }
            }
        }

    }

    @Test
    public void roomInitialized() {
        Room room = new Room("test", 10, 50, false);

        assertEquals("test", room.getName());
        assertEquals(10, room.getHorizontalLength());
        assertEquals(50, room.getVerticalLength());
        assertNull(room.getMonster());
    }

    @Test
    public void monsterGaveExperienceTrue() {
        while (monster.getHealth() > 0) {
            monster.takeDamage(player);
        }
        monster.giveExperience(player);

        assertTrue(monster.getGaveExperience());
    }

    @Test
    public void testStatePoison() {
        StatePoison state = new StatePoison(monster);
        assertEquals(State.POISONED, monster.getState());
        int initialHealth = monster.getHealth();
        assertEquals(10, state.getDamage());
        state.poisonMonster();
        assertEquals(initialHealth - state.getDamage(), monster.getHealth());
    }
}
