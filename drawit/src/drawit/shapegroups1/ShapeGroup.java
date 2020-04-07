package drawit.shapegroups1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.IntVector;
public class ShapeGroup {
	
	/**
	 * @representationObject
	 * @invar | (shape != null) || (subGroups != null)
	 * @invar | Arrays.stream(subGroups).allMatch(v -> v != null)
	 */
	
	private RoundedPolygon shape;
	private ShapeGroup[] subGroups;
	private ShapeGroup parentGroup;
	private Extent originalExtent;
	private Extent extent;
	
	/**	Initializes this ShapeGroup with the given shape and calculates the extent and originalExtent.
	 * @mutates | this
	 * @throws IllegalArgumentException if the argument shape is null
	 * 	| !(shape != null)
	 * 
	 * @post The object's shape is equal to the given shape.
	 * 	| getShape().equals(shape)
	 * @post The object's original extent is equal to the extent.
	 * 	| getOriginalExtent().equals(getExtent())
	 */
	public ShapeGroup(RoundedPolygon shape) {
		this.shape = shape;
		this.originalExtent = this.getExtent();
		this.extent = this.getExtent();
	}
	
	/** Initializes this object to represent a non-leaf shape group that directly contains the given subGroups, in the given order.
	 * @mutates | this
	 * @throws IllegalArgumentException if the argument subGroups is null
	 * 	| !(subGroups != null)
	 * @throws IllegalArgumentException if any ShapeGroup contained by the subGroups array is null
	 * 	| Arrays.stream(subGroups).anyMatch(v -> v == null)
	 * 
	 * @post The object's subGroups should be equal to the subGroups argument.
	 * 	| IntStream.range(0, getSubgroupCount()).allMatch(i -> getSubgroups().get(i).equals(subGroups[i]))
	 * @post The object's original extent is equal to the extent.
	 * 	| getOriginalExtent().equals(getExtent())
	 */
	public ShapeGroup(ShapeGroup[] subGroups) {
		this.subGroups = new ShapeGroup[subGroups.length];
		for(int i = 0; i < subGroups.length; i++) {
			this.subGroups[i] = subGroups[i];
			this.subGroups[i].parentGroup = this;
		}
		this.originalExtent = this.getExtent();
		this.extent = this.getExtent();
	}
	
