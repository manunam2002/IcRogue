package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.DarkLord;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ICRoguePlayer extends ICRogueActor implements Interactor {

    private Animation currentAnimation;

    private Sprite[][] sprites = Sprite.extractSprites("zelda/player", 4, .75f, 1.5f,
            this, 16, 32,
            new Orientation[] {Orientation.DOWN, Orientation.RIGHT, Orientation.UP, Orientation.LEFT});

    private Animation[] animations = Animation.createAnimations(MOVE_DURATION/2, sprites);

    private final static int MOVE_DURATION = 8;

    // minimum time between two fire shots
    private final static float COOLDOWN = 0.2f;

    private float timeCount = 0.f;

    private boolean viewInteraction = false;

    private ICRogueInteractionHandler handler = new ICRoguePlayerInteractionHandler();

    private boolean isStaffCollected = false;

    private boolean isSwitchingRoom = false;

    private String destinationName;

    private DiscreteCoordinates destinationCoordinates;

    private int hpLevel = 10;

    // array where player can store the indentifier when he collects a key
    private ArrayList<Integer> keyIdentifier = new ArrayList<>();

    public ICRoguePlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);
        currentAnimation = animations[0];
    }

    @Override
    public void draw(Canvas canvas) {
        currentAnimation.draw(canvas);
    }

    // a player can move, shoot a fire, make view interactions if keys are pressed
    // the current animation is updated if the player is moving
    @Override
    public void update(float deltaTime) {
        Keyboard keyboard= getOwnerArea().getKeyboard();

        incrementTimeCount(deltaTime);

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
        fireIfPressed(keyboard.get(Keyboard.X));
        interactIfPressed(keyboard.get(Keyboard.W));

        if (isDisplacementOccurs()){
            currentAnimation.update(deltaTime);
        }

        super.update(deltaTime);
    }

    private void incrementTimeCount(float dt) {
        timeCount += dt;
    }

    // makes the player move if a key is pressed
    private void moveIfPressed(Orientation orientation, Button b){
        if(b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                drawOrientation();
                move(MOVE_DURATION);
            }
        }
    }

    // makes a player shoot a fire if a key is pressed, if a staff is collected and if cooldown time is reached
    private void fireIfPressed(Button b){
        if (b.isDown() && isStaffCollected) {
            if (timeCount >= COOLDOWN){
                Fire fire = new Fire(getOwnerArea(),getOrientation(),getCurrentMainCellCoordinates());
                getOwnerArea().registerActor(fire);
                timeCount = 0.f;
            }
        }
    }

    // makes a player ask for a view interaction
    private void interactIfPressed(Button b){
        if (b.isDown()) {
            viewInteraction = true;
        } else viewInteraction = false;
    }

    // sets the player current animation depending on his orientation
    private void drawOrientation(){
        switch (getOrientation()){
            case UP -> currentAnimation = animations[0];
            case DOWN -> currentAnimation = animations[2];
            case RIGHT -> currentAnimation = animations[1];
            case LEFT -> currentAnimation = animations[3];
        }
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return super.getCurrentCells();
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList (getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return viewInteraction;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler,isCellInteraction);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    public String getDestinationName() {
        return destinationName;
    }

    public DiscreteCoordinates getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public void setSwitchingRoom(boolean switchingRoom) {
        isSwitchingRoom = switchingRoom;
    }

    public boolean isSwitchingRoom(){
        return isSwitchingRoom;
    }

    // decreases the hp level of the player by a given damage
    public void damage(int damage){
        hpLevel -= damage;
    }

    public boolean isAlive(){
        if (hpLevel > 0){
            return true;
        } else {return false;}
    }

    // defines all player's possible interactions
    private class ICRoguePlayerInteractionHandler implements ICRogueInteractionHandler{

        // a player collects a cherry with a cell interaction
        @Override
        public void interactWith(Cherry cherry, boolean isCellInteraction) {
            if (isCellInteraction){
                cherry.collect();
                damage(-5);
            }
        }

        // a player collects a staff with a view interaction
        @Override
        public void interactWith(Staff staff, boolean isCellInteraction) {
            if (!isCellInteraction){
                staff.collect();
                isStaffCollected = true;
            }
        }

        // a player collects a key and save the identifier with a cell interaction
        @Override
        public void interactWith(Key key, boolean isCellInteraction) {
            if (isCellInteraction){
                key.collect();
                keyIdentifier.add(key.getIdentifier());
            }
        }

        // a player will switch room when a cell interaction with an open connector occurs
        // a player can open a locked connector with a view interaction if he has the right identifier
        @Override
        public void interactWith(Connector connector, boolean isCellInteraction) {
            if (isCellInteraction && !isDisplacementOccurs()){
                isSwitchingRoom = true;
                destinationName = connector.getDestinationName();
                destinationCoordinates = connector.getDestinationCoordinates();
            } else {
                if (!isCellInteraction){
                    if (connector.getType().equals(Connector.ConnectorType.LOCKED)){
                        for (int i = 0 ; i < keyIdentifier.size() ; ++i){
                            if (keyIdentifier.get(i).equals(connector.getIdentifier())){
                                connector.setType(Connector.ConnectorType.OPEN);
                            }
                        }
                    }
                }
            }
        }

        // a player can kill a turret with a cell interaction
        @Override
        public void interactWith(Turret turret, boolean isCellInteraction) {
            if (isCellInteraction){
                turret.die();
            }
        }

        @Override
        public void interactWith(DarkLord darkLord, boolean isCellinteraction) {
            if (isCellinteraction){
                damage(2);
            }
        }
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        02.12.2002
 */

