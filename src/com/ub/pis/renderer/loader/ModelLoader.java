/**************************************************************************************************
 * Copyright (c) 2013.                                                                            *
 * Machango Fight, the Massive Multiplayer Online.                                                *
 * Android Application                                                                            *
 *                                                                                                *
 * Curso 2012-2013                                                                                *
 *                                                                                                *
 * Este software ha sido desarrollado integramente para la asignatura 'Projecte                   *
 * Integrat de Software' en la Universidad de Barcelona por los estudiantes                       *
 * Pablo Martínez Martínez, Albert Folch, Xavi Moreno y Aaron Negrín.                             *
 **************************************************************************************************/

package com.ub.pis.renderer.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ub.pis.renderer.animation.Action;
import com.ub.pis.renderer.animation.Frame;
import com.ub.pis.renderer.animation.FrameList;
import com.ub.pis.renderer.linearalgebra.Matrix4fGroup;
import com.ub.pis.renderer.models.AnimatedModel;
import com.ub.pis.renderer.models.Model;
import com.ub.pis.renderer.opengl.Mesh;
import com.ub.pis.renderer.opengl.ShaderProgram;
import com.ub.pis.renderer.opengl.SkinnedMesh;
import com.ub.pis.renderer.opengl.Texture;
import com.ub.pis.renderer.renderer.Constants;
import com.ub.pis.renderer.utils.ReadFile;
import com.ub.pis.renderer.utils.VertexStruct;



public class ModelLoader {
	private JSONObject root;
	
	private InputStream modelStream;
	private InputStream textureStream;
	private InputStream vertexShaderStream;
	private InputStream fragmentShaderStream;
	
	public ModelLoader(InputStream modelStream, InputStream textureStream, InputStream vertexShaderStream, InputStream fragmentShaderStream) throws JSONException, IOException {
		this.vertexShaderStream = vertexShaderStream;
		this.fragmentShaderStream = fragmentShaderStream;
		this.textureStream = textureStream;
		this.modelStream = modelStream;
	}
	
