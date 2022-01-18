package com.example.ddh.upload

import android.view.View
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentSecondUploadBinding
import com.example.ddh.upload.UploadActivity.Companion.uploadContentHashMap
import com.jakewharton.rxbinding2.widget.RxTextView


class SecondUploadFragment : UtilityBase.BaseFragment<FragmentSecondUploadBinding>(
    R.layout.fragment_second_upload
), View.OnClickListener {

    override fun FragmentSecondUploadBinding.onCreateView() {

    }

    override fun FragmentSecondUploadBinding.onViewCreated() {
        (activity as UploadActivity).setNextButtonEnable()

        setProgressBar()
        setButton()
        setRadioGroup()
        setRxBinding()
    }

    private fun setRxBinding() {
        io.reactivex.Observable.combineLatest(
            RxTextView.textChanges(binding.etMountain),
            RxTextView.textChanges(binding.etPlaceToMeet),
            RxTextView.textChanges(binding.actvMaximum),
            RxTextView.textChanges(binding.actvMinimum),
            { rxMountain, rxPlace, rxMax, rxMin ->
                if (checkMountainAndPlace(rxMountain) && checkMountainAndPlace(rxPlace)) {
                    (activity as UploadActivity).SetNextButtonAble()
                }

            }
        ).subscribe()
    }

    /** 입력값 유효성 검증 메서드 */
    private fun checkMaxMin(max: CharSequence?, min: CharSequence?): Boolean {
        return when {
            max.isNullOrEmpty() -> {
                false
            }
            min.isNullOrEmpty() -> {
                false
            }
            else -> {
                Integer.parseInt(max.toString()) >= Integer.parseInt(min.toString())
            }
        }

    }

    private fun checkMountainAndPlace(s: CharSequence?): Boolean {
        return s!!.length > 1
    }

    /** 입력값 Hashmap 에 넣는 메서디 */
    private fun setButton() {
        if (checkMountainAndPlace(binding.etMountain.toString()) &&
            checkMountainAndPlace(binding.etPlaceToMeet.toString())
        ) {
            (activity as UploadActivity).SetNextButtonAble()
        }

    }

    private fun setRadioGroup() {
        uploadContentHashMap["gender"] = "normal"
        binding.rgGender.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_btn_both -> uploadContentHashMap["gender"] = "normal"
                R.id.radio_btn_only_man -> uploadContentHashMap["gender"] = "male"
                R.id.radio_btn_only_woman -> uploadContentHashMap["gender"] = "normal"
            }
        }
    }

    private fun setProgressBar() {
        UploadActivity.progressBar.progress = 60
    }

    override fun onClick(v: View?) {

    }


    fun saveHashMapParty() {
        uploadContentHashMap["mountain"] = binding.etMountain.text.toString()
        uploadContentHashMap["place"] = binding.etPlaceToMeet.text.toString()
        uploadContentHashMap["maximum"] = Integer.parseInt(binding.actvMaximum.text.toString())
        uploadContentHashMap["minimum"] = Integer.parseInt(binding.actvMinimum.text.toString())
    }

}