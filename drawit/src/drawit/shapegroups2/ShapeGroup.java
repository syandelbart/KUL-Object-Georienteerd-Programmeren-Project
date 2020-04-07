package drawit.shapegroups2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.IntVector;

public class ShapeGroup {
	
	/**
	 * @representationObject
	 * @invar | (shape != null) || (subgroups != null)
	 * @invar | Arrays.stream(vertices).allMatch(v -> v != null)
	 */
	
	
	private RoundedPolygon shape;
	private ShapeGroup[] subgroups;
	private ShapeGroup parentGroup;
	private Extent originalExtent;
	private Extent extent;
	private int location;
	
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
		if(!(shape != null)) {
			throw new IllegalArgumentException("shape is null");
		}
		
		this.shape = shape;
		this.originalExtent = this.getExtent();
		this.extent = this.getExtent();
	}
	
	/**
	 * @mutates | this
	 * @throws IllegalArgumentException if the argument subgroups is null
	 * 	| !(subgroups != null)
	 * @throws IllegalArgumentException if any ShapeGroup contained by the subgroups array is null
	 * 	| Arrays.stream(subgroups).anyMatch(v -> v == null)
	 * 
	 * @post The object's subgroups should be equal to the subgroups argument.
	 * 	| IntStream.range(0, getSubgroupCount()).allMatch(i -> getSubgroups().get(i).equals(subgroups[i]))
	 * @post The object's original extent is equal to the extent.
	 * 	| getOriginalExtent().equals(getExtent())
	 */
	public ShapeGroup(ShapeGroup[] subgroups) {
		if(!(subgroups != null)) {
			throw new IllegalArgumentException("subgroups is null");
		} else if(Arrays.stream(subgroups).anyMatch(v -> v == null)) {
			throw new IllegalArgumentException("subgroups contains a null element");
		}
		
		this.subgroups = new ShapeGroup[subgroups.length];
		for(int i = 0; i < subgroups.length; i++) {
			this.subgroups[i] = subgroups[i];
			this.subgroups[i].parentGroup = this;
			this.subgroups[i].location = i;
		}
		this.originalExtent = this.getExtent();
		this.extent = this.getExtent();
	}
	
	public Extent getExtent() {
		if(this.extent == null) {
			if(this.subgroups == null) {
				IntPoint[] shapeVertices = this.shape.getVertices();
				IntPoint cursor = shapeVertices[0];
				int Xlow = cursor.getX();
				int Xhigh = cursor.getX();
				int Ylow = cursor.getY();
				int Yhigh = cursor.getY();
				for(int i=1;i<shapeVertices.length;i++) {
					cursor = shapeVertices[i];
					
					//if point has lower X-coordinate than stored lowest X-coordinate
					if(cursor.getX() < Xlow) {
						Xlow = cursor.getX();
					}
					
					//if point has higher X-coordinate than stored highest X-coordinate
					if(cursor.getX() > Xhigh) {
						Xhigh = cursor.getX();
					}
					
					//if point has lower Y-coordinate than stored lowest Y-coordinate
					if(cursor.getY() < Ylow) {
						Ylow = cursor.getY();
					}
					
					//if point has higher Y-coordinate than stored highest Y-coordinate
					if(cursor.getY() > Yhigh) {
						Yhigh = cursor.getY();
					}
					
				}
				
				return Extent.ofLeftTopRightBottom(Xlow, Ylow, Xhigh, Yhigh);
			} else {
				Extent[] subgroupExtentArray = new Extent[this.subgroups.length];
				for(int i = 0; i < this.subgroups.length ; i++) {
					subgroupExtentArray[i] = this.subgroups[i].getExtent();
				}
				Extent cursor = subgroupExtentArray[0];
				int Xlow = cursor.getLeft();
				int Xhigh = cursor.getRight();
				int Ylow = cursor.getTop();
				int Yhigh = cursor.getBottom();
				
				for(int i=1;i<subgroupExtentArray.length;i++) {
					cursor = subgroupExtentArray[i];
					
					//if point has lower X-coordinate than stored lowest X-coordinate
					if(cursor.getLeft() < Xlow) {
						Xlow = cursor.getLeft();
					}
					
					if(cursor.getRight() < Xlow) {
						Xlow = cursor.getRight();
					}
					
					//if point has higher X-coordinate than stored highest X-coordinate
					if(cursor.getRight() > Xhigh) {
						Xhigh = cursor.getRight();
					}
					
					if(cursor.getLeft() > Xhigh) {
						Xhigh = cursor.getLeft();
					}
					
					//if point has lower Y-coordinate than stored lowest Y-coordinate
					if(cursor.getTop() < Ylow) {
						Ylow = cursor.getTop();
					}
					
					if(cursor.getBottom() < Ylow) {
						Ylow = cursor.getBottom();
					}
					
					//if point has higher Y-coordinate than stored highest Y-coordinate
					if(cursor.getBottom() > Yhigh) {
						Yhigh = cursor.getBottom();
					}
					
					if(cursor.getTop() > Yhigh) {
						Yhigh = cursor.getTop();
					}
				}
				
				return Extent.ofLeftTopRightBottom(Xlow, Ylow, Xhigh, Yhigh);
			}
		} else {
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
	
	/**
	 * @creates result
	 * @post The returned list of ShapeGroups should be equal to the object's list of ShapeGroups (subgroups).
	 * 	| IntStream.range(0, getSubgroupCount()).allMatch(i -> getSubgroups().get(i).equals(result.get(i)))
	 * @post The returned list of ShapeGroups should remain the same length as the object's list of ShapeGroups.
	 * 	| result.size() == getSubgroupCount()
	 */
	public java.util.List<ShapeGroup> getSubgroups(){
		ArrayList<ShapeGroup> result = new ArrayList<ShapeGroup>();
		for(int i = 0; i < this.subgroups.length; i++) {
			result.add(this.subgroups[i]);
		}
		return result;
	}
	
	public int getSubgroupCount() {
		return this.subgroups.length;
	}
	
	/**
	 * @throws IllegalArgumentException
	 * 	| !((0 <= index) && (index < getSubgroupCount()))
	 */
	public ShapeGroup getSubgroup(int index) {
		if(!((0 <= index) && (index < getSubgroupCount()))) {
			throw new IllegalArgumentException("index is out of bounds");
		}
		return this.subgroups[index];
	}
	
	/**
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
	
	/**
	 * @throws IllegalArgumentException
	 * 	| !(innerCoordinates != null)
	 * @creates result
	 * @post The returned ShapeGroup's extent should contain the given innerCoordinates
	 * 	| result.getExtent().contains(innerCoordinates)
	 */
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
	
	/**
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
	
	/**
	 * 
	 */
	public void bringToFront() {
		this.getParentGroup().subgroups[location] = this.getParentGroup().subgroups[0];
		this.getParentGroup().subgroups[0] = this;
	}
	
	/**
	 * 
	 */
	public void sendToBack() {
		this.getParentGroup().subgroups[location] = this.getParentGroup().subgroups[this.getParentGroup().getSubgroupCount()-1];
		this.getParentGroup().subgroups[this.getParentGroup().getSubgroupCount()-1] = this;
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