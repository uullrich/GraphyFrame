package com.ullrich.config;

import android.graphics.Color;

public enum TextboxStyle {
	STANDARD_WHITE(14, Color.argb(255, 255, 255, 255), Color.argb(255, 0, 0, 0), 1, 10, 128, 1024),
	STANDARD_YELLOW(14, Color.argb(255, 255, 205, 0), Color.argb(255, 0, 0, 0), 1, 10, 128, 1024);
	
	private final int textSize;
	private final int backgroundColor;
	private final int borderColor;
	private final int borderSize;
	private final int padding;
	private final int pictureHeight;
	private final int pictureWidth;
	
	private TextboxStyle(int textSize, int backgroundColor, int borderColor, int borderSize, int padding, int pictureHeight, int pictureWidth){
		this.textSize = textSize;
		this.backgroundColor = backgroundColor;
		this.borderColor = borderColor;
		this.borderSize = borderSize;
		this.padding = padding;
		this.pictureHeight = pictureHeight;
		this.pictureWidth = pictureWidth;
	}

	public int getTextSize() {
		return textSize;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public int getBorderColor() {
		return borderColor;
	}

	public int getBorderSize() {
		return borderSize;
	}

	public int getPadding() {
		return padding;
	}

	public int getPictureHeight() {
		return pictureHeight;
	}

	public int getPictureWidth() {
		return pictureWidth;
	}

	
}
