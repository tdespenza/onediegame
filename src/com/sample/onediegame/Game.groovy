/**
 *
 */
package com.sample.onediegame

/**
 * @author tdespenza
 *
 */
class Game {

    static main(args) {
        def human = new Human(name: "Human")
        def computer = new Computer(name: "Computer")
        def gameBoard = new GameBoard(players: [human, computer], die: new Die())
        def random = new Random(Calendar.instance.timeInMillis)
        def scanner = new Scanner(System.in)
        def game = new Game()

        game.play(human, computer, gameBoard, random, scanner)
    }

    def play(Player human, Player computer, GameBoard gameBoard, Random random, Scanner scanner) {
        promptToStart(human, scanner)

        gameBoard = human.rollDie(random, gameBoard)

        while (true) {
            println "My turn now $human.name..."
            gameBoard = computer.makeDecision(gameBoard, random)

            if (gameBoard.shouldEndGame()) {
                gameBoard.endGame()
                break
            }

            def selection
            while (!selection || !gameBoard.isValidSelection(selection)) {
                print "Your turn $human.name (choices: ${gameBoard.getValidSelections(true)}):\t"
                try {
                    selection = scanner.nextInt()

                } catch (Exception e) {
                    scanner.next()
                    println "Choose from the available choices $human.name"
                }
            }
            println ""

            gameBoard = human.turnDie(selection, gameBoard)

            if (gameBoard.shouldEndGame()) {
                gameBoard.endGame()
                break
            }
        }
    }

    def promptToStart(Player player, Scanner scanner) {
        def roll
        while (!roll || !(roll ==~ /(?i)[r]/)) {
            print "$player.name...press (R) to roll and start the game:\t"
            roll = scanner.next()
        }
        println ""
    }
}
