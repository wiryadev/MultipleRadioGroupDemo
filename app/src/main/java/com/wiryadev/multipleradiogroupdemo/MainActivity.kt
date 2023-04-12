package com.wiryadev.multipleradiogroupdemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wiryadev.multipleradiogroupdemo.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentCheckedId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dummySections.forEachIndexed { sectionIndex, section ->
            val formattedSectionIndex = (sectionIndex + 1) * 100
            val radioButtonSectionContent = section.content
                .mapIndexed { contentIndex, sectionContent ->
                    Pair(
                        first = formattedSectionIndex + contentIndex,
                        second = sectionContent.name
                    )
                }
            val radioGroupDropdown = RadioGroupDropdown(this).apply {
                setTitle(section.title)
                setData(radioButtonSectionContent)
                onContainerClicked = { tag ->
                    dummySections.forEach {
                        if (it.title != tag) {
                            val radioGroup =
                                binding.root.findViewWithTag<RadioGroupDropdown>(it.title)
                            radioGroup.collapse()
                            radioGroup.clearSelection()
                        } else {

                            Log.d("CheckRadioId", "==============================")
                            Log.d("CheckRadioId", "current Id: $currentCheckedId")
                            Log.d("CheckRadioId", "formattedIndex: $formattedSectionIndex")
                            val rg = binding.root.findViewWithTag<RadioGroupDropdown>(tag)
                            currentCheckedId?.let { id ->
                                val absolute = abs(id - formattedSectionIndex)
                                Log.d("CheckRadioId", "absolute: $absolute")
                                if ((absolute < 100) && (absolute < section.content.size)) {
                                    rg.setCheckedId(id)
                                }
                            }
                        }
                    }
                }
                onCheckedChange = { checkedId ->
                    currentCheckedId = checkedId
//                    Log.d("CheckRadioId", "checkedchange: $checkedId")
//                    Log.d("CheckRadioId", "formattedSectionIndex: $formattedSectionIndex")

                    Log.d("CheckRadioId", "onCheck: ${checkedId - formattedSectionIndex}")
                    onCheckedChange(sectionIndex, abs(checkedId - formattedSectionIndex))
                }
            }
            binding.root.addView(radioGroupDropdown)
        }
    }

    private fun onCheckedChange(
        radioGroupId: Int,
        radioButtonId: Int
    ) {
//        Log.d("CheckRadioId", "===================================")
//        Log.d("CheckRadioId", "radioGroup Id: $radioGroupId")
//        Log.d("CheckRadioId", "radioButton Id: $radioButtonId")
//        val section = dummySections[radioGroupId]
//        val content = section.content[radioButtonId]
//        dummySections.forEach {
//            if (it.title != section.title) {
//                Log.d("CheckRadioId", "findwithtag section: ${section.title}")
//                Log.d("CheckRadioId", "findwithtag loop: ${it.title}")
//                Log.d("CheckRadioId", "===================================")
//                val radioGroup = binding.root.findViewWithTag<RadioGroupDropdown>(it.title)
//                radioGroup.clearSelection()
//            }
//        }


        val section = dummySections[radioGroupId]

//        if (radioButtonId < section.content.size) {

        val content = section.content[radioButtonId]

        Toast.makeText(
            this,
            "${section.title} ${content.name}",
            Toast.LENGTH_SHORT
        ).show()
//        }
    }
}

