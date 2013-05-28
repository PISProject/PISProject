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

package com.ub.pis.renderer.primitives;

import android.opengl.GLES20;
import com.ub.pis.renderer.opengl.Texture;
import com.ub.pis.renderer.scene.Camera;


/**
 * Created by Aarón Negrín on 24/05/13.
 */
public class Sprite extends Quad {

        public Sprite(Texture tex) {
            super(3, 1, tex);
        }
        
        public Sprite(float w, float h, Texture tex) {
            super(w, h, tex);
        }


        @Override
        public void bind() {
            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glDisable(GLES20.GL_DEPTH_TEST);
            super.bind();
        }

        @Override
        public void update(Camera camera) {
            this.setRotation(45,0,180);
            super.update(camera);
        }

        @Override
        public void unbind() {
            GLES20.glDisable(GLES20.GL_BLEND);
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            super.unbind();
        }
    }
