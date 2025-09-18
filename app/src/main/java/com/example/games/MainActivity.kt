package com.example.memorama

import android.animation.Animator
import android.R.drawable
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.viewfinder.core.ScaleType
import androidx.core.content.ContextCompat
import com.example.games.R

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var movesText: TextView
    private lateinit var pairsText: TextView
    private lateinit var resetButton: Button

    private val cards = mutableListOf<ImageButton>()
    private val cardSymbols = mutableListOf<Int>()
    private var firstCard: ImageButton? = null
    private var secondCard: ImageButton? = null
    private var isProcessing = false
    private var moves = 0
    private var pairsFound = 0
    private val totalPairs = 18

    // Símbolos disponibles (18 diferentes para 36 cartas)
    private val symbols = listOf(
        R.drawable.symbol_circle,
        R.drawable.symbol_square,
        R.drawable.symbol_triangle,
        R.drawable.symbol_star,
        R.drawable.symbol_heart,
        R.drawable.symbol_diamond,
        R.drawable.symbol_hexagon,
        R.drawable.symbol_octagon,
        R.drawable.symbol_cross,
        R.drawable.symbol_moon,
        R.drawable.symbol_sun,
        R.drawable.symbol_arrow,
        R.drawable.symbol_lightning,
        R.drawable.symbol_flower,
        R.drawable.symbol_crown,
        R.drawable.symbol_shield,
        R.drawable.symbol_key,
        R.drawable.symbol_bell
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        movesText = findViewById(R.id.movesText)
        pairsText = findViewById(R.id.pairsText)
        resetButton = findViewById(R.id.resetButton)

        resetButton.setOnClickListener {
            resetGame()
        }

        initializeGame()
    }

    private fun initializeGame() {
        // Crear pares de símbolos
        cardSymbols.clear()
        symbols.forEach { symbol ->
            cardSymbols.add(symbol)
            cardSymbols.add(symbol)
        }
        cardSymbols.shuffle()

        // Crear las cartas
        for (i in 0 until 36) {
            val card = ImageButton(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    columnSpec = GridLayout.spec(i % 6, 1f)
                    rowSpec = GridLayout.spec(i / 6, 1f)
                    setMargins(8, 8, 8, 8)
                }
                setBackgroundResource(R.drawable.card_back)
                setScaleType(android.widget.ImageView.ScaleType.CENTER_INSIDE)
                setPadding(16, 16, 16, 16)
                tag = i
                setOnClickListener { onCardClick(this) }
            }
            cards.add(card)
            gridLayout.addView(card)
        }

        updateUI()
    }

    private fun onCardClick(card: ImageButton) {
        if (isProcessing || card == firstCard || card == secondCard) return
        if (card.tag as? Int == -1) return // Carta ya encontrada

        flipCard(card, true)

        if (firstCard == null) {
            firstCard = card
        } else if (secondCard == null) {
            secondCard = card
            moves++
            updateUI()
            checkMatch()
        }
    }

    private fun flipCard(card: ImageButton, show: Boolean) {
        val animator = ObjectAnimator.ofFloat(card, "rotationY", 0f, 90f)
        animator.duration = 150
        animator.interpolator = AccelerateDecelerateInterpolator()

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (show) {
                    val index = card.tag as Int
                    card.setImageResource(cardSymbols[index])
                    card.setBackgroundResource(R.drawable.card_front)
                } else {
                    card.setImageResource(0)
                    card.setBackgroundResource(R.drawable.card_back)
                }

                val animator2 = ObjectAnimator.ofFloat(card, "rotationY", 90f, 0f)
                animator2.duration = 150
                animator2.interpolator = AccelerateDecelerateInterpolator()
                animator2.start()
            }
        })

        animator.start()
    }

    private fun checkMatch() {
        isProcessing = true
        val firstIndex = firstCard?.tag as Int
        val secondIndex = secondCard?.tag as Int

        Handler(Looper.getMainLooper()).postDelayed({
            if (cardSymbols[firstIndex] == cardSymbols[secondIndex]) {
                // ¡Par encontrado!
                firstCard?.apply {
                    isEnabled = false
                    tag = -1
                    animate().alpha(0.5f).duration = 300
                }
                secondCard?.apply {
                    isEnabled = false
                    tag = -1
                    animate().alpha(0.5f).duration = 300
                }
                pairsFound++
                updateUI()

                if (pairsFound == totalPairs) {
                    showWinMessage()
                }
            } else {
                // No coinciden
                flipCard(firstCard!!, false)
                flipCard(secondCard!!, false)
            }

            firstCard = null
            secondCard = null
            isProcessing = false
        }, 1000)
    }

    private fun updateUI() {
        movesText.text = "Movimientos: $moves"
        pairsText.text = "Pares: $pairsFound/$totalPairs"
    }

    private fun resetGame() {
        moves = 0
        pairsFound = 0
        firstCard = null
        secondCard = null
        isProcessing = false

        gridLayout.removeAllViews()
        cards.clear()

        initializeGame()
    }

    private fun showWinMessage() {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("¡Felicitaciones!")
            .setMessage("Has completado el memorama en $moves movimientos")
            .setPositiveButton("Nuevo Juego") { _, _ -> resetGame() }
            .setCancelable(false)
            .create()
        dialog.show()
    }
}