package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.Level0;
import ch.epfl.cs107.play.game.icrogue.area.level0.Level00;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;

public class ICRogue extends AreaGame {

    private ICRoguePlayer player;

    private Level currentLevel;

    private ArrayList<Level> levels = new ArrayList<>();

    // creates a level0 with a random map, by changing the parameter of Level0 to false it is possible to create
    // a fixed map instead, creates a player and registers him in the current area
    private void initLevel(){
        levels.add(new Level00());
        levels.add(new Level0());
        currentLevel = levels.get(0);
        currentLevel.addRooms(this);
        setCurrentArea(currentLevel.getIntialRoomName(), true);
        player = new ICRoguePlayer(getCurrentArea(), Orientation.UP, new DiscreteCoordinates(2,2));
        getCurrentArea().registerActor(player);
        ((ICRogueRoom)getCurrentArea()).isVisited();
    }

    private void nextLevel(){
        currentLevel = levels.get(1);
        currentLevel.addRooms(this);
        setCurrentArea(currentLevel.getIntialRoomName(),true);
        player = new ICRoguePlayer(getCurrentArea(), Orientation.UP, new DiscreteCoordinates(2,2));
        getCurrentArea().registerActor(player);
        ((ICRogueRoom)getCurrentArea()).isVisited();
    }

    // check if the player is switching rooms,
    // if the current level is solved prints "Win", if the player is dead unregisters him and prints "GameOver"
    @Override
    public void update(float deltaTime) {
        Keyboard keyboard= getCurrentArea().getKeyboard();

        resetIfPressed(keyboard.get(Keyboard.R));

        if (player.isSwitchingRoom()){
            switchRoom();
        }

        if (currentLevel.isOn()){
            if (currentLevel instanceof Level00){
                nextLevel();
            } else {
                end();
                System.out.println("Win");
            }
        }

        if (!player.isAlive()) {
            end();
            if (getCurrentArea().exists(player)){
                getCurrentArea().unregisterActor(player);
            }
            System.out.println("GameOver");
        }

        super.update(deltaTime);
    }

    @Override
    public void end() {
    }

    // resets the level if a key is pressed
    private void resetIfPressed(Button b){
        if(b.isDown()) {
            initLevel();
        }
    }

    @Override
    public String getTitle() {
        return "ICRogue";
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)){
            initLevel();
            return true;
        } return false;
    }

    // make the player switch rooms
    protected void switchRoom() {
        player.leaveArea();

        player.setSwitchingRoom(false);

        setCurrentArea(player.getDestinationName(),false);

        player.enterArea(getCurrentArea(),player.getDestinationCoordinates());

        ((ICRogueRoom)getCurrentArea()).isVisited();
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        02.12.2022
 */

