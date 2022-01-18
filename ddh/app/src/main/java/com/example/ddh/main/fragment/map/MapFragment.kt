package com.example.ddh.main.fragment.map

import android.view.View
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentMapBinding


class MapFragment : UtilityBase.BaseFragment<FragmentMapBinding>(
    R.layout.fragment_map
), View.OnClickListener {

    override fun FragmentMapBinding.onCreateView() {

    }

    override fun FragmentMapBinding.onViewCreated() {
        setButton()
        setMapview()
    }

    private fun setMapview() {
        /*val mapView = MapView(this)

        val mapViewContainer = binding.rlMapView as ViewGroup
        mapViewContainer.addView(mapView)*/
    }

    private fun setButton() {

    }

    override fun onClick(v: View?) {

    }

}