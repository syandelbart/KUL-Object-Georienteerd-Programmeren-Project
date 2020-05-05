package drawit.shapes1;

import drawit.IntPoint;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.ShapeGroup;

public class ShapeGroupShape implements Shape {
	ShapeGroup referencedShapeGroup;

    //Initializes this object to store the given ShapeGroup reference;
    public ShapeGroupShape(drawit.shapegroups1.ShapeGroup group) {
        this.referencedShapeGroup = group;
    }

    //Returns the ShapeGruop reference stored by this object.
    public ShapeGroup getShapeGroup() {
        return referencedShapeGroup;
    }

    public drawit.shapegroups1.ShapeGroup getParent() {
        return referencedShapeGroup.getParentGroup();
    }

    public boolean contains(drawit.IntPoint p) {
        return referencedShapeGroup.getExtent().contains(p);
    }

    public String getDrawingCommands() {
        return this.referencedShapeGroup.getDrawingCommands();
    }
    
    public ControlPoint[] createControlPoints() {
    	ControlPointShapeGroup[] result = new ControlPointShapeGroup[2];
    	IntPoint topLeft = referencedShapeGroup.getExtent().getTopLeft();
    	ControlPointShapeGroup shapeGroupTopLeft = new ControlPointShapeGroup(referencedShapeGroup,topLeft,"topleft");
		IntPoint bottomRight = referencedShapeGroup.getExtent().getBottomRight();
		ControlPointShapeGroup shapeGroupBottomRight = new ControlPointShapeGroup(referencedShapeGroup,bottomRight,"bottomright");
		result[0] = shapeGroupTopLeft;
		result[1] = shapeGroupBottomRight;
		return result;
    }
    
    public drawit.IntPoint toShapeCoordinates(drawit.IntPoint p){
    	return referencedShapeGroup.toInnerCoordinates(p);
    }
    
    public drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p){
    	return referencedShapeGroup.toGlobalCoordinates(p);
    }

}