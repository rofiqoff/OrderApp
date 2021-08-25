package id.rofiqoff.orderapp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import id.rofiqoff.orderapp.viewmodel.ViewModelFactory

abstract class BaseActivity<T> : AppCompatActivity() {
    val factory by lazy { ViewModelFactory.getInstance() }

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> ViewBinding

    @Suppress("UNCHECKED_CAST")
    protected val binding: T
        get() = _binding as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun initView()

}