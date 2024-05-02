package org.example


class TableProducer {
    fun buildTable(rectangles: List<Rectangle>): List<Row> {
        val sortedX : List<Int> = rectangles.flatMap { listOf(it.x1, it.x2) }.sorted().distinct()
        val sortedY : List<Int> = rectangles.flatMap { listOf(it.y1, it.y2) }.sorted().distinct()

        val columnsWidth : List<Int> = getLinesWidth(sortedX)
        val rowsHeight : List<Int> = getLinesWidth(sortedY)

        val minX = sortedX.first()
        val minY = sortedY.first()

        val table = mutableListOf<Row>()

        var columnAccumulator = 0
        var rowAccumulator = 0

        rowsHeight.forEach { height ->
            rowAccumulator += height
            columnAccumulator = 0
            val cells = mutableListOf<Row.Cell>()

            columnsWidth.forEach { width ->
                columnAccumulator += width
                val indexOfContainingRect = rectangles.indexOfFirst { rectangle ->
                    rectangle.containsDot(
                        dotX = columnAccumulator + minX,
                        dotY = rowAccumulator + minY
                    )
                }
                val classNum = indexOfContainingRect.takeIf { it != -1 }

                val cell = Row.Cell(width = width, classNum = classNum)
                cells.add(cell)
            }

            val row = Row(height = height, cells = cells)
            table.add(row)
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