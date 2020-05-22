package drawit.shapegroups1;

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
	
	/** Returns the extent of this shape group, expressed in its outer coordinate system.
	 * @creates result
	 * 
	 * @post The result can't be null
	 * 	| result != null
	 * @post The returned extent contains all vertices of this leaf shapegroup
	 *  | IntStream.range(0, getShape().getVertices().length).allMatch(i -> (result.contains(getShape().getVertices()[i])))
	 *  
	 */
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
	
	/**
	 * Returns a textual representation of a sequence of drawing commands for drawing
	 * the shapes contained directly or indirectly by this shape group, expressed in this
	 * shape group's outer coordinate system.
	 * 
	 * For the syntax of the drawing commands, see {@code RoundedPolygon.getDrawingCommands()}.
	 * 
	 * @inspects | this
	 * @post | result != null
	 */
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
}
