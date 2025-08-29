// This file defines the data structure for a single checklist item.
package com.agricultureAgritech.features.safetyChecklists.domain.model

data class ChecklistItem(
    val title: String,
    val subtitle: String,
    val backgroundColor: Int,
    var isChecked: Boolean = false
)