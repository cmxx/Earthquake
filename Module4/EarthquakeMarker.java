package week3;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/** Implements a visual marker for earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public abstract class EarthquakeMarker extends SimplePointMarker
{
	
	// Did the earthquake occur on land?  This will be set by the subclasses.
	protected boolean isOnLand;

	// The radius of the Earthquake marker
	// You will want to set this in the constructor, either
	// using the thresholds below, or a continuous function
	// based on magnitude. 
	protected float radius;
	
	
	/** Greater than or equal to this threshold is a moderate earthquake */
	public static final float THRESHOLD_MODERATE = 5;
	/** Greater than or equal to this threshold is a light earthquake */
	public static final float THRESHOLD_LIGHT = 4;

	/** Greater than or equal to this threshold is an intermediate depth */
	public static final float THRESHOLD_INTERMEDIATE = 70;
	/** Greater than or equal to this threshold is a deep depth */
	public static final float THRESHOLD_DEEP = 300;

	// ADD constants for colors
	
	/* Dont know how to set RGB value as integer.
	int red = #FF0000;
	int yellow = #FFFF00;
	int blue = #0000FF; 
	*/

	
	// abstract method implemented in derived classes
	public abstract void drawEarthquake(PGraphics pg, float x, float y);

	// constructor
	public EarthquakeMarker (PointFeature feature) 
	{
		//SimplePointMarker(Location); Creates a point marker for the given location.
		super(feature.getLocation());
		
		// Add a radius property and then set the properties
		// Pull out the property of SimplePointMarker feature(quake) from subClasses which is earthQuakes data
		//     from EarthquakeCityMap.
		java.util.HashMap<String, Object> properties = feature.getProperties();
		// Extract magnitude
		float magnitude = Float.parseFloat(properties.get("magnitude").toString());
		// Create key and value into the properties variable.
		properties.put("radius", 2*magnitude );
		// Change the property of the feature to the data in properties variable
		setProperties(properties);
		// EarthquakeMarker.radius = 1.75 times magnitude.
		this.radius = 1.75f*getMagnitude();
	}
	
	

	

	
	/** This draw method overrides the draw method in SimplePointmarker.
	 * The draw method is automatically called when map.addMarker() is executed. (I guess)
	 * 
	 */
	// calls abstract method drawEarthquake and then checks age and draws X if needed
	public void draw(PGraphics pg, float x, float y) {
		// save previous styling
		pg.pushStyle();
			
		// determine color of marker from depth
		colorDetermine(pg);
		
		// call abstract method implemented in child class to draw marker shape
		drawEarthquake(pg, x, y);
		
		
		// Draw X
		
		String getAge = (String) getProperty("age");
		if ( getAge.equals("Past Day")) {
			float newscale = radius * 1;
			pg.line(x-newscale,y-newscale,x+newscale,y+newscale);
			pg.line(x-newscale,y+newscale,x+newscale,y-newscale);
		}
		
		// OPTIONAL TODO: draw X over marker if within past day		
		
		// reset to previous styling
		pg.popStyle();
		
	}
	
	// determine color of marker from depth
	// We suggest: Deep = red, intermediate = blue, shallow = yellow
	// But this is up to you, of course.
	// You might find the getters below helpful.
	private void colorDetermine(PGraphics pg) {
		//TODO: Implement this method
		
		float depth = this.getDepth();
		
		if (depth <= THRESHOLD_INTERMEDIATE) {
			pg.fill(255,255,0); // Yellow
		}
		else if (depth <=THRESHOLD_DEEP) {
			pg.fill(0,0,255);  // Blue
		}
		else {
			pg.fill(255,0,0);  // Red
		}
		
	}
	
	
	/*
	 * getters for earthquake properties
	 */
	
	public float getMagnitude() {
		return Float.parseFloat(getProperty("magnitude").toString());
	}
	
	public float getDepth() {
		return Float.parseFloat(getProperty("depth").toString());	
	}
	
	public String getTitle() {
		return (String) getProperty("title");	
		
	}
	
	public float getRadius() {
		return Float.parseFloat(getProperty("radius").toString());
	}
	
	public boolean isOnLand()
	{
		return isOnLand;
	}
	
	
}
