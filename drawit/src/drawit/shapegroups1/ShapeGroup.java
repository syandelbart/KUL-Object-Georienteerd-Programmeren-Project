package drawit.shapegroups1;
import drawit.IntPoint;

import java.util.ArrayList;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.IntVector;
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
		double scaleX = (double)this.getExtent().getWidth() / (double)this.getOriginalExtent().getWidth();
		double scaleY = (double)this.getExtent().getHeight() / (double)this.getOriginalExtent().getHeight();
		double translateX = -1 * (((scaleX - 1) * (double)this.getOriginalExtent().getLeft()) + this.getOriginalExtent().getLeft() - this.getExtent().getLeft());
		double translateY = -1 * (((scaleY - 1) * (double)this.getOriginalExtent().getTop()) + this.getOriginalExtent().getTop() - this.getExtent().getTop());
		IntPoint result = new IntPoint((int)((globalCoordinates.getX() - translateX) / scaleX), (int)((globalCoordinates.getY() - translateY) / scaleY));
		return result;
	}
	
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		if(this.getParentGroup() == null) {
			double scaleX = (double)this.getExtent().getWidth() / (double)this.getOriginalExtent().getWidth();
			double scaleY = (double)this.getExtent().getHeight() / (double)this.getOriginalExtent().getHeight();
			double translateX = -1 * (((scaleX - 1) * (double)this.getOriginalExtent().getLeft()) + this.getOriginalExtent().getLeft() - this.getExtent().getLeft());
			double translateY = -1 * (((scaleY - 1) * (double)this.getOriginalExtent().getTop()) + this.getOriginalExtent().getTop() - this.getExtent().getTop());
			IntPoint result = new IntPoint((int)((innerCoordinates.getX() * scaleX) + translateX),(int)((innerCoordinates.getY() * scaleY) + translateY));
			return result;
		}
		else {
			
		}
	}
	
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		double scaleX = (double)this.getExtent().getWidth() / (double)this.getOriginalExtent().getWidth();
		double scaleY = (double)this.getExtent().getHeight() / (double)this.getOriginalExtent().getHeight();
		double translateX = -1 * (((scaleX - 1) * (double)this.getOriginalExtent().getLeft()) + this.getOriginalExtent().getLeft() - this.getExtent().getLeft());
		double translateY = -1 * (((scaleY - 1) * (double)this.getOriginalExtent().getTop()) + this.getOriginalExtent().getTop() - this.getExtent().getTop());
		IntVector result = new IntVector((int)((relativeGlobalCoordinates.getX()) / scaleX), (int)((relativeGlobalCoordinates.getY()) / scaleY));
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
		this.getParentGroup().subGroups[location] = this.getParentGroup().subGroups[0];
		this.getParentGroup().subGroups[0] = this;
	}
	
	public void sendToBack() {
		int location = 0;
		for(int i = 0; i < this.getParentGroup().getSubgroupCount(); i++) {
			if(this == this.getParentGroup().getSubgroup(i)) {
				location = i;
			}
		}
		this.getParentGroup().subGroups[location] = this.getParentGroup().subGroups[this.getParentGroup().getSubgroupCount()-1];
		this.getParentGroup().subGroups[this.getParentGroup().getSubgroupCount()-1] = this;
	}
	
	public java.lang.String getDrawingCommands(){
		StringBuilder string = new StringBuilder();
		if(shape == null) {
			double scaleX = (double)this.getExtent().getWidth() / (double)this.getOriginalExtent().getWidth();
			double scaleY = (double)this.getExtent().getHeight() / (double)this.getOriginalExtent().getHeight();
			double translateX = -1 * (((scaleX - 1) * (double)this.getOriginalExtent().getLeft()) + this.getOriginalExtent().getLeft() - this.getExtent().getLeft());
			double translateY = -1 * (((scaleY - 1) * (double)this.getOriginalExtent().getTop()) + this.getOriginalExtent().getTop() - this.getExtent().getTop());
			string.append("pushTranslate" + " " + translateX + " " + translateY + "\n");
			string.append("pushScale" + " " + scaleX + " " + scaleY + "\n");
			for(int i = this.getSubgroupCount() - 1; i >= 0; i--) {
				string.append(this.getSubgroup(i).getDrawingCommands());
			}
			string.append("popTransform" + "\n");
			string.append("popTransform" + "\n");
		}
		else{
			double scaleX = (double)this.getExtent().getWidth() / (double)this.getOriginalExtent().getWidth();
			double scaleY = (double)this.getExtent().getHeight() / (double)this.getOriginalExtent().getHeight();
			double translateX = -1 * (((scaleX - 1) * (double)this.getOriginalExtent().getLeft()) + this.getOriginalExtent().getLeft() - this.getExtent().getLeft());
			double translateY = -1 * (((scaleY - 1) * (double)this.getOriginalExtent().getTop()) + this.getOriginalExtent().getTop() - this.getExtent().getTop());
			string.append("pushTranslate" + " " + translateX + " " + translateY + "\n");
			string.append("pushScale" + " " + scaleX + " " + scaleY + "\n");
			string.append(this.shape.getDrawingCommands());
			string.append("popTransform" + "\n");
			string.append("popTransform" + "\n");
		}
		return string.toString();
	}
}



