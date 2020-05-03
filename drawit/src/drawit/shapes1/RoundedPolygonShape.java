package drawit.shapes1;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.ShapeGroup;

public class RoundedPolygonShape {
	
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
		ControlPoint[] result = new ControlPoint[2];
		for(int i = 0; i < 2; i++) {
			
		}
	}
}

