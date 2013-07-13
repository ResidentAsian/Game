package game.main;

import game.objects.Cube;
import game.objects.ObjectList;
import game.player.Player;
import game.world.render.Ground;
import game.phisics.*;
import game.system.Timing;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Main {
	
	/*
	 * All variables declared at the top of each class are globally accessible
	 * provided the class is imported and created.
	 */
	// Declares the window height and width
	public static int windowWidth = 800;
	public static int windowHeight = 600;
	
	// Creates the field of view for the player, their close render distance and far render distance.
	// This allows for fog to be added for when the max render distance has been reached
	public static float fov = 80f;
	public static float zNear = 0.01f;
	public static float zFar = 200f;

	// This is the list of objects that the world generates and makes a store for all collidable
	// within the given space
	public static ObjectList objectList = new ObjectList();
	
	/*
	 * This is the main sub routine
	 * This is where the program begins
	 */
	public static void main(String[] args) {
		// A test is done to render the window using the given parameters. If the user does not
		// have the required specification for their computers then the program will close
		// with an error
		try {
			Display.setDisplayMode(new DisplayMode(windowWidth, windowHeight));
			Display.setTitle("Game Alpha Test 1.0");
			Display.create();
		} catch (LWJGLException ex) {
			ex.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		
		/*
		 * INITIALIZATION CODE
		 */
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		/*
		 * GLU Perspective used for 3D rendering with field of view, width/height, near and far render distances
		 */
		gluPerspective(fov, (float) windowWidth / (float) windowHeight, zNear, zFar);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		/*
		 * Depth test makes sure all objects closer are shown infront of other objects
		 */
		glEnable(GL_DEPTH_TEST);

		/*
		 * Enables the use of 2D textures
		 */
		glEnable(GL_TEXTURE_2D);
		
		/*
		 * Defining the classes used for creating objects within the main loop and for all data
		 * used to get player positions and timing for movement
		 */
		Cube cube1 = new Cube(objectList);
		Cube cube2 = new Cube(objectList);
		Cube cube3 = new Cube(objectList);
		Cube cube4 = new Cube(objectList);
		Player player = new Player();
		Ground ground = new Ground();
		Timing timing = new Timing();
		Movement movement = new Movement(objectList);
		
		/*
		 * Main while loop checking if the window has made a request to close. This could be
		 * from an in game request or the closure of the window using the close button.
		 */
		while (!Display.isCloseRequested()) {
			/*
			 * Clears screen of all previous contents
			 */
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glClearColor(0.8f, 1, 1, 1);

			/*
			 * Renders a square for the ground and creates a texture that is repeated over the rendered square
			 */
			ground.renderGround(objectList);
			glBindTexture(GL_TEXTURE_2D, ground.getFloorTexture());
			glCallList(ground.getFloorList());
			
			/*
			 * Creates new cubes that are added to the list and therefore generated at runtime
			 */
			cube1.create(new Vector3f(-2, 4, 2), 4, 4, 4);
			cube2.create(new Vector3f(-2, 5, 16), 4, 4, 4);
			cube3.create(new Vector3f(-2, 6, 30), 4, 4, 4);
			cube4.create(new Vector3f(-2, 13, 44), 4, 4, 4);
			
			/*
			 * Load identity resets the values for the render settings and allows for all 
			 * unused data to be dumped
			 */
			glLoadIdentity();

			/*
			 * Sets the rotational values for the camera
			 */
			glRotatef(player.getRotation().x, 1, 0, 0);
			glRotatef(player.getRotation().y, 0, 1, 0);
			glRotatef(player.getRotation().z, 0, 0, 1);

			/*
			 * Sets the position of the camera
			 */
			glTranslatef(player.getPosition().x, player.getPosition().y, player.getPosition().z);

			/*
			 * Moves the player testing keyboard and mouse input
			 */
			movement.move(player, timing.delta());
			
			/*
			 * Resets the object count to restart rendering of all objects again
			 */
			
			objectList.objectCount = 1;
			
			
			/*
			 * Updates the display with the new values
			 * Video Sync to limit frame rate
			 */
			 Display.update();
			 Display.sync(60);
		}
		/*
		 * On close request, the while loop finishes and the program closes
		 */
		Display.destroy();
	}
}