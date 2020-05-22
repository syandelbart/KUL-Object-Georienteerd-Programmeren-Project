package drawit.shapegroups2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import drawit.IntPoint;

public class NonleafShapeGroup extends ShapeGroup {
	
	private ShapeGroup firstChild;
	private ShapeGroup lastChild;
	
	/** Returns the last child */
	public ShapeGroup getLastChild() {
		return this.lastChild;
	}
	
	/** Returns the first child */
	public ShapeGroup getFirstChild(){
		return this.firstChild;
	}
	
	/** sets a new first child
	 * @post the new first child is equal to the parameter
	 * |getFirstChild() == newFirstChild
	 */
	public void setFirstChild(ShapeGroup newFirstChild) {
		this.firstChild = newFirstChild;
	}
	
	/** sets a new last child
	 * @post the new last child is equal to the parameter
	 * |getLastChild() == newLastChild
	 */
	public void setLastChild(ShapeGroup newLastChild) {
		this.lastChild = newLastChild;
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
	 * 	| getOriginalExtent().getTop() == getExtent().getTop()
	 * 	| && getOriginalExtent().getLeft() == getExtent().getLeft()
	 * 	| && getOriginalExtent().getBottom() == getExtent().getBottom()
	 * 	| && getOriginalExtent().getRight() == getExtent().getRight()
	 */
	public NonleafShapeGroup(ShapeGroup[] subgroups) {
		if(!(subgroups != null)) {
			throw new IllegalArgumentException("subgroups is null");
		} else if(Arrays.stream(subgroups).anyMatch(v -> v == null)) {
			throw new IllegalArgumentException("subgroups contains a null element");
		}
		if(subgroups.length == 1) {
			this.firstChild = subgroups[0];
			this.firstChild.setParentGroup(this);
			this.lastChild = subgroups[0];
		}
		else if(subgroups.length > 1) {
			this.firstChild = subgroups[0];
			this.firstChild.setNextSibling(subgroups[1]);
			this.firstChild.setParentGroup(this);
			this.lastChild = subgroups[subgroups.length - 1];
			this.lastChild.setPreviousSibling(subgroups[subgroups.length - 2]);
			this.lastChild.setParentGroup(this);
			ShapeGroup current = firstChild.getNextSibling();
			for(int i = 1; i < subgroups.length - 1; i++) {
				current.setNextSibling(subgroups[i + 1]);
				current.setPreviousSibling(subgroups[i - 1]);
				current.setParentGroup(this);
				current = current.getNextSibling();
			}
		}
		
		super.setOriginalExtent(this.calculateExtent());
		super.setExtent(this.calculateExtent());
	}
    
	/** Returns the amount of subgroups this non-leaf shapegroup has
	    * @post result is zero if subgroups is null, otherwise it is equal to subgroups.length
	    */
	public int getSubgroupCount() {
		if(this.firstChild == null) {
			return 0;
		}
		int counter = 0;
		ShapeGroup current = this.firstChild;
		while(current != null) {
			counter += 1;
			current = current.getNextSibling();
		}
		return counter;
	}
	
	/** Returns the subgroup at the given (zero-based) index in this non-leaf shape group's list of subgroups.
	 * @throws IllegalArgumentException
	 * 	| !((0 <= index) && (index < getSubgroupCount()))
	 * @post result can't be null.
	 * 	| result != null
	 */
	public ShapeGroup getSubgroup(int index) {
		if(!((0 <= index) && (index < getSubgroupCount()))) {
			throw new IllegalArgumentException("index is out of bounds");
		}
		ShapeGroup current = this.firstChild;
		int counter = 0;
		while(counter != index) {
			counter += 1;
			current = current.getNextSibling();
		}
		return current;
	}
	
	/** Return the first subgroup in this non-leaf shape group's list of subgroups whose extent contains the given point, expressed in this shape group's inner coordinate system.
	 * @throws IllegalArgumentException
	 * 	| !(innerCoordinates != null)
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
	
	/** Returns the list of subgroups of this shape group, or null if this is a leaf shape group.
	 * @creates result
	 * @post The returned list of ShapeGroups should be equal to the object's list of ShapeGroups (subgroups).
	 * 	| IntStream.range(0, getSubgroupCount()).allMatch(i -> getSubgroup(i).equals(result.get(i)))
	 * @post The returned list of ShapeGroups should remain the same length as the object's list of ShapeGroups.
	 */
	public java.util.List<ShapeGroup> getSubgroups(){
		if(this.firstChild == null || this.lastChild == null) {
			return null;
		}
		List<ShapeGroup> result = new ArrayList<ShapeGroup>();
		ShapeGroup current = this.firstChild;
		while(current != null) {
			result.add(current);
			current = current.getNextSibling();
		}
		return result;
	}
	
	/** Returns the extent of this shape group, expressed in its outer coordinate system.
	 * @creates result
	 * 
	 * @post The result can't be null
	 * 	| result != null
	 * @post The returned extent contains all subgroups of this non-leaf shapegroup
	 *  | IntStream.range(0, getSubgroupCount()).allMatch(i -> ( ( getSubgroup(i).getExtent().getLeft() >= result.getLeft() && getSubgroup(i).getExtent().getRight() <= result.getRight() ) || ( getSubgroup(i).getExtent().getRight() >= result.getLeft() && getSubgroup(i).getExtent().getLeft() <= result.getRight() ) ) && ( ( getSubgroup(i).getExtent().getTop() >= result.getTop() && getSubgroup(i).getExtent().getBottom() <= result.getBottom() ) || ( getSubgroup(i).getExtent().getBottom() >= result.getTop() && getSubgroup(i).getExtent().getTop() <= result.getBottom() ) ) )
	 *  
	 */
	public Extent calculateExtent() {
		Extent[] extentArray = new Extent[this.getSubgroupCount()];
		for(int i = 0; i < this.getSubgroupCount() ; i++) {
			extentArray[i] = this.getSubgroup(i).getExtent();
		}
		int minimumX = extentArray[0].getLeft();
		int maximumX = extentArray[0].getRight();
		int minimumY = extentArray[0].getTop();
		int maximumY = extentArray[0].getBottom();
		for(int i = 0; i < extentArray.length ; i++) {
			extentArray[i] = this.getSubgroup(i).getExtent();
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
		return (Extent.ofLeftTopRightBottom(minimumX, minimumY, maximumX, maximumY));
	}
	
	/**
	 * Returns a textual representation of a sequence of drawing commands for drawing
	 * the shapes contained directly or indirectly by this shape group, expressed in this
	 * shape group's outer coordinate system.
	 * 
	 * For the syntax of the drawing commands, see {@code RoundedPolygon.getDrawingCommands()}.
	 * 
	 * @inspects | this
	 * @post | result != null
	 */
	public java.lang.String getDrawingCommands(){
		StringBuilder string = new StringBuilder();
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
		return string.toString();
	}
	
	
}
