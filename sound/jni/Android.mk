LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := soundtouch

LOCAL_SRC_FILES := \
	SoundTouch/AAFilter.cpp \
	SoundTouch/BPMDetect.cpp \
	SoundTouch/FIFOSampleBuffer.cpp \
	SoundTouch/FIRFilter.cpp \
	SoundTouch/PeakFinder.cpp \
	SoundTouch/RateTransposer.cpp \
	SoundTouch/SoundTouch.cpp \
	SoundTouch/TDStretch.cpp \
	SoundTouch/cpu_detect_x86.cpp \
	SoundTouch/mmx_optimized.cpp \
	SoundTouch/sse_optimized.cpp \

include $(BUILD_SHARED_LIBRARY)
