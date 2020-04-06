package drawit.shapegroups2;
import drawit.IntPoint;

public class Extent {
	private int left;
	private int top;
	private int width;
	private int height;
	
	public int getLeft() {
		return this.left;
	}
	
	public int getTop() {
		return this.top;
	}
	
	public int getRight() {
		return this.left + this.width;
	}
	
	public int getBottom() {
		return this.top + this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public IntPoint getTopLeft() {
		return new IntPoint(this.getLeft(),this.getTop());
	}
	
	public IntPoint getBottomRight() {
		return new IntPoint(this.getRight(),this.getBottom());
	}
	
	
	public boolean contains(IntPoint point) {
		int initialLeft = this.getLeft();
		int initialTop = this.getTop();
		int initialWidth = this.getWidth();
		int initialHeight = this.getHeight();
		if(this.width < 0) {
			this.left = this.left + this.width;
			this.width = this.width * -1;
		}
		if(this.height < 0) {
			this.top = this.top + this.height;
			this.height = this.height * -1;
		}
		boolean result = (point.getX() >= this.getLeft() && point.getX() <= this.getRight() && point.getY() >= this.getTop() && point.getY() <= this.getBottom());
		this.left = initialLeft;
		this.width = initialWidth;
		this.height = initialHeight;
		this.top = initialTop;
		return result;
	}
	
	public static Extent ofLeftTopWidthHeight(int left,int top,int width,int height) {
		Extent res = new Extent();
		res.left = left;
		res.top = top;
		res.width = width;
		res.height = height;
		return res;
	}

	public static Extent ofLeftTopRightBottom(int left,int top,int right,int bottom) {
		Extent res = new Extent();
		res.left = left;
		res.top = top;
		res.width = right - left;
		res.height = bottom - top;
		return res;
	}

	public Extent withLeft(int newLeft) {
		return ofLeftTopRightBottom(newLeft,this.getTop(),this.getRight(),this.getBottom());
	}

	public Extent withTop(int newTop) {
		return ofLeftTopRightBottom(this.getLeft(),newTop,this.getRight(),this.getBottom());
	}

	public Extent withRight(int newRight) {
		return ofLeftTopRightBottom(this.getLeft(),this.getTop(),newRight,this.getBottom());
	}

	public Extent withBottom(int newBottom) {
		return ofLeftTopRightBottom(this.getLeft(),this.getTop(),this.getRight(),newBottom);
	}

	public Extent withWidth(int newWidth) {
		return ofLeftTopWidthHeight(this.getLeft(),this.getTop(),newWidth,this.getHeight());
	}
	
	public Extent withHeight(int newHeight) {
		return ofLeftTopWidthHeight(this.getLeft(),this.getTop(),this.getWidth(),newHeight);
	}
}

