package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DoubleVectorTest {

	@Test
	void test() {
		//creating & getting values of DoubleVector (initiating,getX,getY,getSize)
			//creating DoubleVectors
			DoubleVector doubleVector1 = new DoubleVector(1.2,3.4);
			DoubleVector doubleVector2 = new DoubleVector(5.6,7.8);
			//get value of created DoubleVectors
				//doubleVector1 (getX,getY,getSize)
				assertEquals(1.2,doubleVector1.getX());
				assertEquals(3.4,doubleVector1.getY());
				assertEquals(Math.sqrt(Math.pow(1.2, 2) + Math.pow(3.4, 2)),doubleVector1.getSize());
				//doubleVector2 (getX,getY,getSize)
				assertEquals(5.6,doubleVector2.getX());
				assertEquals(7.8,doubleVector2.getY());
				assertEquals(Math.sqrt(Math.pow(5.6, 2) + Math.pow(7.8, 2)),doubleVector2.getSize());
		//testing implemented operators on DoubleVectors (scale,plus,asAngle)
			//doing operations on already existing DoubleVectors
				//scale
				DoubleVector doubleVector1scale = doubleVector1.scale(0.5);
				//plus
				DoubleVector doubleVector1plus2 = doubleVector1.plus(doubleVector2);
				//asAngle
				double doubleVector1angle = doubleVector1.asAngle();
			//get value of DoubleVectors (& double) created by operations
				//scale
				assertEquals(1.2/2, doubleVector1scale.getX());
				assertEquals(3.4/2, doubleVector1scale.getY());
				//plus
				assertEquals(1.2+5.6,doubleVector1plus2.getX());
				assertEquals(3.4+7.8,doubleVector1plus2.getY());
				//asAngle
				assertEquals(Math.atan2(3.4,1.2),doubleVector1angle);
		//testing "more-complex" functions (dotProduct,crossProduct)
				//dotProduct
				double doubleVector1dot2 = doubleVector1.dotProduct(doubleVector2);
				//crossProduct
				double doubleVector1cross2 = doubleVector1.crossProduct(doubleVector2);
			//get value of doubles created by "more-complex" functions
				//dotProduct
				assertEquals((1.2*5.6)+(3.4*7.8),doubleVector1dot2);
				//crossProduct
				assertEquals((1.2*7.8)-(5.6*3.4),doubleVector1cross2);
	}

}
