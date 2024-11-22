package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Key extends Item{

    private int identifier;

    public Key(Area area, Orientation orientation, DiscreteCoordinates position, int identifier) {
        super(area,orientation,position);
        setSprite(new Sprite("icrogue/key", 0.6f, 0.6f, this));
        this.identifier = identifier;
    }

    // to be called when a player collects a key, so he can save the identifier
    public int getIdentifier() {
        return identifier;
    }

    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        08.12.2022
 */

