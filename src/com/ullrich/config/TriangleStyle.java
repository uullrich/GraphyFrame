package com.ullrich.config;

public enum TriangleStyle {
	NORTH(1f, 0f, 0f, 0.5f, 1f, 1f, 1f, 1f);
	
	private final float innerR;
	private final float innerG;
	private final float innerB;
	private final float innerA;
	
	private final float outerR;
	private final float outerG;
	private final float outerB;
	private final float outerA;
	
	private TriangleStyle(float innerR, float innerG, float innerB, float innerA, float outerR, float outerG, float outerB, float outerA){	
		this.innerR = innerR;
		this.innerG = innerG;
		this.innerB = innerB;
		this.innerA = innerA;
		
		this.outerR = outerR;
		this.outerG = outerG;
		this.outerB = outerB;
		this.outerA = outerA;
	}
	
	public float getInnerR() {
		return innerR;
	}

	public float getInnerG() {
		return innerG;
	}

	public float getInnerB() {
		return innerB;
	}

	public float getInnerA() {
		return innerA;
	}

	public float getOuterR() {
		return outerR;
	}

	public float getOuterG() {
		return outerG;
	}

	public float getOuterB() {
		return outerB;
	}

	public float getOuterA() {
		return outerA;
	}
}
