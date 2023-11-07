#include <jni.h>
#include <string>
#include <map>

const char *API_KEY = "THIS_IS_API_KEY";
std::map<std::string, std::string> map;

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_native_1guard_NativeLib_getApiKey(
        JNIEnv *env,
        jobject /* this */) {
    return env->NewStringUTF(API_KEY);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_native_1guard_NativeLib_getKey(
        JNIEnv *env,
        jobject,
        jstring key
) {
    jboolean isCopy;
    const char *convertedValue = (env)->GetStringUTFChars(key, &isCopy);
    return env->NewStringUTF(map[convertedValue].c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_native_1guard_NativeLib_saveKey(
        JNIEnv *env,
        jobject,
        jstring key,
        jstring value
) {
    jboolean isCopy;
    const char *convertedValue = (env)->GetStringUTFChars(value, &isCopy);
    const char *convertedKey = (env)->GetStringUTFChars(key, &isCopy);
    map[convertedKey] = convertedValue;
}