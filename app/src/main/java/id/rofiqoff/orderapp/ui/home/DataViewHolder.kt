package id.rofiqoff.orderapp.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.rofiqoff.orderapp.data.remote.ResponseItem
import id.rofiqoff.orderapp.databinding.ItemContentBinding
import id.rofiqoff.orderapp.utils.AppHelper
import id.rofiqoff.orderapp.utils.gone
import id.rofiqoff.orderapp.utils.setFormatRupiah
import id.rofiqoff.orderapp.utils.visible

class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun onBind(binding: ItemContentBinding, data: ResponseItem) {
        val urlImage = AppHelper.BASE_URL_IMAGE + data.default_photo?.img_path

        with(binding) {
            Glide.with(binding.root.context)
                .load(urlImage)
                .into(image)

            title.text = data.title
            price.text = if (data.price != "0") setFormatRupiah(data.price) else "Gratis"
            location.text = data.location_name
            username.text = data.user?.user_name

            logoHalal.apply {
                if (data.is_halal == "1") visible() else gone()
            }
        }
    }

}