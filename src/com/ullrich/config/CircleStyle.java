package com.ullrich.config;

public enum CircleStyle {
	POI(0.8f, 0.2f, 1f, 0f, 0f, 0.5f, 1f, 1f, 1f, 0.5f),
	POI_RADAR(0.8f, 0.2f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 1f),
	RADAR(0.9f, 0.1f, 1f, 0f, 0f, 0.5f, 1f, 1f, 1f, 0.5f),
	RADAR_VIEW_PORT(0.9f, 0.1f, 0.9f, 0.9f, 0.9f, 1f, 1f, 1f, 1f, 1f);
	
	private final float innerR;
	private final float innerG;
	private final float innerB;
	private final float innerA;
	private final float innerSize;
	
	private final float outerR;
	private final float outerG;
	private final float outerB;
	private final float outerA;
	private final float outerSize;
	
	private CircleStyle(float innerSize, float outerSize, float innerR, float innerG, float innerB, float innerA, float outerR, float outerG, float outerB, float outerA){
		this.innerSize = innerSize;
		this.outerSize = outerSize;
		
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

	public float getInnerSize() {
		return innerSize;
	}

	public float getOuterSize() {
		return outerSize;
	}
	
}
