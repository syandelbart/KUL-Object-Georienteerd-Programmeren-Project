package drawit.shapegroups2;

import java.util.stream.IntStream;
import drawit.RoundedPolygon;

public class LeafShapeGroup extends ShapeGroup {
	
	private RoundedPolygon shape;
	
	/**	Initializes this ShapeGroup with the given shape and calculates the extent and originalExtent.
	 * @mutates | this
	 * @throws IllegalArgumentException if the argument shape is null
	 * 	| !(shape != null)
	 * 
	 * @post The object's shape is equal to the given shape.
	 * 	| getShape().equals(shape)
	 * @post The object's original extent is equal to the extent.
	 * 	| getOriginalExtent().getTop() == getExtent().getTop()
	 * 	| && getOriginalExtent().getLeft() == getExtent().getLeft()
	 * 	| && getOriginalExtent().getBottom() == getExtent().getBottom()
	 * 	| && getOriginalExtent().getRight() == getExtent().getRight()
	 */
	public LeafShapeGroup(RoundedPolygon shape) {
		super();
		if(!(shape != null)) {
			throw new IllegalArgumentException("shape is null");
		}
		this.shape = shape;
		super.setOriginalExtent(this.calculateExtent());
		super.setExtent(this.calculateExtent());
	}
	
	/** Returns the shape directly contained by this shape group, or null if this is a non-leaf shape group.*/
	public RoundedPolygon getShape() {
		return this.shape;
	}
	
	public void setExtent(Extent newExtent){
		if(!(newExtent != null)) {
			throw new IllegalArgumentException();
		}
		super.setExtent(newExtent);
	}
	
	public Extent calculateExtent() {
		int minimumX = this.shape.getVertices()[0].getX();
		int maximumX = this.shape.getVertices()[0].getX();
		int minimumY = this.shape.getVertices()[0].getY();
		int maximumY = this.shape.getVertices()[0].getY();
		for(int i = 0; i < this.shape.getVertices().length; i++) {
			if(this.shape.getVertices()[i].getX() < minimumX) {
				minimumX = this.shape.getVertices()[i].getX();
			}
			if(this.shape.getVertices()[i].getX() > maximumX) {
				maximumX = this.shape.getVertices()[i].getX();
			}
			if(this.shape.getVertices()[i].getY() > maximumY) {
				maximumY = this.shape.getVertices()[i].getY();
			}
			if(this.shape.getVertices()[i].getY() < minimumY) {
				minimumY = this.shape.getVertices()[i].getY();
			}
		}
		return (Extent.ofLeftTopRightBottom(minimumX, minimumY, maximumX, maximumY));
	}
	
	private int getLocation() {
		int counter = 0;
		while(!this.getParentGroup().getSubgroup(counter).equals(this)) {
			counter++;
		}
		return counter;
	}
	
	public java.lang.String getDrawingCommands(){
		StringBuilder string = new StringBuilder();
		double scaleX = (double)this.getExtent().getWidth() / (double)this.getOriginalExtent().getWidth();
		double scaleY = (double)this.getExtent().getHeight() / (double)this.getOriginalExtent().getHeight();
		double translateX = -1 * (((scaleX - 1) * (double)this.getOriginalExtent().getLeft()) + this.getOriginalExtent().getLeft() - this.getExtent().getLeft());
		double translateY = -1 * (((scaleY - 1) * (double)this.getOriginalExtent().getTop()) + this.getOriginalExtent().getTop() - this.getExtent().getTop());
		string.append("pushTranslate" + " " + translateX + " " + translateY + "\n");
		string.append("pushScale" + " " + scaleX + " " + scaleY + "\n");
		string.append(this.shape.getDrawingCommands());
		string.append("popTransform" + "\n");
		string.append("popTransform" + "\n");
		return string.toString();
	}
	
