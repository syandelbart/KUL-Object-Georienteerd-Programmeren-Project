package drawit.tests;
import drawit.IntPoint;
import drawit.PointArrays;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PointArraysTest {

	@Test
	void test() {
		// Since PointArrays doesn't have it's own children, we will initialize an "individual" array to test our functions.
		// The PointArrays uses functions that expect IntPoints, therefore we will use an array based on IntPoints.
			//initialize array
			IntPoint[] testArray = new IntPoint[0];
			//initialize points used in array
			IntPoint pointA = new IntPoint(2,4);
			IntPoint pointB = new IntPoint(4,4);
				//this point will be added to the array, and will make the polygon invalid
			IntPoint pointToBeRemoved = new IntPoint(8,2);
			IntPoint pointC = new IntPoint(6,4);
			IntPoint pointD = new IntPoint(8,4);
			//initialize points used in array to make a valid polygon (instead of line ABCD)
			IntPoint pointCupdate = new IntPoint(4,8);
			IntPoint pointDupdate = new IntPoint(2,8);
		//doing operations on array itself (insert,copy,remove,update)
			//testing if initial array is length 0 (to ensure that tests are valid)
			assertEquals(0,testArray.length);
			//testing insert function of array
				testArray = PointArrays.insert(testArray,0,pointB);
					//validating array
					assertEquals(1,testArray.length);
					assertEquals(true,testArray[0].equals(pointB));
				testArray = PointArrays.insert(testArray, 0, pointA);
					//validating array
					assertEquals(2,testArray.length);
					assertEquals(true,testArray[0].equals(pointA));
					assertEquals(true,testArray[1].equals(pointB));
				testArray = PointArrays.insert(testArray, 2, pointToBeRemoved);
					//validating array
					assertEquals(3,testArray.length);
					assertEquals(true,testArray[0].equals(pointA));
					assertEquals(true,testArray[1].equals(pointB));
					assertEquals(true,testArray[2].equals(pointToBeRemoved));
				testArray = PointArrays.insert(testArray, 3, pointD);
					//validating array
					assertEquals(4,testArray.length);
					assertEquals(true,testArray[0].equals(pointA));
					assertEquals(true,testArray[1].equals(pointB));
					assertEquals(true,testArray[2].equals(pointToBeRemoved));
					assertEquals(true,testArray[3].equals(pointD));
				testArray = PointArrays.insert(testArray, 3, pointC);
					//validating array
					assertEquals(5,testArray.length);
					assertEquals(true,testArray[0].equals(pointA));
					assertEquals(true,testArray[1].equals(pointB));
					assertEquals(true,testArray[2].equals(pointToBeRemoved));
					assertEquals(true,testArray[3].equals(pointC));	
					assertEquals(true,testArray[4].equals(pointD));	
			//testing copy function of array
				IntPoint[] testArrayCopy = PointArrays.copy(testArray);
					//validating array
					//same length
					assertEquals(testArray.length,testArrayCopy.length);
					//are the objects in the new array the same as the old array?
					assertEquals(true,testArray[0] == testArrayCopy[0]);
			//testing remove function of array (remove pointToBeRemoved from array at index 2)
					//testing if pointToBeRemoved is index 2 in the array
					assertEquals(true,testArray[2] == pointToBeRemoved);
				//removing it from the array
				testArray = PointArrays.remove(testArray, 2);
					//testing if array kept its structure whilst removing the element at index 2
					assertEquals(4,testArray.length);
					assertEquals(true,testArray[0].equals(pointA));
					assertEquals(true,testArray[1].equals(pointB));
					assertEquals(true,testArray[2].equals(pointC));	
					assertEquals(true,testArray[3].equals(pointD));
			//testing update function of array (pointC&pointD)
				//update pointC to pointCupdate
				testArray = PointArrays.update(testArray, 2, pointCupdate);
					//testing if array kept its structure whilst updating the element at index 2
					assertEquals(4,testArray.length);
					assertEquals(true,testArray[0].equals(pointA));
					assertEquals(true,testArray[1].equals(pointB));
					assertEquals(true,testArray[2].equals(pointCupdate));	
					assertEquals(true,testArray[3].equals(pointD));
				//update pointD to pointDupdate
				testArray = PointArrays.update(testArray, 3, pointDupdate);
					//testing if array kept its structure whilst updating the element at index 2
					assertEquals(4,testArray.length);
					assertEquals(true,testArray[0].equals(pointA));
					assertEquals(true,testArray[1].equals(pointB));
					assertEquals(true,testArray[2].equals(pointCupdate));	
					assertEquals(true,testArray[3].equals(pointDupdate));
		//testing more-complex functions (checkDefinesProperPolygon)
				//result should be null, since AB-BC-CD-DA defines a valid polygon (vertical rectangle)
				assertEquals(null,PointArrays.checkDefinesProperPolygon(testArray));
				//we will change pointCupdate to pointDupdate, which should result in 2 points being the same
				IntPoint[] testArrayUpdate = PointArrays.update(testArray, 2, pointDupdate);
				assertEquals("2 points are the same",PointArrays.checkDefinesProperPolygon(testArrayUpdate));
				//we will switch pointCupdate with pointDupdate, 
				//so that our polygon would be defined as AB-BD-DC-CA (where BD should intersect with CA)
				testArrayUpdate = PointArrays.update(testArray, 2, pointDupdate);
				testArrayUpdate = PointArrays.update(testArrayUpdate,3,pointCupdate);
				assertEquals("2 lines intersect",PointArrays.checkDefinesProperPolygon(testArrayUpdate));
				testArrayUpdate = PointArrays.insert(testArray, 0, new IntPoint(3,4));
				assertEquals("vertex is on edge",PointArrays.checkDefinesProperPolygon(testArrayUpdate));
				
	}

}
