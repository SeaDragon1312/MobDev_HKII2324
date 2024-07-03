fun main() {
    val firstNumber = 10
    val secondNumber = 5
    val thirdNumber = 5
    val result = add(firstNumber, secondNumber)

    println("$firstNumber + $secondNumber = $result")

}

fun add(firstNumber: Int, secondNumber: Int): Int {
    return firstNumber + secondNumber
}

fun subtract(firstNumber: Int, secondNumber: Int): Int {
    return firstNumber - secondNumber
}