/**
 *
 */
package com.sample.onediegame

/**
 * @author tdespenza
 *
 */
class GameBoard {
    def players
    def lastPlayer
    def die
    def winner
    def loser
    def score = 0
    final def bust = 31

    def addScore(int selection, Player player) {
        score += selection
        lastPlayer = player
        println "Score: $score\n"
    }

    def getCurrentPlayer() {
        (players - lastPlayer)[0]
    }

    def shouldEndGame() {
        if (score > bust) {
            loser = lastPlayer
            winner = getCurrentPlayer()
            return true
        }

        if (score == bust) {
            winner = lastPlayer
            loser = getCurrentPlayer()
            return true
        }

        false
    }

    def endGame() {
        println "${loser.name} lost."
        println "${winner.name} wins!"
    }

    def rollDie(Random random, Player player) {
        die.roll(random)
        println "${player.name} rolls a ${die.value}"
        addScore(die.value, player)
    }

    def turnDie(int selection, Player player) {
        die.turnTo(selection)
        println "${player.name} selected ${die.value}"
        addScore(die.value, player)
    }

    def getValidSelections(boolean prettyPrint = false) {
        prettyPrint ? die.validSelections.toString().replaceAll(/[\[\]]/, '') : die.validSelections
    }

    def isValidSelection(int selection) {
        die.isValidSelection(selection)
    }

    def getValue(int index) {
        die.validSelections[index]
    }

    def ensureCorrectSelection(int selection) {
        if (!isValidSelection(selection)) {
            def validSelections = die.validSelections.reverse()
            selection = validSelections.findResult(getValue(0)) { it < selection ? it : null }
        }

        selection
    }
}