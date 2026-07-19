package game;

import java.util.Random;


/**
 * Creates a floor object
 * that holds all the Rooms that the player will navigate through
 *
 * @author Diva Delevopers
 * @version M3
 */
public class Floor {
    /**
     * The floor will be made up of 2-Dim array of rooms
     * 
     * rooms[0][0] will correspond to upper-left room
     * 
     * First column will refer to the row in the floor
     * i.e. rooms[0][X] corresponds the 1st row
     * rooms[1][X] corresponds to the 2nd row, etc.
     * 
     * second column refers to the nth room in xth row
     * i.e. rooms[X][0] corresponds to the 1st room in the Xth row
     * room[X][1] corresponds to the 2nd room in the Xth row, etc.
     * 
     * Thank you for coming to my TED talk
     */
    private final Room[][] rooms;

    /**
     * Sets the max number of rows/rooms in each row
     * 
     * So the max num of rows we can have is 12
     * and the max num of rooms per row we can have is also 12
     * 
     * The num of rows for a SPECIFIC floor
     * may not equal the num of rooms per row
     * i.e. there may be 7 rows and 10 rooms per row
     * 
     * The num of rows/rooms per row will be determined randomly in the constructor
     * and the second constructor will allow
     * us to create a floor of a specific dimension
     * 
     * feel free to edit later
     */
    private static final int MAX_LENGTH = 16;
    
    /**
     * Same rules apply for MAX_LENGTH
     * except this is set a boundary for the minimum length
     * 
     * So, there will never be less than 6 rows
     * or less than 6 rooms per row
     */
    private static final int MIN_LENGTH = 12;

    /**
     * The room that the player will start in
     */
    private static Room startRoom;

    /**
     * The room that the player will end in
     */
    private Room endRoom;

    /**
     * A variable for the capacity of rows an instance of a floor has
     * Can either be randomly generated between 12 & 6
     * or we can manually set rowCapacity by handing it to the constructor
     */
    private final int rowCapacity;

    /**
     * A variable for the capacity of rooms per row an instance of a floor has
     * Can either be randomly generated between 12 & 6
     * or we can manually set rowCapacity by handing it to the constructor
     *
     */
    private final int roomCapacity;

    /**
     * Points which room is the room the wizard is in
     */
    private Room wizardRoom;

    /**
     * Points which room is challenge room one
     */
    private Room challengeRoomOne;

    /**
     * Points which room is challenge room one
     */
    private Room challengeRoomTwo;

    /**
     * Row number of the end room
     */
    private int endRowNum;

    /**
     * Room number of the end room
     */
    private int endRoomNum;

    /**
     * Last room visited
     */
    private Room lastRoomVisited;

    /**
     * Creates rows/rooms per row based on random generation
     * Using constructor chaining
     */
    public Floor() {
        this(calculateRandom(MAX_LENGTH, MIN_LENGTH), calculateRandom(MAX_LENGTH, MIN_LENGTH));
    }





    /**
     * Creates rows/rooms per row based on given values
     *
     * @param rowCapacity the capacity of rows the floor will have
     * @param roomCapacity the capacity of rooms per row the floor will have
     */
    public Floor(int rowCapacity, int roomCapacity) {
        this.rowCapacity = rowCapacity;
        this.roomCapacity = roomCapacity;
        //creating 2-Dim array of rooms, where height = num of rows & length = num of rooms per row
        rooms = new Room[rowCapacity][roomCapacity];
    }

    /**
     * Added this method so we can use constructor chaining
     *
     * the "this" method in constructors needs to be on the first line
     * in order to implement constructor chaining
     *
     * @param max the max value the random object can generate
     * @param min the min value the random object can generate
     * @return the random number generated
     */
    private static int calculateRandom(int max, int min) {
        //creating random object based on current time
        Random random = new Random(System.currentTimeMillis());

        //generating a random int within the max/min parameters handed to the method
        return random.nextInt(max - min) + min;
    }

    /**
     * Creates the start room
     */
    public void createStartRoom() {
        Random random = new Random(System.currentTimeMillis());
        //setting row & room number of start room to random int
        //makes sure the row/room numbers are not on the last/first indices
        // (so we can add rooms to top/left/right/bottom)
        int startRowNum = random.nextInt(rowCapacity - 2) + 1;
        int startRoomNum = random.nextInt(roomCapacity - 2) + 1;

        //creating and setting startRoom
        startRoom = new Room(false);
        startRoom.setName("Start Room: " + startRowNum + ", " + startRoomNum);

        rooms[startRowNum][startRoomNum] = startRoom;

        //adding rooms to top/bottom/left/right of startRoom
        rooms[startRowNum][startRoomNum - 1] = new Room("Room "
                + startRowNum + ", " + (startRoomNum - 1));
        rooms[startRowNum][startRoomNum + 1] = new Room("Room "
                + startRowNum + ", " + (startRoomNum + 1));
        rooms[startRowNum - 1][startRoomNum] = new Room("Room "
                + (startRowNum - 1) + ", " + startRoomNum);
        rooms[startRowNum + 1][startRoomNum] =  new Room("Room "
                + (startRowNum + 1) + ", " + startRoomNum);

        //calling to create the rest of the layout
        createEndRoom(startRowNum, startRoomNum);
    }

