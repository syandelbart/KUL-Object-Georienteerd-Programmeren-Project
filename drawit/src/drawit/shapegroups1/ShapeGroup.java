package drawit.shapegroups1;
import drawit.IntPoint;

import java.util.ArrayList;

import drawit.IntPoint;
import drawit.RoundedPolygon;

public class ShapeGroup {
	private RoundedPolygon shape;
	private ShapeGroup[] subGroups;
	private ShapeGroup parentGroup;
	private Extent originalExtent;
	private Extent extent;
	
	public ShapeGroup(RoundedPolygon shape) {
		this.shape = shape;
		this.originalExtent = this.getExtent();
		this.extent = this.getExtent();
	}
	
	public ShapeGroup(ShapeGroup[] subgroups) {
		this.subGroups = new ShapeGroup[subgroups.length];
		for(int i = 0; i < subgroups.length; i++) {
			this.subGroups[i] = subgroups[i];
			this.subGroups[i].parentGroup = this;
		}
		this.originalExtent = this.getExtent();
		this.extent = this.getExtent();
	}
	
	public Extent getExtent() {
		if(extent == null) {
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
					if(this.shape.getVertices()[i].getY() > maximumY) {
						maximumY = this.shape.getVertices()[i].getY();
					}
					if(this.shape.getVertices()[i].getY() < minimumY) {
						minimumY = this.shape.getVertices()[i].getY();
					}
				}
				return Extent.ofLeftTopRightBottom(minimumX, minimumY, maximumX, maximumY);
			}
			else {
				Extent[] extentArray = new Extent[subGroups.length];
				for(int i = 0; i < subGroups.length ; i++) {
					extentArray[i] = subGroups[i].getExtent();
				}
				int minimumX = extentArray[0].getLeft();
				int maximumX = extentArray[0].getRight();
				int minimumY = extentArray[0].getTop();
				int maximumY = extentArray[0].getBottom();
				for(int i = 0; i < extentArray.length ; i++) {
					extentArray[i] = subGroups[i].getExtent();
					if(extentArray[i].getLeft() < minimumX) {
						minimumX = extentArray[i].getLeft();
					}
					if(extentArray[i].getRight() > maximumX) {
						maximumX = extentArray[i].getRight();
					}
					if(extentArray[i].getBottom() > maximumY) {
						maximumY = extentArray[i].getBottom();
					}
					if(extentArray[i].getTop() < minimumY) {
						minimumY = extentArray[i].getTop();
					}
				}
				return Extent.ofLeftTopRightBottom(minimumX, minimumY, maximumX, maximumY);
			}
		}
		else {
			return this.extent;
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
		IntPoint result = new IntPoint(globalCoordinates.getX() + this.getOriginalExtent().getLeft() - this.getExtent().getLeft(),globalCoordinates.getY() + this.getOriginalExtent().getBottom() - this.getExtent().getBottom());
		return result;
	}
	
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		IntPoint result = new IntPoint(innerCoordinates.getX() - this.getOriginalExtent().getLeft() + this.getExtent().getLeft(),innerCoordinates.getY() - this.getOriginalExtent().getBottom() + this.getExtent().getBottom());
		return result;
	}
	
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		for(int i = 0; i < this.getSubgroupCount(); i++) {
			if(this.subGroups[i].getExtent().contains(innerCoordinates)) {
				return this.subGroups[i];
			}
		}
		return null;
	}
	
	public void setExtent(Extent newExtent){
		this.extent = newExtent;
	}
	
	public void bringToFront() {
		int location = 0;
		for(int i = 0; i < this.getParentGroup().getSubgroupCount(); i++) {
			if(this == this.getParentGroup().getSubgroup(i)) {
				location = i;
			}
		}
		ShapeGroup random = new ShapeGroup(this.getParentGroup().getSubgroup(location).shape);
		random.subGroups = new ShapeGroup[this.getParentGroup().getSubgroup(location).getSubgroupCount()];
		for(int i = 0; i < random.getSubgroupCount(); i++) {
			random.subGroups[i] = this.getParentGroup().getSubgroup(location).getSubgroup(i);
		}
		random.parentGroup = this.getParentGroup().getSubgroup(location).getParentGroup();
		random.originalExtent = this.getParentGroup().getSubgroup(location).getOriginalExtent();
		this.getParentGroup().subGroups[location] = this.getParentGroup().subGroups[0];
		this.getParentGroup().subGroups[0] = random;
	}
	
	public void sendToBack() {
		int location = 0;
		for(int i = 0; i < this.getParentGroup().getSubgroupCount(); i++) {
			if(this == this.getParentGroup().getSubgroup(i)) {
				location = i;
			}
		}
		ShapeGroup random = new ShapeGroup(this.getParentGroup().getSubgroup(location).shape);
		random.subGroups = new ShapeGroup[this.getParentGroup().getSubgroup(location).getSubgroupCount()];
		for(int i = 0; i < random.getSubgroupCount(); i++) {
			random.subGroups[i] = this.getParentGroup().getSubgroup(location).getSubgroup(i);
		}
		random.parentGroup = this.getParentGroup().getSubgroup(location).getParentGroup();
		random.originalExtent = this.getParentGroup().getSubgroup(location).getOriginalExtent();
		this.getParentGroup().subGroups[location] = this.getParentGroup().subGroups[-1];
		this.getParentGroup().subGroups[-1] = random;
	}
	
	public java.lang.String getDrawingCommands(){
		StringBuilder string = new StringBuilder();
		double scaleX = (double)this.getExtent().getWidth() / (double)this.getOriginalExtent().getWidth();
		double scaleY = (double)this.getExtent().getHeight() / (double)this.getOriginalExtent().getHeight();
		if(this.getOriginalExtent().getTop() == this.getExtent().getTop()) {
			double translateX = -1 * (scaleX - 1) * (double)this.getOriginalExtent().getLeft();
			double translateY = -1 * (scaleY - 1) * (double)this.getOriginalExtent().getTop();
		}
		double translateX = -1 * (((scaleX - 1) * (double)this.getOriginalExtent().getLeft()) + this.getOriginalExtent().getLeft() - this.getExtent().getLeft());
		double translateY = -1 * (((scaleY - 1) * (double)this.getOriginalExtent().getTop()) + this.getOriginalExtent().getTop() - this.getExtent().getTop());
		string.append("pushTranslate" + " " + translateX + " " + translateY + "\n");
		string.append("pushScale" + " " + scaleX + " " + scaleY + "\n");
		string.append(this.shape.getDrawingCommands());
		string.append("popTransform" + "\n");
		string.append("popTransform" + "\n");
		return string.toString();
	}
}


