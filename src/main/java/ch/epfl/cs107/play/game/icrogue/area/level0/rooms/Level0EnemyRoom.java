package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.enemies.Enemy;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Level0EnemyRoom extends Level0Room{

    private List<Enemy> enemies = new ArrayList<Enemy>();

    private int count = 2;

    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }

    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates, String title) {
        super(roomCoordinates,title);
    }

    protected void setEnemies(List<Enemy> enemies){
        this.enemies = enemies;
    }

    // registers in the area all enemies on the list
    @Override
    protected void initRoom() {
        super.initRoom();
        for (Enemy enemy : enemies) {
            registerActor(enemy);
        }
    }

    // checks if enemies are alive, if not unregisters them
    @Override
    public void update(float deltaTime) {
        ++count;
        if (count >= 2){
            for (Enemy enemy : enemies){
                if (!enemy.isAlive()){
                    if (exists(enemy)){
                        unregisterActor(enemy);
                    }
                }
            }
            count = 0;
        }
        super.update(deltaTime);
    }

    // an enemy room is solved when all enemies are killed
    @Override
    public boolean isOn() {
        for (Enemy enemy : enemies){
            if (enemy.isAlive()){return false;}
        }
        return true;
    }

    @Override
    public boolean isOff() {
        return !isOn();
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        11.12.2022
 */

