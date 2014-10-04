
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := eaglecell
LOCAL_CFLAGS    := -Wall
LOCAL_SRC_FILES := renderer.cpp jniapi.cpp
LOCAL_LDLIBS    := -lGLESv2 -llog -lEGL -landroid -lGLESv1_CM

include $(BUILD_SHARED_LIBRARY)
