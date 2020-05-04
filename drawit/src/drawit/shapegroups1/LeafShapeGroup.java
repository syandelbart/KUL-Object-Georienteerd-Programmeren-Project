package drawit.shapegroups1;

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
		super.setExtent(this.calculateExtent());
		super.setOriginalExtent(this.calculateExtent());
		
		super.setDrawingCommands(this.calculateDrawingCommands());
	}
	
	/** Returns the shape directly contained by this shape group, or null if this is a non-leaf shape group.*/
	public RoundedPolygon getShape() {
		return this.shape;
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
	
	public java.lang.String calculateDrawingCommands(){
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
