fun main() {
    val amanda = Person("Amanda", 33, "play tennis", null)
    val atiqah = Person("Atiqah", 28, "climb", amanda)

    amanda.showProfile()
    atiqah.showProfile()
}

class Person(val name: String, val age: Int, val hobby: String?, val referrer: Person?) {
    fun showProfile() {
        // Fill in code
        println("Name: $name")
        println("Age: $age")
        if (hobby != null) {
            print("Likes to $hobby. ")
        }
        else print("Doesn't have a hobby.")
        if (referrer != null) {
            print("Has a referrer named ${referrer.name}, ")
            if (referrer.hobby != null){
                println("who likes to ${referrer.hobby}")
            }
            else println("who has no hobby")
        }
        else println("Doesn't have a referer")
    }
}