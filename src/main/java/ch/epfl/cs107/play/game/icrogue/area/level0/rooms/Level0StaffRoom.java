package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0StaffRoom extends Level0ItemRoom{

    private Staff staff;

    private final DiscreteCoordinates staffPosition = new DiscreteCoordinates(5,5);

    public Level0StaffRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        staff = new Staff(this, Orientation.UP , staffPosition);
        addItem(staff);
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        09.12.2022
 */

