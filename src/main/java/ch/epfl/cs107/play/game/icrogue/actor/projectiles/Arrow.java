package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;

public class Arrow extends Projectile{

    private ICRogueArrowInteractionHandler handler = new ICRogueArrowInteractionHandler();

    public Arrow(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates, 5, 1);
        setSprite(new Sprite("zelda/arrow", 1f, 1f, this,
                new RegionOfInterest(32*orientation.ordinal(), 0, 32, 32),
                new Vector(0, 0)));
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler,isCellInteraction);
    }

    // defines interactions with player : player takes damage when is hit by an arrow, default damage is 1
    private class ICRogueArrowInteractionHandler extends ICRogueProjectileInteractionHandler{

        @Override
        public void interactWith(ICRoguePlayer icRoguePlayer, boolean isCellInteraction) {
            if (isCellInteraction){
                icRoguePlayer.damage(getDAMAGE());
                consume();
            }
        }
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        11.12.2022
 */

