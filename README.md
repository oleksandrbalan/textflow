[![Maven Central](https://img.shields.io/maven-central/v/io.github.oleksandrbalan/textflow.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.github.oleksandrbalan/textflow)

<img align="right" src="https://user-images.githubusercontent.com/20944869/192884656-f962028f-cbd5-4b24-91ee-2ad50bda1e5c.png">

# Text Flow

Text Flow library for Jetpack Compose.

Allows to display a text which wraps around an image (or any other Composable).

## Examples

See Demo application and [examples](demo/src/main/kotlin/eu/wewox/textflow/screens).

### Simple text around image

<img src="https://user-images.githubusercontent.com/20944869/192884872-3e9b3952-2b8c-40c7-b44c-3f2c5eefce70.png" width="450">

### Text wrapping a letter to create a Drop Cap

<img src="https://user-images.githubusercontent.com/20944869/192884891-a5c151d1-95c0-413d-93f6-9263cb9ec3f8.gif" width="450">

### Animating image size and changing the alignment

<img src="https://user-images.githubusercontent.com/20944869/192884918-bf434adf-46e9-45df-956d-da7258886e65.gif" width="450">

### Annotated string support

<img src="https://user-images.githubusercontent.com/20944869/192884942-ca3b1f11-5009-4489-a971-99dda86bba58.gif" width="450">

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
dependencyResolutionManagement {
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
    implementation "io.github.oleksandrbalan:textflow:$version"
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
