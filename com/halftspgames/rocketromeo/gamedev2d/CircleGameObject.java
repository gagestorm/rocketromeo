package com.halftspgames.rocketromeo.gamedev2d;

import com.halftspgames.rocketromeo.framework.math.Circle;
import com.halftspgames.rocketromeo.framework.math.Vector2;

public class CircleGameObject {
    public final Vector2 position;
    public final Circle bounds;
    public CircleGameObject(float x, float y, float radius) {
        this.position = new Vector2(x, y);
        this.bounds = new Circle(x, y, radius);
    }
}
