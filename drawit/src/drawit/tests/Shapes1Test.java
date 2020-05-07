package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;
import drawit.shapes1.*;

class Shapes1Test {
	//Since controlpoints are generated from the classes used by the Shape interface, we will test this simultaneously
	//We will have 2 different tests, where we test ControlPointRoundedPolygon + RoundedPolygonShape and ControlPointShapeGroup + ShapeGroupShape respectively

	@Test
	void RoundedPolygonShapeAndControlPointRoundedPolygon() {
		//creating objects to experiment with
		//creating points for polygons to insert in shape
			//figure 1
			IntPoint firstPolygonPointA = new IntPoint(0,0);
			IntPoint firstPolygonPointB = new IntPoint(10,0);
			IntPoint firstPolygonPointC = new IntPoint(10,10);
			IntPoint firstPolygonPointD = new IntPoint(0,10);
			
		//creating vertice collection from points
			IntPoint[] firstVerticeArray = new IntPoint[] {firstPolygonPointA,firstPolygonPointB,firstPolygonPointC,firstPolygonPointD};

			
		//creating roundedpolygon from vertice collection
			RoundedPolygon firstRoundedPolygon = new RoundedPolygon();
			firstRoundedPolygon.setVertices(firstVerticeArray);

	
		//creating shapegroup from roundedpolygon
			LeafShapeGroup firstShapeGroup = new LeafShapeGroup(firstRoundedPolygon);
			
			
		//creating RoundedPolygonShape	
		RoundedPolygonShape RoundedPolygonShapeObject = new RoundedPolygonShape(firstShapeGroup,firstRoundedPolygon);
		
			//testing functions
				//getPolygon()
				assertEquals(firstRoundedPolygon,RoundedPolygonShapeObject.getPolygon());
				
				//contains() --> WE ASSUME THIS WORKS IF RoundedPolygonTest WORKS AS INTENDED
				//getDrawingCommands() --> WE ASSUME THIS WORKS IF RoundedPolygonTest WORKS AS INTENDED
				
				//getParent()
				assertEquals(firstShapeGroup,RoundedPolygonShapeObject.getParent());
				
				//createControlPoints() --> Here we shall also test ControlPoints
				ControlPoint[] ControlPointArray = RoundedPolygonShapeObject.createControlPoints();
					//testing ControlPoints
						//testing if contents of every controlpoint is correct
						for(int i=0;i<ControlPointArray.length;i++) {
							ControlPoint cursor = ControlPointArray[i];
							//getLocation()
							assertEquals(true,cursor.getLocation().equals(firstVerticeArray[i]));
						}
						//move() --> this should move a point in the shape that is referenced by a ControlPoint
							//for this example we will move the first ControlPoint so to speak (which is lefttop corner)
							assertEquals(true,firstPolygonPointA.equals(firstRoundedPolygon.getVertices()[0]));
							ControlPointArray[0].move(new IntVector(5,3));
							assertEquals(true,new IntPoint(5,3).equals(firstRoundedPolygon.getVertices()[0]));
						//remove() --> this should remove a point in the shape that is referenced by a Controlpoint
							//for this example we will remove the second ControlPoint so to speak (which is righttop corner)
							assertEquals(true,firstPolygonPointB.equals(firstRoundedPolygon.getVertices()[1]));
							assertEquals(4,firstRoundedPolygon.getVertices().length);
							ControlPointArray[1].remove();
							assertEquals(true,firstPolygonPointC.equals(firstRoundedPolygon.getVertices()[1]));
							assertEquals(3,firstRoundedPolygon.getVertices().length);
							
							
							
				
				//toShapeCoordinates --> WE ASSUME THIS WORKS IF ShapeGroup1ShapeGroupTest WORKS AS INTENDED
				//toGlobalCoordinates --> WE ASSUME THIS WORKS IF ShapeGroup1ShapeGroupTest WORKS AS INTENDED
				
				
				
				
		
			
			
	}
	
