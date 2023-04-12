package com.wiryadev.multipleradiogroupdemo

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.google.android.material.radiobutton.MaterialRadioButton
import com.wiryadev.multipleradiogroupdemo.databinding.LayoutRadioGroupDropdownBinding

class RadioGroupDropdown @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutRadioGroupDropdownBinding by lazy {
        LayoutRadioGroupDropdownBinding.inflate(LayoutInflater.from(context), this)
    }

    var onContainerClicked: ((String) -> Unit)? = null
    var onCheckedChange: ((Int) -> Unit)? = null

    init {
        setupView()
    }

    private fun setupView() {
        orientation = VERTICAL
        layoutTransition = LayoutTransition()
        setPadding(50)
        binding.dropdownLabelContainer.setOnClickListener {
            onContainerClicked?.invoke(tag.toString())
            if (isExpanded()) collapse() else expand()
        }
        binding.radioSelection.setOnCheckedChangeListener { _, checkedId ->
            Log.d("CheckRadioId", "checked listener $checkedId")
            if (checkedId != NO_ID) {
                onCheckedChange?.invoke(checkedId)
            }
        }
    }

    fun setTitle(title: String) {
        binding.tvLabelSection.text = title
        binding.root.tag = title
    }

    fun setCheckedId(id: Int) {
        binding.radioSelection.apply {
            setOnCheckedChangeListener(null)
            check(id)
            setOnCheckedChangeListener { _, checkedId ->
                onCheckedChange?.invoke(checkedId)
            }
        }
    }

    fun clearSelection() {
        binding.radioSelection.apply {
            setOnCheckedChangeListener(null)
            clearCheck()
            setOnCheckedChangeListener { _, checkedId ->
                onCheckedChange?.invoke(checkedId)
            }
        }
    }

    fun setData(datas: List<Pair<Int, String>>) {
        datas.forEach {  data ->
            val radioButton = MaterialRadioButton(context).apply {
                id = data.first
                text = data.second
            }
            binding.radioSelection.addView(radioButton)
        }
    }

    fun isExpanded() = binding.radioSelection.isVisible

    fun expand() {
        with(binding) {
            if (!isExpanded()) {
                imgChevronDropdown.animate().rotationBy(-180f)
                radioSelection.isVisible = true
            }
        }
    }

    fun collapse() {
        with(binding) {
            if (isExpanded()) {
                imgChevronDropdown.animate().rotationBy(180f)
                radioSelection.isVisible = false
//                findViewById()
            }
        }
    }
}
