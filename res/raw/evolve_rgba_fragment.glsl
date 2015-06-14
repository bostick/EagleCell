precision lowp float;

uniform sampler2D uTexture;

varying vec2 vTextureCoord;

float evolve1(float count, float center) {
	if (count == 3.0 || (count == 2.0 && center == 1.0)) {
		return 1.0;
	} else {
		return 0.0;
	}
}

float evolve2(float count, float center) {
	return step(3.4, -(count*count) + 5.5*count - 4.0 + center);
}

float evolve3(float count, float center) {
	return step(0.97, sin(0.6 * count) + 0.1 * center);
}

float evolve4(float count, float center) {
	return step(7.4, count * (5.5 - count) + center);
}

void main() {
	vec4 C = texture2D(uTexture, vTextureCoord );
	vec4 E = texture2D(uTexture, vec2(vTextureCoord.x + %1$f, vTextureCoord.y) );
	vec4 N = texture2D(uTexture, vec2(vTextureCoord.x, vTextureCoord.y + %2$f) );
	vec4 W = texture2D(uTexture, vec2(vTextureCoord.x - %1$f, vTextureCoord.y) );
	vec4 S = texture2D(uTexture, vec2(vTextureCoord.x, vTextureCoord.y - %2$f) );
	vec4 NE = texture2D(uTexture, vec2(vTextureCoord.x + %1$f, vTextureCoord.y + %2$f) );
	vec4 NW = texture2D(uTexture, vec2(vTextureCoord.x - %1$f, vTextureCoord.y + %2$f) );
	vec4 SE = texture2D(uTexture, vec2(vTextureCoord.x + %1$f, vTextureCoord.y - %2$f) );
	vec4 SW = texture2D(uTexture, vec2(vTextureCoord.x - %1$f, vTextureCoord.y - %2$f) );
	float centerTotal = C.r + C.g + C.b + C.a;
	float NTotal = N.b + N.a;
	float WTotal = W.g + W.a;
	float ETotal = E.r + E.b;
	float STotal = S.r + S.g;

	float rcount = centerTotal-C.r;
	rcount += NW.a;
	rcount += NTotal;
	rcount += WTotal;
	float rval = evolve4(rcount, C.r);

	float gcount = centerTotal-C.g;
	gcount += NTotal;
	gcount += NE.b;
	gcount += ETotal;
	float gval = evolve4(gcount, C.g);

	float bcount = centerTotal-C.b;
	bcount += WTotal;
	bcount += SW.g;
	bcount += STotal;
	float bval = evolve4(bcount, C.b);

	float acount = centerTotal-C.a;
	acount += ETotal;
	acount += STotal;
	acount += SE.r;
	float aval = evolve4(acount, C.a);

	gl_FragColor = vec4(rval, gval, bval, aval);
}
