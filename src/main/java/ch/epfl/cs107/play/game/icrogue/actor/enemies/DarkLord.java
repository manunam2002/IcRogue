package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.FlameSkull;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class DarkLord extends Enemy{

    private int hpLevel = 10;

    private Sprite[][] sprites = Sprite.extractSprites("zelda/darkLord", 4, 1.5f, 1.5f,
            this, 32, 32,
            new Orientation[] {Orientation.UP, Orientation.LEFT, Orientation.DOWN, Orientation.RIGHT});

    private Animation currentAnimation;

    private Animation[] animations = Animation.createAnimations(4, sprites);

    private final static float COOLDOWN = 2.f;

    private final static float MOVEDOWN = 1.f;

    private float timeCount = 0.f;
    private float moveCount = 0.f;

    private void incrementTimeCount(float dt) {
        timeCount += dt;
        moveCount += dt;
    }

    public DarkLord(Area owner, Orientation orientation, DiscreteCoordinates coordinates){
        super(owner,orientation,coordinates);
        currentAnimation = animations[0];
    }

    @Override
    public void update(float deltaTime) {
        incrementTimeCount(deltaTime);
        if (timeCount >= COOLDOWN){
            attack();
            timeCount = 0.f;
        }
        if (moveCount >= MOVEDOWN){
            int orientationIndex = RandomHelper.roomGenerator.nextInt(4);
            orientate(Orientation.fromInt(orientationIndex));
            currentAnimation = animations[orientationIndex];
            move(10);
            moveCount = 0.f;
        }
        if (isDisplacementOccurs()){
            currentAnimation.update(deltaTime);
        }
        if (hpLevel <= 0){
            die();
        }
        super.update(deltaTime);
    }

    private void attack(){
        FlameSkull fireUp = new FlameSkull(getOwnerArea(),Orientation.UP,getCurrentMainCellCoordinates());
        getOwnerArea().registerActor(fireUp);
        FlameSkull fireDown = new FlameSkull(getOwnerArea(),Orientation.DOWN,getCurrentMainCellCoordinates());
        getOwnerArea().registerActor(fireDown);
        FlameSkull fireRight = new FlameSkull(getOwnerArea(),Orientation.RIGHT,getCurrentMainCellCoordinates());
        getOwnerArea().registerActor(fireRight);
        FlameSkull fireLeft = new FlameSkull(getOwnerArea(),Orientation.LEFT,getCurrentMainCellCoordinates());
        getOwnerArea().registerActor(fireLeft);
    }

    @Override
    public void draw(Canvas canvas) {
        currentAnimation.draw(canvas);
    }

    public void damage(int damage){
        hpLevel -= damage;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        31.12.2022
 */

