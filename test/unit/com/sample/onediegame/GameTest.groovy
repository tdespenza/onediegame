package com.sample.onediegame

import org.gmock.WithGMock
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: tdespenza
 * Date: 11/30/13
 * Time: 8:16 AM
 * To change this template use File | Settings | File Templates.
 */
@WithGMock
class GameTest extends GroovyTestCase {
    def classUnderTest
    def randomMock
    def humanMock
    def computerMock
    def gameBoardMock
    def scannerMock

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    void setUp() throws Exception {
        classUnderTest = new Game()
        randomMock = mock(Random)
        humanMock = mock(Human)
        computerMock = mock(Computer)
        gameBoardMock = mock(GameBoard)
        scannerMock = mock(Scanner)
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    void tearDown() throws Exception {
        classUnderTest = null
        randomMock = null
    }

    @Test
    void testPlayEndOnFirstGameCheck() {
        humanMock.name.returns("Test Human").atLeast(3)
        scannerMock.next().returns("t").times(2)
        scannerMock.next().returns("r")
        humanMock.rollDie(randomMock, gameBoardMock).returns(gameBoardMock)
        computerMock.makeDecision(gameBoardMock, randomMock).returns(gameBoardMock).atLeastOnce()
        gameBoardMock.shouldEndGame().returns(false).times(2)
        gameBoardMock.getValidSelections(true).returns("1, 2, 5, 6").atLeastOnce()
        scannerMock.nextInt().returns("å").raises(Exception, "an exception happened")
        scannerMock.next().returns("å")
        gameBoardMock.isValidSelection("å").returns(false).times(1..2)
        scannerMock.nextInt().returns(2).atLeastOnce()
        gameBoardMock.isValidSelection(2).returns(true).once()
        humanMock.turnDie(2, gameBoardMock).returns(gameBoardMock).atLeastOnce()
        gameBoardMock.shouldEndGame().returns(true)
        gameBoardMock.endGame()

        play {
            classUnderTest.play(humanMock, computerMock, gameBoardMock, randomMock, scannerMock)
        }
    }

    @Test
    void testPlayEndOnSecondEndGameCheck() {
        humanMock.name.returns("Test Human").atLeast(3)
        scannerMock.next().returns("t").times(2)
        scannerMock.next().returns("r")
        humanMock.rollDie(randomMock, gameBoardMock).returns(gameBoardMock)
        computerMock.makeDecision(gameBoardMock, randomMock).returns(gameBoardMock).atLeastOnce()
        gameBoardMock.shouldEndGame().returns(false)
        gameBoardMock.getValidSelections(true).returns("1, 2, 5, 6").atLeastOnce()
        scannerMock.nextInt().returns(2).atLeastOnce()
        gameBoardMock.isValidSelection(2).returns(true).once()
        humanMock.turnDie(2, gameBoardMock).returns(gameBoardMock).atLeastOnce()
        gameBoardMock.shouldEndGame().returns(true)
        gameBoardMock.endGame()

        play {
            classUnderTest.play(humanMock, computerMock, gameBoardMock, randomMock, scannerMock)
        }
    }
}
