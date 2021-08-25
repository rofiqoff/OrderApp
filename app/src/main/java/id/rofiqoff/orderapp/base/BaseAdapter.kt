package id.rofiqoff.orderapp.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException

@SuppressLint("NotifyDataSetChanged")
abstract class BaseAdapter<Binding, DataType, ViewHolder : RecyclerView.ViewHolder?>(
    private var mModelClass: Class<DataType>,
    private var mViewHolderClass: Class<ViewHolder>,
    data: MutableList<DataType>
) : RecyclerView.Adapter<ViewHolder>() {
    private var mData: MutableList<DataType> = data
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding

    @Suppress("UNCHECKED_CAST")
    protected val binding: Binding
        get() = _binding as Binding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        _binding = bindingInflater.invoke(inflater, parent, false)
        return try {
            val constructor: Constructor<ViewHolder> =
                mViewHolderClass.getConstructor(View::class.java)
            constructor.newInstance(requireNotNull(_binding?.root))
        } catch (e: NoSuchMethodException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position)
        bindView(binding, holder, model, position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int = position

    private fun getItem(position: Int): DataType {
        return mData[position]
    }

    fun addListData(list: List<DataType>?) {
        mData = list as MutableList<DataType>
        notifyDataSetChanged()
    }

    protected abstract fun bindView(
        binding: Binding,
        holder: ViewHolder?,
        data: DataType,
        position: Int
    )
}