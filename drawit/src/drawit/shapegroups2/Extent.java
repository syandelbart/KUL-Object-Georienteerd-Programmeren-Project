package drawit.shapegroups2;
import drawit.IntPoint;

public class Extent {
	private int left;
	private int top;
	private int width;
	private int height;
	
	/** Returns the X coordinate of the left border of this extent.	*/
	public int getLeft() {
		return this.left;
	}
	
	/** Returns the Y coordinate of the top border of this extent. */
	public int getTop() {
		return this.top;
	}
	
	/** Returns the X coordinate of the right border of this extent. */
	public int getRight() {
		return this.left + this.width;
	}
	
	/** Returns the Y coordinate of the bottom border of this extent. */
	public int getBottom() {
		return this.top + this.height;
	}
	/** Returns the width of this extent. */
	public int getWidth() {
		return this.width;
	}
	
	/** Returns the height of this extent. */
	public int getHeight() {
		return this.height;
	}
	
	/** Returns the top-left corner of this extent. */
	public IntPoint getTopLeft() {
		return new IntPoint(this.getLeft(),this.getTop());
	}
	
	/** Returns the bottom-right corner of this extent. */
	public IntPoint getBottomRight() {
		return new IntPoint(this.getRight(),this.getBottom());
	}
	
	/** Returns if this extent contains the given point.
	 *  @throws IllegalArgumentException
	 *  | point == null 
	 *  @post returns true if this extent contains the given point, returns false otherwise.
	 */
	public boolean contains(IntPoint point) {
		if(point == null) {
			throw new IllegalArgumentException("point is null");
		}
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
	
	/** Returns a new extent with the given left, top, width and height.
	 *  @creates result
	 *  @post the created object has the correct variables calculated with the given arguments.
	 *  	|result.getLeft() == left 
	 *  	|&& result.getTop() == top
	 *  	|&& result.getRight() == result.getLeft() + width
	 *  	|&& result.getBottom() == result.getTop() + height
	 */
	public static Extent ofLeftTopWidthHeight(int left,int top,int width,int height) {
		Extent res = new Extent();
		res.left = left;
		res.top = top;
		res.width = width;
		res.height = height;
		return res;
	}

	/** Returns a new extent with the given left, top, width and height.
	 *  @creates result
	 *  @post the created object has the given arguments as variables.
	 *  	|result.getLeft() == left 
	 *  	|&& result.getTop() == top
	 *  	|&& result.getRight() == right
	 *  	|&& result.getBottom() == bottom
	 */
	public static Extent ofLeftTopRightBottom(int left,int top,int right,int bottom) {
		Extent res = new Extent();
		res.left = left;
		res.top = top;
		res.width = right - left;
		res.height = bottom - top;
		return res;
	}

	/** Returns a new extent with the given left, the other variables are equal to the variables of this object.
	 *  @creates result
	 *  @post the created object's getLeft() is equal to the given argument and the other variables are equal to the variables of this object.
	 *  	|result.getLeft() == newLeft 
	 *  	|&& result.getTop() == getTop()
	 *  	|&& result.getRight() == getRight()
	 *  	|&& result.getBottom() == getBottom()
	 */
	public Extent withLeft(int newLeft) {
		return ofLeftTopRightBottom(newLeft,this.getTop(),this.getRight(),this.getBottom());
	}

	/** Returns a new extent with the given top, the other variables are equal to the variables of this object.
	 *  @creates result
	 *  @post the created object's getTop() is equal to the given argument and the other variables are equal to the variables of this object.
	 *  	|result.getLeft() == getLeft()
	 *  	|&& result.getTop() == newTop
	 *  	|&& result.getRight() == getRight()
	 *  	|&& result.getBottom() == getBottom()
	 */
	public Extent withTop(int newTop) {
		return ofLeftTopRightBottom(this.getLeft(),newTop,this.getRight(),this.getBottom());
	}

	/** Returns a new extent with the given right, the other variables are equal to the variables of this object.
	 *  @creates result
	 *  @post the created object's getRight() is equal to the given argument and the other variables are equal to the variables of this object.
	 *  	|result.getLeft() == getLeft()
	 *  	|&& result.getTop() == getTop()
	 *  	|&& result.getRight() == newRight
	 *  	|&& result.getBottom() == getBottom()
	 */
	public Extent withRight(int newRight) {
		return ofLeftTopRightBottom(this.getLeft(),this.getTop(),newRight,this.getBottom());
	}

	/** Returns a new extent with the given bottom, the other variables are equal to the variables of this object.
	 *  @creates result
	 *  @post the created object's getBottom() is equal to the given argument and the other variables are equal to the variables of this object.
	 *  	|result.getLeft() == getLeft()
	 *  	|&& result.getTop() == getTop()
	 *  	|&& result.getRight() == getRight()
	 *  	|&& result.getBottom() == newBottom
	 */
	public Extent withBottom(int newBottom) {
		return ofLeftTopRightBottom(this.getLeft(),this.getTop(),this.getRight(),newBottom);
	}

	/** Returns a new extent with the given width, this changes the getRight(), the other variables are equal to the variables of this object.
	 *  @creates result
	 *  @post the created object's getRight() is equal to getLeft() + newWidth and the other variables are equal to the variables of this object.
	 *  	|result.getLeft() == getLeft()
	 *  	|&& result.getTop() == getTop()
	 *  	|&& result.getRight() == getLeft() + newWidth
	 *  	|&& result.getBottom() == getBottom()
	 */
	public Extent withWidth(int newWidth) {
		return ofLeftTopWidthHeight(this.getLeft(),this.getTop(),newWidth,this.getHeight());
	}
	
	/** Returns a new extent with the given height, this changes the getBottom(), the other variables are equal to the variables of this object.
	 *  @creates result
	 *  @post the created object's getBottom() is equal to getTop() + newHeight and the other variables are equal to the variables of this object.
	 *  	|result.getLeft() == getLeft()
	 *  	|&& result.getTop() == getTop()
	 *  	|&& result.getRight() == getRight()
	 *  	|&& result.getBottom() == getTop() + newHeight
	 */
	public Extent withHeight(int newHeight) {
		return ofLeftTopWidthHeight(this.getLeft(),this.getTop(),this.getWidth(),newHeight);
	}
}

