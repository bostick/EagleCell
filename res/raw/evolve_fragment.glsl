precision highp float;

uniform sampler2D uTexture;

varying vec2 vTextureCoord;

void main() {
	vec4 C = texture2D(uTexture, vTextureCoord);
	vec4 E = texture2D(uTexture, vec2(vTextureCoord.x + 1.0/%1$d.0, vTextureCoord.y));
	vec4 N = texture2D(uTexture, vec2(vTextureCoord.x, vTextureCoord.y + 1.0/%2$d.0));
	vec4 W = texture2D(uTexture, vec2(vTextureCoord.x - 1.0/%1$d.0, vTextureCoord.y));
	vec4 S = texture2D(uTexture, vec2(vTextureCoord.x, vTextureCoord.y - 1.0/%2$d.0));
	vec4 NE = texture2D(uTexture, vec2(vTextureCoord.x + 1.0/%1$d.0, vTextureCoord.y + 1.0/%2$d.0));
	vec4 NW = texture2D(uTexture, vec2(vTextureCoord.x - 1.0/%1$d.0, vTextureCoord.y + 1.0/%2$d.0));
	vec4 SE = texture2D(uTexture, vec2(vTextureCoord.x + 1.0/%1$d.0, vTextureCoord.y - 1.0/%2$d.0));
	vec4 SW = texture2D(uTexture, vec2(vTextureCoord.x - 1.0/%1$d.0, vTextureCoord.y - 1.0/%2$d.0));
	float count = E.r;
	count += N.r;
	count += W.r;
	count += S.r;
	count += NE.r;
	count += NW.r;
	count += SE.r;
	count += SW.r;
//"if (count == 3.0 || (count == 2.0 && C.r == 1.0)) {" +
//"gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);" +
//"} else {" +
//"gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);" +
//"}" +
//"float val = step(3.4, -(count*count) + 5.5*count - 4.0 + C.r);" +
//"float val = step(0.97, sin(0.6 * count) + 0.1 * C.r);" + 
	float val = step(7.4, count * (5.5 - count) + C.r);
	gl_FragColor = vec4(val, val, val, 1.0);
}
