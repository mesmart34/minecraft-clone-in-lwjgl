#version 440

in vec3 fragment_uv;

out vec4 out_color;

uniform samplerCube cubemapTexture;

void main()
{
	out_color = texture(cubemapTexture, fragment_uv);
}
