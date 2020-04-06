package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;
import drawit.shapegroups1.*;

import org.junit.jupiter.api.Test;

class Shapegroups1ExtentTest {

	@Test
	void test() {
		
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
	}

}
