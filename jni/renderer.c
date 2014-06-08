
#include <jni.h>
#include <GLES2/gl2.h>

int stage = 0;

void
Java_com_brentonbostick_eaglecell_Renderer_nativeDrawFrame( JNIEnv* env,
                                                  jobject thiz, jint jFbB, jint jEvolveShaderProgram,
                                                  jint jTextureA, jint jDirectShaderProgram,
                                                  jint jFbA, jint jTextureB)
{

	GLuint fbB = jFbB;
	GLuint evolveShaderProgram = jEvolveShaderProgram;
	GLuint textureA = jTextureA;
	GLuint directShaderProgram = jDirectShaderProgram;
	GLuint fbA = jFbA;
	GLuint textureB = jTextureB;

	if (stage == 0) {

		glBindFramebuffer (GL_FRAMEBUFFER, fbB);

		glUseProgram(evolveShaderProgram);

		glBindTexture(GL_TEXTURE_2D, textureA);

        glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_BYTE, 0);

		glBindFramebuffer(GL_FRAMEBUFFER, 0);

		glUseProgram(directShaderProgram);
        glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_BYTE, 0);

		stage = 1;
	} else {

		glBindFramebuffer(GL_FRAMEBUFFER, fbA);

		glUseProgram(evolveShaderProgram);

		glBindTexture(GL_TEXTURE_2D, textureB);

		glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_BYTE, 0);

		glBindFramebuffer(GL_FRAMEBUFFER, 0);

		glUseProgram(directShaderProgram);
		glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_BYTE, 0);

		stage = 0;
	}

}
