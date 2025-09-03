package dev.syk.quickpasshub.QuickPass_Hub.exams.entities

import dev.syk.quickpasshub.QuickPass_Hub.common.BaseModel
import dev.syk.quickpasshub.QuickPass_Hub.exams.constants.ExamType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table


@Entity
@Table(name = "exams")
open class ExamsModel(

    @Column(nullable = false)
    val name : String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type : ExamType,

    @Column(name="total_questions", nullable = false)
    val totalQuestions: Int,

    @Column(name = "time_limit", nullable = false)
    val timeLimit: Int,

    @OneToMany(mappedBy = "exam", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val questions: MutableList<QuestionsModel> = mutableListOf(),

) : BaseModel()