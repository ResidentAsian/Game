package game.widgets;

import game.objects.ObjectList;

import org.lwjgl.util.vector.Vector3f;

public class Entity {
	
	/*
	 * Defining standard height, width and depth of any given object created
	 */
	public int height;
	public int width;
	public int depth;
	
	/*
	 * This is the given type of object when created. Allows for judging 
	 * of how may vectors will need to be checked when rendering and collision detecting
	 */
	public String type;
	
	/*
	 * The origin is the initial vector which holds no height, width or depth
	 * The object list is added here to be referenced by the object. This is given
	 * the value of the object list passed to the object when created.
	 * The points are the vectors that will be drawn to show the faces of the object
	 */
	public Vector3f origin = new Vector3f();
	public ObjectList objectList;
	public Vector3f[] points;

}