	/**
	 * Loads a model
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public Model loadModel() throws JSONException, IOException {
		String model = ReadFile.read(modelStream);
		root = (JSONObject) new JSONTokener(model).nextValue();
		VertexStruct vertexStruct = parseModel();
		Mesh mesh = new Mesh(vertexStruct);

		Texture texture = loadTexture();
		
		String vertexShader = ReadFile.read(vertexShaderStream);
		String fragmentShader = ReadFile.read(fragmentShaderStream);
		ShaderProgram program = new ShaderProgram(vertexShader, fragmentShader);
		
		return new Model(mesh, texture, program);
	}
	
	/**
	 * Loads an animated model
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public AnimatedModel loadAnimatedModel() throws JSONException, IOException {
		String model = ReadFile.read(modelStream);
		root = (JSONObject) new JSONTokener(model).nextValue();
		VertexStruct vertexStruct = parseAnimatedModel();
		SkinnedMesh mesh = new SkinnedMesh(vertexStruct);
			
		HashMap<String, Action> actions = loadActions();

		Texture texture = loadTexture();

		String vertexShader = ReadFile.read(vertexShaderStream);
		String fragmentShader = ReadFile.read(fragmentShaderStream);
		ShaderProgram program = new ShaderProgram(vertexShader, fragmentShader);
		
		return new AnimatedModel(mesh, texture, program, actions);
	}
	
	private VertexStruct parseModel() throws JSONException {
		VertexStruct vertexStruct = new VertexStruct();
		vertexStruct.numVertices = loadNumVertices();
		vertexStruct.vertices = loadVertices();
		vertexStruct.normals = loadNormals();
		vertexStruct.textures = loadTextures();
		return vertexStruct;
	}
	
	private VertexStruct parseAnimatedModel() throws JSONException {
		VertexStruct vertexStruct = parseModel();
		vertexStruct.isSkinned = true;
		vertexStruct.weights = loadWeights();
		vertexStruct.boneIndices = loadBoneIndices();
		return vertexStruct;
	}
	
	private Texture loadTexture() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		Bitmap textureBitmap = BitmapFactory.decodeStream(textureStream);
		return new Texture(textureBitmap);
	}
	
	
	private HashMap<String, Action> loadActions() throws JSONException {
		HashMap<String, Action> actions = new HashMap<String, Action>();
		JSONObject actionsObj = root.getJSONObject("Actions");
		@SuppressWarnings("unchecked")
		Iterator<String> it = actionsObj.keys();
		while (it.hasNext()) {
			String name = it.next();
			JSONArray framesArray = actionsObj.getJSONArray(name);
			//Frame[] frames = loadFrames(framesArray);
			FrameList frames = loadFrames(framesArray);
			actions.put(name, new Action(name, frames));
		}
		return actions;
	}
	
	private FrameList loadFrames(JSONArray framesArray) throws JSONException {
		int numkeyframes = framesArray.length();
		//Frame[] frames = new Frame[numkeyframes];
		FrameList frames = new FrameList();
		for (int i = 0; i < numkeyframes; i++) {
			JSONObject frame = framesArray.getJSONObject(i);
			JSONArray bonesArray = frame.getJSONArray("bones");
			//Puede que haya que cambiarlo e inicializar una matriz tocha
			int numbones = bonesArray.length();
			float[] bones = new float[numbones];
			for (int j = 0; j < numbones; j++) {
				bones[j] = (float) bonesArray.getDouble(j);
			}
			frames.add(new Frame(frame.getInt("num"), new Matrix4fGroup(bones)));
		}
		return frames;
	}
	

	private int loadNumVertices() throws JSONException {
		JSONObject vertObj = root.getJSONObject("Vertices");
		return vertObj.getInt("num");
	}
	
	
	private float[] loadVertices() throws JSONException {
		JSONObject vertObj = root.getJSONObject("Vertices");
		int n = vertObj.getInt("num")*Constants.VERTEX_COORDINATES;
		float[] v = new float[n];
		JSONArray values = vertObj.getJSONArray("co");
		for (int i = 0; i < n; i++) {
			v[i] = (float) values.getDouble(i);
		}
		return v;
	}
	
	private float[] loadNormals() throws JSONException {
		JSONObject normObj = root.getJSONObject("Normals");
		int n = normObj.getInt("num")*Constants.NORMAL_COORDINATES;
		float[] v = new float[n];
		JSONArray values = normObj.getJSONArray("co");
		for (int i = 0; i < n; i++) {
			v[i] = (float) values.getDouble(i);
		}
		return v;
	}
	
	private float[] loadTextures() throws JSONException {
		JSONObject texObj = root.getJSONObject("Textures");
		int n = texObj.getInt("num") * Constants.TEXTURE_COORDINATES;
		float[] v = new float[n];
		JSONArray values = texObj.getJSONArray("co");
		for (int i = 0; i < n; i++) {
			v[i] = (float) values.getDouble(i);
		}
		return v;
	}
	
	private float[] loadWeights() throws JSONException {
		JSONObject armatureObj = root.getJSONObject("Armature");
		int n = armatureObj.getInt("num") * Constants.NUM_BONES;
		float[] v = new float[n];
		JSONArray values = armatureObj.getJSONArray("weights");
		for (int i = 0; i < n; i++) {
			v[i] = (float) values.getDouble(i);
		}
		return v;
		
	}
	
	private float[] loadBoneIndices() throws JSONException {
		JSONObject armatureObj = root.getJSONObject("Armature");
		int n = armatureObj.getInt("num") * Constants.NUM_BONES;
		float[] v = new float[n];
		JSONArray values = armatureObj.getJSONArray("indices");
		for (int i = 0; i < n; i++) {
			v[i] = (float) values.getDouble(i);
		}
		return v;
	}
}