    /**
     * Create endRoom and draws a path between startRoom and endRoom
     *
     * @param startRowNum the row number of startRoom
     * @param startRoomNum the room number of startRoom
     */
    private void createEndRoom(int startRowNum, int startRoomNum) {
        Random random = new Random(System.currentTimeMillis());

        //create end room indices on opposite side of floor of start room
        endRowNum = (startRowNum + (rowCapacity / 2)) % rowCapacity;
        endRoomNum = (startRoomNum + (roomCapacity / 2)) % roomCapacity;

        //creating end room
        endRoom = new Room("End Room: " + endRowNum + ", " + endRoomNum);

        rooms[endRowNum][endRoomNum] = endRoom;

        //Set roomNum for end room to aid with testing
        endRoom.setRoomNum(endRowNum, endRoomNum);

        //set whether we've reached the indices of the startRoom
        boolean reachedStartRow = false;
        boolean reachedStartRoomNum = false;

        //setting the current row & room to end row & room
        int currRow = endRowNum;
        int currRoom = endRoomNum;

        if (endRoomNum > startRoomNum) {
            rooms[endRowNum][(endRoomNum + 3) % roomCapacity]
                    = new Room(true);
        } else {
            rooms[endRowNum][Math.abs((endRoomNum - 3)) % roomCapacity]
                    = new Room(true);
        }

        //while we haven't reached the start room, we keep drawing our path
        while (!reachedStartRow && !reachedStartRoomNum) {
            //using random object makes the pathway more random
            int var = random.nextInt();
            //if the random int is even, then we more up/down; if odd, we move right/left
            if (var % 2 == 0) {
                //checks to see if we're in the same row as the startRoom
                //if we're underneath the start room, the we
                // move up a row and create a new room there
                if (currRow == startRowNum) {
                    reachedStartRow = true;
                } else if (currRow < startRowNum) {
                    ++currRow;
                    if (rooms[currRow][currRoom] == null) {
                        rooms[currRow][currRoom] = new Room("Room (Warmer): "
                                + currRow + ", " + currRoom);
                    }
                } else {
                    --currRow;
                    if (rooms[currRow][currRoom] == null) {
                        rooms[currRow][currRoom] = new Room("Room (Warmer) : "
                                + currRow + ", " + currRoom);
                    }
                }
            } else {
                if (currRoom == startRoomNum) {
                    reachedStartRoomNum = true;
                } else if (currRoom < startRoomNum) {
                    ++currRoom;
                    if (rooms[currRow][currRoom] == null) {
                        rooms[currRow][currRoom] = new Room("Room (Warmer): "
                                + currRow + ", " + currRoom);
                    }
                } else {
                    --currRoom;
                    if (rooms[currRow][currRoom] == null) {
                        rooms[currRow][currRoom] = new Room("Room (Warmer): "
                                + currRow + ", " + currRoom);
                    }
                }
            }
            if (var % 3 == 0) {
                rooms[currRow][currRoom].setMerchant();
            }
        }
        createChallengeRooms();
    }

    /**
     * creates the challenge rooms/final boss room
     */
    private void createChallengeRooms() {
        int challengeOneRowNum = (endRowNum + 3) % rowCapacity;
        int challengeOneRoomNum = (endRoomNum + 3) % roomCapacity;


        int challengeTwoRowNum = Math.abs((endRowNum - 3)) % rowCapacity;
        int challengeTwoRoomNum = Math.abs((endRoomNum - 3)) % roomCapacity;

        if (rooms[challengeOneRowNum][challengeOneRoomNum] == null) {
            rooms[challengeOneRowNum][challengeOneRoomNum] =
                    new Room("Room " + challengeOneRowNum + ", " + challengeOneRoomNum);
        }
        if (rooms[challengeTwoRowNum][challengeTwoRoomNum] == null) {
            rooms[challengeTwoRowNum][challengeTwoRoomNum] =
                    new Room("Room " + challengeTwoRowNum + ", " + challengeTwoRoomNum);
        }

        challengeRoomOne = rooms[challengeOneRowNum][challengeOneRoomNum];
        challengeRoomTwo = rooms[challengeTwoRowNum][challengeTwoRoomNum];

        challengeRoomOne.setVerticalLength(750);
        challengeRoomOne.setHorizontalLength(750);

        challengeRoomTwo.setVerticalLength(750);
        challengeRoomTwo.setHorizontalLength(750);

        createRestOfLayOut();
    }

