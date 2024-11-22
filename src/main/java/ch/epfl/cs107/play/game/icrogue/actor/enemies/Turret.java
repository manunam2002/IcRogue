package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

public class Turret extends Enemy {

    private Orientation[] arrowOrientations;

    private final static float COOLDOWN = 2.f;

    private float timeCount = 0.f;

    private void incrementTimeCount(float dt) {
        timeCount += dt;
    }

    public Turret(Area owner, Orientation orientation, DiscreteCoordinates coordinates, Orientation[] arrowOrientations) {
        super(owner, orientation, coordinates);
        setSprite(new Sprite("icrogue/static_npc", 1.5f, 1.5f, this, null, new Vector(-0.25f, 0)));
        this.arrowOrientations = arrowOrientations;
    }

    // calls attack method when the time count reaches COOLDOWN
    @Override
    public void update(float deltaTime) {
        incrementTimeCount(deltaTime);
        if (timeCount >= COOLDOWN){
            attack();
            timeCount = 0.f;
        }
        super.update(deltaTime);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    // registers arrows in all the given orientations in the owner area.
    private void attack(){
        for (int i = 0 ; i < arrowOrientations.length ; ++i){
            Arrow arrow = new Arrow(getOwnerArea(),arrowOrientations[i],getCurrentMainCellCoordinates());
            getOwnerArea().registerActor(arrow);
        }
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        11.12.2022
 */

