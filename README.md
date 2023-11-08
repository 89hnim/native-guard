# Securing your data

## Features
- Save/Get key with native function
- Protect your sensitive data (like api key)

## Usage

Save value:
```kotlin
NativeGuard.save(key, value)
```

Get value:
```kotlin
NativeGuard.get(key)
```

To save your api key, you need to use a little bit C<br>
Go to main > cpp > native_guard.cpp > Update your API_KEY
```C
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_native_1guard_NativeLib_getApiKey(
        JNIEnv *env,
        jobject /* this */) {
    return env->NewStringUTF(API_KEY);
}
```

## Under the hood
This library is built according to a simple mechanism. I will show you how:<br>
First we need create a native module: New module -> Android Native Module<br>
Android Studio will do the rest for you<br>
After that, you will see a cpp folder contains CMakeLists which is a configuration file and 
a class .cpp is where your write native code.<br>
Before go further to the .cpp class. we need to write some kotlin code.
```kotlin
class NativeLib {

    /**
     * A native method that is implemented by the 'native_guard' native library,
     * which is packaged with this application.
     */
    external fun getApiKey(): String

    external fun getKey(key: String): String

    external fun saveKey(key: String, value: String)

    companion object {
        // Used to load the 'native_guard' library on application startup.
        init {
            System.loadLibrary("native_guard")
        }
    }
}
```
We need call `System.loadLibrary("native_guard")` so it will load our native library.
And the external functions will call to cpp file.
The mechanism is very simple. Using a map to save data user passed in and when user need it, get it.
<br>Let's take a look at `native_guard.cpp` files
```C
// declare a map. This map use to save and get data passed in from kotlin class
std::map<std::string, std::string> map;

// jstring: this method return a string
// Java_com_example_nativeL_NativeLib: package and class will call to this function
// getKey: function's name in kotlin class
// jstring key: the parameter pass from kotlin class to this function
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_native_1guard_NativeLib_getKey(
        JNIEnv *env,
        jobject,
        jstring key
) {
    jboolean isCopy;
    // get value of jstring key
    const char *convertedValue = (env)->GetStringUTFChars(key, &isCopy);
    // get value from map
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
    // get value of jstring value
    const char *convertedValue = (env)->GetStringUTFChars(value, &isCopy);
    // get value of jstring key
    const char *convertedKey = (env)->GetStringUTFChars(key, &isCopy);
     // save value to map
    map[convertedKey] = convertedValue;
}
```

The sensitive data we don't want to hardcode or information in kotlin class/strings... so
we can't not pass it to cpp by using native function -> We need to edit it directly in cpp file 
like I shown above.