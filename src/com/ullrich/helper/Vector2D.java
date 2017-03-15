package com.ullrich.helper;

import android.util.FloatMath;


public class Vector2D {
	public static float TO_RADIANS = (1 / 180.0f) * (float) Math.PI;
    public static float TO_DEGREES = (1 / (float) Math.PI) * 180;
    public float x, y;

    public Vector2D() {
    }

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2D cpy() {
        return new Vector2D(x, y);
    }

    public Vector2D set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2D set(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
        return this;
    }

    public Vector2D add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2D add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2D sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2D sub(Vector2D other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2D mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }
    
    public Vector2D rotate(float angle){
    	float rad = angle * TO_RADIANS;
    	float cos = FloatMath.cos(rad);
    	float sin = FloatMath.sin(rad);
    	
    	float newX = this.x * cos - this.y * sin;
    	float newY = this.x * sin + this.y * cos;
    	
    	this.x = newX;
    	this.y = newY;
    	return this;
    }
}
