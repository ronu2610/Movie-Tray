package com.ronak.movietray

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.ronak.movietray.databinding.ActivityMainBinding
import com.ronak.movietray.dto.MovieItem
import com.ronak.movietray.ui.details.DetailsActivity
import com.ronak.movietray.utils.SingleEvent
import com.ronak.movietray.utils.observeEvent
import com.ronak.movietray.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.NotNull

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val moviesViewModel: MoviesViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.layout_toolbar_header)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        moviesViewModel.getMovies()
        observeEvent(moviesViewModel.openMovieDetails, ::navigateToDetailsScreen)
    }

    private fun navigateToDetailsScreen(navigateEvent: SingleEvent<MovieItem>) {
        navigateEvent.peekContent().let {
            val intentDetail = Intent(this@MainActivity, DetailsActivity::class.java).apply {
                putExtra(MOVIE_ITEM_KEY,it)
            }
            startActivity(intentDetail)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

