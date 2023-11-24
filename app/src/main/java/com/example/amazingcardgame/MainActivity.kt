package com.example.amazingcardgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), Adaptador.OnItemClickListener {
    private lateinit var adaptador: Adaptador
    private lateinit var toolBar: Toolbar
    private var cards: List<Carta>
    private var puntuacion: Int = 0
    private var vidas: Int = 5

    init {
        cards = listOf(
            Carta(Item("thwomp", R.drawable.thwomp), false, false),
            Carta(Item("fireflower", R.drawable.fire_flower), false, false),
            Carta(Item("brickwall", R.drawable.brick_wall), false, false),
            Carta(Item("bulletbill", R.drawable.bullet_bill), false, false),
            Carta(Item("carnivorous", R.drawable.carnivorous_plant), false, false),
            Carta(Item("roundstar", R.drawable.round_star), false, false),
            Carta(Item("tortoise", R.drawable.tortoise), false, false),
            Carta(Item("warppipe", R.drawable.warp_pipe), false, false),
            Carta(Item("thwomp", R.drawable.thwomp), false, false),
            Carta(Item("fireflower", R.drawable.fire_flower), false, false),
            Carta(Item("brickwall", R.drawable.brick_wall), false, false),
            Carta(Item("bulletbill", R.drawable.bullet_bill), false, false),
            Carta(Item("carnivorous", R.drawable.carnivorous_plant), false, false),
            Carta(Item("roundstar", R.drawable.round_star), false, false),
            Carta(Item("tortoise", R.drawable.tortoise), false, false),
            Carta(Item("warppipe", R.drawable.warp_pipe), false, false)
        ).shuffled()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        empezarJuego()


        findViewById<NavigationView>(R.id.nav_view).setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.pokemode -> {
                    cambiarBaraja(1)
                    true
                }

                R.id.mariomode -> {
                    cambiarBaraja(2)
                    true
                }

                R.id.hollowknightmode -> {
                    cambiarBaraja(3)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.abrirnav -> {
                findViewById<DrawerLayout>(R.id.drawer_layout).openDrawer(GravityCompat.START)
            }

            R.id.reset -> {
                reiniciarJuego()
            }
        }
        return true
    }

    private fun empezarJuego() {
        val rv = findViewById<RecyclerView>(R.id.recycler)
        adaptador = Adaptador(cards, this)
        rv.layoutManager = GridLayoutManager(this, 4)
        findViewById<NavigationView>(R.id.nav_view).setBackgroundResource(R.drawable.fondobarra)
        rv.adapter = adaptador
    }

    override fun onItemClick(position: Int) {
        val carta = cards[position]

        if (!carta.destapada && !carta.acertada) {
            carta.destapada = true
            adaptador.notifyDataSetChanged()

            val parejasDestapadas = cards.filter { it.destapada && it != carta && !it.acertada }
            if (parejasDestapadas.size == 1) {
                val pareja = parejasDestapadas[0]

                if (carta.item.imgid == pareja.item.imgid) {
                    carta.acertada = true
                    pareja.acertada = true
                    puntuacion++

                    if (puntuacion == cards.size / 2) {
                        Toast.makeText(
                            this,
                            "¡Has encontrado todas las parejas!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Handler().postDelayed({
                        carta.destapada = false
                        pareja.destapada = false
                        when (vidas){
                            5 ->{
                                vidas = vidas -1
                                findViewById<ImageView>(R.id.heart5).visibility = View.INVISIBLE
                            }

                            4 ->{
                                vidas = vidas -1
                                findViewById<ImageView>(R.id.heart4).visibility = View.INVISIBLE
                            }

                            3 ->{
                                vidas = vidas -1
                                findViewById<ImageView>(R.id.heart3).visibility = View.INVISIBLE
                            }

                            2 ->{
                                vidas = vidas -1
                                findViewById<ImageView>(R.id.heart2).visibility = View.INVISIBLE
                            }

                            1 ->{
                                vidas = vidas -1
                                findViewById<ImageView>(R.id.heart1).visibility = View.INVISIBLE
                            }

                            0 ->{
                                Toast.makeText(
                                    this,
                                    "¡Has perdido!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                reiniciarJuego()
                            }
                        }
                        adaptador.notifyDataSetChanged()
                    }, 500)
                }
            }
        }
    }

    private fun reiniciarJuego() {
        puntuacion = 0
        cards.forEach { it.destapada = false; it.acertada = false }
        cards = cards.shuffled()
        adaptador.cartas = cards
        findViewById<ImageView>(R.id.heart1).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.heart2).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.heart3).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.heart4).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.heart5).visibility = View.VISIBLE
        vidas = 5
        adaptador.notifyDataSetChanged()
    }

    private fun cambiarBaraja(modo: Int) {
        when (modo) {
            1 -> {
                cards = listOf(
                    Carta(Item("arceus", R.drawable.arceus), false, false),
                    Carta(Item("darkrai", R.drawable.darkrai), false, false),
                    Carta(Item("giratina", R.drawable.giratina), false, false),
                    Carta(Item("tentacruel", R.drawable.tentacruel), false, false),
                    Carta(Item("venusaur", R.drawable.venusaur), false, false),
                    Carta(Item("voltorb", R.drawable.voltorb), false, false),
                    Carta(Item("zapdos", R.drawable.zapdos), false, false),
                    Carta(Item("rapidash", R.drawable.rapidash), false, false),
                    Carta(Item("arceus", R.drawable.arceus), false, false),
                    Carta(Item("darkrai", R.drawable.darkrai), false, false),
                    Carta(Item("giratina", R.drawable.giratina), false, false),
                    Carta(Item("tentacruel", R.drawable.tentacruel), false, false),
                    Carta(Item("venusaur", R.drawable.venusaur), false, false),
                    Carta(Item("voltorb", R.drawable.voltorb), false, false),
                    Carta(Item("zapdos", R.drawable.zapdos), false, false),
                    Carta(Item("rapidash", R.drawable.rapidash), false, false)
                ).shuffled()
                reiniciarJuego()
                adaptador.notifyDataSetChanged()
                findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
                findViewById<ConstraintLayout>(R.id.contraint).setBackgroundResource(R.drawable.fondopokemon)
            }

            2 -> {
                cards = listOf(
                    Carta(Item("thwomp", R.drawable.thwomp), false, false),
                    Carta(Item("fireflower", R.drawable.fire_flower), false, false),
                    Carta(Item("brickwall", R.drawable.brick_wall), false, false),
                    Carta(Item("bulletbill", R.drawable.bullet_bill), false, false),
                    Carta(Item("carnivorous", R.drawable.carnivorous_plant), false, false),
                    Carta(Item("roundstar", R.drawable.round_star), false, false),
                    Carta(Item("tortoise", R.drawable.tortoise), false, false),
                    Carta(Item("warppipe", R.drawable.warp_pipe), false, false),
                    Carta(Item("thwomp", R.drawable.thwomp), false, false),
                    Carta(Item("fireflower", R.drawable.fire_flower), false, false),
                    Carta(Item("brickwall", R.drawable.brick_wall), false, false),
                    Carta(Item("bulletbill", R.drawable.bullet_bill), false, false),
                    Carta(Item("carnivorous", R.drawable.carnivorous_plant), false, false),
                    Carta(Item("roundstar", R.drawable.round_star), false, false),
                    Carta(Item("tortoise", R.drawable.tortoise), false, false),
                    Carta(Item("warppipe", R.drawable.warp_pipe), false, false)
                ).shuffled()
                reiniciarJuego()
                adaptador.notifyDataSetChanged()
                findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
                findViewById<ConstraintLayout>(R.id.contraint).setBackgroundResource(R.drawable.fondojuego)
            }

            3 -> {
                cards = listOf(
                    Carta(Item("Uumuu", R.drawable.uumuu), false, false),
                    Carta(Item("traitorlord", R.drawable.traitor_lord), false, false),
                    Carta(Item("marmu", R.drawable.marmu), false, false),
                    Carta(Item("hornet", R.drawable.hornet), false, false),
                    Carta(Item("radiance", R.drawable.radiance), false, false),
                    Carta(Item("brokenvessel", R.drawable.broken_vessel), false, false),
                    Carta(Item("crystalguard", R.drawable.crystal_guardian), false, false),
                    Carta(Item("elderhu", R.drawable.elder_hu), false, false),
                    Carta(Item("Uumuu", R.drawable.uumuu), false, false),
                    Carta(Item("traitorlord", R.drawable.traitor_lord), false, false),
                    Carta(Item("marmu", R.drawable.marmu), false, false),
                    Carta(Item("hornet", R.drawable.hornet), false, false),
                    Carta(Item("radiance", R.drawable.radiance), false, false),
                    Carta(Item("brokenvessel", R.drawable.broken_vessel), false, false),
                    Carta(Item("crystalguard", R.drawable.crystal_guardian), false, false),
                    Carta(Item("elderhu", R.drawable.elder_hu), false, false)
                ).shuffled()
                reiniciarJuego()
                adaptador.notifyDataSetChanged()
                findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
                findViewById<ConstraintLayout>(R.id.contraint).setBackgroundResource(R.drawable.hollowkightfondo)
            }
        }
    }
}