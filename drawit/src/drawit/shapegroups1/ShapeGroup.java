package drawit.shapegroups1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.IntVector;

abstract public class ShapeGroup {
	
	private NonleafShapeGroup parentGroup;
	private Extent originalExtent;
	private Extent extent;
	
	public ShapeGroup(){
		
    }
	
	abstract public java.lang.String getDrawingCommands();
	abstract public void bringToFront();
	abstract public void sendToBack();
	
	public Extent getExtent() {
		return this.extent;
	}
	
	public void setOriginalExtent(Extent newOriginalExtent) {
		this.originalExtent = newOriginalExtent;
	}
	
	public void setParentGroup(NonleafShapeGroup newParentGroup) {
		this.parentGroup = newParentGroup;
	}
	
	/** Returns the extent of this shape group, expressed in its outer coordinate system.
	 * @creates result
	 * 
	 * @post The result can't be null
	 * 	|result != null
	 * @post The returned extent contains the shape or subgroups' shapes of this object.
	 */
		
	/** Returns the extent of this shape group, expressed in its inner coordinate system. This coincides with the extent expressed in outer coordinates at the time of creation of the shape group. The shape transformation defined by this shape group is the one that transforms the original extent to the current extent. This method returns an equal result throughout the lifetime of this object.*/
	public Extent getOriginalExtent() {
		return this.originalExtent;
	}
	
	/** Returns the shape group that directly contains this shape group, or null if no shape group directly contains this shape group.*/
	public NonleafShapeGroup getParentGroup() {
		return this.parentGroup;
	}
	
	/** Returns the coordinates in this shape group's inner coordinate system of the point whose coordinates in the global coordinate system are the given coordinates.*/
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		if(!(globalCoordinates != null)) {
			throw new IllegalArgumentException("globalCoordinates is null");
		}
		if(globalCoordinates.equals(this.getExtent().getBottomRight())) {
			return this.getOriginalExtent().getBottomRight();
		}
		if(globalCoordinates.equals(this.getExtent().getTopLeft())) {
			return this.getOriginalExtent().getTopLeft();
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
	
	/** Returns the coordinates in the global coordinate system of the point whose coordinates in this shape group's inner coordinate system are the given coordinates. */
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
	
	/** Returns the coordinates in this shape group's inner coordinate system of the vector whose coordinates in the global coordinate system are the given coordinates. This transformation is affected only by mutations of the width or height of this shape group's extent, not by mutations of this shape group's extent that preserve its width and height. */
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
}



