package org.example

import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

class FileRepository {
    fun readTextFromFile(path: String): String {
        val input = Files.newInputStream(Path(path))
        return input.bufferedReader().use { it.readText() }
    }

    fun saveTextToFile(value: String, fileName: String) {
        File(fileName).printWriter().use {
            it.println(value)
        }
    }
}