#include <jni.h>
#include <string>
#include <android/log.h>
#include <opencv2/opencv.hpp>

#define TAG "NativeLib"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_edgedetectionapp_NativeProcessor_testOpenCV(
        JNIEnv* env,
        jobject /* this */) {

    std::string version = cv::getVersionString();
    LOGD("OpenCV Version: %s", version.c_str());

    return env->NewStringUTF(version.c_str());
}