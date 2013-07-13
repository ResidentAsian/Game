package game.player;

import org.lwjgl.util.vector.Vector3f;

/**
 * <code>Player</code> or <code>NPC</code> details are stored using this class.
 */
public class Player {
	
	private Vector3f playerPosition;
	private Vector3f playerRotation;
	
	public float width;
	public float height;
	public float depth;
	
	public int movementSpeed;
	public int standardSpeed;

	public Player() {
		
		playerPosition = new Vector3f();
		playerRotation = new Vector3f(0, 0, 0);
		
		
		width = 2;
		height = 7;
		depth = 2;
		
		standardSpeed = 100;
		movementSpeed = standardSpeed;
		
		setPosition(new Vector3f(0, 90, 0));
	}
	
	public void setPositionX(float newPosition) {
		playerPosition.x = -newPosition;
	}
	
	public void setPositionY(float newPosition) {
		playerPosition.y = -newPosition;
	}
	
	public void setPositionZ(float newPosition) {
		playerPosition.z = -newPosition;
	}

	public void setRotation(Vector3f newPlayerRotation) {
		playerRotation = newPlayerRotation;
	}
	
	public void setRotationX(float newRotation) {
		playerRotation.x = newRotation;
	}
	
	public void setRotationY(float newRotation) {
		playerRotation.y = newRotation;
	}
	
	public void setRotationZ(float newRotation) {
		playerRotation.z = newRotation;
	}

	public Vector3f getRotation() {
		return playerRotation;
	}
	
	public void setMovementSpeed(int newMovementSpeed) {
		movementSpeed = newMovementSpeed;
	}
	
	public float getMovementSpeed() {
		return movementSpeed;
	}
	
	public Vector3f getPosition() {
		return playerPosition;
	}

	public void setPosition(Vector3f newPosition) {
		playerPosition.x = -newPosition.x;
		playerPosition.y = -newPosition.y;
		playerPosition.z = -newPosition.z;
	}
	
}
