#version 440 core

in vec2 fragment_uv;

out vec4 out_color;

uniform sampler2D atlas;

void main()
{
	out_color = texture2D(atlas, fragment_uv);
}
