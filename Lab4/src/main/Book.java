package main;

import java.awt.Container;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.behaviors.mouse.*;

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

public class Book {
	
	private static Node clone(Node node) {
		return node.cloneTree();
	}
	
	private static Material getMaterial(Color3f color) {
		Material material = new Material();
		material.setDiffuseColor(color);
		material.setSpecularColor(color);
		material.setShininess(5.0f);
	    return material;
	}
	
	private static void setColor(Primitive pr, Color3f color) {
	    Appearance app = new Appearance();
	    app.setMaterial(getMaterial(color));
	    pr.setAppearance(app);
	}
	
	private static void setTexture(Primitive pr, Color3f color, String path) {
		TextureLoader loader = new TextureLoader(path, "LUMINANCE", new Container());
		Texture texture = loader.getTexture();
		
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));
		
		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		
		Appearance app = new Appearance();
		app.setTexture(texture);
		app.setTextureAttributes(texAttr);
	    app.setMaterial(getMaterial(color));

		pr.setAppearance(app);
	}
	
	private static TransformGroup getNodeOnPosition(Node node, Vector3f vector) {
		Transform3D transform = new Transform3D(); 
		transform.setTranslation(vector);
		
		TransformGroup tg = new TransformGroup();
		tg.setTransform(transform);
		tg.addChild(node);
		
		return tg;
	}
	
	private static void addChild(BranchGroup group, Node node, Vector3f vector) {
		group.addChild(getNodeOnPosition(node, vector));
	}
	
	
	private static Box createBox(float x, float y, float z, Color3f color) {
		Box box = new Box(x, y, z, null);
		setColor(box, color);
	    return box;
	}
	
	private static Box createBox(float x, float y, float z, Color3f color, String texture) {
		int flags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
		Box box = new Box(x, y, z, flags, null);
		setTexture(box, color, texture);
	    return box;
	}
	
	private static Cylinder createCylinder(float r, float h, Color3f color) {
		Cylinder cylinder = new Cylinder(r, h);
		setColor(cylinder, color);
	    return cylinder;
	}
	
	
	private static BranchGroup newBook(Color3f bookColor, String texture) {
		Color3f pageColor = new Color3f(1, 1, 1);
		
		Box cover = createBox(0.2f, 0.3f, 0.01f, bookColor, texture);
		Cylinder cylinder = createCylinder(0.0605f, 0.6f, bookColor);
		Box page = createBox(0.19f, 0.3f, 0.001f, pageColor);
		
		BranchGroup book = new BranchGroup();
		addChild(book, cover, new Vector3f(0, 0, 0));
		addChild(book, clone(cover), new Vector3f(0, 0, 0.1f));
		addChild(book, cylinder, new Vector3f(-0.2f, 0, 0.05f));
		
		for (int i = 0; i < 20; i++) {
			addChild(book, clone(page), new Vector3f(0, 0, i / 250.0f + 0.011f));
		}
		
		return book;
	}
	
	
	private static void addControl(Group parent, Group child) {
		TransformGroup tg = new TransformGroup();
	    tg.addChild(child);
	
	    parent.addChild(tg);
	
	    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	
	    MouseRotate mouseRotate = new MouseRotate();
	    mouseRotate.setTransformGroup(tg);
	    mouseRotate.setSchedulingBounds(new BoundingSphere());
	    parent.addChild(mouseRotate);
	
	    MouseTranslate mouseTranslate = new MouseTranslate();
	    mouseTranslate.setTransformGroup(tg);
	    mouseTranslate.setSchedulingBounds(new BoundingSphere());
	    parent.addChild(mouseTranslate);
	
	    MouseZoom mouseZoom = new MouseZoom();
	    mouseZoom.setTransformGroup(tg);
	    mouseZoom.setSchedulingBounds(new BoundingSphere());
	    parent.addChild(mouseZoom);
	}
	
	
	private static void addDirectionalLight(BranchGroup group, Vector3f direction, Color3f color) {
	    BoundingSphere bounds = new BoundingSphere();
	    bounds.setRadius(100);

	    DirectionalLight light = new DirectionalLight(color, direction);
	    light.setInfluencingBounds(bounds);

	    group.addChild(light);
	}
	
	
	public static void main(String[] argv) {
		SimpleUniverse universe = new SimpleUniverse();
		BranchGroup group = new BranchGroup();
		
		Color3f purple = new Color3f(0.34f, 0.177f, 0.76f);
		Color3f green = new Color3f(0.5f, 0.9f, 0.5f);
		BranchGroup book = newBook(purple, "Images/forex.jpg");
		
		addControl(group, book);
		
		addDirectionalLight(group, new Vector3f(0, 0, -1), new Color3f(1, 1, 1));
		
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
	}
}
