package drawit.shapegroups1.exporter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.ShapeGroup;

public class ShapeGroupExporter {
	public static Object toPlainData(ShapeGroup shapeGroup) {
		Map<String,Object> result;
		Extent extent = shapeGroup.getExtent();
		Extent originalExtent = shapeGroup.getOriginalExtent();
		if(shapeGroup.getSubgroupCount() == 0) {
			List<Map<String,Integer>> verticesMapList = new ArrayList<Map<String,Integer>>();
			IntPoint[] vertices = shapeGroup.getShape().getVertices();
			for(int i = 0; i < vertices.length; i++) {
				Map<String,Integer> corner = Map.of("x",vertices[i].getX(),"y",vertices[i].getY());
				verticesMapList.add(corner);
			}
			RoundedPolygon shape = shapeGroup.getShape();
			Color color = shape.getColor();
			result = Map.of("originalExtent", Map.of("left", originalExtent.getLeft(), "top", originalExtent.getTop(), "right", originalExtent.getRight(), "bottom", originalExtent.getBottom()),
				    "extent", Map.of("left", extent.getLeft(), "top", extent.getTop(), "right", extent.getRight(), "bottom", extent.getBottom()),
		            "shape", Map.of(
		                "vertices", verticesMapList,
		                "radius", shape.getRadius(),
		                "color", Map.of("red", color.getRed(), "green", color.getGreen(), "blue", color.getBlue())));
		}
		else {
			List<ShapeGroup> subgroups = shapeGroup.getSubgroups();
			List<Object> subgroupsMapList = new ArrayList<Object>();
			for(int i = 0; i < shapeGroup.getSubgroupCount(); i++) {
				subgroupsMapList.add(toPlainData(subgroups.get(i)));
			}
			result = Map.of("originalExtent", Map.of("left", originalExtent.getLeft(), "top", originalExtent.getTop(), "right", originalExtent.getRight(), "bottom", originalExtent.getBottom()),
				    "extent", Map.of("left", extent.getLeft(), "top", extent.getTop(), "right", extent.getRight(), "bottom", extent.getBottom()),
				    "subgroups",subgroupsMapList);
		}
		return result;
	}
}
