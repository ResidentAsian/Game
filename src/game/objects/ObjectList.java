package game.objects;

import game.widgets.Entity;

import java.util.HashMap;
import java.util.Map;

public class ObjectList {
	
	/*
	 * This is the map of all objects created and allows for referencing of each for collision detection
	 */
	public Map<Integer, Entity> Objects = new HashMap<Integer, Entity>();
	/*
	 * Counter for the number of objects added to the map
	 */
	public int objectCount = 1;
	
	/*
	 * This function adds an object to the map. This takes the counter and increments
	 * it for the key used
	 */
	public void addObject(Entity object) {
		Objects.put(objectCount, object);
		objectCount++;
	}
	
	/*
	 * This returns the map when called allowing for rendering and 
	 */
	public Map<Integer, Entity> getMap() {
		return Objects;
	}
	
}
