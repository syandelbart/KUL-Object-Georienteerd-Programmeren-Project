package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;
import drawit.shapegroups1.exporter.ShapeGroupExporter;

class ShapeGroupExporter1Test {

	@Test
	void test() {
		//we use the same objects as ShapeGroups1ShapeGroupTest as only the output of this function is important, not the objects used
		
		//creating objects to experiment with
		//creating points for polygons to insert in shape
			//figure 1
			IntPoint firstPolygonPointA = new IntPoint(0,0);
			IntPoint firstPolygonPointB = new IntPoint(10,0);
			IntPoint firstPolygonPointC = new IntPoint(10,10);
			IntPoint firstPolygonPointD = new IntPoint(0,10);
		
			//figure 2
			IntPoint secondPolygonPointA = new IntPoint(5,5);
			IntPoint secondPolygonPointB = new IntPoint(15,5);
			IntPoint secondPolygonPointC = new IntPoint(15,15);
			IntPoint secondPolygonPointD = new IntPoint(5,15);
			
			//figure 3
			IntPoint thirdPolygonPointA = new IntPoint(10,10);
			IntPoint thirdPolygonPointB = new IntPoint(20,10);
			IntPoint thirdPolygonPointC = new IntPoint(20,20);
			IntPoint thirdPolygonPointD = new IntPoint(10,20);
			
			
		//creating vertice collections from points
			IntPoint[] firstVerticeArray = new IntPoint[] {firstPolygonPointA,firstPolygonPointB,firstPolygonPointC,firstPolygonPointD};
			IntPoint[] secondVerticeArray = new IntPoint[] {secondPolygonPointA,secondPolygonPointB,secondPolygonPointC,secondPolygonPointD};
			IntPoint[] thirdVerticeArray = new IntPoint[] {thirdPolygonPointA,thirdPolygonPointB,thirdPolygonPointC,thirdPolygonPointD};

			
		//creating roundedpolygon from vertice collections
			RoundedPolygon firstRoundedPolygon = new RoundedPolygon();
			firstRoundedPolygon.setVertices(firstVerticeArray);
			
			RoundedPolygon secondRoundedPolygon = new RoundedPolygon();
			secondRoundedPolygon.setVertices(secondVerticeArray);
			
			RoundedPolygon thirdRoundedPolygon = new RoundedPolygon();
			thirdRoundedPolygon.setVertices(thirdVerticeArray);
			
			//creating roundedpolygon array from roundedpolygons
			RoundedPolygon[] roundedPolygonCollection = new RoundedPolygon[] {firstRoundedPolygon,secondRoundedPolygon,thirdRoundedPolygon};
		
	
		//creating shapegroups from roundedpolygons
			LeafShapeGroup firstShapeGroup = new LeafShapeGroup(firstRoundedPolygon);
			LeafShapeGroup secondShapeGroup = new LeafShapeGroup(secondRoundedPolygon);
			LeafShapeGroup thirdShapeGroup = new LeafShapeGroup(thirdRoundedPolygon);	
		
		
			
			
		//creating shapecollection from shapegroups (first figure is front)
			LeafShapeGroup[] shapeCollection = new LeafShapeGroup[] {firstShapeGroup,secondShapeGroup,thirdShapeGroup};
		
			
		//creating parent from shapecollection
			NonleafShapeGroup parentShapeGroup = new NonleafShapeGroup(shapeCollection);
			
			
			Object actualShapeGroupExporterOutput = ShapeGroupExporter.toPlainData(parentShapeGroup);
			String expectedShapeGroupExporterToStringOutput = "{extent={bottom=20, right=20, top=0, left=0}, originalExtent={bottom=20, right=20, top=0, left=0}, subgroups=[{extent={bottom=10, right=10, top=0, left=0}, originalExtent={bottom=10, right=10, top=0, left=0}, shape={vertices=[{x=0, y=0}, {x=10, y=0}, {x=10, y=10}, {x=0, y=10}], radius=0, color={blue=0, green=255, red=255}}}, {extent={bottom=15, right=15, top=5, left=5}, originalExtent={bottom=15, right=15, top=5, left=5}, shape={vertices=[{x=5, y=5}, {x=15, y=5}, {x=15, y=15}, {x=5, y=15}], radius=0, color={blue=0, green=255, red=255}}}, {extent={bottom=20, right=20, top=10, left=10}, originalExtent={bottom=20, right=20, top=10, left=10}, shape={vertices=[{x=10, y=10}, {x=20, y=10}, {x=20, y=20}, {x=10, y=20}], radius=0, color={blue=0, green=255, red=255}}}]}";
			System.out.println(actualShapeGroupExporterOutput.toString());
			//assertEquals(actualShapeGroupExporterOutput.toString(),expectedShapeGroupExporterToStringOutput);
			
	}

}
