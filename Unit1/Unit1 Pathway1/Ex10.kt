fun main() {
    weather("Ankara", 27, 31, 82)
    weather("Tokyo", 32, 36, 10)
    weather("Cape Town", 59, 64, 2)
    weather("Guatemala City", 50, 55, 7)
}

fun weather(city : String, low : Int, high : Int, chanceOfRain: Int) {
    println("City: $city")
    println("Low temperature: $low, High temperature: $high")
    println("Chance of rain: $chanceOfRain%")
    println()
}