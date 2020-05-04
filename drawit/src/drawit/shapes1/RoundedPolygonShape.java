package drawit.shapes1;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.ShapeGroup;

public class RoundedPolygonShape implements Shape {
	
	private RoundedPolygon polygon;
	private ShapeGroup parent;
	
	public RoundedPolygonShape(drawit.shapegroups1.ShapeGroup parent,drawit.RoundedPolygon polygon) {
		this.polygon = polygon;
		this.parent = parent;
	}
	
	public drawit.RoundedPolygon getPolygon(){
		return this.polygon;
	}
	
	public boolean contains(drawit.IntPoint p) {
		return this.polygon.contains(p);
	}
	
	public String getDrawingCommands() {
		return this.polygon.getDrawingCommands();
	}
	
	public drawit.shapegroups1.ShapeGroup getParent(){
		return this.parent;
	}
	
	public ControlPoint[] createControlPoints() {
		ControlPointRoundedPolygon[] result = new ControlPointRoundedPolygon[2];
		IntPoint topLeft = polygon.getBoundingBox().getTopLeft();
		ControlPointRoundedPolygon roundedPolygonTopLeft = new ControlPointRoundedPolygon(polygon,topLeft.getX(),topLeft.getY());
		IntPoint bottomRight = polygon.getBoundingBox().getBottomRight();
		ControlPointRoundedPolygon roundedPolygonBottomRight = new ControlPointRoundedPolygon(polygon,bottomRight.getX(),bottomRight.getY());
		result[0] = roundedPolygonTopLeft;
		result[1] = roundedPolygonBottomRight;
		return result;
	}
	
	public drawit.IntPoint toShapeCoordinates(drawit.IntPoint p){
		
	}
	
	public drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p){
		
	}
}

