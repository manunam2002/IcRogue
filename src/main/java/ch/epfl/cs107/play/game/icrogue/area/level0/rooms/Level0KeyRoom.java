package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0KeyRoom extends Level0ItemRoom{

    private Key key;

    private final DiscreteCoordinates keyPosition = new DiscreteCoordinates(5,5);

    public Level0KeyRoom(DiscreteCoordinates roomCoordinates, int keyId) {
        super(roomCoordinates);
        key = new Key(this, Orientation.LEFT,keyPosition,keyId);
        addItem(key);
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        09.12.2022
 */

