fun main() {

    // Exercice 1: ImmutableList
    fun ex1(): List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // Exercice 2: MutableList
    fun ex2(): List<String> = mutableListOf<String>("Bonjour", "MBDS", "Holtzman").apply {
        add("David")
    }

    // Exercice 3: Even numbers
    fun ex3(): List<Int> = (1..10).filter { it % 2 == 0}

    // Exercice 4: Filter and map
    fun ex4(): List<String> {
        val ages = listOf(12, 17, 18, 21, 30, 15)
            .filter { it >= 18 }
            .map {"Adulte: $it"}

        return ages
    }

    // Exercice 5: Flatten Nested Lists
    fun ex5(): List<Int> {
        val nestedList = listOf(listOf(1, 2), listOf(3, 4), listOf(5))
        return nestedList.flatten()
    }

    // Exercice 6: FlatMap
    fun ex6(): List<String> {
        val phrases = listOf("Kotlin is fun", "I love lists")

        return phrases.flatMap { it.split(" ") }

    }

    // Exercice 7: Eager Processing
    fun ex7(): List<Int> {

        val start = System.currentTimeMillis()

        val result = (1..1_000_000)
            .filter { it % 3 == 0 }
            .map { it * it }
            .take(5)

        val end = System.currentTimeMillis()
        println("Eager Time: ${end - start} ms")

        return result
    }

    // Exercice 8: Lazy Processing
    fun ex8(): List<Int> {

        val start = System.currentTimeMillis()

        val result = (1..1_000_000)
            .asSequence()
            .filter { it % 3 == 0 }
            .map { it * it }
            .take(5)
            .toList()

        val end = System.currentTimeMillis()
        println("Lazy Time: ${end - start} ms")

        return result
    }

    fun ex9(): List<String> =
        listOf("Alice", "Bob", "Alex", "Charlie", "Anna", "David", "Amanda")
            .filter { it.startsWith("A") }
            .map { it.uppercase() }
            .sorted()

}
