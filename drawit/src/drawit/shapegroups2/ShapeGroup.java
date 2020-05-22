package drawit.shapegroups2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups2.Extent;
import drawit.shapegroups2.NonleafShapeGroup;
import drawit.IntVector;

abstract public class ShapeGroup {
	
	private NonleafShapeGroup parentGroup;
	private Extent originalExtent;
	private Extent extent;
	private ShapeGroup nextSibling;
	private ShapeGroup previousSibling;
	
	abstract public java.lang.String getDrawingCommands();
	abstract public void bringToFront();
	abstract public void sendToBack();
	
	/*
	 * MISSCHIEN DEZE FUNCTIES NAAR PRIVATE OMZETTEN
	 */
	
	public void setNextSibling(ShapeGroup newNextSibling) {
		this.nextSibling = newNextSibling;
	}
	
	public ShapeGroup getPreviousSibling() {
		return this.previousSibling;
	}
	
	public void setPreviousSibling(ShapeGroup newPreviousSibling) {
		this.previousSibling = newPreviousSibling;
	}
	
	public ShapeGroup getNextSibling(){
		return this.nextSibling;
	}
	
	public Extent getExtent() {
		return this.extent;
	}
	
	public void setOriginalExtent(Extent newOriginalExtent) {
		this.originalExtent = newOriginalExtent;
	}
	
	public void setParentGroup(NonleafShapeGroup newParentGroup) {
		this.parentGroup = newParentGroup;
	}
	
	/** Returns the extent of this shape group, expressed in its inner coordinate system. This coincides with the extent expressed in outer coordinates at the time of creation of the shape group. The shape transformation defined by this shape group is the one that transforms the original extent to the current extent. This method returns an equal result throughout the lifetime of this object.*/
	public Extent getOriginalExtent() {
		return this.originalExtent;
	}
	
	/** Returns the shape group that directly contains this shape group, or null if no shape group directly contains this shape group.*/
	public NonleafShapeGroup getParentGroup() {
		return this.parentGroup;
	}
	

	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the point
	 * whose coordinates in the global coordinate system are the given coordinates.
	 * 
	 * The global coordinate system is the outer coordinate system of this shape group's root ancestor,
	 * i.e. the ancestor that has no parent.
	 * 
	 * This shape group's inner coordinate system is defined by the fact that the coordinates of its extent
	 * in its inner coordinate system are constant and given by {@code this.getOriginalExtent()}.
	 * 
	 * Its outer coordinate system is defined by the fact that the coordinates of its extent in its outer
	 * coordinate system are as given by {@code this.getExtent()}.
	 * 
	 * When a shape group is created, its inner coordinate system coincides with its outer coordinate system
	 * (and with the global coordinate system). Subsequent calls of {@code this.setExtent()} may cause the
	 * inner and outer coordinate systems to no longer coincide.
	 * 
	 * The inner coordinate system of a non-leaf shape group always coincides with the outer coordinate
	 * systems of its subgroups. Furthermore, the coordinates of the vertices of a shape contained by a leaf
	 * shape group are interpreted in the inner coordinate system of the shape group.
	 * 
	 * @throws IllegalArgumentException if {@code globalCoordinates} is null
	 *    | globalCoordinates == null
	 * @inspects | this
	 * @post | result != null
	 * @post | result.equals(outerToInnerCoordinates(globalToOuterCoordinates(globalCoordinates)))
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		if (globalCoordinates == null)
			throw new IllegalArgumentException("globalCoordinates is null");
		
