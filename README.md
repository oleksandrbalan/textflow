[![Maven Central](https://img.shields.io/maven-central/v/io.github.oleksandrbalan/textflow.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.github.oleksandrbalan/textflow)

# Text Flow

Text Flow library for Jetpack Compose.
Allows to display a text which wraps around an image (or any other Composable).

## Examples

### Simple text around image

### Text wrapping a letter to create a Drop Cap

### Animating image size and changing the alignment

### Annotated string support

See Demo application and [examples](demo/src/main/kotlin/eu/wewox/textflow/screens) for more usage examples.

## Usage

### Get a dependency

**Step 1.** Add the MavenCentral repository to your build file.
Add it in your root `build.gradle` at the end of repositories:
```
allprojects {
    repositories {
        ...
        mavenCentral()
    }
}
```

Or in `settings.gradle`:
```
pluginManagement {
    repositories {
        ...
        mavenCentral()
    }
}
```

**Step 2.** Add the dependency.
Check latest version on the [releases page](https://github.com/oleksandrbalan/textflow/releases).
```
dependencies {
    implementation 'io.github.oleksandrbalan:textflow:$version'
}
```

### Use in Composable

The `TextFlow` behaves as a regular `Text`, but has 2 more arguments:
* **obstacleContent** - The content lambda to provide the obstacle composable which will be wrapped by the text. Could be used as trailing lambda.
* **obstacleAlignment** - The alignment of the **obstacleContent**. Text flow supports `TopStart` (default) and `TopEnd` alignments.

```
TextFlow(
    text = "Text Flow allows to display a text which wraps around an image (or any other Composable)",
    modifier = Modifier.width(220.dp),
    obstacleAlignment = TextFlowObstacleAlignment.TopStart,
    obstacleContent = {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = null,
            modifier = Modifier.padding(4.dp)
        )
    }
)
```
