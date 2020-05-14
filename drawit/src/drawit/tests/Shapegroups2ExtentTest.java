package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import drawit.shapegroups2.Extent;


class Shapegroups2ExtentTest {

	@Test
	void test() {
		//extent is unchanged compared to previous interface

		//testing constructors
			Extent extent1 = Extent.ofLeftTopRightBottom(10,10,20,20);
			Extent extent2 = Extent.ofLeftTopWidthHeight(10,10,10,10);
				
			//testing getters
			assertEquals(10,extent1.getLeft());
			assertEquals(10,extent1.getTop());
			assertEquals(20,extent1.getBottom());
			assertEquals(20,extent1.getRight());
			assertEquals(10,extent1.getWidth());
			assertEquals(10,extent1.getHeight());
			
			assertEquals(10,extent2.getLeft());
			assertEquals(10,extent2.getTop());
			assertEquals(20,extent2.getBottom());
			assertEquals(20,extent2.getRight());
			assertEquals(10,extent2.getWidth());
			assertEquals(10,extent2.getHeight());
			
			//testing withLeft()
			extent1 = extent1.withLeft(30);
			assertEquals(30,extent1.getLeft());
			
			//testing withRight()
			extent1 = extent1.withRight(40);
			assertEquals(40,extent1.getRight());
			
			//testing withTop()
			extent1 = extent1.withTop(30);
			assertEquals(30,extent1.getTop());
			
			//testing withBottom()
			extent1 = extent1.withBottom(40);
			assertEquals(40,extent1.getBottom());
			
			//testing withHeight()
			extent1 = extent1.withHeight(20);
			assertEquals(20,extent1.getHeight());
			assertEquals(50,extent1.getBottom());
			
			//testing withWidth()
			extent1 = extent1.withWidth(20);
			assertEquals(20,extent1.getWidth());
			assertEquals(50,extent1.getRight());
			
			//testing added functions (part 3)
			Extent equalExtent1 = Extent.ofLeftTopRightBottom(10,20,30,40);
			Extent equalExtent2 = Extent.ofLeftTopRightBottom(10,20,30,40);
			Extent NonequalExtent3 = Extent.ofLeftTopRightBottom(40,30,20,10);
			Extent rotatedExtent4 = Extent.ofLeftTopRightBottom(30,40,10,20);
			Extent LRrotatedSidesExtent1 = Extent.ofLeftTopRightBottom(30,30,50,50);
			Extent TBrotatedSidesExtent2 = Extent.ofLeftTopRightBottom(50,50,30,30);
				//hashCode
					//equal extents
					assertEquals(true,equalExtent1.hashCode() == equalExtent2.hashCode());
					//non-equal extents
					assertEquals(false,equalExtent1.hashCode() == NonequalExtent3.hashCode());
					//left is right and bottom is top (inverted)
					assertEquals(true,equalExtent1.hashCode() == rotatedExtent4.hashCode());
					assertEquals(true,TBrotatedSidesExtent2.hashCode() == LRrotatedSidesExtent1.hashCode());
				//equals
					//equal extents
					assertEquals(true,equalExtent1.equals(equalExtent2));
					//non-equal extents
					assertEquals(false,equalExtent1.equals(NonequalExtent3));
					//left is right and bottom is top (inverted)
					assertEquals(true,equalExtent1.equals(rotatedExtent4));
					assertEquals(true,TBrotatedSidesExtent2.equals(LRrotatedSidesExtent1));
				//toString
					//equal extents
					assertEquals("10 30 20 40",equalExtent1.toString());
					//non-equal extents
					assertEquals("20 40 10 30",NonequalExtent3.toString());
					//left is right and bottom is top (inverted)
					assertEquals("10 30 20 40",rotatedExtent4.toString());
	}
}
