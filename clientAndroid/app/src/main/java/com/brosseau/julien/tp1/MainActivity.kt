package com.brosseau.julien.tp1

import android.content.Context
import android.os.Bundle
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
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_send.*
import kotlinx.android.synthetic.main.fragment_tools.*
import kotlinx.android.synthetic.main.content_main.nav_host_fragment
import kotlinx.coroutines.*
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
            refreshFragment()
           //getInfoAndUpdateUI("2","nomEquipe1",null)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_match_1, R.id.nav_match_2,
                R.id.nav_match_3,R.id.nav_match_4,R.id.nav_match_5,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //getInfoAndUpdateUI("2","nomEquipe1", null)




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


    fun getMatchsAndUpdateUI(textviewAUpdate: TextView?){
        thread {
            try {

                val rep = Client.getMatchsEnCours(this)
                println(rep[0])
                runOnUiThread(Runnable {
                    try{
                        updateUI(textviewAUpdate, rep[0])
                        hidePasMatchs(rep[1].toInt())
                    }
                    catch (e:Exception){
                        print(e.toString())
                    }
                })
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

    fun getInfoAndUpdateUI(id_match:String ,id_info: String, textviewAUpdate:TextView? ){
        thread {
            try {

                var rep = Client.getInformationMatch(id_match,id_info,this)
                if(id_info == "chronometreSec"){
                    try {
                        rep = "${rep!!.toInt() / 60}:${rep.toInt() % 60}"
                    }
                    catch (e:Exception){println(e.toString())}
                }
                println(rep)
                runOnUiThread(Runnable {
                    try{
                        updateUI(textviewAUpdate, rep)
                    }
                    catch (e:Exception){

                    }
                })
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

    private fun updateUI(textviewAUpdate:TextView?, reponse:String?) {
        if((textviewAUpdate != null) and (reponse != null)){
            textviewAUpdate!!.text = reponse
        }

    }


    fun refreshFragment() {
        //find current fragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val frag = navHostFragment!!.childFragmentManager.fragments[0]
        //refresh it
        val ft = navHostFragment!!.childFragmentManager.beginTransaction()
        ft.detach(frag)
        ft.attach(frag)
        ft.commit()
    }

    fun validerParam(view: View){
        val IPServ = ipServ.text.toString()
        val pBet = portBet.text.toString()
        val pMatch = portMatch.text.toString()

        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(getString(R.string.saved_ip), IPServ)
            putString(getString(R.string.saved_pBet), pBet)
            putString(getString(R.string.saved_pMatch), pMatch)
            commit()
        }






    }

    fun validerParis(view: View) {
        val montant = montantPari.text.toString()
        val equipe = Equipepari.text.toString()
        val match = IDMatchPari.text.toString()


        thread {
            try {

                val rep = Client.createBet(montant, match, equipe,this)
                println(rep)
                runOnUiThread(Runnable {
                    try {

                    } catch (e: Exception) {

                    }
                })
            } catch (e: Exception) {
                Snackbar.make(
                    findViewById(R.id.fab),
                    "Probleme de connection pour le pari ",
                    Snackbar.LENGTH_LONG

                )
                    .show()
                println(e.toString())
            }
        }
    }



    fun getIP():String{
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val ip = resources.getString(R.string.saved_ip)
        return ip
    }


    fun getpBet():Int {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val pBet = resources.getString(R.string.saved_pBet)
        return pBet.toInt()
    }

    fun getpMatch():Int {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val pMatch = resources.getString(R.string.saved_pMatch)
        return pMatch.toInt()
    }

    fun hidePasMatchs(nbreMatchs:Int){
        if(nbreMatchs<5) findViewById<View>(R.id.nav_match_5).isVisible = false
        if(nbreMatchs<4) findViewById<View>(R.id.nav_match_4).isVisible = false
        if(nbreMatchs<3) findViewById<View>(R.id.nav_match_3).isVisible = false
        if(nbreMatchs<2) findViewById<View>(R.id.nav_match_2).isVisible = false
        if(nbreMatchs<1) findViewById<View>(R.id.nav_match_1).isVisible = false
    }




}
