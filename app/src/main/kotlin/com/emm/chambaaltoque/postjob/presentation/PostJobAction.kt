package com.emm.chambaaltoque.postjob.presentation

sealed interface PostJobAction {

    data class TitleChanged(val title: String) : PostJobAction

    data class DescriptionChanged(val description: String) : PostJobAction

    data class BudgetChanged(val budget: Double) : PostJobAction

    object Publish : PostJobAction

    object DismissError : PostJobAction
}