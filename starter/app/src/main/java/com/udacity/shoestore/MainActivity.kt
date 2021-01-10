package com.udacity.shoestore

import android.app.Fragment
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.get
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.data.LoginDataSource
import com.udacity.shoestore.data.model.LOGGEDIN
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.ui.login.LoginFragment
import com.udacity.shoestore.ui.login.LoginFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    // create the bindnig object
    private lateinit var binding: ActivityMainBinding
    private lateinit var shoeDrawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        // main activity binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // drawer binding
        shoeDrawerLayout = binding.shoeDrawerLayout

        // NavController and ActionBar Setup
        val navController = this.findNavController(R.id.shoeNavHostFragment)

        // get the toolbar from binding and set it as actionbar
        setSupportActionBar(binding.toolbar)

        // set title and subtitle for toolbar
        // commented, because done in the layout
        // note - if fragments have labels > these labels will overwrite Title
        //binding.toolbar.setTitle(R.string.toolbar_title)
        //binding.toolbar.setSubtitle(R.string.toolbar_subtitile)


        NavigationUI.setupActionBarWithNavController(this, navController, shoeDrawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, shoeDrawerLayout)

        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, bundle: Bundle? ->
            if (nd.id == R.id.loginFragment) {
                Timber.i("navigating to LoginFragment nd.id = ${nd.id} to = ${R.id.loginFragment}")
                // thought of hinding the toolbar in login but then we do not have an association to
                // the corresponding app which we want to login. so we just hide the Nav Up / Drawer
                // icon
                //supportActionBar?.hide()
                supportActionBar?.setDisplayHomeAsUpEnabled(false)

                // lock the drawer if in login
                shoeDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            } else if (nd.id == nc.graph.startDestination){
                // in the start destination drawer shold be able to open
                shoeDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }else {
                Timber.i("navigating nd.id = ${nd.id} to = ${nc.graph.id}")
                // if we navigate back to any other place than login, we make sure that
                // the nav/drawer icon is enabled again.
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                // lock the drawer everywhere else
                shoeDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }
        }

        NavigationUI.setupWithNavController(binding.shoeNavView, navController)

        // if the user is not logged in at app startup, we directly move to the login screen.
        if (LOGGEDIN == false){
            navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment())
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.shoeNavHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

}


/* from trivia app

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration : AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        // prevent nav gesture if not on start destination
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, bundle: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}

 */