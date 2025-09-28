package com.emm.chambaaltoque.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.emm.chambaaltoque.screen.*

object Routes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val VERIFY_IDENTITY = "verify_identity"
    const val REGISTER_APPLICANT = "register_applicant"
    const val POST_JOB = "post_job"
    const val CHAMBERO_HOME = "chambero/home"
    const val CHAMBERO_JOB_DETAIL = "chambero/job_detail"
    const val TRACK_CHAMBERO = "track_chambero"
    const val CHAT = "chat"
}

@Composable
fun AppNav(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME,
        modifier = modifier
    ) {
        // Welcome
        composable(Routes.WELCOME) {
            WelcomeScreen(
                onNeedJobClick = {
                    // MVP: Ir directo a publicar una chamba (solicitante)
                    navController.navigate(Routes.POST_JOB)
                },
                onWantWorkClick = {
                    // Flujo Chambero: Verificación de identidad -> Home
                    navController.navigate(Routes.VERIFY_IDENTITY)
                },
                onSignInClick = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onLoggedIn = {
                    // Tras login, para demo vamos al Home de chambero
                    navController.navigate(Routes.CHAMBERO_HOME)
                }
            )
        }

        // Verificación de identidad (MVP manual)
        composable(Routes.VERIFY_IDENTITY) {
            VerifyIdentityScreen(
                onContinueClick = { _, _, _, _ ->
                    navController.navigate(Routes.CHAMBERO_HOME) {
                        popUpTo(Routes.VERIFY_IDENTITY) { inclusive = true }
                    }
                }
            )
        }

        // Home Chambero
        composable(Routes.CHAMBERO_HOME) {
            ChamberoHomeScreen(
                onFilterClick = { /* no-op */ },
                onChambaClick = {
                    navController.navigate(Routes.CHAMBERO_JOB_DETAIL)
                }
            )
        }

        // Detalle chamba (usamos datos por defecto del composable)
        composable(Routes.CHAMBERO_JOB_DETAIL) {
            ChamberoJobDetailScreen(
                onAcceptClick = {
                    // Acepta -> Tracking
                    navController.navigate(Routes.TRACK_CHAMBERO)
                }
            )
        }
    }
}