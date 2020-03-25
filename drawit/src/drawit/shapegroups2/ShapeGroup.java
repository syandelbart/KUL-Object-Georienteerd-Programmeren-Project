package drawit.shapegroups2;
import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;

public class ShapeGroup {
	private RoundedPolygon shape;
	private ShapeGroup[] subgroups;
	
	public ShapeGroup(RoundedPolygon shape) {
		this.shape = shape;
	}
	
	public ShapeGroup(ShapeGroup[] subgroups) {
		this.subgroups = subgroups;
	}
	
	public Extent getExtent() {
		if(subgroups.length == 0) {
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
			for(int subgroupcounter = 0;subgroupcounter<this.subgroups.length;subgroupcounter++) {
				for(int i = 0; i < this.subgroups.length ; i++) {
					subgroupExtentArray[i] = this.subgroups[subgroupcounter].getExtent();
				}
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
				
				//if point has higher X-coordinate than stored highest X-coordinate
				if(cursor.getRight() > Xhigh) {
					Xhigh = cursor.getRight();
				}
				
				//if point has lower Y-coordinate than stored lowest Y-coordinate
				if(cursor.getTop() < Ylow) {
					Ylow = cursor.getTop();
				}
				
				//if point has higher Y-coordinate than stored highest Y-coordinate
				if(cursor.getBottom() > Yhigh) {
					Yhigh = cursor.getBottom();
				}	
			}
			
			return Extent.ofLeftTopRightBottom(Xlow, Ylow, Xhigh, Yhigh);
		}
	}
	
	
	
	
	
	
}
