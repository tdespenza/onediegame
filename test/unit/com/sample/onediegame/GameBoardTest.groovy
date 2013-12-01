package com.sample.onediegame

import org.gmock.WithGMock
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: tdespenza
 * Date: 11/29/13
 * Time: 7:58 PM
 * To change this template use File | Settings | File Templates.
 */
@WithGMock
class GameBoardTest extends GroovyTestCase {
    def humanMock
    def computerMock
    def playersMock
    def dieMock
    def classUnderTest
    def randomMock

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    void setUp() throws Exception {
        humanMock = mock(Human)
        computerMock = mock(Computer)
        playersMock = [humanMock, computerMock]
        dieMock = mock(Die)
        randomMock = mock(Random)
        classUnderTest = new GameBoard(players: playersMock, die: dieMock)
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    void tearDown() throws Exception {
        humanMock = null
        computerMock = null
        playersMock = null
        dieMock = null
        randomMock = null
        classUnderTest = null
    }

    @Test
    void testRollDie() {
        dieMock.roll(randomMock).once()
        humanMock.name.returns("Test Human")
        dieMock.value.returns(6).times(3)

        play {
            classUnderTest.rollDie(randomMock, humanMock)

            assertSame(humanMock, classUnderTest.lastPlayer)
            assertEquals(6, classUnderTest.score)
            assertEquals(6, dieMock.value)
        }
    }

    @Test
    void testTurnDie() {
        dieMock.turnTo(5).once()
        humanMock.name.returns("Test Human")
        dieMock.value.returns(5).times(3)

        play {
            classUnderTest.score = 6
            classUnderTest.turnDie(5, humanMock)

            assertSame(humanMock, classUnderTest.lastPlayer)
            assertEquals(11, classUnderTest.score)
            assertEquals(5, dieMock.value)
        }
    }

    @Test
    void testShouldEndGame() {
        play {
            classUnderTest.score = 15
            assertFalse(classUnderTest.shouldEndGame())
        }
    }

    @Test
    void testShouldEndGameWhenScoreGreaterThanBustAndCurrentPlayerIsWinner() {
        humanMock.name.returns("Test Human")
        computerMock.name.returns("Computer Test")

        play {
            classUnderTest.lastPlayer = humanMock
            classUnderTest.score = 32
            def shouldEndGame = classUnderTest.shouldEndGame()
            def currentPlayer = classUnderTest.getCurrentPlayer()
            classUnderTest.endGame()

            assertTrue(shouldEndGame)
            assertNotSame(humanMock, currentPlayer)
            assertTrue(currentPlayer instanceof Computer)
            assertSame(humanMock, classUnderTest.loser)
            assertSame(currentPlayer, classUnderTest.winner)
        }
    }

    @Test
    void testShouldEndGameWhenScoreEqualsBustAndCurrentPlayerIsLoser() {
        humanMock.name.returns("Test Human")
        computerMock.name.returns("Computer Test")

        play {
            classUnderTest.lastPlayer = humanMock
            classUnderTest.score = 31
            def shouldEndGame = classUnderTest.shouldEndGame()
            def currentPlayer = classUnderTest.getCurrentPlayer()
            classUnderTest.endGame()

            assertTrue(shouldEndGame)
            assertNotSame(humanMock, currentPlayer)
            assertTrue(currentPlayer instanceof Computer)
            assertSame(humanMock, classUnderTest.winner)
            assertSame(currentPlayer, classUnderTest.loser)
        }
    }

    @Test
    void testGetValidSelectionsWithPrettyPrint() {
        dieMock.validSelections.returns([1, 3, 4, 6])

        play {
            assertEquals("1, 3, 4, 6", classUnderTest.getValidSelections(true))
        }
    }

    @Test
    void testGetValidSelectionsWithoutPrettyPrint() {
        dieMock.validSelections.returns([1, 3, 4, 6])

        play {
            assertEquals([1, 3, 4, 6], classUnderTest.getValidSelections())
        }
    }

    @Test
    void testGetValueWhenValueIsValid() {
        dieMock.validSelections.returns([1, 3, 4, 6])

        play {
            assertEquals(4, classUnderTest.getValue(2))
        }
    }

    @Test
    void testGetValueWhenValueIsInvalid() {
        dieMock.validSelections.returns([1, 3, 4, 6])

        play {
            assertEquals(null, classUnderTest.getValue(4))
        }
    }

    @Test
    void testEnsureCorrectSelectionWithValidSelection() {
        dieMock.isValidSelection(3).returns(true)

        play {
            assertEquals(3, classUnderTest.ensureCorrectSelection(3))
        }
    }

    @Test
    void testEnsureCorrectSelectionWithInvalidSelection() {
        dieMock.isValidSelection(1).returns(false)
        dieMock.validSelections.returns([2, 3, 4, 5]).times(2)

        play {
            classUnderTest.score = 30
            assertEquals(2, classUnderTest.ensureCorrectSelection(1))
        }
    }
}
