package drawit.shapes2;

public interface Shape {
    
    drawit.shapegroups2.ShapeGroup getParent();
    boolean contains(drawit.IntPoint p);
    String getDrawingCommands();
    drawit.IntPoint toShapeCoordinates(drawit.IntPoint p);
    drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p);
    ControlPoint[] createControlPoints();
}


