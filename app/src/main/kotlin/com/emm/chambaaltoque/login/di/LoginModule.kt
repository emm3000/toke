package com.emm.chambaaltoque.login.di

import com.emm.chambaaltoque.login.data.AuthRepositoryImpl
import com.emm.chambaaltoque.login.domain.AuthRepository
import com.emm.chambaaltoque.login.presentation.ApplicantRegisterViewModel
import com.emm.chambaaltoque.login.presentation.LoginApplicantViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val loginModule = module {

    factoryOf(::AuthRepositoryImpl) bind AuthRepository::class

    viewModelOf(::ApplicantRegisterViewModel)
    viewModelOf(::LoginApplicantViewModel)
}