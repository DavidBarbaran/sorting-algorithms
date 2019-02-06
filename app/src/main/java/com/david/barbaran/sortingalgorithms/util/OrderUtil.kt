package com.david.barbaran.sortingalgorithms.util

class OrderUtil {

    lateinit var interactor: Interactor

    fun <T:Comparable<T>>selectionsort(items:MutableList<T>):MutableList<T>{
        if (items.isEmpty()){
            return items
        }
        for (idx in 0..items.count()){
            interactor.onInteracting()
            val array = items.subList(0,items.count()-idx)
            println("array-> $array")
            val minItem = array.min()
            println("minItem-> $minItem")
            val indexOfMinItem = array.indexOf(minItem)
            println("indexOfMinItem-> $indexOfMinItem")
            if (minItem != null) {
                items.removeAt(indexOfMinItem)
                items.add(minItem)
                println("RemoveAndAdd-> $items")
                println("")
            }
        }
        return items
    }

    fun <T:Comparable<T>>quicksort(items:List<T>):List<T>{
        interactor.onInteracting()
        if (items.count() < 2){
            return items
        }
        val pivot = items[items.count()/2]
        println("$pivot")
        val equal = items.filter { it == pivot }
        println("$equal")
        val less = items.filter { it < pivot }
        println("$less")
        val greater = items.filter { it > pivot }
        println("$greater")
        val quick = quicksort(less) + equal + quicksort(greater)
        println("$quick")
        println("")
        return quick
    }

    interface Interactor {
        fun onInteracting()
    }
}

