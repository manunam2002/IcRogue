package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.Title;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.area.ConnectorInRoom;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Level0Room extends ICRogueRoom {

    Title title;

    public enum Level0Connectors implements ConnectorInRoom {

        // ordre des attributs: position, destination, orientation
        W (new DiscreteCoordinates(0, 4), new DiscreteCoordinates(8, 5), Orientation.LEFT),
        S (new DiscreteCoordinates(4, 0), new DiscreteCoordinates(5, 8), Orientation.DOWN),
        E (new DiscreteCoordinates(9, 4), new DiscreteCoordinates(1, 5), Orientation.RIGHT),
        N (new DiscreteCoordinates(4, 9), new DiscreteCoordinates(5, 1), Orientation.UP);

        final DiscreteCoordinates position;
        final DiscreteCoordinates destination;
        final Orientation orientation;

        Level0Connectors(DiscreteCoordinates position, DiscreteCoordinates destination, Orientation orientation){
            this.position = position;
            this.destination = destination;
            this.orientation = orientation;
        }

        @Override
        public DiscreteCoordinates getDestination() {
            return destination;
        }

        @Override
        public int getIndex() {
            return ordinal();
        }

        public static List<Orientation> getAllConnectorsOrientation(){
            List<Orientation> orientations = new ArrayList<>();
            for (Level0Connectors ict : Level0Connectors.values()){
                orientations.add(ict.orientation);
            }
            return orientations;
        }

        public static List<DiscreteCoordinates> getAllConnectorsPosition(){
            List<DiscreteCoordinates> positions = new ArrayList<>();
            for (Level0Connectors ict : Level0Connectors.values()){
                positions.add(ict.position);
            }
            return positions;
        }

        public static List<DiscreteCoordinates> getAllConnectorsDestination(){
            List<DiscreteCoordinates> destinations = new ArrayList<>();
            for (Level0Connectors ict : Level0Connectors.values()){
                destinations.add(ict.destination);
            }
            return destinations;
        }

        // returns a LevelO connector given an orientation
        public static Level0Connectors getConnector(Orientation orientation){
            switch (orientation){
                case LEFT -> {return Level0Connectors.W;}
                case DOWN -> {return Level0Connectors.S;}
                case RIGHT -> {return Level0Connectors.E;}
                case UP -> {return Level0Connectors.N;}
            }
            return null;
        }
    }


    @Override
    public String getTitle() {
        return "icrogue/level0"+roomCoordinates.x+roomCoordinates.y;
    }

    public Level0Room(DiscreteCoordinates roomCoordinates){
        super(Level0Connectors.getAllConnectorsPosition(),Level0Connectors.getAllConnectorsOrientation(),
                Level0Connectors.getAllConnectorsDestination(),"icrogue/Level0Room",roomCoordinates);
    }

    public Level0Room(DiscreteCoordinates roomCoordinates, String title){
        super(Level0Connectors.getAllConnectorsPosition(),Level0Connectors.getAllConnectorsOrientation(),
                Level0Connectors.getAllConnectorsDestination(),"icrogue/Level0Room",roomCoordinates);
        this.title = new Title(this,title);
    }

    @Override
    protected void initRoom() {
        super.initRoom();
        registerActor(new Background(this, behaviorName));
        if (title != null){
            registerActor(title);
        }
        //registerActor(new Cherry(this, Orientation.DOWN,new DiscreteCoordinates(6,3)));
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        02.12.2002
 */

