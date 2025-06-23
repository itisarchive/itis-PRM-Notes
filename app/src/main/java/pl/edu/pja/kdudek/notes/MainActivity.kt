package pl.edu.pja.kdudek.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.scale
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pl.edu.pja.kdudek.notes.ui.theme.NotesTheme
import pl.edu.pja.kdudek.notes.ui.view.NoteAddScreen
import pl.edu.pja.kdudek.notes.ui.view.NoteDetailsScreen
import pl.edu.pja.kdudek.notes.ui.view.NotesListScreen
import pl.edu.pja.kdudek.notes.ui.viewmodel.NoteAddViewModel
import pl.edu.pja.kdudek.notes.ui.viewmodel.NoteDetailsViewModel
import pl.edu.pja.kdudek.notes.ui.viewmodel.NotesListViewModel

class MainActivity : ComponentActivity() {
    private val listViewModel: NotesListViewModel by viewModels()
    private val detailsViewModel: NoteDetailsViewModel by viewModels()
    private val addViewModel: NoteAddViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesTheme {
                val controller = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        val backStackEntry by controller.currentBackStackEntryAsState()
                        if (backStackEntry?.destination?.route == "/notes") {
                            FloatingActionButton(
                                modifier = Modifier.scale(0.5f),
                                onClick = {
                                    controller.navigate("/notes/add")
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                    contentDescription = "Add Note",
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = controller,
                        startDestination = "/notes",
                    ) {
                        composable("/notes") {
                            NotesListScreen(
                                listViewModel = listViewModel,
                                controller = controller
                            )
                        }
                        composable(
                            "/notes/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) { backStackEntry ->
                            NoteDetailsScreen(
                                detailsViewModel = detailsViewModel,
                                controller = controller,
                                id = backStackEntry.arguments?.getInt("id") ?: -1
                            )
                        }
                        composable("/notes/add") {
                            NoteAddScreen(
                                addViewModel = addViewModel,
                                controller = controller
                            )
                        }
                    }
                }
            }
        }
    }
}
