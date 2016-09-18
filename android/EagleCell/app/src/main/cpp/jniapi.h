#ifndef JNIAPI_H
#define JNIAPI_H

extern "C" {
    JNIEXPORT void JNICALL Java_com_brentonbostick_eaglecell_MainNativeActivity_nativeOnStart(JNIEnv* jenv, jobject obj);
    JNIEXPORT void JNICALL Java_com_brentonbostick_eaglecell_MainNativeActivity_nativeOnResume(JNIEnv* jenv, jobject obj);
    JNIEXPORT void JNICALL Java_com_brentonbostick_eaglecell_MainNativeActivity_nativeOnPause(JNIEnv* jenv, jobject obj);
    JNIEXPORT void JNICALL Java_com_brentonbostick_eaglecell_MainNativeActivity_nativeOnStop(JNIEnv* jenv, jobject obj);
    JNIEXPORT void JNICALL Java_com_brentonbostick_eaglecell_MainNativeActivity_nativeSetSurface(JNIEnv* jenv, jobject obj, jobject surface);
};

#endif // JNIAPI_H
