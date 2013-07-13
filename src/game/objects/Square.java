package game.objects;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.lwjgl.util.vector.Vector3f;

import game.widgets.Entity;

public class Square extends Entity {

	public Square(ObjectList objects) {
		/*
		 * Type is defined in the entity class to allow dynamic rendering of
		 * the object without having a fixed render
		 */
		type = "Square";
		/*
		 * Sets out the vectors for the object
		 */
		objectList = objects;
		/*
		 * Sets out the vectors for the object
		 */
		points = new Vector3f[4];
	}
	
	/*
	 * Constructor for when the object is created at runtime
	 */
	public void create(Vector3f origin, int x, int y, int z) {
		// Sets the origin for the object within the parent class
		this.origin = origin;
		
		/*
		 * Defines the dimensions within easier to reference values
		 */
		width = x;
		height = y;
		depth = z;
		
		/*
		 * Creates all the vectors required to render the object
		 */
		points[0] = new Vector3f(origin.x, origin.y, origin.z);
		points[1] = new Vector3f(origin.x + 1, origin.y, origin.z);
		points[2] = new Vector3f(origin.x + 1, origin.y + 1, origin.z);
		points[3] = new Vector3f(origin.x, origin.y, origin.z);

		/*
		 * Begins the draw process for creating the squares for making the cube
		 */
		glBegin(GL_QUADS);
		
		/*
		 * Front
		 */
		glColor3f(0, 0, 0);
		
		drawPoint(0);
		drawPoint(1);
		drawPoint(2);
		drawPoint(3);
		
		/*
		 * Resets the colour of the render and ends the generation of squares
		 */
		glColor3f(1, 1, 1);
		
		glEnd();
		glEndList();
		
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
