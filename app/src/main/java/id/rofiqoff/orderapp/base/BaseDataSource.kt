package id.rofiqoff.orderapp.base

import id.rofiqoff.orderapp.data.remote.Result
import retrofit2.Response

abstract class BaseDataSource {
    @Suppress("UNREACHABLE_CODE")
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }

            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            e.printStackTrace()
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        return Result.error("Api call failed: $message")
    }

}