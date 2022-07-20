package com.mustfaibra.instagraph.sealed

sealed class Orientation {
    object Vertical : Orientation()
    object Horizontal : Orientation()
}