package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

import java.util.ArrayList;
import java.util.Arrays;

abstract public class Level implements Logic {

    // defines the Mapstate for the room placement
    protected enum MapState {
        NULL, // Empty space
        PLACED, // The room has been placed but not yet explored by the room placement algorithm
        EXPLORED, // The room has been placed and explored by the algorithm
        BOSS_ROOM, // The room is a boss room
        CREATED; // The room has been instantiated in the room map

        @Override
        public String toString() {
            return Integer.toString(ordinal());
        }
    }

    private ICRogueRoom [][] map; //widht, height

    DiscreteCoordinates arrivalCoordinates;

    DiscreteCoordinates bossRoomPosition;

    String intialRoomName;

    private int nbrooms;

    // sets a room in the map
    protected void setRoom(DiscreteCoordinates coords, ICRogueRoom room){
        map[coords.x][coords.y] = room;
    }

    // sets a connector destination name
    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination, ConnectorInRoom connector){
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setDestinationName(destination);
    }

    // sets a connector destination name and closes it
    protected void setRoomConnector(DiscreteCoordinates coords, String destination, ConnectorInRoom connector){
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setDestinationName(destination);
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setType(Connector.ConnectorType.CLOSED);
    }

    // locks a connector with a given identifier
    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector, int keyId){
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setIdentifier(keyId);
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setType(Connector.ConnectorType.LOCKED);
    }

    // sets the initial room name given a position in the map
    protected void setIntialRoomName(DiscreteCoordinates coords){
        intialRoomName = map[coords.x][coords.y].getTitle();
    }

    public String getIntialRoomName() {
        return intialRoomName;
    }

    // can generate a level with a fixed map or a random one, depending on the value of the randomMap parameter
    public Level(boolean randomMap, DiscreteCoordinates arrivalCoordinates,int[] roomsDistribution, int width, int height){
        this.arrivalCoordinates = arrivalCoordinates;
        if (!randomMap){
            map = new ICRogueRoom[width][height];
            generateFixedMap();
            bossRoomPosition = new DiscreteCoordinates(0,0);
        } else {
            for (int val : roomsDistribution){
                nbrooms += val;
            }
            map = new ICRogueRoom[nbrooms][nbrooms];
            generateRandomMap(roomsDistribution);
        }
    }

    // to be defined in all levels
    public abstract void generateFixedMap();

    // generates a random map given a room distribution
    private void generateRandomMap(int[] roomsDistribution){
        MapState[][] roomPlacement = generateRandomRoomPlacement();
        ArrayList <DiscreteCoordinates> placedRooms = new ArrayList<>();
        for (int i = 0 ; i < roomPlacement.length ; ++i){
            for (int j = 0 ; j < roomPlacement[i].length ; ++j){
                if (roomPlacement[i][j].equals(MapState.PLACED)||roomPlacement[i][j].equals(MapState.EXPLORED)){
                    placedRooms.add(new DiscreteCoordinates(i,j));
                }
            }
        }
        for (int i = 0 ; i < roomsDistribution.length ; ++i){
            int roomsToCreate = roomsDistribution[i];
            while (roomsToCreate > 0){
                int idx = RandomHelper.roomGenerator.nextInt(placedRooms.size());
                DiscreteCoordinates roomCoords = placedRooms.get(idx);
                setUpRoom(i,roomCoords);
                setUpConnector(roomPlacement,map[roomCoords.x][roomCoords.y]);
                roomPlacement[roomCoords.x][roomCoords.y] = MapState.CREATED;
                placedRooms.remove(idx);
                --roomsToCreate;
            }
        }
        setUpBossRoom(bossRoomPosition);
        setUpConnector(roomPlacement,map[bossRoomPosition.x][bossRoomPosition.y]);
    }


    abstract public void setUpRoom(int idx, DiscreteCoordinates coordinates);

    abstract public void setUpBossRoom(DiscreteCoordinates coordinates);

    abstract public void setUpConnector(MapState[][] roomsPlacement, ICRogueRoom room);

    // generates a random room placement map
    protected MapState[][] generateRandomRoomPlacement(){
        int roomsToPlace = nbrooms;
        MapState[][] roomPlacement = new MapState[nbrooms][nbrooms];
        for (int i = 0 ; i < roomPlacement.length ; ++i){
            Arrays.fill(roomPlacement[i], MapState.NULL);
        }
        ArrayList<DiscreteCoordinates> placedRooms = new ArrayList<>();
        int centerWidth = roomPlacement.length/2;
        int centerHeight = roomPlacement[centerWidth].length/2;
        roomPlacement[centerWidth][centerHeight] = MapState.PLACED;
        placedRooms.add(new DiscreteCoordinates(centerWidth,centerHeight));
        --roomsToPlace;
        while (roomsToPlace>0){
            int index = RandomHelper.roomGenerator.nextInt(placedRooms.size());
            DiscreteCoordinates exploredRoom = placedRooms.get(index);
            placedRooms.remove(index);
            int x = exploredRoom.x;
            int y = exploredRoom.y;
            roomPlacement[x][y] = MapState.EXPLORED;
            ArrayList<DiscreteCoordinates> freeSlots = getFreeSlots(roomPlacement,x,y);
            int newRoomsPlaced = 0;
            if (freeSlots.size()>roomsToPlace){
                newRoomsPlaced = RandomHelper.roomGenerator.nextInt(1,roomsToPlace+1);
            } else {
                newRoomsPlaced = RandomHelper.roomGenerator.nextInt(1,freeSlots.size()+1);
            }
            roomsToPlace -= newRoomsPlaced;
            while (newRoomsPlaced>0){
                int idx = RandomHelper.roomGenerator.nextInt(freeSlots.size());
                DiscreteCoordinates roomCords = freeSlots.get(idx);
                freeSlots.remove(idx);
                roomPlacement[roomCords.x][roomCords.y] = MapState.PLACED;
                placedRooms.add(roomCords);
                --newRoomsPlaced;
            }
        }
        boolean bossRoomPlaced = false;
        while (!bossRoomPlaced){
            int idx = RandomHelper.roomGenerator.nextInt(placedRooms.size());
            DiscreteCoordinates bossNeighbourCoords = placedRooms.get(idx);
            int x = bossNeighbourCoords.x;
            int y = bossNeighbourCoords.y;
            ArrayList<DiscreteCoordinates> freeSlots = getFreeSlots(roomPlacement,x,y);
            if (!freeSlots.isEmpty()){
                int i = RandomHelper.roomGenerator.nextInt(freeSlots.size());
                DiscreteCoordinates bossCoords = freeSlots.get(i);
                roomPlacement[bossCoords.x][bossCoords.y] = MapState.BOSS_ROOM;
                bossRoomPlaced = true;
                bossRoomPosition = bossCoords;
            }
        }
        printMap(roomPlacement);
        return roomPlacement;
    }

    // prints a room placement map
    private void printMap(MapState[][] map) {
        System.out.println("Generated map:");
        System.out.print(" | ");
        for (int j = 0; j < map[0].length; j++) {
            System.out.print(j + " "); }
        System.out.println(); System.out.print("--|-");
        for (int j = 0; j < map[0].length; j++) {
            System.out.print("--"); }
        System.out.println();
        for (int i = 0; i < map.length; i++) { System.out.print(i + " | ");
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[j][i] + " "); }
            System.out.println(); }
        System.out.println();
    }

    // checks if a given position is in a given map bounds
    protected boolean isInMapBounds(int i,int j,MapState[][] mapStates){
        if ((i<mapStates.length) && (i>=0)){
            if ((j<mapStates[i].length) && (j>=0)){
                return true;
            }
        }
        return false;
    }

    // returns an arraylist of all free slots next to a given position in a given map
    private ArrayList<DiscreteCoordinates> getFreeSlots(MapState[][] mapState, int x, int y){
        ArrayList<DiscreteCoordinates> freeSlots = new ArrayList<>();
        if (isInMapBounds(x+1,y,mapState)){
            if (mapState[x+1][y].equals(MapState.NULL)){
                freeSlots.add(new DiscreteCoordinates(x+1,y));
            }
        }
        if (isInMapBounds(x-1,y,mapState)){
            if (mapState[x-1][y].equals(MapState.NULL)){
                freeSlots.add(new DiscreteCoordinates(x-1,y));
            }
        }
        if (isInMapBounds(x,y+1,mapState)){
            if (mapState[x][y+1].equals(MapState.NULL)){
                freeSlots.add(new DiscreteCoordinates(x,y+1));
            }
        }
        if (isInMapBounds(x,y-1,mapState)){
            if (mapState[x][y-1].equals(MapState.NULL)){
                freeSlots.add(new DiscreteCoordinates(x,y-1));
            }
        }
        return freeSlots;
    }

    // adds oll the rooms of the map to the game
    public void addRooms(AreaGame game){
        for (int i = 0 ; i < map.length ; ++i){
            for (int j = 0 ; j < map[i].length; ++j){
                if (map[i][j]!=null){
                    game.addArea(map[i][j]);
                }
            }
        }
    }

    // a level is solved if the boss room is solved
    @Override
    public boolean isOn() {
        if (map[bossRoomPosition.x][bossRoomPosition.y].isOn()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isOff() {
        return !isOn();
    }

    @Override
    public float getIntensity() {
        return 0;
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        09.12.2022
 */

