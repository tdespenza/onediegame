/**
 *
 */
package com.sample.onediegame

/**
 * @author tdespenza
 *
 */
class Human extends Player {
    def turnDie(Integer selection, GameBoard gameBoard) {
        gameBoard.turnDie(selection, this)
        gameBoard
    }

    def rollDie(Random random, GameBoard gameBoard) {
        gameBoard.rollDie(random, this)
        gameBoard
    }
}
