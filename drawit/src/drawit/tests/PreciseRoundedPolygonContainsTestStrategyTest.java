package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import drawit.PreciseRoundedPolygonContainsTestStrategy;
import drawit.IntPoint;
import drawit.RoundedPolygon;

class PreciseRoundedPolygonContainsTestStrategyTest {

	@Test
	void test() {
		//figure 1
		IntPoint firstPolygonPointA = new IntPoint(0,0);
		IntPoint firstPolygonPointB = new IntPoint(10,0);
		IntPoint firstPolygonPointC = new IntPoint(10,10);
		IntPoint firstPolygonPointD = new IntPoint(0,10);
		
		//contain test points
		IntPoint pointInsideFigure = new IntPoint(5,5);
		IntPoint pointOutsideFigure = new IntPoint(15,5);
		
		//creating vertice collections from points
		IntPoint[] firstVerticeArray = new IntPoint[] {firstPolygonPointA,firstPolygonPointB,firstPolygonPointC,firstPolygonPointD};
		
		//creating roundedpolygon from vertice collections
		RoundedPolygon firstRoundedPolygon = new RoundedPolygon();
		firstRoundedPolygon.setVertices(firstVerticeArray);
		
		//creating PreciceRoundedPolygoncontainsTestStrategy
		PreciseRoundedPolygonContainsTestStrategy TestStrategyClass = new PreciseRoundedPolygonContainsTestStrategy();
		
		//testing contains()
		assertEquals(true,TestStrategyClass.contains(firstRoundedPolygon, pointInsideFigure));
		assertEquals(false,TestStrategyClass.contains(firstRoundedPolygon, pointOutsideFigure));
		//since this class deals with parameters defensively, we shall test whether function throws nullpointerexception when expected
		Boolean hasThrown = false;
		try {
			TestStrategyClass.contains(firstRoundedPolygon, null);
			}
		catch(Exception NullPointerException) {
			hasThrown = true;
				  //  Block of code to handle errors
			}
		assertEquals(true,hasThrown);
	}
}
