package com.egoriku.radiotok.extensions

inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}