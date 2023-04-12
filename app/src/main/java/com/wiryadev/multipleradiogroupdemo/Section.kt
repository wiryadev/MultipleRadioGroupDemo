package com.wiryadev.multipleradiogroupdemo

data class Section(
    val title: String,
    val content: List<SectionContent>
)

data class SectionContent(
    val name: String
)

val dummySections = listOf(
    Section(
        "Smartphone",
        listOf(
            SectionContent("Samsung"),
            SectionContent( "Xiaomi"),
            SectionContent("Apple"),
        )
    ),
    Section(
        "Accessories",
        listOf(
            SectionContent("Semua"),
            SectionContent( "Cases"),
            SectionContent("Skin"),
            SectionContent("Charger"),
        )
    ),
)