fun main() {
    // Fill in the code.
    printFinalTemperature(40.0, "C", "F") { 9.0 / 5.0 * it + 32 }
    printFinalTemperature(40.0, "K", "C") { it - 273.15 }
    printFinalTemperature(40.0, "F", "K") { 5.0 / 9.0 * (it - 32) + 273.15 }
}

fun printFinalTemperature(
    initialMeasurement: Double,
    initialUnit: String,
    finalUnit: String,
    conversionFormula: (Double) -> Double
) {
    val finalMeasurement = String.format("%.2f", conversionFormula(initialMeasurement)) // two decimal places
    println("$initialMeasurement degrees $initialUnit is $finalMeasurement degrees $finalUnit.")
}