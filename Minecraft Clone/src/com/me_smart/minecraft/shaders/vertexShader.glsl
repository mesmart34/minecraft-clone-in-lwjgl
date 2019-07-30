#version 440 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 uv;

out vec2 fragment_uv;

uniform mat4 projection_matrix;
uniform mat4 transform_matrix;
uniform mat4 view_matrix;

void main()
{
	fragment_uv = uv;
	gl_Position = projection_matrix * view_matrix * transform_matrix * vec4(position, 1.0f);
}
