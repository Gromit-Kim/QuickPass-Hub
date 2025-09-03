package dev.syk.quickpasshub.QuickPass_Hub.exams.entities

import dev.syk.quickpasshub.QuickPass_Hub.common.BaseModel
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name="choices")
open class ChoicesModel(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    val question: QuestionsModel,

    @Column(columnDefinition = "TEXT", nullable = false)
    val text: String,

    @Column(name = "is_correct", nullable = false)
    val isCorrect: Boolean,

) : BaseModel()