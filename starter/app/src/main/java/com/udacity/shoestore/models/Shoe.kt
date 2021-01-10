package com.udacity.shoestore.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



@Parcelize
data class Shoe(var name: String, var size: Double, var company: String, var description: String,
                val images: List<String> = mutableListOf()) : Parcelable

var SHOE_LIST = listOf<Shoe>(
    Shoe("New Style",38.0,"Prada","Some nice Words about this wonderful Shoe! ",
        mutableListOf("shoe1")
    ),
    Shoe("Defender", 39.0, "Nike","Soluta uae consectetur molestias totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores.",
        mutableListOf("shoe2")),
    Shoe("Remember", 42.0, "Puma","Totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores."),
    Shoe("Blender", 27.0, "Adidas","Sicta. Quae consectetur molestias totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores."),
    Shoe("Obscure", 32.0, "Bergans","Ipsum dicta. Quae consectetur molestias totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores."),
    Shoe("Banana", 34.0, "Xped","Quae consectetur molestias totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores."),
    Shoe("Foo", 43.0, "Diesel","Consectetur molestias totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores."),
    Shoe("Bar", 36.0, "Waldviertler","Molestias totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores."),
    Shoe("Neverlast", 37.0, "Adidas","Totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores."),
    Shoe("Happydust", 32.0, "Luis Viton","Corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores.")
)

