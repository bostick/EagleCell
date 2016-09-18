
LOCAL_PATH := $(call my-dir)
JNI_SRC_PATH := $(LOCAL_PATH)/../../EagleCell/app/src/main/cpp
include $(CLEAR_VARS)

LOCAL_MODULE    := eaglecell
LOCAL_CFLAGS    := -Wall
LOCAL_SRC_FILES := $(JNI_SRC_PATH)/renderer.cpp $(JNI_SRC_PATH)/jniapi.cpp
LOCAL_LDLIBS    := -lGLESv2 -llog -lEGL -landroid -lGLESv1_CM

include $(BUILD_SHARED_LIBRARY)
