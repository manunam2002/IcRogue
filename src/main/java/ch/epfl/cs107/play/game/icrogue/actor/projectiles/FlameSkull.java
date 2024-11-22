package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class FlameSkull extends Projectile{

    private ICRogueFlameSkullInteractionHandler handler = new ICRogueFlameSkullInteractionHandler();

    private Sprite[][] sprites = Sprite.extractSprites("zelda/flameskull",4,1f,1f,
            this,32,32,
            new Orientation[] {Orientation.UP, Orientation.LEFT, Orientation.DOWN, Orientation.RIGHT});

    private Animation[] animations = Animation.createAnimations(4,sprites,true);

    private Animation currentAnimation;

    public FlameSkull(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates, 5, 2);
        int idx = orientation.ordinal();
        currentAnimation = animations[idx];
    }

    @Override
    public void draw(Canvas canvas) {
        currentAnimation.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        //currentAnimation.update(deltaTime);
        super.update(deltaTime);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler,isCellInteraction);
    }

    // defines interactions with turrets : a turret dies when is hit by a fire
    private class ICRogueFlameSkullInteractionHandler extends ICRogueProjectileInteractionHandler{

        @Override
        public void interactWith(ICRoguePlayer player, boolean isCellInteraction) {
            if (isCellInteraction){
                consume();
                player.damage(getDAMAGE());
            }
        }
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        31.12.2022
 */

