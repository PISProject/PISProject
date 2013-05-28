#define MAX_BONES 15
#define NUM_BONES 4
uniform mat4 bones[MAX_BONES];

uniform mat4 mvpMatrix;
uniform mat4 mvMatrix;

attribute vec3 position;
attribute vec3 normal;
attribute vec2 textureCoords;
attribute vec4 weights;
attribute vec4 bonesIndices;

varying vec3 vPosition;
varying vec3 vNormal; 
varying vec2 vTextureCoords;

vec3 applybones() {
	vec3 finalPos = vec3(0);	
	
	int index;
	mat4 bone;
	vec4 pos = vec4(position,1);
	
	index = int(bonesIndices.x);
	bone = bones[index];
	finalPos += (weights.x * bone * pos).xyz;
	index = int(bonesIndices.y);
	bone = bones[index];
	finalPos += (weights.y * bone * pos).xyz;
	index = int(bonesIndices.z);
	bone = bones[index];
	finalPos += (weights.z * bone * pos).xyz;
	index = int(bonesIndices.w);
	bone = bones[index];
	finalPos += (weights.w * bone * pos).xyz;
	
	return finalPos;
}


vec4 applyBones2() {
	vec4 indices = bonesIndices;
	vec4 w = weights;
	mat4 mat = bones[int(indices.x)] * w.x;
	
	for (int i = 1; i < NUM_BONES; i++) {
		indices = indices.yzwx;
		w = w.yzwx;
		if (w.x > 0.0) {
			mat = mat + bones[int(indices.x)] * w.x;
		}
	}
	return mat * vec4(position,1);
}

	
void main() {
	//vec3 finalPos = applybones();
	vec3 finalPos = applyBones2().xyz;
	
	vPosition = vec3(mvMatrix * vec4(finalPos,1));

    vNormal = vec3(mvMatrix * vec4(normal, 0.0));
    
    vTextureCoords = textureCoords;

	gl_Position = mvpMatrix * vec4(finalPos ,1);
}