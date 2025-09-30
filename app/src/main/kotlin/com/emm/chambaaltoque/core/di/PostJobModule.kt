package com.emm.chambaaltoque.core.di

import com.emm.chambaaltoque.postjob.data.JobRepositoryImpl
import com.emm.chambaaltoque.postjob.domain.JobRepository
import com.emm.chambaaltoque.postjob.presentation.PostJobViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val postJobModule = module {

    viewModelOf(::PostJobViewModel)

    factoryOf(::JobRepositoryImpl) bind JobRepository::class
}