package drawit.shapegroups1;

import java.util.ArrayList;

import drawit.IntPoint;
import drawit.RoundedPolygon;

public class ShapeGroup {
	private RoundedPolygon shape;
	private ShapeGroup[] subGroups;
	private ShapeGroup parentGroup;
	private Extent originalExtent;
	
	public ShapeGroup(RoundedPolygon shape) {
		this.shape = shape;
		originalExtent = this.getExtent();
	}
	
	public ShapeGroup(ShapeGroup[] subgroups) {
		this.subGroups = new ShapeGroup[subgroups.length];
		for(int i = 0; i < subgroups.length; i++) {
			this.subGroups[i] = subgroups[i];
			this.subGroups[i].parentGroup = this;
		}
	}
	
	public Extent getExtent() {
		if(this.subGroups == null) {
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
				if(this.shape.getVertices()[0].getY() > maximumY) {
					maximumY = this.shape.getVertices()[i].getY();
				}
				if(this.shape.getVertices()[0].getY() < minimumY) {
					minimumY = this.shape.getVertices()[i].getY();
				}
			}
			Extent result = new Extent();
			result.left = minimumX;
			result.right = maximumX;
			result.top = maximumY;
			result.bottom = minimumY;
			return result;
		}
		else {
			Extent[] extentArray = new Extent[subGroups.length];
			for(int i = 0; i < subGroups.length ; i++) {
				extentArray[i] = subGroups[i].getExtent();
			}
			int minimumX = extentArray[0].left;
			int maximumX = extentArray[0].right;
			int minimumY = extentArray[0].bottom;
			int maximumY = extentArray[0].top;
			for(int i = 0; i < extentArray.length ; i++) {
				extentArray[i] = subGroups[i].getExtent();
				if(extentArray[i].left < minimumX) {
					minimumX = extentArray[i].left;
				}
				if(extentArray[i].right > maximumX) {
					maximumX = extentArray[i].right;
				}
				if(extentArray[i].top > maximumY) {
					maximumY = extentArray[i].top;
				}
				if(extentArray[i].bottom < minimumY) {
					minimumY = extentArray[i].bottom;
				}
			}
			Extent result = new Extent();
			result.left = minimumX;
			result.right = maximumX;
			result.top = maximumY;
			result.bottom = minimumY;
			return result;
		}
	}
	
	public Extent getOriginalExtent() {
		return this.originalExtent;
	}
	
	public ShapeGroup getParentGroup() {
		return this.parentGroup;
	}
	
	public RoundedPolygon getShape() {
		return shape;
	}
	
	public java.util.List<ShapeGroup> getSubgroups(){
		ArrayList<ShapeGroup> result = new ArrayList<ShapeGroup>();
		for(int i = 0; i < this.subGroups.length; i++) {
			result.add(this.subGroups[i]);
		}
		return result;
	}
	
	public int getSubgroupCount() {
		return this.subGroups.length;
	}
	
	public ShapeGroup getSubgroup(int index) {
		return this.subGroups[index];
	}
	
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		IntPoint result = new IntPoint(globalCoordinates.getX() + this.getExtent().bottom - this.getOriginalExtent().bottom,globalCoordinates.getY() + this.getExtent().left - this.getOriginalExtent().left);
		return result;
	}
	
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		IntPoint result = new IntPoint(innerCoordinates.getX() - this.getExtent().bottom + this.getOriginalExtent().bottom,innerCoordinates.getY() - this.getExtent().left + this.getOriginalExtent().left);
		return result;
	}
	
	
}


