package dev.syk.quickpasshub.QuickPass_Hub.exams.entities

import dev.syk.quickpasshub.QuickPass_Hub.common.BaseModel
import dev.syk.quickpasshub.QuickPass_Hub.exams.constants.QuestionType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "questions")
open class QuestionsModel(
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    val exam : ExamsModel,

    @Column(columnDefinition = "TEXT", nullable = false)
    val text: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type : QuestionType, // MULTIPLE_CHOICE, SHORT_ANSWER

    @Column(columnDefinition = "TEXT")
    val explanation : String? = null,

    @Column(nullable = false)
    val verified: Boolean = false,

    @OneToMany(mappedBy = "question", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val choices: MutableList<ChoicesModel> = mutableListOf(),

) : BaseModel()