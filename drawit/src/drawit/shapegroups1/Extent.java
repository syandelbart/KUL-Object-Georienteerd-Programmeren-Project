package drawit.shapegroups1;

import drawit.IntPoint;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Extent {
	private int left;
	private int top;
	private int right;
	private int bottom;
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Extent)) {
			return false;
		}
		Extent other = (Extent)obj;
		return other.hashCode() == this.hashCode();
	}
	
	@Override
	public int hashCode() {
		List<Integer> extentX = new ArrayList<Integer>();
		List<Integer> extentY = new ArrayList<Integer>();
		extentX.add(this.left);
		extentY.add(this.top);
		extentX.add(this.right);
		extentY.add(this.bottom);
		Collections.sort(extentY);
		Collections.sort(extentX);
		int result = 17;
		result = result * 11 + extentX.get(0);
		result = result * 11 + extentX.get(1);
		result = result * 11 + extentY.get(0);
		result = result * 11 + extentY.get(1);
		return result;
	}
	
	@Override
	public String toString() {
		List<Integer> extentX = new ArrayList<Integer>();
		List<Integer> extentY = new ArrayList<Integer>();
		extentX.add(this.left);
		extentY.add(this.top);
		extentX.add(this.right);
		extentY.add(this.bottom);
		Collections.sort(extentY);
		Collections.sort(extentX);
		String result = "";
		result += extentX.get(0) + " "; 
		result += extentX.get(1) + " "; 
		result += extentY.get(0) + " ";
		result += extentY.get(1);
		return result;
	}
	
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
		return this.right;
	}
	
	/** Returns the Y coordinate of the bottom border of this extent. */
	public int getBottom() {
		return this.bottom;
	}
	
	/** Returns the width of this extent. 
	 * @post The width is equal to getRight() - getLeft().
	 * 	| result == getRight() - getLeft()
	 */
	public int getWidth() {
		return this.getRight() - this.getLeft();
	}
	
	/** Returns the height of this extent.
	 * @post The height is equal to getBottom() - getTop().
	 * 	| result == getBottom() - getTop()
	 */
	public int getHeight() {
		return this.getBottom() - this.getTop();
	}
	
	/** Returns the top-left corner of this extent.
	 * @creates result 
	 * @post result.getX() is equal to getLeft().
	 *  | result.getX() == getLeft()
	 * @post result.getY() is equal to getTop().
	 *  | result.getY() == getTop()
	 */
	public IntPoint getTopLeft() {
		IntPoint result = new IntPoint(this.getLeft(),this.getTop());
		return result;
	}
	
	/** Returns the bottom-right corner of this extent.
	 * @creates result 
	 * @post result.getX() is equal to getRight().
	 *  | result.getX() == getRight()
	 * @post result.getY() is equal to getBottom().
	 *  | result.getY() == getBottom()
	 */
	public IntPoint getBottomRight() {
		IntPoint result = new IntPoint(this.getRight(),this.getBottom());
		return result;
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
		int initialRight = this.getRight();
		int initialTop = this.getTop();
		int initialBottom = this.getBottom();
		if(this.left > this.right) {
			int random = this.left;
			this.left = this.right;
			this.right = random;
		}
		if(this.top > this.bottom) {
			int random = this.top;
			this.top = this.bottom;
			this.bottom = random;
		}
		boolean result = (point.getX() >= this.getLeft() && point.getX() <= this.getRight() && point.getY() >= this.getTop() && point.getY() <= this.getBottom());
		this.left = initialLeft;
		this.right = initialRight;
		this.bottom = initialBottom;
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
		Extent result = new Extent();
		result.left = left;
		result.top = top;
		result.bottom = result.getTop() + height;
		result.right = result.getLeft() + width;
		return result;
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
		Extent result = new Extent();
		result.left = left;
		result.top = top;
		result.bottom = bottom;
		result.right = right;
		return result;
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
		Extent result = new Extent();
		result.left = newLeft;
		result.top = this.getTop();
		result.bottom = this.getBottom();
		result.right = this.getRight();
		return result;
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
		Extent result = new Extent();
		result.left = this.getLeft();
		result.top = newTop;
		result.bottom = this.getBottom();
		result.right = this.getRight();
		return result;
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
		Extent result = new Extent();
		result.left = this.getLeft();
		result.top = this.getTop();
		result.bottom = this.getBottom();
		result.right = newRight;
		return result;
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
		Extent result = new Extent();
		result.left = this.getLeft();
		result.top = this.getTop();
		result.bottom = newBottom;
		result.right = this.getRight();
		return result;
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
		Extent result = new Extent();
		result.left = this.getLeft();
		result.top = this.getTop();
		result.bottom = this.getBottom();
		result.right = this.getLeft() + newWidth;
		return result;
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
		Extent result = new Extent();
		result.left = this.getLeft();
		result.top = this.getTop();
		result.bottom = this.getTop() + newHeight;
		result.right = this.getRight();
		return result;
	}
}