    /**
     * Creates the rest of the layout of the floor
     * that doesn't include startRoom, endRoom, or the pathway between them
     */
    private void createRestOfLayOut() {
        //creating random object based on current time
        Random random = new Random(System.currentTimeMillis());

        //iterating through each row
        for (int i = 0; i < rowCapacity; ++i) {
            //iterating through each space for each room in capacity
            for (int j = 0; j < roomCapacity; ++j) {
                //deciding whether to add a room based on random object
                //making sure the room is null
                if (random.nextInt() % 8 == 0 && rooms[i][j] == null) {
                    rooms[i][j] = new Room(true);
                    rooms[i][j].setName("Room: " + i + ", " + j);
                    if (random.nextInt() % 3 == 0) {
                        rooms[i][j].setMerchant();
                    }
                }
            }
        }
        //calls connect rooms so that all rooms points to any rooms to the
        // top, bottom, left, or right of itself
        connectEndRoom();
    }

    private void connectEndRoom() {
        rooms[endRowNum][endRoomNum].setTopExit(this, endRowNum, endRoomNum);
        rooms[endRowNum][endRoomNum].setBottomExit(this, endRowNum, endRoomNum);
        rooms[endRowNum][endRoomNum].setLeftExit(this, endRowNum, endRoomNum);
        rooms[endRowNum][endRoomNum].setRightExit(this, endRowNum, endRoomNum);
        connectRooms();
    }




    /**
     * Connects the rooms together so player can navigate between them
     *
     * Sets the pointers of each room
     * to the rooms that are to the top/bottom/left/right of the current room
     *
     * Made this private so that it will only be called whenever createRestOfLayOut is called
     *
     */
    private void connectRooms() {
        //iterating through the rows
        for (int i = 0; i < rowCapacity; ++i) {
            //checking row isn't null
            if (rooms[i] != null) {
                //iterating through each room
                for (int j = 0; j < roomCapacity; ++j) {
                    //checking room isn't null
                    if (rooms[i][j] != null) {
                        //if room isn't null, then check all sides of room to see
                        // if there needs to be an exit added
                        rooms[i][j].setTopExit(this, i, j);
                        rooms[i][j].setBottomExit(this, i, j);
                        rooms[i][j].setLeftExit(this, i, j);
                        rooms[i][j].setRightExit(this, i, j);
                    }
                }
            }
        }
        setWizardRoom();
    }

    /**
     * Sets the room the final boss is going to be in
     */
    private void setWizardRoom() {
        int wizardRowNum = (endRowNum + 1) % rowCapacity;
        int wizardRoomNum = endRoomNum;

        if (rooms[wizardRowNum][wizardRoomNum] == null) {
            rooms[wizardRowNum][wizardRoomNum] = new Room(false);
        }
        wizardRoom = rooms[wizardRowNum][wizardRoomNum];

        wizardRoom.removeExitPointers(this, wizardRowNum, wizardRoomNum);

        if (endRoom.getBottomRoom() != null && endRowNum - 1 > -1) {
            endRoom.getBottomRoom().removeExitPointers(this, endRowNum - 1, endRoomNum);
        }
        endRoom.setBottomRoom(wizardRoom);
        wizardRoom.setTopExit(endRoom);
        wizardRoom.setHorizontalLength(750);
        wizardRoom.setVerticalLength(750);
        endRoom.setHorizontalLength(750);
        endRoom.setVerticalLength(750);
    }



    /**
     * @return the room the player starts in
     */
    public Room getStartRoom() {
        return startRoom;
    }

    /**
     * @return the row capacity of a floor
     */
    public int getRowCapacity() {
        return  rowCapacity;
    }

    /**
     * @return the room capacity of each row of a floor
     */
    public int getRoomCapacity() {
        return  roomCapacity;
    }

    /**
     * Returns a Room at a given index
     *
     * @param row the row the Room is in
     * @param roomNum the room number of the Room to be return
     * @return the room given by index [row, roomNum]
     */
    public Room getRoom(int row, int roomNum) {
        return rooms[row][roomNum];
    }

    /**
     * @return the end room for the floor
     */
    public Room getEndRoom() {
        return endRoom;
    }

    /**
     * @return the final boss room that is right before the end room
     */
    public Room getWizardRoom() {
        return wizardRoom;
    }

    /**
     * @return challenge room one
     */
    public Room getChallengeRoomOne() { return challengeRoomOne; }

    /**
     * @return challenge room two
     */
    public Room getChallengeRoomTwo() { return challengeRoomTwo; }

    /**
     * @param room the last room the player visited
     */
    public void setLastRoomVisited(Room room) { lastRoomVisited = room; }

    /**
     * @return the last room the player visited
     */
    public Room getLastRoomVisited() { return lastRoomVisited; }

}
