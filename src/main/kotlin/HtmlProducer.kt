package org.example

class HtmlProducer {
    private val colors by lazy {
        listOf("#FF0000", "#FFFF00", "#00FF00", "#00FFFF", "#0000FF", "#FF00FF", "#800080", "#808000", "#800000")
    }

    fun buildRectanglesTableHtml(table: List<Row>, rectangleCount: Int): String {
        val style = buildString {
            (0..<rectangleCount).forEach { classIndex ->
                val color = colors[classIndex % colors.size]
                val className = "$CLASS_NAME$classIndex"
                append(".$className{background-color:$color}")
            }
        }

        val body = buildString {
            append("<table>")
            table.forEach { row ->
                append("<tr>")
                row.cells.forEach { cell ->
                    append("<td style=\"width:${cell.width}px;height:${row.height}px\"")
                    if (cell.classNum != null) {
                        append("class=\"$CLASS_NAME${cell.classNum}\"")
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

    companion object {
        private const val CLASS_NAME = "class_"
    }
}