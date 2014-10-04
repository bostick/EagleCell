#ifndef JNIAPI_H
#define JNIAPI_H

extern "C" {
    JNIEXPORT void JNICALL Java_com_brentonbostick_eaglecell_MainActivity_nativeOnStart(JNIEnv* jenv, jobject obj);
    JNIEXPORT void JNICALL Java_com_brentonbostick_eaglecell_MainActivity_nativeOnResume(JNIEnv* jenv, jobject obj);
    JNIEXPORT void JNICALL Java_com_brentonbostick_eaglecell_MainActivity_nativeOnPause(JNIEnv* jenv, jobject obj);
    JNIEXPORT void JNICALL Java_com_brentonbostick_eaglecell_MainActivity_nativeOnStop(JNIEnv* jenv, jobject obj);
    JNIEXPORT void JNICALL Java_com_brentonbostick_eaglecell_MainActivity_nativeSetSurface(JNIEnv* jenv, jobject obj, jobject surface);
};

#endif // JNIAPI_H
