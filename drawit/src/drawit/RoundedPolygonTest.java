package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RoundedPolygonTest {

	@Test
	void test() {
		// We will essentially use the same method as in PointArraysTest,
		// the only difference is that RoundedPolygon class actually has an array (vertices) as its own child
			//initialize RoundedPolygon
			RoundedPolygon roundedPolygonTest = new RoundedPolygon();
			//initialize points used in RoundedPolygon
			IntPoint pointA = new IntPoint(2,4);
			IntPoint pointB = new IntPoint(4,4);
				//this point will be added to the array, and will make the polygon invalid
			IntPoint pointToBeRemoved = new IntPoint(8,2);
			IntPoint pointC = new IntPoint(6,4);
			IntPoint pointD = new IntPoint(8,4);
			//initialize points used in array to make a valid polygon (instead of line ABCD)
			IntPoint pointCupdate = new IntPoint(4,8);
			IntPoint pointDupdate = new IntPoint(2,8);
			
			IntPoint pointInCenter = new IntPoint(3,6);
			IntPoint pointInLeftTop = new IntPoint(3,5);
			IntPoint pointOutsidePolygon = new IntPoint(10,10);
		// Testing operations on RoundedPolygon (insert,remove,update)
			//testing if initial size of RoundedPolygon.vertices is 0 & radius is set to 0 (to ensure that the tests are valid)
			assertEquals(0,roundedPolygonTest.getVertices().length);
			assertEquals(0,roundedPolygonTest.getRadius());
				//setting radius to test the setRadius function
			roundedPolygonTest.setRadius(10);
			assertEquals(10,roundedPolygonTest.getRadius());
			//testing insert function of array
				roundedPolygonTest.insert(0, pointB);
					//validating array
					assertEquals(1,roundedPolygonTest.getVertices().length);
					assertEquals(true,roundedPolygonTest.getVertices()[0].equals(pointB));
				roundedPolygonTest.insert(0, pointA);
					//validating array
					assertEquals(2,roundedPolygonTest.getVertices().length);
					assertEquals(true,roundedPolygonTest.getVertices()[0].equals(pointA));
					assertEquals(true,roundedPolygonTest.getVertices()[1].equals(pointB));
				roundedPolygonTest.insert(2, pointToBeRemoved);
					//validating array
					assertEquals(3,roundedPolygonTest.getVertices().length);
					assertEquals(true,roundedPolygonTest.getVertices()[0].equals(pointA));
					assertEquals(true,roundedPolygonTest.getVertices()[1].equals(pointB));
					assertEquals(true,roundedPolygonTest.getVertices()[2].equals(pointToBeRemoved));
				roundedPolygonTest.insert(3, pointD);
					//validating array
					assertEquals(4,roundedPolygonTest.getVertices().length);
					assertEquals(true,roundedPolygonTest.getVertices()[0].equals(pointA));
					assertEquals(true,roundedPolygonTest.getVertices()[1].equals(pointB));
					assertEquals(true,roundedPolygonTest.getVertices()[2].equals(pointToBeRemoved));
					assertEquals(true,roundedPolygonTest.getVertices()[3].equals(pointD));
				roundedPolygonTest.insert(3, pointC);
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
							"line 4.0 4.0 4.0 8.0\n" +
							"line 4.0 8.0 2.0 8.0\n" +
							"line 2.0 8.0 2.0 4.0\n" +
							"line 2.0 4.0 4.0 4.0\n",roundedPolygonTest.getDrawingCommands());
					//testing getDrawingCommands with radius of 10
					roundedPolygonTest.setRadius(10);
					assertEquals("arc 3.0 5.0 1.0 -1.5707963267948966 1.5707963267948966\n" +
							"arc 3.0 7.0 1.0 0.0 1.5707963267948966\n" +
							"arc 3.0 7.0 1.0 1.5707963267948966 1.5707963267948966\n" +
							"arc 3.0 5.0 1.0 3.141592653589793 1.5676110196153106\n" +
							"line 4.0 5.0 4.0 7.0\n" +
							"line 3.0 8.0 3.0 8.0\n" +
							"line 2.0 7.0 2.0 5.0\n" +
							"line 3.0 4.0 3.0 4.0\n",roundedPolygonTest.getDrawingCommands());
			//testing setVertices
					IntPoint[] testArray1 = new IntPoint[0];
					testArray1 = PointArrays.insert(testArray1, 0, pointA);
					testArray1 = PointArrays.insert(testArray1, 1, pointB);
					testArray1 = PointArrays.insert(testArray1, 2, pointC);
					roundedPolygonTest.setVertices(testArray1);
						//see if array structure is correct
							assertEquals(3,roundedPolygonTest.getVertices().length);
							assertEquals(true,roundedPolygonTest.getVertices()[0].equals(pointA));
							assertEquals(true,roundedPolygonTest.getVertices()[1].equals(pointB));
							assertEquals(true,roundedPolygonTest.getVertices()[2].equals(pointC));
					
					
	}

}
