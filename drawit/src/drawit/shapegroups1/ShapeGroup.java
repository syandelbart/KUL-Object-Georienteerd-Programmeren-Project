package drawit.shapegroups1;

import drawit.RoundedPolygon;

public class ShapeGroup {
	private RoundedPolygon shape;
	private ShapeGroup[] subGroups;
	
	public ShapeGroup(RoundedPolygon shape) {
		this.shape = shape;
	}
}

