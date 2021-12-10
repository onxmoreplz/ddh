package com.example.ddh.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

sealed class UtilityBase {

    open class BaseActivity<B : ViewDataBinding>(
        @LayoutRes val layoutId: Int
    ) : FragmentActivity() {

        lateinit var binding: B

        override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
            super.onCreate(savedInstanceState, persistentState)
            binding = DataBindingUtil.setContentView(this, layoutId)
            binding.onCreate()

        }

        open fun B.onCreate() = Unit
    }

    open class BaseFragment<B : ViewDataBinding>(
        @LayoutRes val layoutId: Int
    ) : Fragment() {

        lateinit var binding: B

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            binding.onCreateView()
            binding.onViewCreated()
            return binding.root
        }

        open fun B.onCreateView() = Unit
        open fun B.onViewCreated() = Unit


        // Toast 역할
        protected fun showToast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}