	/** Returns the extent of this shape group, expressed in its outer coordinate system.
	 * @post The returned extent contains the shape or the shapes of the subGroups of this object.
	 * 	
	 *
	 */
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
				for(int i = 0; i < this.subGroups.length ; i++) {
					extentArray[i] = this.subGroups[i].getExtent();
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
					if(extentArray[i].getRight() < minimumX) {
						minimumX = extentArray[i].getRight();
					}
					if(extentArray[i].getLeft() > maximumX) {
						maximumX = extentArray[i].getLeft();
					}
					if(extentArray[i].getTop() > maximumY) {
						maximumY = extentArray[i].getTop();
					}
					if(extentArray[i].getBottom() < minimumY) {
						minimumY = extentArray[i].getBottom();
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
	
	/** Returns the list of subGroups of this shape group, or null if this is a leaf shape group.
	 * @creates result
	 * @post The returned list of ShapeGroups should be equal to the object's list of ShapeGroups (subGroups).
	 * 	| IntStream.range(0, getSubgroupCount()).allMatch(i -> getSubgroups().get(i).equals(result.get(i)))
	 * @post The returned list of ShapeGroups should remain the same length as the object's list of ShapeGroups.
	 * 	| result.size() == getSubgroupCount()
	 */
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
	
	/** Returns the subgroup at the given (zero-based) index in this non-leaf shape group's list of subGroups.
	 * @throws IllegalArgumentException
	 * 	| !((0 <= index) && (index < getSubgroupCount()))
	 */
	public ShapeGroup getSubgroup(int index) {
		return this.subGroups[index];
	}
	
	/** Returns the coordinates in this shape group's inner coordinate system of the point whose coordinates in the global coordinate system are the given coordinates.
	 * @throws IllegalArgumentException
	 * 	| !(globalCoordinates != null)
	 * 
	 * @creates result
	 * 
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		IntPoint result;
		double scaleX = (double)this.getExtent().getWidth() / (double)this.getOriginalExtent().getWidth();
		double scaleY = (double)this.getExtent().getHeight() / (double)this.getOriginalExtent().getHeight();
		double translateX = -1 * (((scaleX - 1) * (double)this.getOriginalExtent().getLeft()) + this.getOriginalExtent().getLeft() - this.getExtent().getLeft());
		double translateY = -1 * (((scaleY - 1) * (double)this.getOriginalExtent().getTop()) + this.getOriginalExtent().getTop() - this.getExtent().getTop());
		if(this.getParentGroup() != null) {
			result = this.getParentGroup().toInnerCoordinates(globalCoordinates);
			result = new IntPoint((int)((result.getX() - translateX) / scaleX), (int)((result.getY() - translateY) / scaleY));
		}
		else {
			result = new IntPoint((int)((globalCoordinates.getX() - translateX) / scaleX), (int)((globalCoordinates.getY() - translateY) / scaleY));
		}
		return result;
	}
	
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		double scaleX = (double)this.getExtent().getWidth() / (double)this.getOriginalExtent().getWidth();
		double scaleY = (double)this.getExtent().getHeight() / (double)this.getOriginalExtent().getHeight();
		double translateX = -1 * (((scaleX - 1) * (double)this.getOriginalExtent().getLeft()) + this.getOriginalExtent().getLeft() - this.getExtent().getLeft());
		double translateY = -1 * (((scaleY - 1) * (double)this.getOriginalExtent().getTop()) + this.getOriginalExtent().getTop() - this.getExtent().getTop());
		IntPoint result = new IntPoint((int)((innerCoordinates.getX() * scaleX) + translateX),(int)((innerCoordinates.getY() * scaleY) + translateY));
		if(this.getParentGroup() != null) {
			result = this.getParentGroup().toGlobalCoordinates(result);
		}
		return result;
	}
	
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		IntVector result;
		double scaleX = (double)this.getExtent().getWidth() / (double)this.getOriginalExtent().getWidth();
		double scaleY = (double)this.getExtent().getHeight() / (double)this.getOriginalExtent().getHeight();
		if(this.getParentGroup() != null) {
			result = this.getParentGroup().toInnerCoordinates(relativeGlobalCoordinates);
			result = new IntVector((int)((result.getX()) / scaleX), (int)((result.getY()) / scaleY));
		}
		else {
			result = new IntVector((int)((relativeGlobalCoordinates.getX()) / scaleX), (int)((relativeGlobalCoordinates.getY()) / scaleY));
		}
		return result;
	}
	/** Return the first subgroup in this non-leaf shape group's list of subGroups whose extent contains the given point, expressed in this shape group's inner coordinate system.
	 * @throws IllegalArgumentException
	 * 	| !(innerCoordinates != null)
	 * @creates result
	 * @post The returned ShapeGroup's extent should contain the given innerCoordinates
	 * 	| result.getExtent().contains(innerCoordinates)
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		for(int i = 0; i < this.getSubgroupCount(); i++) {
			if(this.subGroups[i].getExtent().contains(innerCoordinates)) {
				return this.subGroups[i];
			}
		}
		return null;
	}
	
	/** Registers the given extent as this shape group's extent, expressed in this shape group's outer coordinate system.
	 * @throws IllegalArgumentException
	 * 	| !(newExtent != null)
	 * 
	 * @post The object's extent should be the newExtent
	 * 	| getExtent().equals(newExtent)
	 */
	public void setExtent(Extent newExtent){
		this.extent = newExtent;
	}
	
	/** Moves this shape group to the front of its parent's list of subGroups.
	 * 
	 */
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
	
	/** Moves this shape group to the back of its parent's list of subGroups.
	 * 
	 */
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



