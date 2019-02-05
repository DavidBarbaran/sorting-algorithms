package com.david.barbaran.sortingalgorithms.model

class User(
    var id: Int,
    var first_name: String,
    var last_name: String,
    var email: String,
    var gender: String,
    var ip_address: String,
    var sale: Double
) : Comparable<User> {
    override fun compareTo(other: User): Int {
        if (this.sale > other.sale) return 1
        if (this.sale < other.sale) return -1
        return 0
    }
}