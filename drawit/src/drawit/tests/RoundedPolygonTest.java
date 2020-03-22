package drawit.tests;
import drawit.RoundedPolygon;
import drawit.PointArrays;
import drawit.IntPoint;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

//need to change this since the program itself checks for the validity of the polygon in the moment itself

class RoundedPolygonTest {

	@Test
	void test() {
		// We will essentially use the same method as in PointArraysTest,
		// the only difference is that RoundedPolygon class actually has an array (vertices) as its own child
		//we create a square with a triangle on its ride that forms a polygon, and the triangle is used to check if points outside the triangle
		//are still seen as points inside the polygon or outside
			//initialize RoundedPolygon
			RoundedPolygon roundedPolygonTest = new RoundedPolygon();
			//initialize points used in RoundedPolygon
			IntPoint pointA = new IntPoint(2,4);
			IntPoint pointB = new IntPoint(4,4);
				//this point will be added to the array, and will make the polygon invalid
			IntPoint pointToBeRemoved = new IntPoint(6,5);
			IntPoint pointC = new IntPoint(4,6);
			IntPoint pointD = new IntPoint(2,6);
			//initialize points used in array to make a valid polygon (instead of line ABCD)
			IntPoint pointCupdate = new IntPoint(4,8);
			IntPoint pointDupdate = new IntPoint(2,8);
			
			IntPoint pointInCenter = new IntPoint(4,6);
			IntPoint pointInLeftTop = new IntPoint(3,5);
			IntPoint pointOutsidePolygon = new IntPoint(10,10);
			IntPoint pointOutsideTriangleOnRightOfPolygon = new IntPoint(5,4);
		// Testing operations on RoundedPolygon (insert,remove,update)
			//testing if initial size of RoundedPolygon.vertices is 0 & radius is set to 0 (to ensure that the tests are valid)
			assertEquals(0,roundedPolygonTest.getVertices().length);
			assertEquals(0,roundedPolygonTest.getRadius());
				//setting radius to test the setRadius function
			roundedPolygonTest.setRadius(10);
			assertEquals(10,roundedPolygonTest.getRadius());
		// Creating a list of vertices and setting that list as the vertices of the object roundedPolygonTest
		// we suppose that every instance of roundedPolygon is either empty or valid, therefor we must inesrt a valid list of points to begin with
			IntPoint[] toBeInsertedArray = new IntPoint[0];
			toBeInsertedArray = PointArrays.insert(toBeInsertedArray, 0, pointB);
			toBeInsertedArray = PointArrays.insert(toBeInsertedArray, 0, pointA);
			toBeInsertedArray = PointArrays.insert(toBeInsertedArray, 2, pointToBeRemoved);
			roundedPolygonTest.setVertices(toBeInsertedArray);
			//testing insert function of array
				roundedPolygonTest.insert(3, pointC);
					//validating array
					assertEquals(4,roundedPolygonTest.getVertices().length);
					assertEquals(true,roundedPolygonTest.getVertices()[0].equals(pointA));
					assertEquals(true,roundedPolygonTest.getVertices()[1].equals(pointB));
					assertEquals(true,roundedPolygonTest.getVertices()[2].equals(pointToBeRemoved));
					assertEquals(true,roundedPolygonTest.getVertices()[3].equals(pointC));	
				roundedPolygonTest.insert(4, pointD);
					//validating array
					assertEquals(5,roundedPolygonTest.getVertices().length);
					assertEquals(true,roundedPolygonTest.getVertices()[0].equals(pointA));
					assertEquals(true,roundedPolygonTest.getVertices()[1].equals(pointB));
					assertEquals(true,roundedPolygonTest.getVertices()[2].equals(pointToBeRemoved));
					assertEquals(true,roundedPolygonTest.getVertices()[3].equals(pointC));	
					assertEquals(true,roundedPolygonTest.getVertices()[4].equals(pointD));
			//testing remove function of array (remove pointToBeRemoved from array at index 2)
					//testing if pointToBeRemoved is index 2 in the array
					assertEquals(true,roundedPolygonTest.getVertices()[2] == pointToBeRemoved);
					//testing if a point above the triangle (which is formed by pointToBeRemoved
					//is part of the polygon
					assertEquals(false,roundedPolygonTest.contains(pointOutsideTriangleOnRightOfPolygon));
				//removing it from the array
				roundedPolygonTest.remove(2);
					//testing if array kept its structure whilst removing the element at index 2
					assertEquals(4,roundedPolygonTest.getVertices().length);
					assertEquals(true,roundedPolygonTest.getVertices()[0].equals(pointA));
					assertEquals(true,roundedPolygonTest.getVertices()[1].equals(pointB));
					assertEquals(true,roundedPolygonTest.getVertices()[2].equals(pointC));	
					assertEquals(true,roundedPolygonTest.getVertices()[3].equals(pointD));
			//testing update function of array (pointC&pointD)
				//update pointC to pointCupdate
				roundedPolygonTest.update(2, pointCupdate);
					//testing if array kept its structure whilst updating the element at index 2
					assertEquals(4,roundedPolygonTest.getVertices().length);
					assertEquals(true,roundedPolygonTest.getVertices()[0].equals(pointA));
					assertEquals(true,roundedPolygonTest.getVertices()[1].equals(pointB));
					assertEquals(true,roundedPolygonTest.getVertices()[2].equals(pointCupdate));	
					assertEquals(true,roundedPolygonTest.getVertices()[3].equals(pointD));
				//update pointD to pointDupdate
					roundedPolygonTest.update(3, pointDupdate);
					//testing if array kept its structure whilst updating the element at index 2
					assertEquals(4,roundedPolygonTest.getVertices().length);
					assertEquals(true,roundedPolygonTest.getVertices()[0].equals(pointA));
					assertEquals(true,roundedPolygonTest.getVertices()[1].equals(pointB));
					assertEquals(true,roundedPolygonTest.getVertices()[2].equals(pointCupdate));
					assertEquals(true,roundedPolygonTest.getVertices()[3].equals(pointDupdate));
		// Testing more-complex functions (contains,getDrawingCommands,setVertices)
			//testing contains function
					assertEquals(true,roundedPolygonTest.contains(pointInCenter));
					assertEquals(true,roundedPolygonTest.contains(pointInLeftTop));
					assertEquals(false,roundedPolygonTest.contains(pointOutsidePolygon));
			//testing getDrawingCommands (the reality is that testing this visually is way more easy than manually, we can however see that the calculations
			//done here are correct, and we will assume that this works for every shape)
					//testing getDrawingCommands with radius of 0
					roundedPolygonTest.setRadius(0);
					assertEquals("arc 4.0 4.0 0.0 0.0 0.0\n" +
							"arc 4.0 8.0 0.0 0.0 0.0\n" +
							"arc 2.0 8.0 0.0 0.0 0.0\n" +
							"arc 2.0 4.0 0.0 0.0 0.0\n" +
							"line 4 4 4 8\n" +
							"line 4 8 2 8\n" +
							"line 2 8 2 4\n" +
							"line 2 4 4 4\n",roundedPolygonTest.getDrawingCommands());
					//testing getDrawingCommands with radius of 10
					roundedPolygonTest.setRadius(10);
					assertEquals("arc 3.0 5.0 1.0 -1.5707963267948966 1.5707963267948966\n" +
							"arc 3.0 7.0 1.0 0.0 1.5707963267948966\n" +
							"arc 3.0 7.0 1.0 1.5707963267948966 1.5707963267948966\n" +
							"arc 3.0 5.0 1.0 3.141592653589793 1.5676110196153106\n" +
							"line 4 5 4 7\n" +
							"line 3 8 3 8\n" +
							"line 2 7 2 5\n" +
							"line 3 4 3 4\n",roundedPolygonTest.getDrawingCommands());
			//testing setVertices
					IntPoint[] testArray1 = new IntPoint[0];
					testArray1 = PointArrays.insert(testArray1, 0, pointA);
					testArray1 = PointArrays.insert(testArray1, 1, pointB);
					testArray1 = PointArrays.insert(testArray1, 2, pointD);
					testArray1 = PointArrays.insert(testArray1, 3, pointC);
					boolean IllegalArgumentExceptionThrown = false;
					try {
					roundedPolygonTest.setVertices(testArray1);
					} catch(IllegalArgumentException i) {
						IllegalArgumentExceptionThrown = true;
					}
					assertEquals(true,IllegalArgumentExceptionThrown);
					
						//see if array structure is correct --> if this is correct this means that setVertices didn't go through and illegalargument was thrown
						assertEquals(true,roundedPolygonTest.getVertices()[0].equals(pointA));
						assertEquals(true,roundedPolygonTest.getVertices()[1].equals(pointB));
						assertEquals(true,roundedPolygonTest.getVertices()[2].equals(pointCupdate));
						assertEquals(true,roundedPolygonTest.getVertices()[3].equals(pointDupdate));
					
					
	}

}
