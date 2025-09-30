package com.emm.chambaaltoque.core.di

import com.emm.chambaaltoque.postjob.presentation.PostJobViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val postJobModule = module {

    viewModelOf(::PostJobViewModel)

}