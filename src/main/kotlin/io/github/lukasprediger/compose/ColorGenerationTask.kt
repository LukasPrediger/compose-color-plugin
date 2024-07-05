package io.github.lukasprediger.compose

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import palettes.TonalPalette

val tones =
    listOf(
        0,
        4,
        5,
        6,
        10,
        12,
        17,
        15,
        20,
        22,
        24,
        25,
        30,
        35,
        40,
        50,
        60,
        70,
        80,
        87,
        90,
        92,
        94,
        95,
        96,
        98,
        99,
        100,
    )

private const val blue = 0x769CDF

abstract class ColorGenerationTask : DefaultTask() {
    @Input
    val colors: MutableMap<String, String> = mutableMapOf()

    @Input
    var generateColorScheme = false

    @OutputDirectory
    val generateSrcDir =
        project.layout.buildDirectory
            .dir("generated/composeColors")

    @TaskAction
    fun test() {
        val colorMap =
            colors
                .map { (colorName, colorString) ->
                    val baseColor =
                        colorString
                            .removePrefix("#")
                            .removePrefix("0x")
                            .toInt(16)

                    val palette = TonalPalette.fromInt(baseColor)

                    colorName to
                        tones.associateWith {
                            palette.tone(it)
                        }
                }.toMap()

        writeColorsFile(
            generateSrcDir.get().asFile,
            "com.rewedigital.ravenclaw",
            colorMap,
        )

        if (generateColorScheme) {
            writeColorSchemeFile(
                generateSrcDir.get().asFile,
                "com.rewedigital.ravenclaw",
            )
        }
    }

    fun color(
        key: String,
        hexString: String,
    ) {
        colors[key] = hexString
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun color(
        key: String,
        argb: Int,
    ) {
        color(key, argb.toHexString(HexFormat.UpperCase))
    }

    fun defaultColors(
        primary: String,
        secondary: String,
        tertiary: String,
        neutral: String,
        neutralVariant: String,
        error: String,
    ) {
        color("primary", primary)
        color("secondary", secondary)
        color("tertiary", tertiary)
        color("neutral", neutral)
        color("neutralVariant", neutralVariant)
        color("error", error)
    }
}
