package id.rofiqoff.orderapp.data.remote

data class ResponseItem(
    val is_halal: String?,
    val location_name: String?,
    val title: String?,
    val price: String?,
    val user: User?,
    val default_photo: DefaultPhoto?
) {
    data class DefaultPhoto(
        val img_path: String?
    )

    data class User(
        val user_name: String?
    )
}