precision highp float;

uniform sampler2D uTexture;

varying vec2 vTextureCoord;

void main() {
	vec4 res = texture2D(uTexture, vTextureCoord);
	gl_FragColor = vec4(res.rgb, 1.0);
}
