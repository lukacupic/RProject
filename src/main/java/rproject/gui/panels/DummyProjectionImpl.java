package rproject.gui.panels;

import de.fhpotsdam.unfolding.geo.AbstractProjection;
import processing.core.PVector;

public class DummyProjectionImpl extends AbstractProjection {

	@Override
	public PVector rawProject(PVector point) {
		return new PVector(1, 1, 1);
	}

	@Override
	public PVector rawUnproject(PVector point) {
		return new PVector(1, 1, 1);
	}
}
