package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.DarkLord;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Enemy;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;

public class Level0DarkLordRoom extends Level0EnemyRoom{

    public Level0DarkLordRoom(DiscreteCoordinates roomCoordinates,String title) {
        super(roomCoordinates,title);
        ArrayList<Enemy> darkLord = new ArrayList<>();
        darkLord.add(new DarkLord(this, Orientation.UP,new DiscreteCoordinates(4,5)));
        setEnemies(darkLord);
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        31.12.2022
 */

