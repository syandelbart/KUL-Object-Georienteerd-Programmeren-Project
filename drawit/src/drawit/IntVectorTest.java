package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IntVectorTest {

	@Test
	void test() {
	//creating & getting values of IntVector (initiating,getX,getY)
		//creating IntVectors
		IntVector intVector1 = new IntVector(1,2);
		IntVector intVector2 = new IntVector(3,4);
		
		IntVector intVector1col = new IntVector(2,4);
		//get value of created IntVectors
			//intVector1 (getX,getY)
			assertEquals(1,intVector1.getX());
			assertEquals(2,intVector1.getY());
			//intVector2 (getX,getY,getSize)
			assertEquals(3,intVector2.getX());
			assertEquals(4,intVector2.getY());
	//testing "more-complex" functions (crossProduct,isCollinearWith,dotProduct)
			//crossProduct
			assertEquals((1*4)-(3*2),intVector1.crossProduct(intVector2));
			//isCollinearWith --> boolean value so instant assert possible
			assertEquals(false,intVector1.isCollinearWith(intVector2));
			assertEquals(true,intVector1.isCollinearWith(intVector1col));
			//dotProduct
			assertEquals((1*3)+(2*4),intVector1.dotProduct(intVector2));
	}

}
