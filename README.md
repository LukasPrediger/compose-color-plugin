# Jetpack Compose Material 3 Color Generation

This plugin generates a tonal palette and optional a Jetpack Compose 3 color scheme from a set of colors

## Prerequisites
- Kotlin
- Java 17 or higher
- Gradle 7.0 or higher
- `androidx.compose.material3:material3`

## Usage

Register a new ColorGenerationTask in your build.gradle.kts file

```kotlin
tasks.register("processColors", ColorGenerationTask::class.java) {
    //These are the default value
    packageName = "com.material3.generation.theme"
    generateColorScheme = false
}
```

In order to generate colors add the name and base color to the task

```kotlin

tasks.processColors {
    // Color names should be CamelCase. The base color can either be a hex string or an argb integer
    color("Primary", "#769CDF")
    color("Secondary", "#8991A2")
    color("Tertiary", "#A288A6")
}
```

The plugin will generate the tonal palettes in the specified package.

```kotlin
import com.material3.generation.theme.Colors

val primary = Colors.Primary.tone40
val secondary = Colors.Secondary.tone40
val tertiary = Colors.Tertiary.tone40
```

Lastly add the task as a dependency for the build task

```kotlin
tasks.build {
    dependsOn("processColors")
}
```

### Generating a color scheme

The plugin can generate a dark and light color scheme for you.

For this the following base colors are required:

- Primary
- Secondary
- Tertiary
- Neutral
- NeutralVariant
- Error

These colors can be easily set using the `defaultColors()` function

```kotlin
defaultColors(
    primary = "#769CDF",
    secondary = "#8991A2",
    tertiary = "#A288A6",
    neutral = "#919093",
    neutralVariant = "#8E9098",
    error = "#FF5449",
)

// generateColorScheme is set to false by default
generateColorScheme = true
```

The plugin will generate the color scheme in the specified package.

```kotlin
import com.material3.generation.theme.ColorSchemes

@Composable
fun Theme(
    darkMode: Boolean,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkMode) ColorSchemes.darkColorScheme else ColorSchemes.lightColorScheme,
        content = content,
    )
}
```
