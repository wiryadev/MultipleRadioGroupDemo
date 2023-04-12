package com.wiryadev.multipleradiogroupdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.allViews
import com.wiryadev.multipleradiogroupdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentSectionContent: SectionContent? = null

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
                onContainerClicked = {
                    dummySections.forEach {
                        if (it.title != section.title) {
                            val radioGroup =
                                binding.root.findViewWithTag<RadioGroupDropdown>(it.title)
                            if (radioGroup.isExpanded()) {
                                radioGroup.collapse()
                            }
                        }
                    }
                }
                onCheckedChange = { labelTag, checkedId ->
//                    Log.d("CheckRadioId", "checkedId: $checkedId")
//                    Log.d("CheckRadioId", "formattedSectionIndex: $formattedSectionIndex")
                    onCheckedChange(labelTag, sectionIndex, checkedId - formattedSectionIndex)
                }
            }
            binding.root.addView(radioGroupDropdown)
        }
    }

    private fun onCheckedChange(
        labelTag: String,
        radioGroupId: Int,
        radioButtonId: Int
    ) {
        val section = dummySections[radioGroupId]

        Log.d("CheckRadioId", "===================================")
        Log.d("CheckRadioId", "selected section: ${section.title}")
        dummySections.forEach {
            if (it.title != section.title) {
                Log.d("CheckRadioId", "findwithtag section: ${section.title}")
                Log.d("CheckRadioId", "findwithtag loop: ${it.title}")
                Log.d("CheckRadioId", "===================================")
                val radioGroup = binding.root.findViewWithTag<RadioGroupDropdown>(it.title)
                radioGroup.clearSelection()
            }
        }
        currentSectionContent = section.content[radioButtonId]
        Toast.makeText(
            this,
            "${section.title} ${currentSectionContent?.name}",
            Toast.LENGTH_SHORT
        ).show()
    }
}

