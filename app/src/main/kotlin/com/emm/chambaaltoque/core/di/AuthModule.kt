package com.emm.chambaaltoque.core.di

import com.emm.chambaaltoque.auth.data.AuthRepositoryImpl
import com.emm.chambaaltoque.auth.domain.AuthRepository
import com.emm.chambaaltoque.auth.presentation.login.applicant.LoginApplicantViewModel
import com.emm.chambaaltoque.auth.presentation.register.aplicant.ApplicantRegisterViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val loginModule = module {

    factoryOf(::AuthRepositoryImpl) bind AuthRepository::class

    viewModelOf(::ApplicantRegisterViewModel)
    viewModelOf(::LoginApplicantViewModel)
}