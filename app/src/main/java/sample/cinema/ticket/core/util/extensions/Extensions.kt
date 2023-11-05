package sample.cinema.ticket.core.util.extensions

fun <T> List<T>.splitIntoParts(partSize: Int): List<List<T>> {

    val parts = mutableListOf<List<T>>()

    for (i in indices step partSize) {
        val end = minOf(i + partSize, size)
        parts.add(subList(i, end))
    }

    return parts
}
