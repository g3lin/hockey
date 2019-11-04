package com.brosseau.julien.tp1

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.brosseau.julien.tp1.ui.Client
import kotlinx.coroutines.*
import java.net.InetSocketAddress
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.util.cio.write
import kotlinx.android.synthetic.main.fragment_match_1.*
import kotlinx.android.synthetic.main.fragment_match_2.*
import kotlinx.android.synthetic.main.fragment_match_2.text_team_1
import kotlinx.coroutines.io.readUTF8Line
import kotlinx.coroutines.io.writeByte
import kotlinx.coroutines.io.writeStringUtf8
import java.lang.Exception
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
           getInfoAndUpdateUI("2","nomEquipe1")
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_match_1, R.id.nav_match_2,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        getInfoAndUpdateUI("2","nomEquipe1")


        }





        //getInfoAndUpdateUI("nomEquipe1")



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }




    private fun getInfoAndUpdateUI(id_match:String ,id_info: String ){
        println(55)

        thread {
            try {
                println(50)
                val rep = Client.getInformationMatch(id_match,id_info)
                println(44)
                println(rep)
                runOnUiThread(Runnable {})
            }
            catch (e:Exception) {
                Snackbar.make(
                    findViewById(R.id.fab),
                    "Probleme de connection",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
        }


    }

}
