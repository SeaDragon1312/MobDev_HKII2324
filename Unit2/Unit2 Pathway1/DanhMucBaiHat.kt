fun main() {
    var song = Song("Đừng làm trái tim anh đau", "Sơn Tùng M-TP", 2024, 50000000)
    song.printSongDescription()
    println(song.isPopular)
}

class Song (
    val title : String,
    val artist: String,
    val yearRelease : Int,
    var replay : Int
) {
    val isPopular : Boolean
        get() = replay >= 1000
    fun printSongDescription() {
        println("$title, do $artist thực hiện, được phát hành vào năm $yearRelease")
    }
}