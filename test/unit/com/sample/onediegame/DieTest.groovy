package com.sample.onediegame

import org.gmock.WithGMock
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: tdespenza
 * Date: 11/30/13
 * Time: 7:10 AM
 * To change this template use File | Settings | File Templates.
 */
@WithGMock
class DieTest extends GroovyTestCase {
    def classUnderTest
    def randomMock

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    void setUp() throws Exception {
        classUnderTest = new Die()
        randomMock = mock(Random)
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
    void testRoll() {
        randomMock.nextInt(5).returns(5)

        play {
            classUnderTest.roll(randomMock)

            assertEquals(6, classUnderTest.value)
            assertEquals([2, 3, 4, 5], classUnderTest.validSelections)
        }
    }

    @Test
    void testTurnTo() {
        play {
            classUnderTest.turnTo(5)

            assertEquals(5, classUnderTest.value)
            assertEquals([1, 3, 4, 6], classUnderTest.validSelections)
        }
    }

    @Test
    void testIsValidSelection() {
        play {
            classUnderTest.validSelections = [1, 3, 4, 6]

            assertFalse(classUnderTest.isValidSelection(5))
            assertTrue(classUnderTest.isValidSelection(6))
        }
    }
}
