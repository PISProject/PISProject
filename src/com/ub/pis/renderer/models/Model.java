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

package com.ub.pis.renderer.models;

import com.ub.pis.renderer.linearalgebra.Matrix4f;
import com.ub.pis.renderer.linearalgebra.Vector3f;
import com.ub.pis.renderer.opengl.Mesh;
import com.ub.pis.renderer.opengl.ShaderProgram;
import com.ub.pis.renderer.opengl.Texture;
import com.ub.pis.renderer.scene.Camera;

/*
 * TODO: Hacer que se pueda rotar y trasladar independientemente?
 */
public class Model extends ModelBase implements IPositionable{

	protected final Matrix4f mvpMatrix = new Matrix4f();
	protected final Matrix4f mvMatrix = new Matrix4f();
	
	protected final Matrix4f modelMatrix = new Matrix4f();
	protected Vector3f position;
	
	protected int mvpMatrixHandle;
	protected int mvMatrixHandle;
	
	protected float rotx;
	protected float roty;
	protected float rotz;


    public Model(ModelNode parent, Mesh mesh, Texture texture, ShaderProgram program) {
        super(parent, mesh, texture, program);
        position = new Vector3f();
        modelMatrix.setIdentity();
        rotx = 0;
        roty = 0;
        rotz = 0;
    }

	public Model(Mesh mesh, Texture texture, ShaderProgram program) {
		super(mesh, texture, program);
		position = new Vector3f();
		modelMatrix.setIdentity();
        rotx = 0;
        roty = 0;
        rotz = 0;
    }
	
	@Override
	protected void getShaderLocations() {
		super.getShaderLocations();
		mvpMatrixHandle = program.getUniformLocation("mvpMatrix");
		mvMatrixHandle = program.getUniformLocation("mvMatrix");
	}
	
	@Override
	public void setRotX(float a) {
		rotx = a;
	}


	@Override
	public void setRotY(float a) {
		roty = a;
	}


	@Override
	public void setRotZ(float a) {
		rotz = a;
	}
	
	@Override
	public void setRotation(float rotx, float roty, float rotz) {
		this.rotx = rotx;
		this.roty = roty;
		this.rotz = rotz;
	}

	@Override
	public void setTranslation(float x, float y, float z) {
		position.setValues(x, y, z);
	}

	@Override
	public Vector3f getPosition() {
		return position;
	}
	
	@Override
	public Vector3f getRotation() {
		return new Vector3f(rotx,roty,rotz);
	}
	
	@Override
	public void update(Camera camera) {
		modelMatrix.setIdentity();
		modelMatrix.translate(position);
		modelMatrix.rotate(rotx, roty, rotz);
		camera.getViewProjectionMatrix().multiplyRight(modelMatrix, mvpMatrix);
		camera.getViewMatrix().multiplyRight(modelMatrix, mvMatrix);
	}
	
	@Override
	public void bind() {
		super.bind();
		program.setUniformValue(mvMatrixHandle, mvMatrix);
		program.setUniformValue(mvpMatrixHandle, mvpMatrix);
	}

}
