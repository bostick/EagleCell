precision mediump float;

uniform sampler2D uTexture;

varying vec2 vTextureCoord;

void main() {
	vec4 C = texture2D(uTexture, vTextureCoord );
	vec4 E = texture2D(uTexture, vec2(vTextureCoord.x + 1.0/%1$d.0, vTextureCoord.y) );
	vec4 N = texture2D(uTexture, vec2(vTextureCoord.x, vTextureCoord.y + 1.0/%2$d.0) );
	vec4 W = texture2D(uTexture, vec2(vTextureCoord.x - 1.0/%1$d.0, vTextureCoord.y) );
	vec4 S = texture2D(uTexture, vec2(vTextureCoord.x, vTextureCoord.y - 1.0/%2$d.0) );
	vec4 NE = texture2D(uTexture, vec2(vTextureCoord.x + 1.0/%1$d.0, vTextureCoord.y + 1.0/%2$d.0) );
	vec4 NW = texture2D(uTexture, vec2(vTextureCoord.x - 1.0/%1$d.0, vTextureCoord.y + 1.0/%2$d.0) );
	vec4 SE = texture2D(uTexture, vec2(vTextureCoord.x + 1.0/%1$d.0, vTextureCoord.y - 1.0/%2$d.0) );
	vec4 SW = texture2D(uTexture, vec2(vTextureCoord.x - 1.0/%1$d.0, vTextureCoord.y - 1.0/%2$d.0) );
	float centerTotal = C.r + C.g + C.b + C.a;
	float NTotal = N.b + N.a;
	float WTotal = W.g + W.a;
	float ETotal = E.r + E.b;
	float STotal = S.r + S.g;
	float rcount = centerTotal-C.r;
	rcount += NW.a;
	rcount += NTotal;
	rcount += WTotal;
	float rval = step(7.4, rcount * (5.5 - rcount) + C.r);
	float gcount = centerTotal-C.g;
	gcount += NTotal;
	gcount += NE.b;
	gcount += ETotal;
	float gval = step(7.4, gcount * (5.5 - gcount) + C.g);
	float bcount = centerTotal-C.b;
	bcount += WTotal;
	bcount += SW.g;
	bcount += STotal;
	float bval = step(7.4, bcount * (5.5 - bcount) + C.b);
	float acount = centerTotal-C.a;
	acount += ETotal;
	acount += STotal;
	acount += SE.r;
	float aval = step(7.4, acount * (5.5 - acount) + C.a);
	gl_FragColor = vec4(rval, gval, bval, aval);
}
