uniform mat4 mvpMatrix;
uniform mat4 mvMatrix;

attribute vec3 position;
attribute vec3 normal;
attribute vec2 textureCoords;

varying vec3 vPosition;
varying vec3 vNormal; 
varying vec2 vTextureCoords;

void main() {
    vPosition = vec3(mvMatrix * vec4(position,1));

    vNormal = vec3(mvMatrix * vec4(normal, 0.0));

	vTextureCoords = textureCoords;
	
	gl_Position = mvpMatrix * vec4(position,1);
}