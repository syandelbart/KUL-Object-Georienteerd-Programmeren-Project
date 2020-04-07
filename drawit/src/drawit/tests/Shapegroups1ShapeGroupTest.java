package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import drawit.shapegroups1.Extent;
import drawit.shapegroups1.ShapeGroup;
import drawit.RoundedPolygon;
import drawit.*;


class Shapegroups1ShapeGroupTest {

	@Test
	void test() {
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
				ShapeGroup firstShapeGroup = new ShapeGroup(firstRoundedPolygon);
				ShapeGroup secondShapeGroup = new ShapeGroup(secondRoundedPolygon);
				ShapeGroup thirdShapeGroup = new ShapeGroup(thirdRoundedPolygon);	
			
			
				
				
			//creating shapecollection from shapegroups (first figure is front)
				ShapeGroup[] shapeCollection = new ShapeGroup[] {firstShapeGroup,secondShapeGroup,thirdShapeGroup};
			
				
			//creating parent from shapecollection
				ShapeGroup parentShapeGroup = new ShapeGroup(shapeCollection);
				
				
		//testing objects 
			//getExtent() 
				//doing operations on object
					Extent resultingExtent = parentShapeGroup.getExtent();
				//testing values object
					assertEquals(0,resultingExtent.getLeft());
					assertEquals(0,resultingExtent.getTop());
					assertEquals(20,resultingExtent.getRight());
					assertEquals(20,resultingExtent.getBottom());
					
					
			//getOriginalExtent() --> this should be the same extent as the tested getExtent()
				//doing operations on object
					Extent resultingOriginalExtent = parentShapeGroup.getOriginalExtent();
				//testing values object
					assertEquals(0,resultingOriginalExtent.getLeft());
					assertEquals(0,resultingOriginalExtent.getTop());
					assertEquals(20,resultingOriginalExtent.getRight());
					assertEquals(20,resultingOriginalExtent.getBottom());
					
			//if these two tests are correct, we can assume that they are both the same.
			
					
			//getParentGroup() 
				//testing if parentShapeGroup childs all have parentShapeGroup as parent
					for(int i = 0;i<parentShapeGroup.getSubgroupCount();i++) {
						assertEquals(parentShapeGroup,parentShapeGroup.getSubgroup(i).getParentGroup());
					}
				//testing if parentShapeGroup has no parent
					assertEquals(null,parentShapeGroup.getParentGroup());

			
			//getShape()
				//doing operations on object
					for(int i = 0;i<parentShapeGroup.getSubgroupCount();i++) {
						assertEquals(true,roundedPolygonCollection[i].equals(parentShapeGroup.getSubgroup(i).getShape()));
					}
				
					
			//getSubGroups()
				//doing operations on objects
					java.util.List<ShapeGroup> testShapeGroups = parentShapeGroup.getSubgroups();
				//testing values object
					for(int i = 0;i<parentShapeGroup.getSubgroupCount();i++) {
						assertEquals(true,testShapeGroups.get(i).equals(shapeCollection[i]));
					}
					
			
			//getSubgroupCount()
				//testing value objects
					assertEquals(3,parentShapeGroup.getSubgroupCount());
					
					
			//getSubgroup
				//testing value objects
					for(int i = 0;i<parentShapeGroup.getSubgroupCount();i++) {
						assertEquals(true,shapeCollection[i].equals(parentShapeGroup.getSubgroup(i)));
					}
					
					
			//toInnerCoordinates (IntPoint)
				//doing operations on objects
					IntPoint testIntPoint1 = parentShapeGroup.toInnerCoordinates(thirdPolygonPointA);
				//testing values object
					//since nothing is changed yet, the testIntPoint1 should be equal to thirdPolygonPointA
					assertEquals(true,testIntPoint1.equals(thirdPolygonPointA));
					
			//toGlobalCoordinates
				//doing operations on objects
					IntPoint testIntPoint2 = parentShapeGroup.toGlobalCoordinates(thirdPolygonPointA);
				//testing values object
					//since nothing is changed yet, the testIntPoint1 should be equal to thirdPolygonPointA
					assertEquals(true,testIntPoint2.equals(thirdPolygonPointA));
					
					
			//toInnerCoordinates (IntVector)
					IntVector testIntVector = new IntVector(10,10);
				//doing operations on objects
					IntVector testIntVector1 = parentShapeGroup.toInnerCoordinates(testIntVector);
				//testing values object
					//since nothing is changed yet, the testIntPoint1 should be equal to thirdPolygonPointA
					assertEquals(10,testIntVector1.getX());
					assertEquals(10,testIntVector1.getY());
						
			
			//getSubgroupAt()
				//testing values object
					assertEquals(true,secondShapeGroup.equals(parentShapeGroup.getSubgroupAt(secondPolygonPointB)));
					
					
			//setExtent()
				//doing operations on objects
					Extent oldExtent = parentShapeGroup.getExtent();
					//making new extent twice as big
					Extent newExtent = Extent.ofLeftTopWidthHeight(oldExtent.getLeft(), oldExtent.getTop(), oldExtent.getWidth() * 2, oldExtent.getHeight() * 2);
					//setting new extent to parentShapeGroup
					parentShapeGroup.setExtent(newExtent);
				//testing values object
					assertEquals(true,parentShapeGroup.getExtent().equals(newExtent));
					
					//testing toGlobalCoordinates()
					IntPoint expectedIntPoint1 = new IntPoint(20,20);
					IntPoint newExtentIntPoint1 = parentShapeGroup.getSubgroup(0).toGlobalCoordinates(firstPolygonPointC);
					assertEquals(true,expectedIntPoint1.equals(newExtentIntPoint1));
					
					//testing toInnerCoordinates() (IntPoint)
					IntPoint expectedIntPoint2 = new IntPoint(7,7);
					IntPoint inputIntPoint2 = new IntPoint(14,14);
					IntPoint newExtentIntPoint2 = parentShapeGroup.getSubgroup(0).toInnerCoordinates(inputIntPoint2);
					assertEquals(true,expectedIntPoint2.equals(newExtentIntPoint2));
					
					//testing toInnerCoordinates() (IntVector)
					IntVector inputIntVector2 = new IntVector(16,8);
					IntVector newExtentIntVector2 = parentShapeGroup.getSubgroup(0).toInnerCoordinates(inputIntVector2);
					assertEquals(8,newExtentIntVector2.getX());
					assertEquals(4,newExtentIntVector2.getY());
			
			//bringToFront()
				//doing operations on objects
					//third figure to front --> first figure to back 
					parentShapeGroup.getSubgroup(2).bringToFront();
				//testing object values
					assertEquals(true,thirdShapeGroup.equals(parentShapeGroup.getSubgroup(0)));
					assertEquals(true,secondShapeGroup.equals(parentShapeGroup.getSubgroup(1)));
					assertEquals(true,firstShapeGroup.equals(parentShapeGroup.getSubgroup(2)));
					
				
			//sendToBack()
				//doing operations on objects
					//second figure to back --> first figure to second 
					parentShapeGroup.getSubgroup(1).sendToBack();
				//testing object values
					assertEquals(true,thirdShapeGroup.equals(parentShapeGroup.getSubgroup(0)));
					assertEquals(true,firstShapeGroup.equals(parentShapeGroup.getSubgroup(1)));
					assertEquals(true,secondShapeGroup.equals(parentShapeGroup.getSubgroup(2)));
			
			
			//getDrawingCommands()
					assertEquals("pushTranslate -0.0 -0.0\n" +
							"pushScale 2.0 2.0\n" +
							"pushTranslate -0.0 -0.0\n" +
							"pushScale 1.0 1.0\n" +
							"line 5 10 5 5\n" +
							"arc 5 5 0 0.0 0.0\n" +
							"line 5 5 10 5\n" +
							"line 10 5 15 5\n" +
							"arc 15 5 0 0.0 0.0\n" +
							"line 15 5 15 10\n" +
							"line 15 10 15 15\n" +
							"arc 15 15 0 0.0 0.0\n" +
							"line 15 15 10 15\n" +
							"line 10 15 5 15\n" +
							"arc 5 15 0 0.0 0.0\n" +
							"line 5 15 5 10\n" +
							"fill 255 255 0\n" +
							"popTransform\n" +
							"popTransform\n" +
							"pushTranslate -0.0 -0.0\n" +
							"pushScale 1.0 1.0\n" +
							"line 0 5 0 0\n" +
							"arc 0 0 0 0.0 0.0\n" +
							"line 0 0 5 0\n" +
							"line 5 0 10 0\n" +
							"arc 10 0 0 0.0 0.0\n" +
							"line 10 0 10 5\n" +
							"line 10 5 10 10\n" +
							"arc 10 10 0 0.0 0.0\n" +
							"line 10 10 5 10\n" +
							"line 5 10 0 10\n" +
							"arc 0 10 0 0.0 0.0\n" +
							"line 0 10 0 5\n" +
							"fill 255 255 0\n" +
							"popTransform\n" +
							"popTransform\n" +
							"pushTranslate -0.0 -0.0\n" +
							"pushScale 1.0 1.0\n" +
							"line 10 15 10 10\n" +
							"arc 10 10 0 0.0 0.0\n" +
							"line 10 10 15 10\n" +
							"line 15 10 20 10\n" +
							"arc 20 10 0 0.0 0.0\n" +
							"line 20 10 20 15\n" +
							"line 20 15 20 20\n" +
							"arc 20 20 0 0.0 0.0\n" +
							"line 20 20 15 20\n" +
							"line 15 20 10 20\n" +
							"arc 10 20 0 0.0 0.0\n" +
							"line 10 20 10 15\n" +
							"fill 255 255 0\n" +
							"popTransform\n" +
							"popTransform\n" +
							"popTransform\n" +
							"popTransform\n",parentShapeGroup.getDrawingCommands());
					
	}

}
