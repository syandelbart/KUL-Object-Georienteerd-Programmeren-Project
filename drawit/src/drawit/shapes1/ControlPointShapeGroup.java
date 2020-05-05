package drawit.shapes1;
import drawit.IntPoint;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.ShapeGroup;

public class ControlPointShapeGroup implements ControlPoint{
	IntPoint point;
	ShapeGroup shape;
	String location;
	
	public ControlPointShapeGroup(ShapeGroup shape, IntPoint point, String location) {
		this.shape = shape;
		this.point = point;
		this.location = location;
	}
	
	public drawit.IntPoint getLocation(){
		if(location.equals("topleft")) {
			return new IntPoint(shape.getOriginalExtent().getLeft(),shape.getOriginalExtent().getTop());
		}
		else if(location.equals("bottomright")) {
			return new IntPoint(shape.getOriginalExtent().getRight(),shape.getOriginalExtent().getBottom());
		}
		return null;
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	public void move(drawit.IntVector delta) {
		
		Extent extent = shape.getExtent();
		if(location.equals("topleft")) {
			Extent newExtent = Extent.ofLeftTopRightBottom(delta.getX() + this.point.getX(), delta.getY() + this.point.getY(), extent.getRight(), extent.getBottom());
			shape.setExtent(newExtent);
		}
		else if(location.equals("bottomright")) {
			Extent newExtent = Extent.ofLeftTopRightBottom(extent.getLeft(), extent.getTop(),this.point.getX() + delta.getX(), this.point.getY() + delta.getY());
			shape.setExtent(newExtent);
		}
	}
}