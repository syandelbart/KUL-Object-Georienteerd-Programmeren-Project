package drawit.shapegroups1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import drawit.IntPoint;

public class NonleafShapeGroup extends ShapeGroup {
	
	private ShapeGroup[] subgroups;
	
	/** Initializes this object to represent a non-leaf shape group that directly contains the given subgroups, in the given order.
     * @mutates | this
     * @throws IllegalArgumentException if the argument subgroups is null
     *     | !(subgroups != null)
     * @throws IllegalArgumentException if any ShapeGroup contained by the subgroups array is null
     *     | Arrays.stream(subgroups).anyMatch(v -> v == null)
     * 
     * @post The object's subgroups should be equal to the subgroups argument.
     *     | IntStream.range(0, getSubgroupCount()).allMatch(i -> getSubgroups().get(i).equals(subgroups[i]))
     * @post The object's original extent is equal to the extent.
     *     | getOriginalExtent().getTop() == getExtent().getTop()
     *     | && getOriginalExtent().getLeft() == getExtent().getLeft()
     *     | && getOriginalExtent().getBottom() == getExtent().getBottom()
     *     | && getOriginalExtent().getRight() == getExtent().getRight()
     */
	
    public NonleafShapeGroup(ShapeGroup[] subgroups) {
    	super();
        if(!(subgroups != null)) {
            throw new IllegalArgumentException("subgroups is null");
        } else if(Arrays.stream(subgroups).anyMatch(v -> v == null)) {
            throw new IllegalArgumentException("subgroups contains a null element");
        }

        this.subgroups = new ShapeGroup[subgroups.length];
        for(int i = 0; i < subgroups.length; i++) {
            this.subgroups[i] = subgroups[i];
            this.subgroups[i].setParentGroup(this);
        }
        
        super.setExtent(this.calculateExtent());
		super.setOriginalExtent(this.calculateExtent());
    }
	
	public int getSubgroupCount() {
		if(this.subgroups == null) {
			return 0;
		}
		return this.subgroups.length;
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
		return this.subgroups[index];
	}
	
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		if(!(innerCoordinates != null)) {
			throw new IllegalArgumentException("innerCoordinates is null");
		}
		
		for(int i = 0; i < this.getSubgroupCount(); i++) {
			if(this.subgroups[i].getExtent().contains(innerCoordinates)) {
				return this.subgroups[i];
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
		if(this.subgroups == null) {
			return null;
		}
		List<ShapeGroup> result = new ArrayList<ShapeGroup>();
		for(int i = 0; i < this.getSubgroupCount(); i++) {
			result.add(this.subgroups[i]);
		}
		return result;
	}
	
	private int getLocation() {
		int counter = 0;
		while(!this.getParentGroup().getSubgroup(counter).equals(this)) {
			counter++;
		}
		return counter;
	}
	/** Moves this shape group to the front of its parent's list of subgroups.
	 * @mutates this
	 * 
	 * @throws IllegalArgumentException
	 * 	| getParentGroup() == null
	 * 
	 * @post This object is the first child of its getParentGroup().
	 * 	| getParentGroup().getSubgroup(0) == this
	 * 
	 * @post The indexes of the children of getParentGroup that were in front of this object are incremented by one.
	 * 	| IntStream.range(0,old(getLocation())).allMatch(i -> old(getParentGroup().getSubgroups()).get(i).equals(getParentGroup().getSubgroup(i + 1)))
	 * 
	 * @post The indexes of the children of getParentGroup that were behind this object are the same.
	 * 	| IntStream.range(old(getLocation()) + 1, getParentGroup().getSubgroupCount()).allMatch(i -> old(getParentGroup().getSubgroups()).get(i).equals(getParentGroup().getSubgroup(i)))
	 */
	public void bringToFront() {
		int location = this.getLocation();
		ShapeGroup[] result = new ShapeGroup[this.getParentGroup().getSubgroupCount()];
		for(int i = 0; i < location; i++) {
			result[i+1] = this.getParentGroup().getSubgroup(i);
		}
		for(int i = location + 1; i < this.getParentGroup().getSubgroupCount(); i++) {
			result[i] = this.getParentGroup().getSubgroup(i);
		}
		result[0] = this;
		this.getParentGroup().subgroups = result;
	}
	
	/** Moves this shape group to the back of its parent's list of subgroups.
	 * @mutates this
	 * 
	 * @throws IllegalArgumentException
	 * 	| getParentGroup() == null
	 * 
	 * @post This object is the last child of its getParentGroup().
	 * 	| getParentGroup().getSubgroup(getParentGroup().getSubgroupCount() - 1) == this
	 * 
	 * @post The indexes of the children of getParentGroup that were in front of this object are the same.
	 * 	| IntStream.range(0,old(getLocation())).allMatch(i -> old(getParentGroup().getSubgroups()).get(i).equals(getParentGroup().getSubgroup(i)))
	 * 
	 * @post The indexes of the children of getParentGroup that were behind this object are decremented by one.
	 * 	| IntStream.range(old(getLocation()) + 1, getParentGroup().getSubgroupCount()).allMatch(i -> old(getParentGroup().getSubgroups()).get(i).equals(getParentGroup().getSubgroups().get(i - 1)))
	 */
	public void sendToBack() {
		int location = this.getLocation();
		ShapeGroup[] result = new ShapeGroup[this.getParentGroup().getSubgroupCount()];
		for(int i = 0; i < location; i++) {
			result[i] = this.getParentGroup().getSubgroup(i);
		}
		for(int i = location + 1; i < this.getParentGroup().getSubgroupCount(); i++) {
			result[i - 1] = this.getParentGroup().getSubgroup(i);
		}
		result[this.getParentGroup().getSubgroupCount() - 1] = this;
		this.getParentGroup().subgroups = result;
	}
	
	public void setExtent(Extent newExtent){
		if(!(newExtent != null)) {
			throw new IllegalArgumentException();
		}
		super.setExtent(newExtent);
	}
	
	public Extent calculateExtent() {
		Extent[] extentArray = new Extent[this.getSubgroupCount()];
		for(int i = 0; i < this.getSubgroupCount() ; i++) {
			extentArray[i] = this.subgroups[i].getExtent();
		}
		int minimumX = extentArray[0].getLeft();
		int maximumX = extentArray[0].getRight();
		int minimumY = extentArray[0].getTop();
		int maximumY = extentArray[0].getBottom();
		for(int i = 0; i < extentArray.length ; i++) {
			extentArray[i] = subgroups[i].getExtent();
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
	
	public java.lang.String calculateDrawingCommands(){
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
