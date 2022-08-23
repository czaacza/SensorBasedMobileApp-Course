package com.czaacza.lab03presidents

class President(
    var name: String,
    var startOfficeYear: Int,
    var endOfficeYear: Int,
    var description: String
) {

    override fun toString(): String {
        return "President(name='$name', startOfficeYear=$startOfficeYear, endOfficeYear=$endOfficeYear, description='$description')"
    }
}