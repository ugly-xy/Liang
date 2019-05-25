package com.zb.service.image;

import java.awt.Point;

import net.coobird.thumbnailator.geometry.Position;

public class SimplePositions implements Position {

	private Point point = null;

	public void setPoint(Point point) {
		this.point = point;
	}

	private int type = 0;

	public SimplePositions buildType(int opType) {
		this.type = opType;
		return this;
	}

	private int horizontalOffset = 0;

	public SimplePositions buildHorizontalOffset(int offset) {
		this.horizontalOffset = offset;
		return this;
	}

	private int verticalOffset = 0;

	public SimplePositions buildVerticalOffset(int offset) {
		this.verticalOffset = offset;
		return this;
	}

	public void set(int type, int horizontalOffset, int verticalOffset) {
		this.type = type;
		this.horizontalOffset = horizontalOffset;
		this.verticalOffset = verticalOffset;
	}

	@Override
	public Point calculate(int enclosingWidth, int enclosingHeight, int width,
			int height, int insetLeft, int insetRight, int insetTop,
			int insetBottom) {
		if (point != null)
			return point;

		int x = 0;
		int y = 0;
		if (type == 0) {
			x = horizontalOffset;
			y = verticalOffset;
		} else {
			x = enclosingWidth - width - horizontalOffset;
			y = verticalOffset;
		}

		return new Point(x, y);
	}

}
