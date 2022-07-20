package com.mustfaibra.instagraph.sealed

sealed class DataResponse<T>(
    var data: T? = null,
    var error: com.mustfaibra.instagraph.sealed.Error? = null,
) {
    class Success<T>(data: T) : DataResponse<T>(data = data)
    class Error<T>(error: com.mustfaibra.instagraph.sealed.Error) : DataResponse<T>(error = error)
}