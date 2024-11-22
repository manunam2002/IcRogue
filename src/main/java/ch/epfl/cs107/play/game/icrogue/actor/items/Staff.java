package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Staff extends Item{

    private Sprite[] sprites = Sprite.extractSprites("zelda/staff",4,1f,1f,this,32,32);
    private Animation animation = new Animation(5,sprites,true);

    public Staff(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area,orientation,position);
        setSprite(new Sprite("zelda/staff_water.icon", .5f, .5f, this));
    }

    // staff is only view interactable : he can be collected only by view interactions
    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        animation.update(deltaTime);
        super.update(deltaTime);
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        04.12.2022
 */

