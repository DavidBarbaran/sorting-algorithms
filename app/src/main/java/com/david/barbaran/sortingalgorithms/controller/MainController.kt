package com.david.barbaran.sortingalgorithms.controller

import com.david.barbaran.sortingalgorithms.model.User

interface MainController {
    fun onLoadUserSuccessful(list: MutableList<User>)
    fun onIteration(count : Int)
    fun onRecursive(count : Int)
}