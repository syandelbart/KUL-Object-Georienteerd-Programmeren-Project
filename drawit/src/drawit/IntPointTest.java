package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IntPointTest {

	@Test
	void test() {
		//creating IntPoints to use in tests
			//creating IntPoints & IntVector for some operations
			IntPoint intPointA = new IntPoint(2,2);
			IntPoint intPointB = new IntPoint(6,2);
			IntPoint intPointC = new IntPoint(4,2);
			IntPoint intPointD = new IntPoint(4,8);
			IntPoint intPointCaxisYLower = new IntPoint(4,1);
			IntVector intVector1 = new IntVector(6,2);
			//get value of created IntPoints (A&B)
			assertEquals(2,intPointA.getX());
			assertEquals(2,intPointA.getY());
			assertEquals(6,intPointB.getX());
			assertEquals(2,intPointB.getY());
		//testing implemented operators on IntPoints (minus,plus,equals)
			//doing operations on already existing IntPoints
				//minus
				IntVector intPointAminusB = intPointA.minus(intPointB);
				//plus
				IntPoint intPointAplusB = intPointA.plus(intVector1);
				//equals --> boolean so test can be done directly
				assertEquals(false,intPointA.equals(intPointB));
			//get value of IntPoints created by operations
				//minus
				assertEquals(2-6,intPointAminusB.getX());
				assertEquals(2-2,intPointAminusB.getY());
				//plus
				assertEquals(2+6,intPointAplusB.getX());
				assertEquals(2+2,intPointAplusB.getY());
		//testing implemented more-complex functions on IntPoints (isOnLineSegment,lineSegmentsIntersect)
			//doing operations on already existing IntPoints --> boolean value so instant check
				//isOnLineSegment
					//line is AC-CB so C is on line AB
					assertEquals(true,intPointC.isOnLineSegment(intPointA, intPointB));
					//line is CD so A is not a part of line CD
					assertEquals(false,intPointA.isOnLineSegment(intPointC, intPointD));
				//lineSegmentIntersects
					//line is AC-CB || AB so AC should not intersect with AB
					assertEquals(false,IntPoint.lineSegmentsIntersect(intPointA, intPointB, intPointA, intPointC));
					//line CD is orthogonal on AB so they shouldn't intersect
					assertEquals(false,IntPoint.lineSegmentsIntersect(intPointA, intPointB, intPointC, intPointD));
					//say we make our C one lower on the Y-axis, CD should intersect with AB
					assertEquals(true,IntPoint.lineSegmentsIntersect(intPointA, intPointB, intPointCaxisYLower, intPointD));
	}

}
