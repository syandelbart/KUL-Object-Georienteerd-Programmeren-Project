package drawit.shapegroups1;

import drawit.RoundedPolygon;

public class ShapeGroup {
	private RoundedPolygon shape;
	private ShapeGroup[] subGroups;
	
	public ShapeGroup(RoundedPolygon shape) {
		this.shape = shape;
	}
	
	public ShapeGroup(ShapeGroup[] subgroups) {
		this.subGroups = new ShapeGroup[subgroups.length];
		for(int i = 0; i < subgroups.length; i++) {
			this.subGroups[i] = subgroups[i];
		}
	}
	
	public Extent getExtent() {
		int minimumX = shape.getVertices()[0].getX();
		int maximumX = shape.getVertices()[0].getX();
		int minimumY = shape.getVertices()[0].getY();
		int maximumY = shape.getVertices()[0].getY();
		for(int i = 0; i < shape.getVertices().length; i++) {
			if(shape.getVertices()[i].getX() < minimumX) {
				minimumX = shape.getVertices()[i].getX();
			}
			if(shape.getVertices()[i].getX() > maximumX) {
				maximumX = shape.getVertices()[i].getX();
			}
			if(shape.getVertices()[0].getY() > maximumY) {
				maximumY = shape.getVertices()[i].getY();
			}
			if(shape.getVertices()[0].getY() < minimumY) {
				minimumY = shape.getVertices()[i].getY();
			}
		}
		Extent result = new Extent();
		result.left = minimumX;
		result.right = maximumX;
		result.top = maximumY;
		result.bottom = minimumY;
		return result;
	}
	
	public Extent getOriginalExtent() {
		
	}
	
	public ShapeGroup getParentGroup() {
		
	}
}

