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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Aarón Negrín on 21/05/13.
 */
public abstract class ModelNode implements Comparable<ModelNode> {
    private ModelNode parent;
    private ArrayList<ModelNode> children;
    private int depth;

    public ModelNode() {
        children = new ArrayList<ModelNode>();
        setParent(null);
    }

    public ModelNode(ModelNode parent) {
        children = new ArrayList<ModelNode>();
        setParent(parent);
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public int compareTo(ModelNode modelNode) {
        if (this.depth > modelNode.getDepth()) {
            return 1;
        } else {
            return this.depth < modelNode.getDepth() ? -1 : 0;
        }
    }

    public void setParent(ModelNode parent) {
        if (this.parent != null) {
            this.parent.removeChild(this);
        }
        if (parent == null) {
            this.parent = null;
            depth = 0;
        } else {
            this.parent = parent;
            depth = this.parent.getDepth()+1;
            this.parent.addChild(this);
        }
    }

    public ModelNode getParent() {
        return parent;
    }

    public boolean addChild(ModelNode r) {
        return children.add(r);
    }

    public boolean removeChild(ModelNode node) {
        return children.remove(node);
    }

    public Collection<ModelNode> getChildren() {
        return children;
    }
}
