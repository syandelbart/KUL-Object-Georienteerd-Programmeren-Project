package drawit.shapes1;
import drawit.IntPoint;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;

public class ControlPointShapeGroup implements ControlPoint{
	IntPoint point;
	LeafShapeGroup shape;
	
	public ControlPointShapeGroup(LeafShapeGroup shape, IntPoint point) {
		this.shape = shape;
		this.point = point;
	}
	
	public drawit.IntPoint getLocation(){
		return new IntPoint(point.getX(),point.getY());
	}
	
	public void remove() {
		IntPoint[] vertices = shape.getShape().getVertices();
		for(int i = 0; i < vertices.length; i++) {
			if(vertices[i].equals(this.point)) {
				shape.getShape().remove(i);
			}
		}
	}
	
	public void move(drawit.IntVector delta) {
		Extent extent = shape.getExtent();
		if(point.getX() == extent.getLeft()) {
			Extent newExtent = Extent.ofLeftTopRightBottom(extent.getLeft() + delta.getX(), extent.getTop() + delta.getY(), extent.getRight(), extent.getBottom());
			shape.setExtent(newExtent);
		}
		else if(point.getX() == extent.getRight()) {
			Extent newExtent = Extent.ofLeftTopRightBottom(extent.getLeft(), extent.getTop(), extent.getRight() + delta.getX(), extent.getBottom() + delta.getY());
			shape.setExtent(newExtent);
		}
		this.point = point.plus(delta);
	}
}