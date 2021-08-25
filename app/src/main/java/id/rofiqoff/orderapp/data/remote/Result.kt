package id.rofiqoff.orderapp.data.remote

data class Result<out T>(val status: Status, val data: T? = null, val message: String? = "", val isLoading: Boolean = true) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data)
        }

        fun <T> error(message: String?, data: T? = null): Result<T> {
            return Result(Status.ERROR, data, message)
        }

        fun <T> loading(isLoading: Boolean) : Result<T> {
            return Result(Status.LOADING, null, null, isLoading)
        }
    }

}