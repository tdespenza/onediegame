package com.sample.onediegame

import org.gmock.WithGMock
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: tdespenza
 * Date: 11/26/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
@WithGMock
class HumanTest extends GroovyTestCase {
    def classUnderTest
    def gameBoardMock
    def randomMock

    /**
     * @throws java.lang.Exception
     */
    @Before
    void setUp() throws Exception {
        classUnderTest = new Human(name: "Test Human")
        gameBoardMock = mock(GameBoard)
        randomMock = mock(Random)
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    void tearDown() throws Exception {
        classUnderTest = null
        gameBoardMock = null
        randomMock = null
    }

    @Test
    void testRollDie() {
        gameBoardMock.rollDie(randomMock, classUnderTest).once()

        play {
            gameBoardMock = classUnderTest.rollDie(randomMock, gameBoardMock)

            assertNotNull(gameBoardMock)
            assertEquals("Test Human", classUnderTest.name)
        }
    }

    @Test
    void testCorrectTurnDieSelection() {
        gameBoardMock.turnDie(1, classUnderTest).once()

        play {
            gameBoardMock = classUnderTest.turnDie(1, gameBoardMock)

            assertNotNull(gameBoardMock)
            assertEquals("Test Human", classUnderTest.name)
        }
    }
}
