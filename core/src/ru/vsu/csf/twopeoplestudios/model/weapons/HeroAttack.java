package ru.vsu.csf.twopeoplestudios.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class HeroAttack {

    public static final float ATTACK_DURATION = 0.35f;

    Body body;
    float timeLeft;

    public void init(Body body) {
        this.body = body;
        this.body.setUserData(this);
        body.setActive(true);
        body.setUserData(this);
        timeLeft = ATTACK_DURATION;
    }

    public boolean decreaseTime(float delta) {
        timeLeft -= delta;
        return timeLeft < 0;
    }

    public void destroy() {
        if (body != null)
            body.setActive(false);
    }

    public void updatePosition(Vector2 position) {
        body.setTransform(position, 0);
    }

    public boolean isAlive() {
        return timeLeft > 0;
    }
}
