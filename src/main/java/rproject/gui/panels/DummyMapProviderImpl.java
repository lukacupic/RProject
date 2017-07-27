package rproject.gui.panels;

import de.fhpotsdam.unfolding.core.Coordinate;
import de.fhpotsdam.unfolding.geo.AbstractProjection;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import processing.core.PImage;

public class DummyMapProviderImpl extends AbstractMapProvider {

	public DummyMapProviderImpl(AbstractProjection abstractProjection) {
		super(abstractProjection);
	}

	@Override
	public PImage getTile(Coordinate coordinate) {
		return new PImage(15, 15);
	}

	@Override
	public String[] getTileUrls(Coordinate coordinate) {
		return new String[0];
	}

	@Override
	public int tileWidth() {
		return 0;
	}

	@Override
	public int tileHeight() {
		return 0;
	}
}