		return outerToInnerCoordinates(globalToOuterCoordinates(globalCoordinates));
	}

	/**
	 * @throws IllegalArgumentException if {@code outerCoordinates} is null
	 *    | outerCoordinates == null
	 * @inspects | this
	 * @post | result != null
	 * @post | result.equals(getOriginalExtent().getTopLeft().plus(
	 *       |     outerToInnerCoordinates(outerCoordinates.minus(getExtent().getTopLeft()))))
	 */
	public IntPoint outerToInnerCoordinates(IntPoint outerCoordinates) {
		if (outerCoordinates == null)
			throw new IllegalArgumentException("outerCoordinates is null");
		
		return originalExtent.getTopLeft().plus(
				outerToInnerCoordinates(outerCoordinates.minus(this.getExtent().getTopLeft())));
	}
	
	/**
	 * @throws IllegalArgumentException if {@code relativeOuterCoordinates} is null
	 * @inspects | this
	 * @post | result != null
	 */
	public IntVector outerToInnerCoordinates(IntVector relativeOuterCoordinates) {
		if (relativeOuterCoordinates == null)
			throw new IllegalArgumentException("relativeOuterCoordinates is null");
		
		return new IntVector(
				(int)((long)relativeOuterCoordinates.getX() * this.getOriginalExtent().getWidth() / this.getExtent().getWidth()),
				(int)((long)relativeOuterCoordinates.getY() * this.getOriginalExtent().getHeight() / this.getExtent().getHeight()));
	}
	
	/**
	 * @throws IllegalArgumentException if {@code globalCoordinates} is null
	 *    | globalCoordinates == null
	 * @inspects | this
	 * @post | result != null
	 * @post | result.equals(
	 *       |     getParentGroup() == null ?
	 *       |         globalCoordinates
	 *       |     :
	 *       |         getParentGroup().toInnerCoordinates(globalCoordinates)
	 *       | )
	 */
	public IntPoint globalToOuterCoordinates(IntPoint globalCoordinates) {
		if (globalCoordinates == null)
			throw new IllegalArgumentException("globalCoordinates is null");
		
		return this.getParentGroup() == null ? globalCoordinates : this.getParentGroup().toInnerCoordinates(globalCoordinates);
	}
	
	/**
	 * @throws IllegalArgumentException if {@code relativeInnerCoordinates} is null
	 *    | relativeInnerCoordinates == null
	 * @inspects | this
	 * @post | result != null
	 */
	public IntVector innerToOuterCoordinates(IntVector relativeInnerCoordinates) {
		if (relativeInnerCoordinates == null)
			throw new IllegalArgumentException("relativeInnerCoordinates is null");
		
		return new IntVector(
				(int)((long)relativeInnerCoordinates.getX() * this.getExtent().getWidth() / originalExtent.getWidth()),
				(int)((long)relativeInnerCoordinates.getY() * this.getExtent().getHeight() / originalExtent.getHeight()));
	}
	
	/**
	 * @throws IllegalArgumentException if {@code innerCoordinates} is null
	 *    | innerCoordinates == null
	 * @inspects | this
	 * @post | result != null
	 * @post | result.equals(getExtent().getTopLeft().plus(
	 *       |     innerToOuterCoordinates(innerCoordinates.minus(getOriginalExtent().getTopLeft()))))
	 */
	public IntPoint innerToOuterCoordinates(IntPoint innerCoordinates) {
		if (innerCoordinates == null)
			throw new IllegalArgumentException("innerCoordinates is null");
		
		return getExtent().getTopLeft().plus(
				innerToOuterCoordinates(innerCoordinates.minus(getOriginalExtent().getTopLeft())));
	}
	
	/**
	 * @throws IllegalArgumentException if {@code outerCoordinates} is null
	 * @inspects | this
	 * @post | result != null
	 * @post | result.equals(
	 *       |     getParentGroup() == null ?
	 *       |         outerCoordinates
	 *       |     :
	 *       |         getParentGroup().toGlobalCoordinates(outerCoordinates)
	 *       | )
	 */
	public IntPoint outerToGlobalCoordinates(IntPoint outerCoordinates) {
		if (outerCoordinates == null)
			throw new IllegalArgumentException("outerCoordinates is null");
		
		return this.getParentGroup() == null ? outerCoordinates : this.getParentGroup().toGlobalCoordinates(outerCoordinates);
	}
	
	/**
	 * Returns the coordinates in the global coordinate system of the point whose coordinates
	 * in this shape group's inner coordinate system are the given coordinates.
	 * 
	 * @throws IllegalArgumentException if {@code innerCoordinates} is null
	 * @inspects | this
	 * @post | result != null
	 * @post | result.equals(outerToGlobalCoordinates(innerToOuterCoordinates(innerCoordinates)))
	 */
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		if (innerCoordinates == null)
			throw new IllegalArgumentException("innerCoordinates is null");
		
		return outerToGlobalCoordinates(innerToOuterCoordinates(innerCoordinates));
	}
	
	/**
	 * @inspects | this
	 * @post | result != null
	 */
	public IntVector globalToOuterCoordinates(IntVector relativeGlobalCoordinates) {
		if (relativeGlobalCoordinates == null)
			throw new IllegalArgumentException("relativeGlobalCoordinates is null");
		
		return this.getParentGroup() == null ? relativeGlobalCoordinates : this.getParentGroup().toInnerCoordinates(relativeGlobalCoordinates);
	}
	
	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the vector
	 * whose coordinates in the global coordinate system are the given coordinates.
	 * 
	 * This transformation is affected only by mutations of the width or height of this shape group's
	 * extent, not by mutations of this shape group's extent that preserve its width and height.
	 * 
	 * @inspects | this
	 * @post | result != null
	 */
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		if (relativeGlobalCoordinates == null)
			throw new IllegalArgumentException("relativeGlobalCoordinates is null");

		return outerToInnerCoordinates(globalToOuterCoordinates(relativeGlobalCoordinates));
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