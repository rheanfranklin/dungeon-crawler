package game;

import javafx.scene.image.Image;



import java.util.Random;

/**
 * Creates an instance of a room
 *
 * @author Diva Developers
 * @version M3
 */
public class Room {

    /**
     * The size of each room will, by default, be generated randomly
     *
     * This variable set up the upper parameter for the size of a room
     *
     * Change change later, i have no concept of scale
     */
    public static final int MAX_LENGTH = 800;

    /**
     * Same things a MAX_LENGTH
     * Except sets min parameter
     */
    public static final int MIN_LENGTH = 500;

    private ListOfMonsters monsterList;

    /**
     * boolean for whether the room had been visited yet
     */
    private boolean visited;

    /**
     * Points to the rooms that are to the top/bottom/left/right of the current Room
     */
    private Room topRoom;
    private Room bottomRoom;
    private Room leftRoom;
    private Room rightRoom;
    private String roomNum;

    /**
     * The monster that is in a room
     */
    private Monster monster;

    /**
     * The merchant that is in the room
     */
    private Merchant merchant;

    /**
     * Name of room
     */
    private String name;

    /**
     * Incorporate shape later on? I was thinking true = circle; false = square;
     * we can pretend that the circles are squares when making various calculations
     * and just change where the player can move around
     * and what picture to display
     *
     * for now though, I guess just keep it at square
     */
    private boolean shape;

    /*
     * Imma upload a picture of a dungeon floor that I "made"
     */
    private Image floorPicture;

    /**
     * Variable for horizontal length of room
     */
    private int horizontalLength;

    /**
     * Variable for vertical length of room
     */
    private int verticalLength;



    /**
     * Constructor for generating room with random size
     * Uses constructor chaining
     * @param setMonster whether to set a monster or not
     */
    @SuppressWarnings("checkstyle:JavadocMethod")
    public Room(boolean setMonster) {
        this("", calculateRandom(MAX_LENGTH, MIN_LENGTH),
                calculateRandom(MAX_LENGTH, MIN_LENGTH), setMonster);
    }

    public Room(String name) {
        this(name, calculateRandom(MAX_LENGTH, MIN_LENGTH),
                calculateRandom(MAX_LENGTH, MIN_LENGTH), true);
    }

    /**
     * Constructor for generating room with given size
     *
     * @param name the name of the room
     * @param horizontalLength the horizontal length of the Room to be created
     * @param verticalLength the vertical length of the Room to be created
     * @param setMonster whether to set a monster or not
     */
    public Room(String name, int horizontalLength, int verticalLength, boolean setMonster) {
        this.horizontalLength = horizontalLength;
        this.verticalLength = verticalLength;
        this.name = name;
        monsterList = new ListOfMonsters();
        visited = false;

        //setting default values of room pointers to null
        topRoom = null;
        bottomRoom = null;
        rightRoom = null;
        leftRoom = null;
    }

    /**
     * Added this method so we can use constructor chaining
     *
     * the "this" method in constructors needs to be on the first line
     * in order to implement constructor chaining
     *
     * @param max the max value the random object can generate
     * @param min the min value the random object can generate
     * @return the random int
     */
    private static int calculateRandom(int max, int min) {
        //creating random object based on current time
        Random random = new Random(System.currentTimeMillis());

        //generating a random int within the max/min parameters handed to the method
        int generate = random.nextInt(max - min) + min;

        return generate;
    }

    /**
     * Determines whether there will be a top exit
     *
     * @param floor the floor the room is in
     * @param row the row the room is in
     * @param roomNum the room number of the room
     */
    public void setTopExit(Floor floor, int row, int roomNum) {
        if (floor.getRoom(row, roomNum) != null) {
            //iterating through the rows above the room
            for (int i = (row - 1); i >= 0; --i) {
                if (floor.getRoom(i, roomNum) != null) {
                    //current room points to the closest room above itself
                    topRoom = floor.getRoom(i, roomNum);
                    floor.getRoom(i, roomNum).bottomRoom = floor.getRoom(row, roomNum);
                    break;
                }
            }
        }
    }

    /**
     * Determines whether there will be a bottom exit
     *
     * @param floor the floor the room is in
     * @param row the row the room is in
     * @param roomNum the room number of the room
     */
    public void setBottomExit(Floor floor, int row, int roomNum) {
        if (floor.getRoom(row, roomNum) != null) {
            //iterating through the rows below the room
            for (int i = (row + 1); i < floor.getRowCapacity(); ++i) {
                if (floor.getRoom(i, roomNum) != null) {
                    //current room points to the closest room below itself
                    bottomRoom = floor.getRoom(i, roomNum);
                    floor.getRoom(i, roomNum).topRoom = floor.getRoom(row, roomNum);
                    break;
                }
            }
        }
    }

    /**
     * Determines whether there will be a left exit
     *
     * @param floor the floor the room is in
     * @param row the row the room is in
     * @param roomNum the room number of the room
     */
    public void setLeftExit(Floor floor, int row, int roomNum) {
        if (floor.getRoom(row, roomNum) != null) {
            //iterating through the rooms to the left of the room
            for (int i = roomNum - 1; i >= 0; --i) {
                if (floor.getRoom(row, i) != null) {
                    //current room points to the closest room to the left of itself
                    leftRoom = floor.getRoom(row, i);
                    floor.getRoom(row, i).rightRoom = floor.getRoom(row, roomNum);
                    break;
                }
            }
        }
    }

