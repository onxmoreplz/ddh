package com.example.ddh.upload

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.view.View
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentThirdUploadBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import java.util.*
import java.util.regex.Pattern

class ThirdUploadFragment : UtilityBase.BaseFragment<FragmentThirdUploadBinding>(
    R.layout.fragment_third_upload
), View.OnClickListener {

    private val today = GregorianCalendar()
    private val year: Int = today.get(Calendar.YEAR)
    private val month: Int = today.get(Calendar.MONTH)
    private val day: Int = today.get(Calendar.DATE)
    private val dayOfWeek = today.get(Calendar.DAY_OF_WEEK)
    private var hourOfDay = ""
    private var minute = ""

    override fun FragmentThirdUploadBinding.onCreateView() {
        (activity as UploadActivity).setNextButtonEnable()
    }

    override fun FragmentThirdUploadBinding.onViewCreated() {
        setProgressBar()
        setButton()
        setRadioGroup()
        setRxBinding()
    }


    private fun setProgressBar() {
        UploadActivity.progressBar.progress = 90
    }

    private fun setButton() {
        binding.tvCalender.setOnClickListener(this)
        binding.ivCalendar.setOnClickListener(this)
        binding.ivTimeToMeet.setOnClickListener(this)
        binding.tvTimeToMeet.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.tvCalender, binding.ivCalendar -> {
                val calDatePicker = Calendar.getInstance()
                val dlg = this.context?.let { it1 ->
                    DatePickerDialog(it1, R.style.MyDatePicker, { _, year, month, dayOfMonth ->
                        binding.tvCalender.setText("${year}년 ${month + 1}월 ${dayOfMonth}일")
                    }, year, month, day)
                }
                dlg!!.show()
            }
            binding.tvTimeToMeet, binding.ivTimeToMeet -> {
                val cal = Calendar.getInstance()
                val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    this.hourOfDay = hourOfDay.toString()
                    this.minute = minute.toString()
                    binding.tvTimeToMeet.text = this.hourOfDay + "시 " + this.minute + "분"
                }
                TimePickerDialog(this.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
            }
        }


    }

    private fun setRadioGroup() {
        binding.rgFee.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_btn_free -> {
                    binding.llNotFree.visibility = View.GONE
                }
                R.id.radio_btn_not_free -> {
                    binding.llNotFree.visibility = View.VISIBLE
                }

            }
        }
    }

    fun saveHashMapParty() {
        if (binding.radioBtnFree.isChecked) {
            UploadActivity.uploadContentHashMap["cost"] = 0
        } else {
            UploadActivity.uploadContentHashMap["cost"] = Integer.parseInt(binding.etFee.text.toString())
            UploadActivity.uploadContentHashMap["costDescription"] = binding.etFeeDescription.text
        }
        UploadActivity.uploadContentHashMap["departureAt"] = "$year-${month + 1}-${day + 2} $hourOfDay:$minute"
    }

    private fun setRxBinding() {
        io.reactivex.Observable.combineLatest(
            RxTextView.textChanges(binding.tvCalender),
            RxTextView.textChanges(binding.tvTimeToMeet),
            RxTextView.textChanges(binding.etFee),
            { rxCalendar, rxTimeToMeet, rxFee ->
                if (checkDateAndPlace(rxCalendar) && checkDateAndPlace(rxTimeToMeet)) {
                    (activity as UploadActivity).SetNextButtonAble()
                }
            }
        ).subscribe()
    }

    private fun checkFee(s: CharSequence?): Boolean {
        val phoneNumberRegEx = "/^[0-9]/g"
        return Pattern.matches(phoneNumberRegEx, s)
    }

    private fun checkDateAndPlace(s: CharSequence?): Boolean {
        return s!!.length > 1
    }

}