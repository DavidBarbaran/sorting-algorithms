package com.david.barbaran.sortingalgorithms.util

import com.david.barbaran.sortingalgorithms.config.Setting

class OrderUtil {

    lateinit var interactor: Interactor

    /**
     * @SelectionSort
     **/

    fun <T : Comparable<T>> selectionsort(list: MutableList<T>): MutableList<T> {
        val items = mutableListOf<T>()
        items.addAll(list)
        if (items.isEmpty()) {
            return items
        }
        for (idx in 0..items.count()) {
            interactor.onInteracting()
            val array = items.subList(0, items.count() - idx)
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

    fun <T : Comparable<T>> Iterable<T>.min(): T? {
        val iterator = iterator()
        if (!iterator.hasNext()) return null
        var min = iterator.next()
        while (iterator.hasNext()) {
            if (Setting.isDetail) {
                interactor.onInteracting()
            }
            val e = iterator.next()
            if (min > e) min = e
        }
        return min
    }

    /**
     * @QuickSort
     **/

    fun <T : Comparable<T>> auxQuickSort(list: List<T>): List<T> {
        val items = mutableListOf<T>()
        items.addAll(list)
        return quicksort(items)
    }

    fun <T : Comparable<T>> quicksort(items: List<T>): List<T> {
        interactor.onRecursive()
        if (items.count() < 2) {
            return items
        }
        val pivot = items[items.count() / 2]
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

    inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> {
        return filterTo(ArrayList<T>(), predicate)
    }

    inline fun <T, C : MutableCollection<in T>> Iterable<T>.filterTo(destination: C, predicate: (T) -> Boolean): C {
        for (element in this) {
            if (predicate(element)) destination.add(element)
            if (Setting.isDetail) {
                interactor.onInteracting()
            }
        }
        return destination
    }

    /**
     * @InsertionSort
     **/

    fun <T : Comparable<T>> insertionsort(list: MutableList<T>): List<T> {
        val items = mutableListOf<T>()
        items.addAll(list)
        if (items.isEmpty()) {
            return items
        }
        for (count in 1..items.count() - 1) {
            interactor.onInteracting()
            val item = items[count]
            var i = count
            while (i > 0 && item < items[i - 1]) {
                if (Setting.isDetail) {
                    interactor.onInteracting()
                }
                items[i] = items[i - 1]
                i -= 1
            }
            items[i] = item
        }
        return items
    }

    interface Interactor {
        fun onInteracting()
        fun onRecursive()
    }
}