	@Test
	void ShapeGroupShapeAndControlPointShapeGroup() {
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
		
	
		//creating shapegroups from roundedpolygons
			LeafShapeGroup firstShapeGroup = new LeafShapeGroup(firstRoundedPolygon);
			LeafShapeGroup secondShapeGroup = new LeafShapeGroup(secondRoundedPolygon);
			LeafShapeGroup thirdShapeGroup = new LeafShapeGroup(thirdRoundedPolygon);	
		
		
			
			
		//creating shapecollection from shapegroups (first figure is front)
			LeafShapeGroup[] shapeCollection = new LeafShapeGroup[] {firstShapeGroup,secondShapeGroup,thirdShapeGroup};
			
		//creating parent from shapecollection
			NonleafShapeGroup parentShapeGroup = new NonleafShapeGroup(shapeCollection);
			
			
			//creating ShapeGroupShape
			ShapeGroupShape ShapeGroupShapeObject = new ShapeGroupShape(parentShapeGroup);
			ShapeGroupShape ShapeGroupShapeObjectChild = new ShapeGroupShape(secondShapeGroup);
		
			//testing functions
				//getShapeGroup()
				assertEquals(parentShapeGroup,ShapeGroupShapeObject.getShapeGroup());
				assertEquals(secondShapeGroup,ShapeGroupShapeObjectChild.getShapeGroup());
			
				//getParentGroup()
				assertEquals(null,ShapeGroupShapeObject.getParent());
				assertEquals(parentShapeGroup,ShapeGroupShapeObjectChild.getParent());
			
				//contains() --> WE ASSUME THIS WORKS IF ShapeGroup1ShapeGroupTest WORKS AS INTENDED
				//getDrawingCommands() --> WE ASSUME THIS WORKS IF ShapeGroup1ShapeGroupTest WORKS AS INTENDED
			
				//createControlPoints()
				ControlPoint[] ControlPointArray = ShapeGroupShapeObject.createControlPoints();
				ControlPoint[] ControlPointArrayChild = ShapeGroupShapeObjectChild.createControlPoints();
				//testing ControlPoints
					//testing if contents of every controlpoint is correct
						//ControlPointArray should be left upper and right bottom of all Polygons combined
						assertEquals(2,ControlPointArray.length);
						assertEquals(true,firstPolygonPointA.equals(ControlPointArray[0].getLocation()));
						assertEquals(true,thirdPolygonPointC.equals(ControlPointArray[1].getLocation()));
						//ControlPointArrayChild should be left upper and right bottom of secondShapeGroup polygon (secondRoundedPolygon)
						assertEquals(2,ControlPointArrayChild.length);
						assertEquals(true,secondPolygonPointA.equals(ControlPointArrayChild[0].getLocation()));
						assertEquals(true,secondPolygonPointC.equals(ControlPointArrayChild[1].getLocation()));
					
				
					//move() --> this should not move any points in shapes as only the extent will be changed
						//for this example we will move the first ControlPoint so to speak (which is lefttop corner)
						assertEquals(true,firstPolygonPointA.equals(parentShapeGroup.getExtent().getTopLeft()));
						ControlPointArray[0].move(new IntVector(-5,-5));
						assertEquals(true,new IntPoint(-5,-5).equals(parentShapeGroup.getExtent().getTopLeft()));
					//remove() --> as we cannot simply remove a point from a ShapeGroup (since this is defined by an Extent which is in turn defined by shapes)
					//we should get an UnsupportedOperationException();
					Boolean hasThrown = false;
					try {
							ControlPointArray[0].remove();
						}
					catch(Exception UnsupportedOperationException) {
						hasThrown = true;
							  //  Block of code to handle errors
						}
					assertEquals(true,hasThrown);
			
				//toShapeCoordinates() --> WE ASSUME THIS WORKS IF ShapeGroup1ShapeGroupTest WORKS AS INTENDED
				//toGlobalCoordinates() --> WE ASSUME THIS WORKS IF ShapeGroup1ShapeGroupTest WORKS AS INTENDED
	}
	
	
	

}
