package hr.algebra.iamuapp.model

data class Item(
    var _id: Long?,
    var title: String,
    var description: String,
    var picturePath: String,
    var date: String,
    var read: Boolean
)
