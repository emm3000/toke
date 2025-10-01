package com.emm.chambaaltoque.home

import com.emm.chambaaltoque.core.data.JobRepositoryImpl
import com.emm.chambaaltoque.core.domain.JobRepository
import com.emm.chambaaltoque.home.presentation.applicant.ApplicantHomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {

    factoryOf(::JobRepositoryImpl) bind JobRepository::class

    viewModelOf(::ApplicantHomeViewModel)
}