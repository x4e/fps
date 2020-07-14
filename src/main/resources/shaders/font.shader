#shader vert
#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;

out vec2 outTexCoord;

uniform mat4 projection;

void main() {
	gl_Position = projection * vec4(position, 1.0);
	outTexCoord = texCoord;
}

#shader frag
#version 330

in  vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform vec4 colour;

void main() {
	fragColor = colour;
	fragColor.a *= texture(texture_sampler, outTexCoord).a;
}
