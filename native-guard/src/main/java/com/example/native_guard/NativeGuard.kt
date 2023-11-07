package com.example.native_guard

/**
 * @author minhta
 * @since 07/11/2023
 */
object NativeGuard {

    private val nativeLib = NativeLib()

    fun save(key: String, value: String) {
        nativeLib.saveKey(key, value)
    }

    fun get(key: String): String {
        return nativeLib.getKey(key)
    }

    fun getApiKey(): String {
        return nativeLib.getApiKey()
    }

}