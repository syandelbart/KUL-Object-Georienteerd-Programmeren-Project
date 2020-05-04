package drawit.shapes1;
import drawit.IntPoint;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.ShapeGroup;

public class ControlPointShapeGroup implements ControlPoint{
	int x;
	int y;
	LeafShapeGroup shape;
	
	public ControlPointShapeGroup(LeafShapeGroup shape, int x, int y) {
		this.shape = shape;
		this.x = x;
		this.y = y;
	}
	
	public drawit.IntPoint getLocation(){
		return new IntPoint(x,y);
	}
	
	public void remove() {
		IntPoint[] vertices = shape.getShape().getVertices();
		for(int i = 0; i < vertices.length; i++) {
			if(vertices[i].equals(new IntPoint(this.x,this.y))) {
				shape.getShape().remove(i);
			}
		}
	}
	
	public void move(drawit.IntVector delta) {
		this.x += delta.getX();
		this.y += delta.getY();
	}
}