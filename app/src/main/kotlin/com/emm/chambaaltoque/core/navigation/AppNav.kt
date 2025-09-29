package com.emm.chambaaltoque.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.emm.chambaaltoque.core.screen.ChamberoHomeScreen
import com.emm.chambaaltoque.core.screen.ChamberoJobDetailScreen
import com.emm.chambaaltoque.core.screen.ChatScreen
import com.emm.chambaaltoque.core.screen.JobPostedScreen
import com.emm.chambaaltoque.core.screen.LoginScreen
import com.emm.chambaaltoque.core.screen.PostJobScreen
import com.emm.chambaaltoque.core.screen.RegisterApplicantScreen
import com.emm.chambaaltoque.core.screen.RequesterActiveJobsScreen
import com.emm.chambaaltoque.core.screen.RequesterHomeScreen
import com.emm.chambaaltoque.core.screen.TrackChamberoScreen
import com.emm.chambaaltoque.core.screen.WelcomeScreen

@Composable
fun AppNav(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME,
        modifier = modifier,
    ) {
        // Welcome
        composable(Routes.WELCOME) {
            WelcomeScreen(
                onNeedJobClick = {
                    // Mejor UX: llevar a un Home de solicitante
                    navController.navigate(Routes.REQUESTER_HOME)
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

        // Login (básico)
        composable(Routes.LOGIN) {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onLoggedIn = {
                    // Tras login, para demo vamos al Home de chambero
                    navController.navigate(Routes.CHAMBERO_HOME)
                }
            )
        }

        // Registro solicitante (disponible para futuro)
        composable(Routes.REGISTER_APPLICANT) {
            RegisterApplicantScreen()
        }

        // Home Solicitante
        composable(Routes.REQUESTER_HOME) {
            RequesterHomeScreen(
                onPostJobClick = { navController.navigate(Routes.POST_JOB) },
                onActiveJobsClick = { navController.navigate(Routes.REQUESTER_ACTIVE_JOBS) }
            )
        }

        // Publicar chamba (solicitante)
        composable(Routes.POST_JOB) {
            PostJobScreen(
                onAddPhotoClick = { /* no-op */ },
                onPublishClick = {
                    // Tras publicar, confirmación y CTAs
                    navController.navigate(Routes.JOB_POSTED)
                }
            )
        }

        // Confirmación de publicación
        composable(Routes.JOB_POSTED) {
            JobPostedScreen(
                onGoToTracking = { navController.navigate(Routes.TRACK_CHAMBERO) },
                onGoHome = {
                    navController.navigate(Routes.REQUESTER_HOME) {
                        popUpTo(Routes.WELCOME) { inclusive = false }
                    }
                }
            )
        }

        // Listado de chambas activas del solicitante
        composable(Routes.REQUESTER_ACTIVE_JOBS) {
            RequesterActiveJobsScreen(
                onJobClick = { _ ->
                    navController.navigate(Routes.TRACK_CHAMBERO)
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

        // Tracking Chambero
        composable(Routes.TRACK_CHAMBERO) {
            TrackChamberoScreen(
                onCallClick = { /* no-op */ },
                onChatClick = {
                    navController.navigate(Routes.CHAT)
                }
            )
        }

        // Chat
        composable(Routes.CHAT) {
            ChatScreen(
                onSend = { /* no-op */ },
                onQuickReplyClick = { /* no-op */ }
            )
        }
    }
}