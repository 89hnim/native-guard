package com.example.native_guard

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