package com.nutrisport.home.domain

enum class CustomerDrawerState {
    opened,
    closed
}
fun CustomerDrawerState.isOpen(): Boolean{
    return this == CustomerDrawerState.opened
}
fun CustomerDrawerState.opposite(): CustomerDrawerState{
    return if( this == CustomerDrawerState.opened) CustomerDrawerState.closed
    else CustomerDrawerState.opened
}