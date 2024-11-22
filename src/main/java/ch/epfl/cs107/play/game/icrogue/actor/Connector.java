package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class Connector extends AreaEntity {

    public enum ConnectorType{
        OPEN,
        CLOSED,
        LOCKED,
        INVISIBLE;
    }

    private ConnectorType type;

    private String destinationName;

    private DiscreteCoordinates destinationCoordinates;

    private final int NO_KEY_ID = 0;

    private int identifier = NO_KEY_ID;

    private Sprite spriteInvisible = new Sprite("icrogue/invisibleDoor_"+getOrientation().ordinal(),
            (getOrientation().ordinal()+1)%2+1, getOrientation().ordinal()%2+1, this);
    private Sprite spriteClosed = new Sprite("icrogue/door_"+getOrientation().ordinal(),
            (getOrientation().ordinal()+1)%2+1, getOrientation().ordinal()%2+1, this);
    private Sprite spriteLocked = new Sprite("icrogue/lockedDoor_"+getOrientation().ordinal(),
            (getOrientation().ordinal()+1)%2+1, getOrientation().ordinal()%2+1, this);

    public Connector(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);
        type = ConnectorType.INVISIBLE;
    }

    public void setDestinationCoordinates(DiscreteCoordinates destinationCoordinates) {
        this.destinationCoordinates = destinationCoordinates;
    }

    public DiscreteCoordinates getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setType(ConnectorType type) {
        this.type = type;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public int getIdentifier() {
        return identifier;
    }

    public ConnectorType getType() {
        return type;
    }

    // draws one of the 3 sprites depending on the connector's type
    @Override
    public void draw(Canvas canvas) {
        switch (type){
            case INVISIBLE -> spriteInvisible.draw(canvas);
            case CLOSED -> spriteClosed.draw(canvas);
            case LOCKED -> spriteLocked.draw(canvas);
        }
    }

    // connector doesn't take cell space only if it's open
    @Override
    public boolean takeCellSpace() {
        if (type.equals(ConnectorType.OPEN)){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        DiscreteCoordinates coord = getCurrentMainCellCoordinates();
        return List.of(coord, coord.jump(new Vector((getOrientation().ordinal()+1)%2, getOrientation().ordinal()%2)));
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        08.12.2022
 */

