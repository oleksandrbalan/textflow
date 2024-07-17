package eu.wewox.textflow

/**
 * Enumeration of available demo examples.
 *
 * @param label Example name.
 * @param description Brief description.
 */
enum class Example(
    val label: String,
    val description: String,
) {
    SimpleTextFlow(
        "Simple Text Flow",
        "Basic text flow usage",
    ),
    BookChapter(
        "Book Chapter",
        "How text flow can be used to create a Drop Cap",
    ),
    AlignmentAnimation(
        "Alignment Animation",
        "Alignment change with a scale in-out animation",
    ),
    AnnotatedString(
        "Annotated String",
        "Usage of the annotated string in the text flow",
    ),
}
