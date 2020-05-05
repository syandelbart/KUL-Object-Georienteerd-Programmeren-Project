package drawit.shapes2;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups2.ShapeGroup;

public class RoundedPolygonShape implements Shape {
	
	private RoundedPolygon polygon;
	private ShapeGroup parent;
	
	public RoundedPolygonShape(ShapeGroup parent,drawit.RoundedPolygon polygon) {
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
	
	public drawit.shapegroups2.ShapeGroup getParent(){
		return this.parent;
	}
	
	public ControlPoint[] createControlPoints() {
		IntPoint[] vertices = polygon.getVertices();
		ControlPointRoundedPolygon[] result = new ControlPointRoundedPolygon[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
			IntPoint current = vertices[i];
			ControlPointRoundedPolygon controlPoint = new ControlPointRoundedPolygon(polygon,current,i,this.parent);
			result[i] = controlPoint;
		}
		return result;
	}
	
	public drawit.IntPoint toShapeCoordinates(drawit.IntPoint p){
		if(this.getParent() == null) {
			return p;
		}
		else {
			return this.getParent().toInnerCoordinates(p);
		}
	}
	
	public drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p){
		if(this.getParent() == null) {
			return p;
		}
		else {
			return this.getParent().toGlobalCoordinates(p);
		}
	}
}

