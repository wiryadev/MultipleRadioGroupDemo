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
    var onCheckedChange: ((String, Int) -> Unit)? = null

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
            if (checkedId != NO_ID) {
                onCheckedChange?.invoke(tag.toString(), checkedId)
                Log.d("CheckRadioId", "checked listener $checkedId")
            }
        }
    }

    fun setTitle(title: String) {
        binding.tvLabelSection.text = title
        binding.root.tag = title
    }

    fun clearSelection() {
        binding.radioSelection.apply {
            setOnCheckedChangeListener(null)
            clearCheck()
            setOnCheckedChangeListener { _, checkedId ->
                onCheckedChange?.invoke(id.toString(), checkedId)
            }
        }
    }

    fun setData(datas: List<Pair<Int, String>>) {
        datas.forEach {  data ->
            val radioButton = MaterialRadioButton(context).apply {
                id = data.first
                text = data.second
            }
            Log.d("CheckRadioId", "setData button Id: ${radioButton.id}")
            binding.radioSelection.addView(radioButton)
        }
    }

    fun isExpanded() = binding.radioSelection.isVisible

    fun expand() {
        with(binding) {
            if (!radioSelection.isVisible) {
                imgChevronDropdown.animate().rotationBy(-180f)
                radioSelection.isVisible = true
            }
        }
    }

    fun collapse() {
        with(binding) {
            if (radioSelection.isVisible) {
                imgChevronDropdown.animate().rotationBy(180f)
                radioSelection.isVisible = false
//                findViewById()
            }
        }
    }
}
