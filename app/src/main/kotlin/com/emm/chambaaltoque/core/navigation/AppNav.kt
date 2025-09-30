package com.emm.chambaaltoque.core.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.emm.chambaaltoque.auth.presentation.login.applicant.LoginApplicantRoute
import com.emm.chambaaltoque.auth.presentation.login.applicant.LoginApplicantScreen
import com.emm.chambaaltoque.auth.presentation.login.applicant.LoginApplicantViewModel
import com.emm.chambaaltoque.auth.presentation.register.aplicant.ApplicantRegisterScreen
import com.emm.chambaaltoque.auth.presentation.register.aplicant.ApplicantRegisterViewModel
import com.emm.chambaaltoque.auth.presentation.register.aplicant.ApplicationRegisterRoute
import com.emm.chambaaltoque.welcome.WelcomeRoute
import com.emm.chambaaltoque.welcome.WelcomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNav(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WelcomeRoute,
        modifier = modifier,
    ) {

        composable<WelcomeRoute> {
            WelcomeScreen(
                onNeedJobClick = {
                    navController.navigate(ApplicationRegisterRoute)
                },
                onWantWorkClick = {
                    navController.navigate(Routes.VERIFY_IDENTITY)
                },
                onSignInClick = {
                    navController.navigate(LoginApplicantRoute)
                }
            )
        }

        composable<LoginApplicantRoute> {
            val vm: LoginApplicantViewModel = koinViewModel()

            LaunchedEffect(vm.state.isSuccessful) {
                if (vm.state.isSuccessful) {
                    navController.navigate(Routes.REQUESTER_HOME)
                }
            }

            LoginApplicantScreen(
                state = vm.state,
                onAction = vm::onAction
            )
        }

        composable(Routes.REQUESTER_HOME) {
            Text("gaaa")
        }

        composable<ApplicationRegisterRoute> {

            val vm: ApplicantRegisterViewModel = koinViewModel()

            LaunchedEffect(vm.state.isSuccessful) {
                if (vm.state.isSuccessful) {
                    navController.navigate(LoginApplicantRoute) {
                        popUpTo(ApplicationRegisterRoute) {
                            inclusive = true
                        }
                    }
                }
            }

            ApplicantRegisterScreen(
                state = vm.state,
                onAction = vm::onAction
            )
        }
    }
}