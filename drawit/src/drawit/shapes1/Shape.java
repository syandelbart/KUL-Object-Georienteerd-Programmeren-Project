package drawit.shapes1;

public interface Shape {
    
    drawit.shapegroups1.ShapeGroup getParent();
    boolean contains(drawit.IntPoint p);
    String getDrawingCommands();
    drawit.IntPoint toShapeCoordinates(drawit.IntPoint p);
    drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p);
    ControlPoint[] createControlPoints();
}