    /**
     * Determines whether there will be a left exit
     *
     * @param floor the floor the room is in
     * @param row the row the room is in
     * @param roomNum the room number of the room
     */
    public void setRightExit(Floor floor, int row, int roomNum) {
        //iterating through the rooms to the right of the room
        if (floor.getRoom(row, roomNum) != null) {
            for (int i = roomNum + 1; i < floor.getRowCapacity(); ++i) {
                if (floor.getRoom(row, i) != null) {
                    //current room points to the closest room to the right of itself
                    rightRoom = floor.getRoom(row, i);
                    floor.getRoom(row, i).leftRoom = floor.getRoom(row, roomNum);
                    break;
                }
            }
        }
    }

    /**
     * Adds monster to room
     *
     * @param floor the floor the room is in
     */
    public void addMonster(Floor floor) {
        if (floor.getChallengeRoomOne().equals(this)) {
            monster = monsterList.returnChallengeOneMonster();
        } else if (floor.getChallengeRoomTwo().equals(this)) {
            monster = monsterList.returnChallengeTwoMonster();
        } else if (floor.getEndRoom().equals(this)) {
            monster = monsterList.returnFinalBoss();
        } else {
            monster = monsterList.returnRandomMonster();
        }
    }

    /**
     * @return whether the room has a top exit
     */
    public boolean hasTopExit() {
        return topRoom != null;
    }

    /**
     * @return whether the room has a bottom exit
     */
    public boolean hasBottomExit() {
        return bottomRoom != null;
    }

    /**
     * @return whether the room has a left exit
     */
    public boolean hasLeftExit() {
        return leftRoom != null;
    }

    /**
     * @return whether the room has a right exit
     */
    public boolean hasRightExit() {
        return rightRoom != null;
    }

    /**
     * @return whether the room has a monster
     */
    public boolean hasMonster() {
        return monster != null;
    }

    /**
     * Adds a monster to the room
     *
     * @param monster the monster that is added to the room
     */
    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    /**
     * @return whether the room has a merchant
     */
    public boolean hasMerchant() { return merchant != null; }

    /**
     * Adds a merchant to the room
     */
    public void setMerchant() { this.merchant = new Merchant(); }

    /**
     * @return the merchant in the room
     */
    public Merchant getMerchant() { return merchant; }

    /**
     * Sets name of room
     *
     * @param name of room
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param length the length to set the vertical length to
     */
    public void setVerticalLength(int length) { verticalLength = length; }

    /**
     * @param length the length to set the horizontal length to
     */
    public void setHorizontalLength(int length) { horizontalLength = length; }

    /**
     * @return horizontal length of room
     */
    public int getHorizontalLength() {
        return horizontalLength;
    }

    /**
     * @return vertical length of room
     */
    public int getVerticalLength() {
        return verticalLength;
    }

    /**
     * @return the top room of the current room
     */
    public Room getTopRoom() {
        return topRoom;
    }

    /**
     * @return the below room of the current room
     */
    public Room getBottomRoom() {
        return bottomRoom;
    }

    /**
     * @return the left room of the current room
     */
    public Room getLeftRoom() {
        return leftRoom;
    }

    /**
     * @return the right room of the current room
     */
    public Room getRightRoom() {
        return rightRoom;
    }

    /**
     * @return name of room
     */
    public String getName() {
        return name;
    }

    /**
     * @return the monster that is in the room
     */
    public Monster getMonster() {
        return monster; }

    /**
     * @param floor the floor containing the end room
     * @return whether this room is the end room
     */
    public boolean isEndRoom(Floor floor) {
        return floor.getEndRoom().equals(this);
    }

    public boolean hasBeenVisited() {
        return visited; }

    /**
     * Sets the room number
     *
     * @param i row of room
     * @param j room number of room
     */
    public void setRoomNum(int i, int j) {
        roomNum = "[" + i + "], [" + j + "]";
    }

    public void setVisited() {
        visited = true;
    }

    /**
     * ToString method for room
     * @return the room number in a string
     */
    public String toString() {
        return "Room: " + roomNum;
    }

    public ListOfMonsters getMonsterList() {
        return monsterList;
    }

    public boolean getVisited() {
        return visited;
    }

    public void removeExitPointers(Floor floor, int rowNum, int roomNum) {

        if (topRoom != null) {
            topRoom = null;
            floor.getRoom(rowNum - 1, roomNum).bottomRoom = null;
        } else if (bottomRoom != null) {
            bottomRoom = null;
            floor.getRoom(rowNum + 1, roomNum).topRoom = null;
        } else if (leftRoom != null) {
            leftRoom = null;
            floor.getRoom(rowNum, roomNum - 1).rightRoom = null;
        } else if (rightRoom != null) {
            rightRoom = null;
            floor.getRoom(rowNum, roomNum + 1).leftRoom = null;
        }
    }

    public void setTopExit(Room room) {
        topRoom = room;
    }
    public void setBottomRoom(Room room) {
        bottomRoom = room;
    }
}


