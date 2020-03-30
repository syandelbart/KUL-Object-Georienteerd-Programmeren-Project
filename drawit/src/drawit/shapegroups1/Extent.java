package drawit.shapegroups1;

import drawit.IntPoint;

public class Extent {
	private int left;
	private int top;
	private int right;
	private int bottom;
	
	public int getLeft() {
		return this.left;
	}
	
	public int getTop() {
		return this.top;
	}
	
	public int getRight() {
		return this.right;
	}
	
	public int getBottom() {
		return this.bottom;
	}
	
	public int getWidth() {
		return this.getRight() - this.getLeft();
	}
	
	public int getHeight() {
		return this.getBottom() - this.getTop();
	}
	
	public IntPoint getTopLeft() {
		IntPoint result = new IntPoint(this.getLeft(),this.getTop());
		return result;
	}
	
	public IntPoint getBottomRight() {
		IntPoint result = new IntPoint(this.getRight(),this.getBottom());
		return result;
	}
	
	public boolean contains(IntPoint point) {
		boolean result = (point.getX() >= this.getTopLeft().getX() && point.getX() <= this.getBottomRight().getX() && point.getY() >= this.getTopLeft().getY() && point.getY() <= this.getBottomRight().getY());
		return result;
	}
	
	public static Extent ofLeftTopWidthHeight(int left,int top,int width,int height) {
		Extent result = new Extent();
		result.left = left;
		result.top = top;
		result.bottom = result.getTop() + height;
		result.right = result.getLeft() + width;
		return result;
	}
	
	public static Extent ofLeftTopRightBottom(int left,int top,int right,int bottom) {
		Extent result = new Extent();
		result.left = left;
		result.top = top;
		result.bottom = bottom;
		result.right = right;
		return result;
	}
	
	public Extent withLeft(int newLeft) {
		Extent result = new Extent();
		result.left = newLeft;
		result.top = this.getTop();
		result.bottom = this.getBottom();
		result.right = this.getRight();
		return result;
	}
	
	public Extent withTop(int newTop) {
		Extent result = new Extent();
		result.left = this.getLeft();
		result.top = newTop;
		result.bottom = this.getBottom();
		result.right = this.getRight();
		return result;
	}
	
	public Extent withRight(int newRight) {
		Extent result = new Extent();
		result.left = this.getLeft();
		result.top = this.getTop();
		result.bottom = this.getBottom();
		result.right = newRight;
		return result;
	}
	
	public Extent withBottom(int newBottom) {
		Extent result = new Extent();
		result.left = this.getLeft();
		result.top = this.getTop();
		result.bottom = newBottom;
		result.right = this.getRight();
		return result;
	}
	
	public Extent withWidth(int newWidth) {
		Extent result = new Extent();
		result.left = this.getLeft();
		result.top = this.getTop();
		result.bottom = this.getBottom();
		result.right = this.getLeft() + newWidth;
		return result;
	}
	
	public Extent withHeight(int newHeight) {
		Extent result = new Extent();
		result.left = this.getLeft();
		result.top = this.getTop();
		result.bottom = this.getTop() - newHeight;
		result.right = this.getRight();
		return result;
	}
}

