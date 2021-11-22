package com.example.spalbumtask.util

import com.example.spalbumtask.model.Album

fun provideTestList():List<Album>{
    val dummyList = arrayListOf<Album>()
    dummyList.add(Album("1",1,"A Title"))
    dummyList.add(Album("2",2,"B Title"))
    dummyList.add(Album("3",3,"C Title"))
    return dummyList
}