/*
 * Copyright (c) ${YEAR} Mobile CAPPtivate, LLC
 * ${PRODUCT_NAME}
 * ${PROJECT_NAME}
 * ${PACKAGE_NAME}
 * ${FILE_NAME}
 * ${DATE} ${TIME}
 * ${USER}
 */

package com.sample.onediegame

import org.gmock.WithGMock
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: tdespenza
 * Date: 11/29/13
 * Time: 6:41 PM
 * To change this template use File | Settings | File Templates.
 */
@WithGMock
class ComputerTest extends GroovyTestCase {
    def classUnderTest
    def gameBoardMock
    def randomMock

    /**
     * @throws java.lang.Exception
     */
    @Before
    void setUp() throws Exception {
        classUnderTest = new Computer(name: "Computer Test")
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
    void testMakeDecisionWhenScoreUnderTwentyFive() {
        gameBoardMock.getValidSelections(true).returns("1, 2, 5, 6")
        gameBoardMock.getValidSelections().returns([1, 2, 5, 6])
        gameBoardMock.score.returns(4)
        randomMock.nextInt(3).returns(2)
        gameBoardMock.getValue(2).returns(5)
        gameBoardMock.ensureCorrectSelection(5).returns(5)
        gameBoardMock.turnDie(5, classUnderTest).once()

        play {
            gameBoardMock = classUnderTest.makeDecision(gameBoardMock, randomMock)

            assertNotNull(gameBoardMock)
            assertEquals("Computer Test", classUnderTest.name)
        }
    }

    @Test
    void testMakeDecisionWhenScoreOverTwentyFourAndValidSelection() {
        gameBoardMock.getValidSelections(true).returns("1, 2, 5, 6")
        gameBoardMock.score.returns(26).times(2)
        gameBoardMock.bust.returns(31)
        gameBoardMock.ensureCorrectSelection(5).returns(5)
        gameBoardMock.turnDie(5, classUnderTest).once()

        play {
            gameBoardMock = classUnderTest.makeDecision(gameBoardMock, randomMock)

            assertNotNull(gameBoardMock)
            assertEquals("Computer Test", classUnderTest.name)
        }
    }

    @Test
    void testMakeDecisionWhenScoreOverTwentyFourAndInvalidSelection() {
        gameBoardMock.getValidSelections(true).returns("1, 2, 5, 6")
        gameBoardMock.score.returns(27).times(2)
        gameBoardMock.bust.returns(31)
        gameBoardMock.ensureCorrectSelection(4).returns(2)
        gameBoardMock.turnDie(2, classUnderTest).once()

        play {
            gameBoardMock = classUnderTest.makeDecision(gameBoardMock, randomMock)

            assertNotNull(gameBoardMock)
            assertEquals("Computer Test", classUnderTest.name)
        }
    }

    @Test
    void testMakeDecisionWhenScoreEqualsThirtyAndMinValidSelectionIsGreaterThanOne() {
        gameBoardMock.getValidSelections(true).returns("2, 3, 4, 5")
        gameBoardMock.score.returns(30).times(2)
        gameBoardMock.bust.returns(31)
        gameBoardMock.ensureCorrectSelection(1).returns(5)
        gameBoardMock.turnDie(5, classUnderTest).once()

        play {
            gameBoardMock = classUnderTest.makeDecision(gameBoardMock, randomMock)

            assertNotNull(gameBoardMock)
            assertEquals("Computer Test", classUnderTest.name)
        }
    }
}