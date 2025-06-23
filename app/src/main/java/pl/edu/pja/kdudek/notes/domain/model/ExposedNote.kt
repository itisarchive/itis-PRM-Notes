package pl.edu.pja.kdudek.notes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ExposedNote(
    val id: Int,
    val title: String,
    val content: String
)