	/** Moves this shape group to the front of its parent's list of subgroups.
	 * @mutates this
	 * 
	 * @throws IllegalArgumentException
	 * 	| getParentGroup() == null
	 * 
	 * @post This object is the first child of its getParentGroup().
	 * 	| getParentGroup().getSubgroup(0) == this
	 * 
	 * @post The indexes of the children of getParentGroup that were in front of this object are incremented by one.
	 * 	| IntStream.range(0,old(getLocation())).allMatch(i -> old(getParentGroup().getSubgroups()).get(i).equals(getParentGroup().getSubgroup(i + 1)))
	 * 
	 * @post The indexes of the children of getParentGroup that were behind this object are the same.
	 * 	| IntStream.range(old(getLocation()) + 1, getParentGroup().getSubgroupCount()).allMatch(i -> old(getParentGroup().getSubgroups()).get(i).equals(getParentGroup().getSubgroup(i)))
	 */
	public void bringToFront() {
		if(this.getParentGroup() == null) {
			throw new IllegalArgumentException("parentgroup is null");
		}
		if(this.getParentGroup().getFirstChild() != this) {
			if(this.getParentGroup().getLastChild() == this) {
				this.getPreviousSibling().setNextSibling(null);
				this.getParentGroup().setLastChild(this.getPreviousSibling());
				this.setPreviousSibling(null);
				this.setNextSibling(this.getParentGroup().getFirstChild());
				this.getParentGroup().getFirstChild().setPreviousSibling(this);
				this.getParentGroup().setFirstChild(this);
			}
			else {
				this.getNextSibling().setPreviousSibling(this.getPreviousSibling());
				this.getPreviousSibling().setNextSibling(this.getNextSibling());
				this.setNextSibling(this.getParentGroup().getFirstChild());
				this.getParentGroup().getFirstChild().setPreviousSibling(this);
				this.getParentGroup().setFirstChild(this);
				this.setPreviousSibling(null);
			}
		}
		
	}
	
	/** Moves this shape group to the back of its parent's list of subgroups.
	 * @mutates this
	 * 
	 * @throws IllegalArgumentException
	 * 	| getParentGroup() == null
	 * 
	 * @post This object is the last child of its getParentGroup().
	 * 	| getParentGroup().getSubgroup(getParentGroup().getSubgroupCount() - 1) == this
	 * 
	 * @post The indexes of the children of getParentGroup that were in front of this object are the same.
	 * 	| IntStream.range(0,old(getLocation())).allMatch(i -> old(getParentGroup().getSubgroups()).get(i).equals(getParentGroup().getSubgroup(i)))
	 * 
	 * @post The indexes of the children of getParentGroup that were behind this object are decremented by one.
	 * 	| IntStream.range(old(getLocation()) + 1, getParentGroup().getSubgroupCount()).allMatch(i -> old(getParentGroup().getSubgroups()).get(i).equals(getParentGroup().getSubgroups().get(i - 1)))
	 */
	public void sendToBack() {
		if(this.getParentGroup() == null) {
			throw new IllegalArgumentException("parentgroup is null");
		}
		if(this.getParentGroup().getLastChild() != this) {
			if(this.getParentGroup().getFirstChild() == this) {
				this.getNextSibling().setPreviousSibling(null);
				this.getParentGroup().setFirstChild(this.getNextSibling());
				this.setNextSibling(null);
				this.setPreviousSibling(this.getParentGroup().getLastChild());
				this.getParentGroup().getLastChild().setNextSibling(this);
				this.getParentGroup().setLastChild(this);
			}
			else {
				this.getPreviousSibling().setNextSibling(this.getNextSibling());
				this.getNextSibling().setPreviousSibling(this.getPreviousSibling());
				this.setPreviousSibling(this.getParentGroup().getLastChild());
				this.getParentGroup().getLastChild().setNextSibling(this);
				this.getParentGroup().setLastChild(this);
				this.setNextSibling(null);
			}
		}
	}
}
