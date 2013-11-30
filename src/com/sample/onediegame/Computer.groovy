/**
 *
 */
package com.sample.onediegame

/**
 * @author tdespenza
 *
 */
class Computer extends Player {
    def makeDecision(GameBoard gameBoard, Random random) {
        println "...choosing from ${gameBoard.getValidSelections(true)}..."

        Thread.sleep(3000)

        def selection = (gameBoard.score < 25
                            ? gameBoard.getValue(random.nextInt(gameBoard.getValidSelections().size() - 1))
                            : gameBoard.bust - gameBoard.score)

        gameBoard.turnDie(gameBoard.ensureCorrectSelection(selection), this)
        gameBoard
    }
}
