package ch.epfl.cs107.play.game.icrogue.area.level0;

import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0KeyRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0StaffRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0TurretRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level00 extends Level {

    private static final int PART_1_KEY_ID = 1;
    private static final int BOSS_KEY_ID = 1;

    @Override
    public void generateFixedMap() {
        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0KeyRoom(room00, PART_1_KEY_ID));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);
        lockRoomConnector(room00, Level0Room.Level0Connectors.E,  PART_1_KEY_ID);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level000", Level0Room.Level0Connectors.W);
        setIntialRoomName(room10);
    }

    @Override
    public void setUpRoom(int idx, DiscreteCoordinates coordinates) {
        switch (idx){
            case 0 -> setRoom(coordinates, new Level0TurretRoom(coordinates));
            case 1 -> setRoom(coordinates, new Level0StaffRoom(coordinates));
            case 2 -> setRoom(coordinates, new Level0KeyRoom(coordinates,BOSS_KEY_ID));
            case 3 -> {
                setRoom(coordinates, new Level0Room(coordinates,"Level 0"));
                setIntialRoomName(coordinates);
            }
            case 4 -> setRoom(coordinates, new Level0Room(coordinates));
        }
    }

    @Override
    public void setUpBossRoom(DiscreteCoordinates coordinates) {
        Level0Room bossRoom = new Level0TurretRoom(coordinates,"Boss 0");
        setRoom(coordinates,bossRoom);
    }

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

    public enum LevelRoomTypes{
        TURRET_ROOM(1),
        STAFF_ROOM(1),
        BOSS_KEY(1),
        SPAWN(1),
        NORMAL(3);

        private final int nbRooms;

        private LevelRoomTypes(int nbRooms){
            this.nbRooms = nbRooms;
        }

        public static int[] getRoomDistribution(){
            return new int[]{TURRET_ROOM.nbRooms, STAFF_ROOM.nbRooms, BOSS_KEY.nbRooms, SPAWN.nbRooms, NORMAL.nbRooms};
        }
    }

    public Level00(){
        super(true,new DiscreteCoordinates(2,0),LevelRoomTypes.getRoomDistribution(),4,2);
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        08.01.2023
 */

