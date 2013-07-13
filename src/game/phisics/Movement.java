package game.phisics;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import game.widgets.Entity;
import game.objects.ObjectList;
import game.player.Player;

public class Movement {

	/*
	 * The speed the mouse will move relative to degrees on screen
	 */
	private float mouseSensitivity = 1f;

	/*
	 * Angle used to work out the current heading based upon the rotation of the player.
	 * Distance is how far the player will travel based upon time since last frame.
	 * xDistance is how far the player will move in the x axis. This applies to y and z distance.
	 * Velocity is how fast the player is travelling and is used for the gravity of a player.
	 */
	private float angle;
	private float distance;
	private float xDistance;
	private float yDistance;
	private float zDistance;
	private float velocity;
	
	/*
	 * Collision if the player is intersecting an object.
	 */
	private boolean collision = false;
	
	/*
	 * List of objects set to value passed to it from constructor
	 */
	private ObjectList objectList;

	/*
	 * New position is where the player will be if they moved
	 */
	private Vector3f newPosition = new Vector3f(0, 0, 0);

	/*
	 * Constructor for movement given only the object list
	 */
	public Movement(ObjectList objects) {
		objectList = objects;
		velocity = 100;
	} 

	/*
	 * Move the player based upon the change in time and player's position
	 */
	public void move(Player player, int delta) {

		/*
		 * Variables to catch user inputs for movement such as "w", "a", "s", "d" and space
		 */
		boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_D);
		boolean run = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		boolean space = Keyboard.isKeyDown(Keyboard.KEY_SPACE);

		/*
		 * These are checks if the user has used a given one or combination of key presses and 
		 * the response for the players movement
		 */
		
		if (keyUp && !keyDown && !keyLeft && !keyRight) {
			angle = player.getRotation().y;
			distance = (player.getMovementSpeed() * 0.0002f) * delta;
			zDistance = distance * (float) Math.cos(Math.toRadians(angle));
			xDistance = (float) (Math.sin(Math.toRadians(angle)) * distance);
		} else if (!keyUp && keyDown && !keyLeft && !keyRight) {
			angle = player.getRotation().y;
			distance = -(player.getMovementSpeed() * 0.0002f) * delta;
			zDistance = distance * (float) Math.cos(Math.toRadians(angle));
			xDistance = (float) (Math.sin(Math.toRadians(angle)) * distance);
		} else if (!keyUp && !keyDown && keyLeft && !keyRight) {
			angle = player.getRotation().y - 90;
			distance = (player.getMovementSpeed() * 0.0002f) * delta;
			zDistance = distance * (float) Math.cos(Math.toRadians(angle));
			xDistance = (float) (Math.sin(Math.toRadians(angle)) * distance);
		} else if (!keyUp && !keyDown && !keyLeft && keyRight) {
			angle = player.getRotation().y + 90;
			distance = (player.getMovementSpeed() * 0.0002f) * delta;
			zDistance = distance * (float) Math.cos(Math.toRadians(angle));
			xDistance = (float) (Math.sin(Math.toRadians(angle)) * distance);
		} else if (keyUp && !keyDown && keyLeft && !keyRight) {
			angle = player.getRotation().y - 45;
			distance = (player.getMovementSpeed() * 0.0002f) * delta;
			zDistance = distance * (float) Math.cos(Math.toRadians(angle));
			xDistance = (float) (Math.sin(Math.toRadians(angle)) * distance);
		} else if (keyUp && !keyDown && !keyLeft && keyRight) {
			angle = player.getRotation().y + 45;
			distance = (player.getMovementSpeed() * 0.0002f) * delta;
			zDistance = distance * (float) Math.cos(Math.toRadians(angle));
			xDistance = (float) (Math.sin(Math.toRadians(angle)) * distance);
		} else if (!keyUp && keyDown && keyLeft && !keyRight) {
			angle = player.getRotation().y + 45;
			distance = -(player.getMovementSpeed() * 0.0002f) * delta;
			zDistance = distance * (float) Math.cos(Math.toRadians(angle));
			xDistance = (float) (Math.sin(Math.toRadians(angle)) * distance);
		} else if (!keyUp && keyDown && !keyLeft && keyRight) {
			angle = player.getRotation().y - 45;
			distance = -(player.getMovementSpeed() * 0.0002f) * delta;
			zDistance = distance * (float) Math.cos(Math.toRadians(angle));
			xDistance = (float) (Math.sin(Math.toRadians(angle)) * distance);
		} else if (run) {
			yDistance -= 1;
		}
		
