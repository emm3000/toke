package com.emm.chambaaltoque.home.domain

enum class JobStatus(val key: String) {

    Pending("pending"),
    InProgress("in_progress"),
    Completed("completed"),
    Cancelled("cancelled");

    companion object {

        fun fromKey(key: String): JobStatus = entries.find { it.key == key } ?: Pending
    }
}