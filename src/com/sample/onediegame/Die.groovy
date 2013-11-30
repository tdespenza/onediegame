/**
 *
 */
package com.sample.onediegame

/**
 * @author tdespenza
 *
 */
class Die {
    def value
    def validSelections = []
    final def values = [1, 2, 3, 4, 5, 6]

    def turnTo(int selection) {
        value = selection
        updateValidSelections()
    }

    def roll(Random random) {
        println("Rolling die...")

        value = values[random.nextInt(values.size() - 1)]
        updateValidSelections()
    }

    def updateValidSelections() {
        validSelections = (values - [value, 7 - value]).sort()
    }

    def isValidSelection(int selection) {
        validSelections.contains(selection)
    }
}
