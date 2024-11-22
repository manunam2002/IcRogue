package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0LootRoom extends Level0Room{

    public Level0LootRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }

    @Override
    protected void initRoom() {
        super.initRoom();
        registerActor(new Cherry(this, Orientation.UP,new DiscreteCoordinates(2,2)));
        registerActor(new Cherry(this,Orientation.UP,new DiscreteCoordinates(7,7)));
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        08.01.2022
 */

