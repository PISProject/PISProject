precision mediump float;

uniform sampler2D texture; 

varying vec2 vTextureCoords;
varying vec3 vPosition;
varying vec3 vNormal;

void main() {
	vec3 lightPos = vec3(0,10,0);
    float distance = length(lightPos - vPosition);
    vec3 lightVector = normalize(lightPos - vPosition);
 
    // Calculate the dot product of the light vector and vertex normal. If the normal and light vector are
    // pointing in the same direction then it will get max illumination.
    float diffuse = max(dot(normalize(vNormal), lightVector), 0.1);
 
 	diffuse = diffuse * 1.0/1.0;
 	//Comentario
    // Add attenuation.
    //diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));
    
 
    // Multiply the color by the diffuse illumination level to get final output color.
    //gl_FragColor = v_Color * diffuse;

	gl_FragColor = diffuse * texture2D(texture, vTextureCoords);
}