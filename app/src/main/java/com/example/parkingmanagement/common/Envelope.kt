package com.example.parkingmanagement.common


sealed class Envelope<T>(val data: T? = null, val message: String = "") {
    class Success<T>(data: T) : Envelope<T>(data)
    class Failure<T>(error: String) : Envelope<T>(message = error)
    class Loading<T>() : Envelope<T>()
}


