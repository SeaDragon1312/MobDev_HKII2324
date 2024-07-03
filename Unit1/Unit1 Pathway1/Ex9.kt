fun main() {
    println(timeCompare(30, 10))
    println(timeCompare(30, 30))
    println(timeCompare(30, 40))
}

fun timeCompare(timeSpentToday: Int, timeSpentYesterday: Int) : Boolean {
    return timeSpentToday > timeSpentYesterday
}
