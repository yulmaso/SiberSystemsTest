package org.example

class RectangleParser {
    private val rectanglePattern = "[0-9-]*\\s?,\\s?[0-9-]*\\s?;\\s?[0-9-]*\\s?,\\s?[0-9-]*\n?".toRegex()
    private val separatorRegex = "[,;]".toRegex()
    private val redundantSymbolsRegex = "[^0-9-]".toRegex()

    fun parse(input: String): List<Rectangle> {
        val rectangleStrings = rectanglePattern.findAll(input).map { it.value }.toList()
        return rectangleStrings.map { rectangleString ->
            val coordinates = rectangleString.split(separatorRegex).map {
                redundantSymbolsRegex.replace(it, "").toInt()
            }
            Rectangle(
                x1 = coordinates[0],
                y1 = coordinates[1],
                x2 = coordinates[2],
                y2 = coordinates[3]
            )
        }
    }
}