package org.example

data class Row(
    val height: Int,
    val cells: List<Cell>
) {
    data class Cell(
        val width: Int,
        val classNum: Int?
    )
}