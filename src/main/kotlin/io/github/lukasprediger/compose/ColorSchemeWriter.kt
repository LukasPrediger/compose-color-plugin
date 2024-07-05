package io.github.lukasprediger.compose

import java.io.File

fun writeColorSchemeFile(
    generatedSourcesDir: File,
    packageName: String,
) {
    val objectSource = object {}.javaClass.getResource("/ColorSchemeSource.kt")!!.readText()

    val packageDir = File(generatedSourcesDir, packageName.replace(".", "/"))
    packageDir.mkdirs()

    val targetFile = File(packageDir, "ColorScheme.kt")
    targetFile.createNewFile()
    targetFile.writeText(
        buildString {
            appendLine("package $packageName")
            appendLine()
            appendLine("import androidx.compose.material3.ColorScheme")
            appendLine()
            append(objectSource)
        },
    )
}
