package drawit.shapes2;

import drawit.IntPoint;

public interface ControlPoint {
	public drawit.IntPoint getLocation();
	public void remove();
	public void move(drawit.IntVector delta);
}