		/*
		 * Used to check the "f" key to reset the players position for testing the start
		 * position on load and while running
		 */
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			player.setPosition(new Vector3f(0, 90, 0));
		}
		
		/*
		 * 
		 */
		if (space) {
			yDistance = -(gravity(-160, collision) * 0.0002f) * delta;
		} else {
			yDistance = -(gravity(80, collision) * 0.0002f) * delta;
		}
		
		collision = false;
		
		newPosition.x = -player.getPosition().x + xDistance;
		newPosition.y = -player.getPosition().y + yDistance;
		newPosition.z = -player.getPosition().z - zDistance;

		if (run) {
			player.setMovementSpeed(player.standardSpeed * 2);
		} else {
			player.setMovementSpeed(player.standardSpeed);
		}

		
		// Object being tested within for loop
		Entity currentObject;

		// Currently Colliding
		boolean xColliding;
		boolean yColliding;
		boolean zColliding;

		// Will it collide on next movement
		boolean xCollision;
		boolean yCollision;
		boolean zCollision;

		for (int i = 1; i <= objectList.getMap().size(); i++) {

			currentObject = objectList.getMap().get(i);

			xColliding = testCollision(player, -player.getPosition().x, currentObject, 1);
			yColliding = testCollision(player, -player.getPosition().y, currentObject, 2);
			zColliding = testCollision(player, -player.getPosition().z, currentObject, 3);

			xCollision = testCollision(player, newPosition.x, currentObject, 1);
			yCollision = testCollision(player, newPosition.y, currentObject, 2);
			zCollision = testCollision(player, newPosition.z, currentObject, 3);

			if (!xColliding && yColliding && zColliding && xCollision) {
				newPosition.x = -player.getPosition().x;
			}
			if (xColliding && !yColliding && zColliding && yCollision) {
				newPosition.y = -player.getPosition().y;
				collision = true;
			}
			if (xColliding && yColliding && !zColliding && zCollision) {
				newPosition.z = -player.getPosition().z;
			}
		}

		player.setPosition(newPosition);
		
		xDistance = 0;
		yDistance = 0;
		zDistance = 0;

		/*
		 * If the mouse is grabbed and the user wants to escape grab, pressing escape will release mouse
		 */
		if (Mouse.isGrabbed() && Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Mouse.setGrabbed(false);
		}

		/*
		 * Mouse Checks if grabbed and if right clicked
		 */
		while (Mouse.next()) {
			if (Mouse.isButtonDown(0)) {
				Mouse.setGrabbed(true);
			} else if (Mouse.isButtonDown(1)) {
				// TODO - Create block at position based on rotation and position
			}
		}

		/*
		 * This checks the user's rotation and resets it after
		 * the new angle is under 0 or over 360
		 */
		if (Mouse.isGrabbed()) {
			float mouseDX = Mouse.getDX() * mouseSensitivity * 0.16f;
			float mouseDY = Mouse.getDY() * mouseSensitivity * 0.16f;
			if (player.getRotation().y + mouseDX >= 360) {
				player.getRotation().y = player.getRotation().y + mouseDX - 360;
			} else if (player.getRotation().y + mouseDX < 0) {
				player.getRotation().y = 360 - player.getRotation().y + mouseDX;
			} else {
				player.getRotation().y += mouseDX;
			}
			if (player.getRotation().x - mouseDY >= -80 && player.getRotation().x - mouseDY <= 80) {
				player.getRotation().x += -mouseDY;
			} else if (player.getRotation().x - mouseDY < -80) {
				player.getRotation().x = -80;
			} else if (player.getRotation().x - mouseDY > 80) {
				player.getRotation().x = 80;
			}
		}
	}

	public float gravity (int newVelocity, boolean collision) {
		
		if (collision) {
			velocity = newVelocity;
		}
		
		if (velocity < 0) {
			velocity += 7.3f;
		}
		
		if (velocity > 0 && velocity < 1000) {
			velocity += 17;
		}
		
		return velocity;
	}

	public boolean testCollision (Player player, float position, Entity currentObject, int axisId) {

		float playerSize;
		float objectSize;
		float playerPosition;
		float objectPosition;

		if (axisId == 1) {
			playerSize = player.width;
			objectSize = currentObject.width;
			playerPosition = position;
			objectPosition = currentObject.origin.x;

			if (playerPosition + (playerSize/2) > objectPosition && playerPosition - (playerSize/2) < objectPosition + objectSize) {
				return true;
			} else {
				return false;
			}

		} else if (axisId == 2) {
			playerSize = player.height;
			objectSize = currentObject.height;
			playerPosition = position;
			objectPosition = currentObject.origin.y;
			if (playerPosition + (1) > objectPosition && playerPosition - (playerSize - 1) < objectPosition + objectSize) {
				return true;
			} else {
				return false;
			}
		} else if (axisId == 3) {
			playerSize = player.depth;
			objectSize = currentObject.depth;
			playerPosition = position;
			objectPosition = currentObject.origin.z;
			if (playerPosition - (playerSize/2)  < objectPosition && playerPosition + (playerSize/2) > objectPosition - objectSize) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
