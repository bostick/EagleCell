
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := eaglecell
LOCAL_CFLAGS    := -Wall
LOCAL_SRC_FILES := renderer.c
LOCAL_LDLIBS    := -lGLESv2

include $(BUILD_SHARED_LIBRARY)
