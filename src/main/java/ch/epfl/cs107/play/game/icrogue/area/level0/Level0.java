package ch.epfl.cs107.play.game.icrogue.area.level0;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.Title;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.*;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

import java.awt.*;


public class Level0 extends Level {

    private static final int PART_1_KEY_ID = 1;
    private static final int BOSS_KEY_ID = 1;

    // defines all Level0 room types and their distribution
    public enum Level0RoomTypes{
        TURRET_ROOM(3),
        STAFF_ROOM(1),
        BOSS_KEY(1),
        SPAWN(1),
        NORMAL(1);

        private final int nbRooms;

        private Level0RoomTypes(int nbRooms){
            this.nbRooms = nbRooms;
        }

        public static int[] getRoomDistribution(){
            return new int[]{TURRET_ROOM.nbRooms, STAFF_ROOM.nbRooms, BOSS_KEY.nbRooms, SPAWN.nbRooms, NORMAL.nbRooms};
        }
    }

    public Level0(boolean randomMap){
        super(randomMap, new DiscreteCoordinates(2,0),Level0RoomTypes.getRoomDistribution(),4,2);
    }

    // by default a Level0 is constructed with a random map
    public Level0(){
        super(true, new DiscreteCoordinates(2,0),Level0RoomTypes.getRoomDistribution(),
                4,2);
    }

    // generates a fixed map : it is possible to choose between two given fixed map
    @Override
    public void generateFixedMap() {
        //generateMap1();
        generateMap2();
    }

    public void generateMap1(){
        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0KeyRoom(room00, PART_1_KEY_ID));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);
        lockRoomConnector(room00, Level0Room.Level0Connectors.E,  PART_1_KEY_ID);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level000", Level0Room.Level0Connectors.W);
        setIntialRoomName(room10);
    }

    public void generateMap2(){
        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0TurretRoom(room00,"BossRoom"));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1,0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level011", Level0Room.Level0Connectors.S);
        setRoomConnector(room10, "icrogue/level020", Level0Room.Level0Connectors.E);

        lockRoomConnector(room10, Level0Room.Level0Connectors.W,  BOSS_KEY_ID);
        setRoomConnectorDestination(room10, "icrogue/level000", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room20 = new DiscreteCoordinates(2,0);
        setRoom(room20,  new Level0StaffRoom(room20));
        setRoomConnector(room20, "icrogue/level010", Level0Room.Level0Connectors.W);
        setRoomConnector(room20, "icrogue/level030", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room30 = new DiscreteCoordinates(3,0);
        setRoom(room30, new Level0KeyRoom(room30, BOSS_KEY_ID));
        setRoomConnector(room30, "icrogue/level020", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room11 = new DiscreteCoordinates(1, 1);
        setRoom (room11, new Level0Room(room11,"Level 0"));
        setRoomConnector(room11, "icrogue/level010", Level0Room.Level0Connectors.N);
        setIntialRoomName(room11);
    }

    // sets a room given a position in the map and an index in the Level0 room types enum
    @Override
    public void setUpRoom(int idx, DiscreteCoordinates coordinates){
        switch (idx){
            case 0 -> setRoom(coordinates, new Level0TurretRoom(coordinates));
            case 1 -> setRoom(coordinates, new Level0StaffRoom(coordinates));
            case 2 -> setRoom(coordinates, new Level0KeyRoom(coordinates,BOSS_KEY_ID));
            case 3 -> {
                setRoom(coordinates, new Level0Room(coordinates,"Level 1"));
                setIntialRoomName(coordinates);
            }
            case 4 -> setRoom(coordinates, new Level0LootRoom(coordinates));
        }
    }

    // sets all connector for a given room given the room respective placement
    @Override
    public void setUpConnector(MapState[][] roomsPlacement, ICRogueRoom room) {
        DiscreteCoordinates roomCoords = room.getRoomCoordinates();
        for (Connector connector : room.getConnectors()){
            DiscreteCoordinates destination = roomCoords.jump(connector.getOrientation().toVector());
            if (isInMapBounds(destination.x,destination.y,roomsPlacement)){
                if (!roomsPlacement[destination.x][destination.y].equals(MapState.NULL)){
                    if (roomsPlacement[destination.x][destination.y].equals(MapState.BOSS_ROOM)){
                        lockRoomConnector(roomCoords,
                                Level0Room.Level0Connectors.getConnector(connector.getOrientation().opposite()),BOSS_KEY_ID);
                        setRoomConnectorDestination(roomCoords,"icrogue/level0"+destination.x+destination.y,
                                Level0Room.Level0Connectors.getConnector(connector.getOrientation().opposite()));
                    } else {
                        setRoomConnector(roomCoords,"icrogue/level0"+destination.x+destination.y,
                                Level0Room.Level0Connectors.getConnector(connector.getOrientation().opposite()));
                    }
                }
            }
        }
    }

    // sets the boss room given a position in the map
    @Override
    public void setUpBossRoom(DiscreteCoordinates coordinates) {
        //Level0Room bossRoom = new Level0TurretRoom(coordinates,"Boss 0");
        //setRoom(coordinates,bossRoom);
        Level0Room bossRoom = new Level0DarkLordRoom(coordinates,"Boss 1");
        setRoom(coordinates,bossRoom);
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        09.12.2022
 */

