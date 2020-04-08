package drawit.shapegroups2;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.IntVector;

public class ShapeGroup {
	
	/**
	 * @representationObject
	 * @invar | (shape != null) || (getSubgroups() != null)
	 */
	
	
	private RoundedPolygon shape;
	private ShapeGroup parentGroup;
	private Extent originalExtent;
	private Extent extent;
	private ShapeGroup nextSibling;
	private ShapeGroup previousSibling;
	private ShapeGroup firstChild;
	private ShapeGroup lastChild;
	
	/**	Initializes this ShapeGroup with the given shape and calculates the extent and originalExtent.
	 * @mutates | this
	 * @throws IllegalArgumentException if the argument shape is null
	 * 	| !(shape != null)
	 * 
	 * @post The object's shape is equal to the given shape.
	 * 	| getShape().equals(shape)
	 * @post The object's original extent is equal to the extent.
	 */
	public ShapeGroup(RoundedPolygon shape) {
		if(!(shape != null)) {
			throw new IllegalArgumentException("shape is null");
		}
		
		this.shape = shape;
		this.originalExtent = this.getExtent();
		this.extent = this.getExtent();
	}
	
	/** Initializes this object to represent a non-leaf shape group that directly contains the given subgroups, in the given order.
	 * @mutates | this
	 * @throws IllegalArgumentException if the argument subgroups is null
	 * 	| !(subgroups != null)
	 * @throws IllegalArgumentException if any ShapeGroup contained by the subgroups array is null
	 * 	| Arrays.stream(subgroups).anyMatch(v -> v == null)
	 * 
	 * @post The object's subgroups should be equal to the subgroups argument.
	 * 	| IntStream.range(0, getSubgroupCount()).allMatch(i -> getSubgroups().get(i).equals(subgroups[i]))
	 * @post The object's original extent is equal to the extent.
	 */
	public ShapeGroup(ShapeGroup[] subgroups) {
		if(!(subgroups != null)) {
			throw new IllegalArgumentException("subgroups is null");
		} else if(Arrays.stream(subgroups).anyMatch(v -> v == null)) {
			throw new IllegalArgumentException("subgroups contains a null element");
		}
		if(subgroups.length == 1) {
			this.firstChild = subgroups[0];
			this.firstChild.parentGroup = this;
		}
		else if(subgroups.length > 1) {
			this.firstChild = subgroups[0];
			this.firstChild.nextSibling = subgroups[1];
			this.firstChild.parentGroup = this;
			this.lastChild = subgroups[subgroups.length - 1];
			this.lastChild.previousSibling = subgroups[subgroups.length - 2];
			this.lastChild.parentGroup = this;
			ShapeGroup current = firstChild.nextSibling;
			for(int i = 1; i < subgroups.length - 1; i++) {
				current.nextSibling = subgroups[i + 1];
				current.previousSibling = subgroups[i - 1];
				current.parentGroup = this;
				current = current.nextSibling;
			}
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
		if(this.extent == null) {
			if(this.firstChild == null) {
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
				Extent[] extentArray = new Extent[this.getSubgroupCount()];
				for(int i = 0; i < this.getSubgroupCount() ; i++) {
					extentArray[i] = this.getSubgroup(i).getExtent();
				}
				int minimumX = extentArray[0].getLeft();
				int maximumX = extentArray[0].getRight();
				int minimumY = extentArray[0].getTop();
				int maximumY = extentArray[0].getBottom();
				for(int i = 0; i < extentArray.length ; i++) {
					extentArray[i] = getSubgroup(i).getExtent();
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
		return this.shape;
	}
	

	/** Returns the list of subgroups of this shape group, or null if this is a leaf shape group.
	 * @creates result
	 * @post The returned list of ShapeGroups should be equal to the object's list of ShapeGroups (subgroups).
	 * 	| IntStream.range(0, getSubgroupCount()).allMatch(i -> getSubgroup(i).equals(result.get(i)))
	 * @post The returned list of ShapeGroups should remain the same length as the object's list of ShapeGroups.
	 * 	| result.size() == getSubgroupCount()
	 */
	public java.util.List<ShapeGroup> getSubgroups(){
		List<ShapeGroup> result = new ArrayList<ShapeGroup>();
		ShapeGroup current = this.firstChild;
		while(current != null) {
			result.add(current);
			current = current.nextSibling;
		}
		return result;
	}
	
	public int getSubgroupCount() {
		if(this.firstChild == null) {
			return 0;
		}
		int counter = 0;
		ShapeGroup current = this.firstChild;
		while(current != null) {
			counter += 1;
			current = current.nextSibling;
		}
		return counter;
	}
	
	/** Returns the subgroup at the given (zero-based) index in this non-leaf shape group's list of subgroups.
	 * @throws IllegalArgumentException
	 * 	| !((0 <= index) && (index < getSubgroupCount()))
	 */
	public ShapeGroup getSubgroup(int index) {
		if(!((0 <= index) && (index < getSubgroupCount()))) {
			throw new IllegalArgumentException("index is out of bounds");
		}
		ShapeGroup current = this.firstChild;
		int counter = 0;
		while(counter != index) {
			counter += 1;
			current = current.nextSibling;
		}
		return current;
	}
	
	/** Returns the coordinates in this shape group's inner coordinate system of the point whose coordinates in the global coordinate system are the given coordinates.
	 * @throws IllegalArgumentException
	 * 	| !(globalCoordinates != null)
	 * 
	 * @creates result
	 * 
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		if(!(globalCoordinates != null)) {
			throw new IllegalArgumentException("globalCoordinates is null");
		}
		
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
		if(!(innerCoordinates != null)) {
			throw new IllegalArgumentException("innerCoordinates is null");
		}
		
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
		if(!(relativeGlobalCoordinates != null)) {
			throw new IllegalArgumentException("relativeGlobalCoordinates is null");
		}
		
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
	
	/** Return the first subgroup in this non-leaf shape group's list of subgroups whose extent contains the given point, expressed in this shape group's inner coordinate system.
	 * @throws IllegalArgumentException
	 * 	| !(innerCoordinates != null)
	 * @creates result
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		if(!(innerCoordinates != null)) {
			throw new IllegalArgumentException("innerCoordinates is null");
		}
		
		for(int i = 0; i < this.getSubgroupCount(); i++) {
			if(this.getSubgroup(i).getExtent().contains(innerCoordinates)) {
				return this.getSubgroup(i);
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
		if(!(newExtent != null)) {
			throw new IllegalArgumentException();
		}
		
		this.extent = newExtent;
	}
	
	/** Moves this shape group to the front of its parent's list of subgroups.
	 * 
	 */
	public void bringToFront() {
		if(this.getParentGroup() == null) {
			throw new IllegalArgumentException("parentgroup is null");
		}
		if(this.getParentGroup().firstChild != this) {
			if(this.getParentGroup().lastChild == this) {
				this.previousSibling.nextSibling = null;
				this.getParentGroup().lastChild = this.previousSibling;
				this.previousSibling = null;
				this.nextSibling = this.getParentGroup().firstChild;
				this.getParentGroup().firstChild.previousSibling = this;
				this.getParentGroup().firstChild = this;
			}
			else {
				this.nextSibling.previousSibling = this.previousSibling;
				this.previousSibling.nextSibling = this.nextSibling;
				this.nextSibling = this.parentGroup.firstChild;
				this.parentGroup.firstChild.previousSibling = this;
				this.parentGroup.firstChild = this;
				this.previousSibling = null;
			}
		}
		
	}
	
	/** Moves this shape group to the back of its parent's list of subgroups.
	 * 
	 */
	public void sendToBack() {
		if(this.getParentGroup() == null) {
			throw new IllegalArgumentException("parentgroup is null");
		}
		if(this.getParentGroup().lastChild != this) {
			if(this.getParentGroup().firstChild == this) {
				this.nextSibling.previousSibling = null;
				this.getParentGroup().firstChild = this.nextSibling;
				this.nextSibling = null;
				this.previousSibling = this.getParentGroup().lastChild;
				this.getParentGroup().lastChild.nextSibling = this;
				this.getParentGroup().lastChild = this;
			}
			else {
				this.previousSibling.nextSibling = this.nextSibling;
				this.nextSibling.previousSibling = this.previousSibling;
				this.previousSibling = this.parentGroup.lastChild;
				this.parentGroup.lastChild.nextSibling = this;
				this.parentGroup.lastChild = this;
				this.nextSibling = null;
			}
		}
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