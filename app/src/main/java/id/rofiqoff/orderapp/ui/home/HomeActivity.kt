package id.rofiqoff.orderapp.ui.home

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewbinding.ViewBinding
import com.ethanhua.skeleton.SkeletonScreen
import id.rofiqoff.orderapp.R
import id.rofiqoff.orderapp.base.BaseActivity
import id.rofiqoff.orderapp.base.BaseAdapter
import id.rofiqoff.orderapp.data.remote.ResponseItem
import id.rofiqoff.orderapp.data.remote.Result
import id.rofiqoff.orderapp.databinding.ActivityHomeBinding
import id.rofiqoff.orderapp.databinding.ItemContentBinding
import id.rofiqoff.orderapp.utils.*
import id.rofiqoff.orderapp.viewmodel.DataViewModel
import java.lang.String
import kotlin.math.min

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private val viewModel by lazy { initViewModel(DataViewModel::class.java, factory) }
    private lateinit var dataAdapter: BaseAdapter<ItemContentBinding, ResponseItem, DataViewHolder>
    private var skeleton: SkeletonScreen? = null
    private var textCartItemCount: AppCompatTextView? = null
    private var mCartItemCount = 0
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = ActivityHomeBinding::inflate

    override fun initView() {
        initAdapter()
        observeData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuItem = menu?.findItem(R.id.menu_cart)

        val actionView = menuItem?.actionView
        textCartItemCount = actionView?.findViewById(R.id.menu_cart)

        setupBadge()

        return true
    }

    private fun observeData() {
        observe(viewModel.data, {
            when (it.status) {
                Result.Status.SUCCESS -> {
                    hideLoading()
                    val data = it.data
                    if (data?.isNotEmpty() == true) dataAdapter.addListData(data)
                }
                Result.Status.ERROR -> {
                    hideLoading()
                }
                Result.Status.LOADING -> showLoading()
            }
        })
    }

    private fun initAdapter() {
        dataAdapter = object : BaseAdapter<ItemContentBinding, ResponseItem, DataViewHolder>(
            ResponseItem::class.java, DataViewHolder::class.java, arrayListOf()
        ) {
            override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
                get() = ItemContentBinding::inflate

            override fun bindView(
                binding: ItemContentBinding,
                holder: DataViewHolder?,
                data: ResponseItem,
                position: Int
            ) {
                holder?.onBind(binding, data)

                binding.buttonAddToCart.setOnClickListener {
                    mCartItemCount += 1
                    setupBadge()
                }
            }
        }

        binding.list.adapter = dataAdapter
    }

    private fun showLoading() {
        skeleton = showSkeleton(binding.list, dataAdapter, R.layout.item_list_placeholder)
    }

    private fun hideLoading() {
        skeleton?.hide()
    }

    private fun setupBadge() {
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount?.visibility != View.GONE) {
                    textCartItemCount?.gone()
                }
            } else {
                textCartItemCount?.text = String.valueOf(min(mCartItemCount, 99))
                if (textCartItemCount?.visibility != View.VISIBLE) {
                    textCartItemCount?.visible()
                }
            }
        }
    }

}