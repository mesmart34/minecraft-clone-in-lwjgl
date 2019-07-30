#version 440

layout (location = 0) in vec3 position;

out vec3 fragment_uv;

uniform mat4 projection_matrix;
uniform mat4 view_matrix;
uniform vec3 camera_position;

void main()
{
	vec4 final_position = projection_matrix * view_matrix * vec4(position + camera_position, 1.0f);
	fragment_uv = position;
	gl_Position = final_position;
}
