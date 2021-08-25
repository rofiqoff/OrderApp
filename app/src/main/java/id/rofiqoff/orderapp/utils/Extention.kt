package id.rofiqoff.orderapp.utils

import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import id.rofiqoff.orderapp.R
import id.rofiqoff.orderapp.data.remote.Result
import id.rofiqoff.orderapp.viewmodel.ViewModelFactory
import java.text.NumberFormat
import java.util.*

fun <T> resultLiveData(
    dataResult: suspend () -> Result<List<T>>
): LiveData<Result<List<T>>> =
    liveData {
        emit(Result.loading(true))
        val result = dataResult.invoke()

        if (result.data != null) emit(Result.success(result.data))
        else emit(Result.error(result.message, null))
    }

fun <T : ViewModel?> ViewModelStoreOwner.initViewModel(
    viewModelClass: Class<T>,
    factory: ViewModelFactory
): T {
    return ViewModelProvider(this, factory)[viewModelClass]
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) =
    liveData.observe(this, Observer(body))

fun <T : RecyclerView.Adapter<out RecyclerView.ViewHolder>> showSkeleton(
    rv: RecyclerView, adapter: T?, @LayoutRes layoutPlaceholder: Int
): SkeletonScreen {
    return Skeleton.bind(rv)
        .adapter(adapter)
        .count(R.color.skeleton)
        .load(layoutPlaceholder)
        .frozen(true)
        .angle(10)
        .show()
}

fun setFormatRupiah(value: Any?): String {
    return try {
        val localeId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeId)

        formatRupiah.maximumFractionDigits = 0
        formatRupiah.minimumFractionDigits = 0

        formatRupiah.format(value?.toString()?.toDouble())
    } catch (e: Exception) {
        ""
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
