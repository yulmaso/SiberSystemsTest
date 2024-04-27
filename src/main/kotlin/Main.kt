package org.example

private const val resultFileName = "result.html"

private val fileRepository by lazy { FileRepository() }
private val rectangleParser by lazy { RectangleParser() }
private val htmlProducer by lazy { HtmlProducer() }

fun main() {
    try {
        println("Write path to a file with coordinates:")
        val path = readln()
        val input = fileRepository.readTextFromFile(path)
        val rectangles = rectangleParser.parse(input)
        val htmlString = htmlProducer.buildRectanglesTableHtml(rectangles)
        fileRepository.saveTextToFile(htmlString, resultFileName)
        println("Success! The file created: $resultFileName")
    } catch (e: Exception) {
        println("Fail! An exception occurred: ${e.message}")
    }
}
