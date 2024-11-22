package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class ICRogueRoom extends Area implements Logic {

    private ICRogueBehavior behavior;

    protected String behaviorName;

    protected DiscreteCoordinates roomCoordinates;

    private List<Connector> connectors = new ArrayList<Connector>();

    private boolean isVisited = false;

    // creates a room without setting destination coordinates for the connectors
    public ICRogueRoom(List<DiscreteCoordinates> connectorsCoordinates, List<Orientation> orientations,
                       String behaviorName, DiscreteCoordinates roomCoordinates){
        this.behaviorName = behaviorName;
        this.roomCoordinates = roomCoordinates;
        for (int i = 0 ; i < connectorsCoordinates.size() ; ++i){
            connectors.add(new Connector(this, orientations.get(i).opposite(),connectorsCoordinates.get(i)));
        }
    }

    // creates a room and sets the destination coordinates for the connectors
    public ICRogueRoom(List<DiscreteCoordinates> connectorsCoordinates, List<Orientation> orientations,
                       List<DiscreteCoordinates> connectorsDestination, String behaviorName,
                       DiscreteCoordinates roomCoordinates){
        this.behaviorName = behaviorName;
        this.roomCoordinates = roomCoordinates;
        for (int i = 0 ; i < connectorsCoordinates.size() ; ++i){
            connectors.add(new Connector(this, orientations.get(i).opposite(),connectorsCoordinates.get(i)));
            connectors.get(i).setDestinationCoordinates(connectorsDestination.get(i));
        }
    }

    // registers all connectors in the area
    protected void initRoom(){
        for (int i = 0 ; i < connectors.size() ; ++i){
            registerActor(connectors.get(i));
        }
    }

    // sets the behaviour map
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            behavior = new ICRogueBehavior(window, behaviorName);
            setBehavior(behavior);
            initRoom();
            return true;
        }
        return false;
    }

    @Override
    public float getCameraScaleFactor() {
        return 11;
    }

    // opens all connectors if the room is solved
    @Override
    public void update(float deltaTime) {
        //Keyboard keyboard= this.getKeyboard();

        //openIfPressed(keyboard.get(Keyboard.O));
        //lockIfPressed(keyboard.get(Keyboard.L));
        //switchIfPressed(keyboard.get(Keyboard.T));

        if (isOn()){
            openConnectors();
        }

        super.update(deltaTime);
    }

    private void openConnectors(){
        for (Connector connector : connectors){
            if (connector.getType().equals(Connector.ConnectorType.CLOSED)){
                connector.setType(Connector.ConnectorType.OPEN);
            }
        }
    }

    // opens all connectors if a key is pressed
    private void openIfPressed(Button b){
        if (b.isDown()) {
            for (int i = 0; i < connectors.size() ; ++i){
                connectors.get(i).setType(Connector.ConnectorType.OPEN);
            }
        }
    }

    // locks the first connector if a key is pressed
    private void lockIfPressed(Button b){
        if (b.isDown()) {
            connectors.get(0).setType(Connector.ConnectorType.LOCKED);
            connectors.get(0).setIdentifier(1);
        }
    }

    // switches the state of all connector but the locked ones
    private void switchIfPressed(Button b){
        if (b.isDown()) {
            for (int i = 0; i < connectors.size() ; ++i){
                if (!connectors.get(i).getType().equals(Connector.ConnectorType.LOCKED)){
                    if (connectors.get(i).getType().equals(Connector.ConnectorType.OPEN)){
                        connectors.get(i).setType(Connector.ConnectorType.CLOSED);
                    } else {
                        connectors.get(i).setType(Connector.ConnectorType.OPEN);
                    }
                }
            }
        }
    }

    public List<Connector> getConnectors() {
        return connectors;
    }

    public void isVisited() {
        isVisited = true;
    }

    // by default a room is solved when is visited
    @Override
    public boolean isOn() {
        return isVisited;
    }

    @Override
    public boolean isOff() {
        return !isVisited;
    }

    @Override
    public float getIntensity() {
        return 0;
    }

    public DiscreteCoordinates getRoomCoordinates() {
        return roomCoordinates;
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        02.12.2022
 */

