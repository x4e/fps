#shader vert
#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 normal;

out vec2 outTexCoord;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;

void main() {
	gl_Position = projectionMatrix * worldMatrix * vec4(position, 1.0);
	outTexCoord = texCoord;
}

#shader frag
#version 330

in  vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform vec3 color;
uniform int useColor;

void main() {
	if (useColor == 1) {
		fragColor = vec4(color, 1);
	} else {
		fragColor = texture(texture_sampler, outTexCoord);
	}
}
