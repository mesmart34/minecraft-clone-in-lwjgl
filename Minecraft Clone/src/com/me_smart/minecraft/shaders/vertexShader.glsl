#version 440 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normals;
layout (location = 2) in vec2 uv;

out vec2 fragment_uv;

uniform mat4 projection_matrix;
uniform mat4 transform_matrix;
uniform mat4 view_matrix;
uniform vec3 lightPosition;
uniform vec3 lightColor;
uniform float lightIntensity;

out vec3 lightColorFragment;
out vec3 toLightVector;
out vec3 surfaceNormal;
out float lightIntensityFragment;

void main()
{
	vec4 worldPosition = transform_matrix * vec4(position, 1.0);
	fragment_uv = uv;
	gl_Position = projection_matrix * view_matrix * worldPosition;
	surfaceNormal = (transform_matrix * vec4(normals, 0.0)).xyz;
	toLightVector = lightPosition - worldPosition.xyz;
	lightColorFragment = lightColor;
	lightIntensityFragment = lightIntensity;
}
