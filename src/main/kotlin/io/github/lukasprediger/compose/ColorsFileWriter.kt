package io.github.lukasprediger.compose

import org.gradle.configurationcache.extensions.capitalized
import java.io.File

@OptIn(ExperimentalStdlibApi::class)
fun writeColorsFile(
    generatedSourcesDir: File,
    packageName: String,
    colors: Map<String, Map<Int, Int>>,
) {
    val packageDir = File(generatedSourcesDir, packageName.replace(".", "/"))
    packageDir.mkdirs()

    val targetFile = File(packageDir, "Colors.kt")
    targetFile.createNewFile()
    targetFile.writeText(
        buildString {
            appendLine("package $packageName")
            appendLine()

            appendLine("import androidx.compose.ui.graphics.Color")
            appendLine()

            appendLine("object Colors {")
            colors.forEach { (name, tones) ->
                appendLine("    object ${name.capitalized()} {")
                tones.forEach { (tone, argb) ->
                    val toneName = "tone$tone".padEnd(7, ' ')
                    appendLine("        val $toneName = Color(0x${argb.toHexString(HexFormat.UpperCase)})")
                }
                appendLine("    }")
            }
            appendLine("}")
        },
    )
}
