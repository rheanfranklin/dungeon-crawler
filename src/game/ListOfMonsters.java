package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Generates list of all possible monsters
 * to select from when randomly assigning
 * monsters to the rooms that the player
 * navigates through
 */
public class ListOfMonsters {

    /**
     * Hash map that uses monster rarity as keys
     */
    private HashMap<Rarity, List<Monster>> monsterList;

    /**
     * Constructor for MonsterList
     */
    public ListOfMonsters() {
        monsterList = new HashMap<>();
    }

    /**
     * Adds all the common monsters to the hashmap
     */
    private void addCommonMonsters() {
        ListOfItems listOfItems = new ListOfItems();

        monsterList.put(Rarity.COMMON, new ArrayList<>());

        Rarity rarity = Rarity.COMMON;

        ImageView fishImage = new ImageView(new Image("resources/Monsters/Fish.png"));
        fishImage.setFitHeight(150);
        fishImage.setFitWidth(150);

        //setting up sequences for each monster
        SequenceOgre sequenceOgre = new SequenceOgre();
        SequenceBabySpider sequenceBabySpider = new SequenceBabySpider();
        SequenceGhost sequenceGhost = new SequenceGhost();
        SequenceMonster sequenceFish = new SequenceMonster(fishImage);

        Monster ogre = new Monster("Ogre", 100, 10, 15, sequenceOgre);
        Monster babySpider = new Monster("Baby Spider", 75, 15, 15, sequenceBabySpider);
        Monster ghost = new Monster("Ghost", 80, 3, 15, sequenceGhost);
        Monster fish = new Monster("Fish", 10, 5, 5, sequenceFish);

        ogre.setItemDropType(Monster.ItemDropType.COMMON_CONSUMABLE);
        babySpider.setItemDropType(Monster.ItemDropType.COMMON_CONSUMABLE);
        ghost.setItemDropType(Monster.ItemDropType.COMMON_CONSUMABLE);
        fish.setItemDropType(Monster.ItemDropType.COMMON_CONSUMABLE);

        //adding the common monsters to array list associated with common key
        monsterList.get(rarity).add(ogre);
        monsterList.get(rarity).add(babySpider);
        monsterList.get(rarity).add(ghost);
        monsterList.get(rarity).add(fish);

    }

    /**
     * Adds uncommon monsters to hashmap
     */
    private void addUncommonMonsters() {
        monsterList.put(Rarity.UNCOMMON, new ArrayList<>());

        Rarity rarity = Rarity.UNCOMMON;

        ImageView justinImage = new ImageView(new Image("resources/Monsters/JustinBeiber.png"));
        ImageView beeImage = new ImageView(new Image("resources/Monsters/Bee.png"));

        justinImage.setFitHeight(250);
        justinImage.setFitWidth(150);

        beeImage.setFitHeight(150);
        beeImage.setFitWidth(150);

        SequenceJustin sequenceJustin = new SequenceJustin();
        SequenceMonster sequenceBee = new SequenceMonster(beeImage);

        Monster bee = new Monster("Bee", 80, 10, 20, sequenceBee);
        Monster justinBieber = new Monster("Justin Bieber", 90, 10, 20, sequenceJustin);

        bee.setItemDropType(Monster.ItemDropType.COMMON_CONSUMABLE);
        justinBieber.setItemDropType(Monster.ItemDropType.COMMON_CONSUMABLE);


        monsterList.get(rarity).add(bee);
        monsterList.get(rarity).add(justinBieber);
    }

    /**
     * Adds rare monsters to hash map
     */
    private void addRareMonsters() {
        monsterList.put(Rarity.RARE, new ArrayList<>());

        Rarity rarity = Rarity.RARE;

        ImageView succubusImage =
                new ImageView(new Image("resources/Monsters/Succubus/SuccubusStatic.png"));
        succubusImage.setFitHeight(400);
        succubusImage.setFitWidth(400);

        ImageView wolfImage =
                new ImageView(new Image("resources/Monsters/WolfMonster.png"));
        wolfImage.setFitHeight(300);
        wolfImage.setFitWidth(300);

        ImageView bigSpiderImage =
                new ImageView(new Image("resources/Monsters/BigSpider.png"));
        bigSpiderImage.setFitHeight(250);
        bigSpiderImage.setFitHeight(250);

        SequenceSuccubus sequenceSuccubus = new SequenceSuccubus();
        SequenceMonster sequenceMonster = new SequenceMonster(wolfImage);
        SequenceBigSpider sequenceBigSpider = new SequenceBigSpider();

        Monster wolf = new Monster("Wolf", 500, 50, 150, sequenceMonster);
        Monster succubus = new Monster("Succubus", 200, 20, 50, sequenceSuccubus);
        Monster bigSpider = new Monster("Big Spider", 200, 20, 50, sequenceBigSpider);

        succubus.setItemDropType(Monster.ItemDropType.SUPER_RARE_CONSUMABLE);
        bigSpider.setItemDropType(Monster.ItemDropType.SUPER_RARE_CONSUMABLE);


        monsterList.get(rarity).add(wolf);
        monsterList.get(rarity).add(succubus);
        monsterList.get(rarity).add(bigSpider);
    }

    /**
     * Generates and returns random monster
     * Whether monster is returned depends on rarity
     *
     * @return a randomly generated monster
     */
    public Monster returnRandomMonster() {
        Random random = new Random(System.currentTimeMillis());
        Monster returnMonster;
        int index = Math.abs(random.nextInt());

        //returns a rare, uncommon, or common monster
        //based on whether the random int is divisible by 3 or 7
        //FIXME: altered to show which monster I wont
        if (index % 3 == 0) {
            addUncommonMonsters();
            index = index % monsterList.get(Rarity.UNCOMMON).size();
            returnMonster = monsterList.get(Rarity.UNCOMMON).get(index);
        } else {
            addCommonMonsters();
            index = index % monsterList.get(Rarity.COMMON).size();
            returnMonster = monsterList.get(Rarity.COMMON).get(index);
        }
        return returnMonster;
    }

    /**
     * Generates the monster that will go in the first challenge room
     *
     * @return monster that goes in the first challenge room (succubus)
     */
    public Monster returnChallengeOneMonster() {
        addRareMonsters();
        return monsterList.get(Rarity.RARE).get(1);
    }

    /**
     * Generates the monster that will go in the second challenge room
     *
     * @return monster that goes in the second challenge room (big spider)
     */
    public Monster returnChallengeTwoMonster() {
        //TODO: add the other rare monster
        addRareMonsters();
        return monsterList.get(Rarity.RARE).get(2);
    }

    public Monster returnFinalBoss() {
        addRareMonsters();
        return monsterList.get(Rarity.RARE).get(0);
    }
}
