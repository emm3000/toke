package com.emm.chambaaltoque.core.presentation.navigation

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.emm.chambaaltoque.auth.domain.UserType
import com.emm.chambaaltoque.auth.presentation.login.LoginViewModel
import com.emm.chambaaltoque.auth.presentation.login.applicant.LoginApplicantRoute
import com.emm.chambaaltoque.auth.presentation.login.applicant.LoginApplicantScreen
import com.emm.chambaaltoque.auth.presentation.login.worker.LoginWorkerRoute
import com.emm.chambaaltoque.auth.presentation.login.worker.LoginWorkerScreen
import com.emm.chambaaltoque.auth.presentation.register.aplicant.ApplicantRegisterScreen
import com.emm.chambaaltoque.auth.presentation.register.aplicant.ApplicantRegisterViewModel
import com.emm.chambaaltoque.auth.presentation.register.aplicant.ApplicationRegisterRoute
import com.emm.chambaaltoque.auth.presentation.register.worker.UriOrchestrator
import com.emm.chambaaltoque.auth.presentation.register.worker.WorkerRegisterAction
import com.emm.chambaaltoque.auth.presentation.register.worker.WorkerRegisterFlow
import com.emm.chambaaltoque.auth.presentation.register.worker.WorkerRegisterRoute
import com.emm.chambaaltoque.auth.presentation.register.worker.WorkerRegisterViewModel
import com.emm.chambaaltoque.home.presentation.ApplicantHomeRoute
import com.emm.chambaaltoque.home.presentation.ApplicantHomeScreen
import com.emm.chambaaltoque.home.presentation.ApplicantHomeState
import com.emm.chambaaltoque.home.presentation.ApplicantHomeViewModel
import com.emm.chambaaltoque.postjob.presentation.PostJobRoute
import com.emm.chambaaltoque.postjob.presentation.PostJobScreen
import com.emm.chambaaltoque.postjob.presentation.PostJobViewModel
import com.emm.chambaaltoque.welcome.WelcomeRoute
import com.emm.chambaaltoque.welcome.WelcomeScreen
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun AppNav(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WelcomeRoute,
        modifier = modifier,
    ) {

        composable<WelcomeRoute> {
            val supabaseClient: SupabaseClient = koinInject<SupabaseClient>()

            val status: SessionStatus by supabaseClient.auth.sessionStatus.collectAsStateWithLifecycle()

            LaunchedEffect(status) {
                if (status is SessionStatus.Authenticated) {
                    navController.navigate(ApplicantHomeRoute) {
                        popUpTo(WelcomeRoute) {
                            inclusive = true
                        }
                    }
                }
            }

            if (status is SessionStatus.Initializing) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            } else {
                WelcomeScreen(
                    onNeedJobClick = {
                        navController.navigate(ApplicationRegisterRoute)
                    },
                    onWantWorkClick = {
                        navController.navigate(WorkerRegisterRoute)
                    },
                    onSignInClick = {
                        navController.navigate(LoginApplicantRoute)
                    }
                )
            }
        }

        composable<WorkerRegisterRoute> {
            val vm: WorkerRegisterViewModel = koinViewModel()
            val uriOrchestrator = koinInject<UriOrchestrator>()

            var tmpUri: Uri? = remember { null }

            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = {}
            )

            val cameraDniLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.TakePicture(),
                onResult = { capturedSuccessfully ->
                    if (capturedSuccessfully) {
                        val uri = tmpUri ?: return@rememberLauncherForActivityResult
                        vm.onAction(WorkerRegisterAction.SetDniPhoto(uri))
                    }
                }
            )

            val cameraSelfieLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.TakePicture(),
                onResult = { capturedSuccessfully ->
                    if (capturedSuccessfully) {
                        val uri = tmpUri ?: return@rememberLauncherForActivityResult
                        vm.onAction(WorkerRegisterAction.SetSelfie(uri))
                    }
                }
            )

            LaunchedEffect(Unit) {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }

            LaunchedEffect(vm.state.isSuccessful) {
                if (vm.state.isSuccessful) {
                    navController.navigate(LoginWorkerRoute) {
                        popUpTo(WorkerRegisterRoute) {
                            inclusive = true
                        }
                    }
                }
            }

            WorkerRegisterFlow(
                state = vm.state,
                onAction = vm::onAction,
                onBack = { navController.popBackStack() },
                onPickDniPhoto = {
                    tmpUri = uriOrchestrator.createTempFileUri()
                    cameraDniLauncher.launch(tmpUri!!)
                },
                onPickSelfie = {
                    tmpUri = uriOrchestrator.createTempFileUri()
                    cameraSelfieLauncher.launch(tmpUri)
                },
            )
        }

        composable<LoginWorkerRoute> {

            val vm: LoginViewModel = koinViewModel()

            LaunchedEffect(vm.state.isSuccessful) {
                if (vm.state.isSuccessful) {
                    when(vm.state.userType) {
                        UserType.Worker -> navController.navigate(ApplicantHomeRoute)
                        UserType.Applicant -> navController.navigate(ApplicantHomeRoute)
                    }
                }
            }

            LoginWorkerScreen(
                state = vm.state,
                onAction = vm::onAction,
            )
        }

        composable<LoginApplicantRoute> {
            val vm: LoginViewModel = koinViewModel()

            LaunchedEffect(vm.state.isSuccessful) {
                if (vm.state.isSuccessful) {
                    navController.navigate(ApplicantHomeRoute)
                }
            }

            LoginApplicantScreen(
                state = vm.state,
                onAction = vm::onAction
            )
        }

        composable<ApplicantHomeRoute> {
            val vm: ApplicantHomeViewModel = koinViewModel()

            val state: ApplicantHomeState by vm.state.collectAsStateWithLifecycle()

            ApplicantHomeScreen(
                onPublishClick = {
                    navController.navigate(PostJobRoute)
                },
                state = state,
            )
        }

        composable<PostJobRoute> {
            val vm: PostJobViewModel = koinViewModel()

            LaunchedEffect(vm.state.isSuccessful) {
                if (vm.state.isSuccessful) {
                    navController.popBackStack()
                }
            }

            PostJobScreen(
                state = vm.state,
                onAction = vm::onAction,
                onCancel = navController::popBackStack,
            )
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