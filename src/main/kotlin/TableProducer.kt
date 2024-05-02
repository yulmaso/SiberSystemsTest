package org.example


class TableProducer {
    fun buildTable(rectangles: List<Rectangle>): List<Row> {
        val sortedX = rectangles.flatMap { listOf(it.x1, it.x2) }.sorted()
        val sortedY = rectangles.flatMap { listOf(it.y1, it.y2) }.sorted()

        val columnsWidth = getLinesWidth(sortedX.distinct())
        val rowsHeight = getLinesWidth(sortedY.distinct())

        val minX = sortedX.first()
        val minY = sortedY.first()

        var columnAccumulator = 0
        var rowAccumulator = 0

        val table = rowsHeight.map { height ->
            rowAccumulator += height
            columnAccumulator = 0
            Row(
                height = height,
                cells = columnsWidth.map { width ->
                    columnAccumulator += width
                    val indexOfRect = rectangles.indexOfFirst {
                        it.containsDot(
                            dotX = columnAccumulator + minX,
                            dotY = rowAccumulator + minY
                        )
                    }
                    val classNum = indexOfRect.takeIf { it != -1 }
                    Row.Cell(
                        width = width,
                        classNum = classNum
                    )
                }
            )
        }

        return table
    }

    private fun Rectangle.containsDot(dotX: Int, dotY: Int): Boolean {
        return dotX in (x1 + 1)..x2 && dotY in (y1 + 1)..y2
    }

    private fun getLinesWidth(sortedDots: List<Int>): List<Int> {
        val lines = mutableListOf<Int>()
        for (i in sortedDots.indices) {
            if (i != 0) {
                lines.add(sortedDots[i] - sortedDots[i - 1])
            }
        }
        return lines
    }
}