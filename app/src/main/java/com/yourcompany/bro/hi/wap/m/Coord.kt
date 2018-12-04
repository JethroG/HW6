package com.yourcompany.bro.hi.wap.m

class Coord {

    var lon: Double = 0.toDouble()
    var lat: Double = 0.toDouble()


    override fun toString(): String {
        return StringBuilder("[").append(this.lat).append(",").append(this.lon).append(']').toString()
    }
}
