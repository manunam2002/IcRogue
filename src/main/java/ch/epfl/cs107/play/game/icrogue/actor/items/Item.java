package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

abstract public class Item extends CollectableAreaEntity {

    private Sprite sprite;

    public Item(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
    }

    // items by default do not take cell space, to override if changes are to be made for a specific item
    @Override
    public boolean takeCellSpace() {
        return false;
    }

    // item is drawn if is not collected
    @Override
    public void draw(Canvas canvas) {
        if (!isCollected()){
            sprite.draw(canvas);
        }
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        04.12.2022
 */

