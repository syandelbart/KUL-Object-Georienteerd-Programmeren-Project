package drawit.shapes2;

import drawit.IntPoint;
import drawit.shapegroups2.LeafShapeGroup;
import drawit.shapegroups2.ShapeGroup;

public class ShapeGroupShape implements Shape {
	ShapeGroup referencedShapeGroup;

    //Initializes this object to store the given ShapeGroup reference;
    public ShapeGroupShape(drawit.shapegroups2.ShapeGroup group) {
        this.referencedShapeGroup = group;
    }

    //Returns the ShapeGruop reference stored by this object.
    public ShapeGroup getShapeGroup() {
        return referencedShapeGroup;
    }

    public drawit.shapegroups2.ShapeGroup getParent() {
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
    	if(this.referencedShapeGroup.getParentGroup() == null) {
    		return p;
    	}
    	else {
    		return referencedShapeGroup.getParentGroup().toInnerCoordinates(p);
    	}
    }
    
    public drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p){
    	if(this.referencedShapeGroup.getParentGroup() == null) {
    		return p;
    	}
    	else {
    		return referencedShapeGroup.getParentGroup().toGlobalCoordinates(p);
    	}
    }

}
