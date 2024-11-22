package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Enemy;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;

public class Level0TurretRoom extends Level0EnemyRoom{

    // adds two turrets to the room
    public Level0TurretRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        ArrayList<Enemy> turrets = new ArrayList<Enemy>();
        turrets.add(new Turret(this, Orientation.UP,new DiscreteCoordinates(1,8),
                new Orientation[]{Orientation.DOWN, Orientation.RIGHT}));
        turrets.add(new Turret(this,Orientation.UP,new DiscreteCoordinates(8,1),
                new Orientation[]{Orientation.UP, Orientation.LEFT}));
        setEnemies(turrets);
    }

    public Level0TurretRoom(DiscreteCoordinates roomCoordinates, String title) {
        super(roomCoordinates,title);
        ArrayList<Enemy> turrets = new ArrayList<Enemy>();
        turrets.add(new Turret(this, Orientation.UP,new DiscreteCoordinates(1,8),
                new Orientation[]{Orientation.DOWN, Orientation.RIGHT}));
        turrets.add(new Turret(this,Orientation.UP,new DiscreteCoordinates(8,1),
                new Orientation[]{Orientation.UP, Orientation.LEFT}));
        setEnemies(turrets);
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        14.12.2022
 */

