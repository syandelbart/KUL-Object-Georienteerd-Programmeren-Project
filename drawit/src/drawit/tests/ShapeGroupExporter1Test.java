package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;
import drawit.shapegroups1.exporter.ShapeGroupExporter;
import drawit.shapegroups1.Extent;

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
			
		//testing data against handmade model
		//first we create the actual data, then compare it to the data made by the function
			
			//first subgroup
				//create first vertices collection in maplist
					List<Map<String,Integer>> firstVerticeMapList = new ArrayList<Map<String,Integer>>();
					for(int i = 0; i < firstVerticeArray.length; i++) {
						firstVerticeMapList.add(Map.of("x",firstVerticeArray[i].getX(),"y",firstVerticeArray[i].getY()));
					}
				//get extent
				Extent firstExtent = firstShapeGroup.getExtent();
				//get originalExtent
				Extent firstOriginalExtent = firstShapeGroup.getOriginalExtent();
				//get color
				Color firstColor = firstRoundedPolygon.getColor();
				
				//set resulting Object of this subgroups toPlaindata
				Object firstSubgroup = Map.of("originalExtent", Map.of("left", firstOriginalExtent.getLeft(), "top", firstOriginalExtent.getTop(), "right", firstOriginalExtent.getRight(), "bottom", firstOriginalExtent.getBottom()),
					    "extent", Map.of("left", firstExtent.getLeft(), "top", firstExtent.getTop(), "right", firstExtent.getRight(), "bottom", firstExtent.getBottom()),
			            "shape", Map.of(
			                "vertices", firstVerticeMapList,
			                "radius", firstRoundedPolygon.getRadius(),
			                "color", Map.of("red", firstColor.getRed(), "green", firstColor.getGreen(), "blue", firstColor.getBlue())));
			
			
			//second subgroup
				//create first vertices collection in maplist
					List<Map<String,Integer>> secondVerticeMapList = new ArrayList<Map<String,Integer>>();
					for(int i = 0; i < secondVerticeArray.length; i++) {
						secondVerticeMapList.add(Map.of("x",secondVerticeArray[i].getX(),"y",secondVerticeArray[i].getY()));
					}
				//get extent
				Extent secondExtent = secondShapeGroup.getExtent();
				//get originalExtent
				Extent secondOriginalExtent = secondShapeGroup.getOriginalExtent();
				//get color
				Color secondColor = secondRoundedPolygon.getColor();
				
				//set resulting Object of this subgroups toPlaindata
				Object secondSubgroup = Map.of("originalExtent", Map.of("left", secondOriginalExtent.getLeft(), "top", secondOriginalExtent.getTop(), "right", secondOriginalExtent.getRight(), "bottom", secondOriginalExtent.getBottom()),
					    "extent", Map.of("left", secondExtent.getLeft(), "top", secondExtent.getTop(), "right", secondExtent.getRight(), "bottom", secondExtent.getBottom()),
			            "shape", Map.of(
			                "vertices", secondVerticeMapList,
			                "radius", secondRoundedPolygon.getRadius(),
			                "color", Map.of("red", secondColor.getRed(), "green", secondColor.getGreen(), "blue", secondColor.getBlue())));
				
				
			//third subgroup
				//create first vertices collection in maplist
					List<Map<String,Integer>> thirdVerticeMapList = new ArrayList<Map<String,Integer>>();
					for(int i = 0; i < thirdVerticeArray.length; i++) {
						thirdVerticeMapList.add(Map.of("x",thirdVerticeArray[i].getX(),"y",thirdVerticeArray[i].getY()));
					}
				//get extent
				Extent thirdExtent = thirdShapeGroup.getExtent();
				//get originalExtent
				Extent thirdOriginalExtent = thirdShapeGroup.getOriginalExtent();
				//get color
				Color thirdColor = thirdRoundedPolygon.getColor();
				
				//set resulting Object of this subgroups toPlaindata
				Object thirdSubgroup = Map.of("originalExtent", Map.of("left", thirdOriginalExtent.getLeft(), "top", thirdOriginalExtent.getTop(), "right", thirdOriginalExtent.getRight(), "bottom", thirdOriginalExtent.getBottom()),
					    "extent", Map.of("left", thirdExtent.getLeft(), "top", thirdExtent.getTop(), "right", thirdExtent.getRight(), "bottom", thirdExtent.getBottom()),
			            "shape", Map.of(
			                "vertices", thirdVerticeMapList,
			                "radius", thirdRoundedPolygon.getRadius(),
			                "color", Map.of("red", thirdColor.getRed(), "green", thirdColor.getGreen(), "blue", thirdColor.getBlue())));
			
			
				
				
			//main object
				//get main object's original extent (we assume that this method is tested already)
				Extent originalExtent = parentShapeGroup.getOriginalExtent();
				//get main object's currect extent ( we assume that this method is tested already)
				Extent extent = parentShapeGroup.getExtent();
				//set main object's subgroups
				List<Object> subgroupsMapList = new ArrayList<Object>();
				subgroupsMapList.add(firstSubgroup);
				subgroupsMapList.add(secondSubgroup);
				subgroupsMapList.add(thirdSubgroup);
			
				//actual expected
				Object expectedShapeGroupExporterOutput = Map.of("originalExtent", Map.of("left", originalExtent.getLeft(), "top", originalExtent.getTop(), "right", originalExtent.getRight(), "bottom", originalExtent.getBottom()),
					    "extent", Map.of("left", extent.getLeft(), "top", extent.getTop(), "right", extent.getRight(), "bottom", extent.getBottom()),
					    "subgroups",subgroupsMapList);
			
				
				//expected
				Object actualShapeGroupExporterOutput = ShapeGroupExporter.toPlainData(parentShapeGroup);
				
				
				//test if is equal
				assertEquals(true,expectedShapeGroupExporterOutput.equals(actualShapeGroupExporterOutput));
				
			//assertEquals(actualShapeGroupExporterOutput.toString(),expectedShapeGroupExporterToStringOutput);
			
	}

}
