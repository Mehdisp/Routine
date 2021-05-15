package ir.mehdisp.routine.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import ir.mehdisp.routine.R
import ir.mehdisp.routine.databinding.ActivityMainBinding
import ir.mehdisp.routine.ui.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = host.navController

        navController.setGraph(R.navigation.main_navigation)

        binding.navView.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> navController.navigate(R.id.homeFragment)
            R.id.nav_tasks -> navController.navigate(R.id.tasksFragment)
            R.id.nav_notes -> navController.navigate(R.id.notesFragment)
            R.id.nav_news -> navController.navigate(R.id.newsFragment)
            R.id.nav_calendar -> navController.navigate(R.id.calendarFragment)
            else -> return false
        }

        return true
    }

}