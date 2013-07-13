package game.world.render;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex3f;

import game.objects.Cube;
import game.objects.ObjectList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Ground {

	private int floorTexture;
	private int floorDisplayList;
	
	public void renderGround(ObjectList objects) {
		/*
		 * Generates a square on the ground level without a given texture
		 */
		floorDisplayList = glGenLists(1);
		glNewList(floorDisplayList, GL_COMPILE);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex3f(-100, 0, -100);
		glTexCoord2f(0, 100 * 10 * 0.1f);
		glVertex3f(-100, 0, 100);
		glTexCoord2f(10 * 100 * 0.1f, 100 * 10 * 0.1f);
		glVertex3f(100, 0, 100);
		glTexCoord2f(10 * 100 * 0.1f, 0);
		glVertex3f(100, 0, -100);
		glEnd();
		glEndList();

		Cube cube = new Cube(objects, false);
		cube.create(new Vector3f(-100, -10, 100), 200, 10, 200);
		objects.addObject(cube);
		
		/*
		 * Floor textures bound to shapes using PNG Decoder
		 */
		floorTexture = glGenTextures();
		InputStream in = null;
		try {
			in = this.getClass().getResourceAsStream("../images/floor.png");			
			PNGDecoder decoder = new PNGDecoder(in);
			ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
			buffer.flip();
			glBindTexture(GL_TEXTURE_2D, floorTexture);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			glBindTexture(GL_TEXTURE_2D, 0);
		} catch (FileNotFoundException ex) {
			System.err.println("Failed to find the texture files.");
			ex.printStackTrace();
			Display.destroy();
			System.exit(1);
		} catch (IOException ex) {
			System.err.println("Failed to load the texture files.");
			ex.printStackTrace();
			Display.destroy();
			System.exit(1);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getFloorTexture() {
		return floorTexture;
	}
	
	public int getFloorList() {
		return floorDisplayList;
	}
	
}
