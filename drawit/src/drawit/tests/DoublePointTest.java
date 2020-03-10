package drawit.tests;
import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.DoubleVector;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DoublePointTest {

	@Test
	void test() {
		//creating & getting values of DoublePoint (initiating,getX,getY)
			//creating DoublePoints
			DoublePoint doublePoint1 = new DoublePoint(1.2,3.4);
			DoublePoint doublePoint2 = new DoublePoint(5.6,7.8);
			//get value of created DoublePoints
			assertEquals(1.2,doublePoint1.getX());
			assertEquals(3.4,doublePoint1.getY());
			assertEquals(5.6,doublePoint2.getX());
			assertEquals(7.8,doublePoint2.getY());
		//testing implemented operators on DoublePoints (minus,plus)
			//doing operations on already existing DoublePoints
				//minus
				DoubleVector doubleVector1minus2 = doublePoint1.minus(doublePoint2);
				//plus
				DoublePoint doublePoint1plusVector = doublePoint1.plus(doubleVector1minus2);
			//get value of DoublePoints created by operations
				//minus
				assertEquals(1.2-5.6,doubleVector1minus2.getX());
				assertEquals(3.4-7.8,doubleVector1minus2.getY());
				//plus
				assertEquals(1.2+(1.2-5.6),doublePoint1plusVector.getX());
				assertEquals(3.4+(3.4-7.8),doublePoint1plusVector.getY());
		//testing rounding of values (round)
			IntPoint intPoint1Round = doublePoint1.round();
			IntPoint intPoint2Round = doublePoint2.round();
			//get value of IntPoints created by rounding
			assertEquals(1,intPoint1Round.getX());
			assertEquals(3,intPoint1Round.getY());
			assertEquals(6,intPoint2Round.getX());
			assertEquals(8,intPoint2Round.getY());
	}

}
