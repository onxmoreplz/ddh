package com.example.ddh.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.ddh.R
import com.example.ddh.databinding.ActivityMainBinding
import com.example.ddh.main.fragment.home.HomeFragment
import com.example.ddh.main.fragment.mypage.MypageFragment
import com.example.ddh.main.fragment.search.SearchFragment

class MainActivity : FragmentActivity() {

    private lateinit var databinding: ActivityMainBinding

    private val homeFragment by lazy { HomeFragment() }
    private val searchFragment by lazy { SearchFragment() }
    private val mypageFragment by lazy { MypageFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        databinding.bnvHome.background = null
        databinding.bnvHome.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        replaceFragment(homeFragment)
                    }
                    R.id.feed -> {
                        replaceFragment(searchFragment)
                    }
                    R.id.search -> {
                        replaceFragment(searchFragment)
                    }
                    R.id.myPage -> {
                        replaceFragment(mypageFragment)
                    }
                }
                true
            }
            selectedItemId = R.id.home // 초기 프래그먼트
        }
    }

    /**
     *  <p> 프래그먼트 전환 함수 </p>
     *
     * @param fragment: Fragment 전환될 프래그먼트
     */
    public fun replaceFragment(fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        manager.beginTransaction()
            .replace(R.id.fl_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}