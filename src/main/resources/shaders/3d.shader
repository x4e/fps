#shader vert
#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;

out vec2 outTexCoord;

uniform mat4 worldMatrix;
uniform mat4 projectionMatrix;

void main() {
	gl_Position = projectionMatrix * worldMatrix * vec4(position, 1.0);
	outTexCoord = texCoord;
}

#shader frag
#version 330

in  vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D texture_sampler;

void main() {
	fragColor = texture(texture_sampler, outTexCoord);
}

