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

import com.ub.pis.renderer.opengl.Mesh;
import com.ub.pis.renderer.opengl.ShaderProgram;
import com.ub.pis.renderer.opengl.Texture;

public abstract class ModelBase extends ModelNode implements IRendereable{
	
	protected ShaderProgram program;
	protected Mesh mesh;
	protected Texture texture;
	protected int textureHandle;
	protected boolean initialized;
	
	public ModelBase(Mesh mesh, Texture texture, ShaderProgram program) {
        super();
		this.mesh = mesh;
		this.program = program;
		this.texture = texture;
		initialized = false;
	}

    public ModelBase(ModelNode parent, Mesh mesh, Texture texture, ShaderProgram program) {
        super(parent);
        this.mesh = mesh;
        this.program = program;
        this.texture = texture;
        initialized = false;
    }

    public boolean isInitialized() {
        return initialized;
    }

	public void initialize() {
		if (initialized) return;
		this.mesh.initialize();
		this.program.initialize();
		this.texture.initialize();
		getShaderLocations();
		initialized = true;
	}


	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
		if (initialized) {
			this.texture.initialize();
		}
	}
	
	public ShaderProgram getShaderProgram() {
		return program;
	}
	
	public void setShaderProgram(ShaderProgram program) {
		this.program = program;
	}

	
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}


	protected void getShaderLocations() {
		textureHandle = program.getUniformLocation("texture");
		mesh.setPositionLocation(program.getAttribLocation("position"));
		mesh.setNormalLocation(program.getAttribLocation("normal"));
		mesh.setTextureLocation(program.getAttribLocation("textureCoords"));
	}
	
	@Override
	public void bind() {
		this.program.useProgram();
		texture.bind();
		mesh.bind();
	}
	
	@Override
	public void unbind() {
		texture.unbind();
		this.program.unbindProgram();
		mesh.unbind();
	}
	
	@Override
	public void draw() {
		mesh.draw();
	}
}
