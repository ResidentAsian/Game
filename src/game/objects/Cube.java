package game.objects;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glVertex3f;
import game.widgets.Entity;

import org.lwjgl.util.vector.Vector3f;

public class Cube extends Entity {
	
	/*
	 * Variable to store if the object is to be rendered
	 */
	private boolean display = true;
	
	/*
	 * Constructor for when the object is created at runtime
	 */
	public Cube(ObjectList objects, boolean display) {
		/*
		 * Type is defined in the entity class to allow dynamic rendering of
		 * the object without having a fixed render
		 */
		type = "Cube";
		/*
		 * Sets objects list to allow for an object to be added
		 */
		objectList = objects;
		/*
		 * Sets out the vectors for the object
		 */
		points = new Vector3f[8];
		/*
		 * Sets the inputted value to the object
		 * This defines if the object will be visible
		 */
		this.display = display;
	}
	
	/*
	 * Constructor for when the object is created at runtime without display defined
	 */
	public Cube(ObjectList objects) {
		this(objects, true);
	}

	public void create(Vector3f origin, int x, int y, int z) {
		// Sets the origin for the object within the parent class
		this.origin = origin;
		
		/*
		 * Defines the dimensions within easier to reference values
		 */
		width = x;
		height = y;
		depth = z;
		
		if (display) {
			/*
			 * Creates all the vectors required to render the object
			 */
			points[0] = new Vector3f(origin.x, origin.y, origin.z);
			points[1] = new Vector3f(origin.x + x, origin.y, origin.z);
			points[2] = new Vector3f(origin.x + x, origin.y, origin.z - z);
			points[3] = new Vector3f(origin.x, origin.y, origin.z - z);
			points[4] = new Vector3f(origin.x, origin.y + y, origin.z);
			points[5] = new Vector3f(origin.x + x, origin.y + y, origin.z);
			points[6] = new Vector3f(origin.x + x, origin.y + y, origin.z - z);
			points[7] = new Vector3f(origin.x, origin.y + y, origin.z - z);
		
			/*
			 * Begins the draw process for creating the squares for making the cube
			 */
			glBegin(GL_QUADS);
			
			/*
			 * Bottom
			 */
			glColor3f(0, 0, 0);
			drawPoint(0);
			drawPoint(1);
			drawPoint(2);
			drawPoint(3);
			
			/*
			 * Top
			 */
			glColor3f(0, 0, 1);
			drawPoint(4);
			drawPoint(5);
			drawPoint(6);
			drawPoint(7);
			
			/*
			 * Front
			 */
			glColor3f(0, 0, 0);
			drawPoint(0);
			drawPoint(1);
			drawPoint(5);
			drawPoint(4);
			
			/*
			 * Left
			 */
			glColor3f(0, 1, 1);
			drawPoint(0);
			drawPoint(3);
			drawPoint(7);
			drawPoint(4);
			
			/*
			 * Back
			 */
			glColor3f(1, 0, 0);
			drawPoint(3);
			drawPoint(7);
			drawPoint(6);
			drawPoint(2);
			
			/*
			 * Right
			 */
			glColor3f(1, 0, 1);
			drawPoint(1);
			drawPoint(2);
			drawPoint(6);
			drawPoint(5);
			
			/*
			 * Resets the colour of the render and ends the generation of squares
			 */
			glColor3f(1, 1, 1);
			glEnd();
			glEndList();
		}
		/*
		 * Adds the object to the list of objects allowing for collision detection when rendered
		 */
		objectList.addObject(this);
	}
	
	/*
	 * Allow for the creating of a vertex giving the variables for 
	 * position and dimensions
	 */
	private void drawPoint(int point) {
		glVertex3f(points[point].x, points[point].y, points[point].z);
	}
	
}