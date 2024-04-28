package org.example

import java.util.Collections.max
import java.util.Collections.min

class HtmlProducer {
    private val colors by lazy {
        listOf("#FF0000", "#FFFF00", "#00FF00", "#00FFFF", "#0000FF", "#FF00FF", "#800080", "#808000", "#800000")
    }

    fun buildRectanglesTableHtml(rectangles: List<Rectangle>): String {
        val matrix = buildMatrix(rectangles)
        val classes = List(rectangles.size) { index -> "class_$index" }

        val style = buildString {
            classes.forEachIndexed { index, className ->
                val color = colors[index % colors.size]
                append(".$className{background-color:$color}")
            }
        }

        val body = buildString {
            append("<table>")
            matrix.forEach { row ->
                append("<tr>")
                row.forEach { classIndex ->
                    append("<td $CELL_STYLE")
                    if (classIndex != null) {
                        append("class=\"${classes[classIndex]}\"")
                    }
                    append("></td>")
                }
                append("</tr>")
            }
            append("</table>")
        }

        return buildHtmlString(
            style = style,
            body = body
        )
    }

    private fun buildHtmlString(style: String, body: String): String {
        return "<html><head><style>$style</style></head><body>$body</body></html>"
    }

    private fun buildMatrix(rectangles: List<Rectangle>): Array<Array<Int?>> {
        var maxX = Int.MIN_VALUE
        var maxY = Int.MIN_VALUE
        var minX = Int.MAX_VALUE
        var minY = Int.MAX_VALUE

        rectangles.forEach { rect ->
            maxX = max(listOf(maxX, rect.x1, rect.x2))
            minX = min(listOf(minX, rect.x1, rect.x2))
            maxY = max(listOf(maxY, rect.y1, rect.y2))
            minY = min(listOf(minY, rect.y1, rect.y2))
        }

        val rowsSize = maxY - minY + 1
        val columnsSize = maxX - minX + 1

        val result = Array(rowsSize) { Array<Int?>(columnsSize) { null } }

        rectangles.forEachIndexed { index, rect ->
            for (y in rect.y1 toward rect.y2) {
                for (x in rect.x1 toward rect.x2) {
                    result[y - minY][x - minX] = index
                }
            }
        }

        return result
    }

    private infix fun Int.toward(other: Int): IntProgression {
        val step = if (this > other) -1 else 1
        return IntProgression.fromClosedRange(this, other, step)
    }

    companion object {
        private const val CELL_STYLE = "style=\"width:50px;height:50px\""
    }
}