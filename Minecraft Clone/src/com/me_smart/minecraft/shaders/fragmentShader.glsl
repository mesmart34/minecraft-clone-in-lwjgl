#version 440 core

in vec2 fragment_uv;

out vec4 out_color;

uniform sampler2D atlas;
in vec3 lightColorFragment;
in float lightIntensityFragment;

in vec3 toLightVector;
in vec3 surfaceNormal;


void main()
{
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVector);
	float nDot1 = dot(unitNormal, unitLightVector);
	float brightness = max(nDot1, 0.0);
	vec3 diffuse = brightness * lightColorFragment * lightIntensityFragment;
	out_color = vec4(diffuse, 1) * texture2D(atlas, fragment_uv);
}
