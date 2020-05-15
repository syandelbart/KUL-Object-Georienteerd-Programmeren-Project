package drawit.shapes1;
import drawit.IntPoint;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.ShapeGroup;

public class ControlPointShapeGroup implements ControlPoint{
	IntPoint point;
	IntPoint initialLocation;
	ShapeGroup shape;
	String location;
	
	public ControlPointShapeGroup(ShapeGroup shape, IntPoint point, String location) {
		this.shape = shape;
		this.point = point;
		this.initialLocation = new IntPoint(point.getX(),point.getY());
		this.location = location;
	}
	
	public drawit.IntPoint getLocation(){
		return this.shape.toInnerCoordinates(this.point);
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	public void move(drawit.IntVector delta) {
		if(this.shape.getParentGroup() != null) {
			delta = this.shape.getParentGroup().toInnerCoordinates(delta);
		}
		Extent extent = shape.getExtent();
		if(location.equals("topleft")) {
			Extent newExtent = Extent.ofLeftTopRightBottom(delta.getX() + this.initialLocation.getX(), delta.getY() + this.initialLocation.getY(), extent.getRight(), extent.getBottom());
			shape.setExtent(newExtent);
		}
		else if(location.equals("bottomright")) {
			Extent newExtent = Extent.ofLeftTopRightBottom(extent.getLeft(), extent.getTop(),this.initialLocation.getX() + delta.getX(), this.initialLocation.getY() + delta.getY());
			shape.setExtent(newExtent);
		}
		
	}
}