package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

abstract public class Projectile extends ICRogueActor implements Consumable, Interactor {

    private Sprite sprite;

    private final static int DEFAULT_MOVE_DURATION = 10;
    private final static int DEFAULT_DAMAGE = 1;

    private static int MOVE_DURATION;
    private static int DAMAGE;

    protected int getDAMAGE(){
        return DAMAGE;
    }

    private boolean isConsumed = false;

    private ICRogueProjectileInteractionHandler handler = new ICRogueProjectileInteractionHandler();

    public Projectile(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);
        MOVE_DURATION = DEFAULT_MOVE_DURATION;
        DAMAGE = DEFAULT_DAMAGE;
    }

    public Projectile(Area owner, Orientation orientation, DiscreteCoordinates coordinates, int moveDuration, int damage) {
        super(owner, orientation, coordinates);
        MOVE_DURATION = moveDuration;
        DAMAGE = damage;
    }

    // makes te projectile move
    @Override
    public void update(float deltaTime) {
        move(MOVE_DURATION);
        super.update(deltaTime);
    }

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    // a projectile is unregistered from the owner are if it is cunsumed
    @Override
    public void consume() {
        getOwnerArea().unregisterActor(this);
        isConsumed = true;
    }

    @Override
    public boolean isConsumed() {
        return isConsumed;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
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
        return true;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler,isCellInteraction);
    }

    // defines interactions with cells : a projectile is consumed if it interacts with a wall or a hole
    protected class ICRogueProjectileInteractionHandler implements ICRogueInteractionHandler{

        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell icRogueCell, boolean isCellInteraction) {
            if (!isCellInteraction){
                if (icRogueCell.getType().equals(ICRogueBehavior.ICRogueCellType.WALL)||
                        icRogueCell.getType().equals(ICRogueBehavior.ICRogueCellType.HOLE)){
                    consume();
                }
            }
        }
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        04.12.2022
 */

