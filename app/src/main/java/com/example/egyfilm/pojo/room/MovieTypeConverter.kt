package com.example.egyfilm.pojo.room

import androidx.room.TypeConverter

class MovieTypeConverter {
    /*    @TypeConverter
        fun fromListToString(intLst : List<Int>? = null, strLst : List<String>? = null):String{
            return when(intLst){
                null -> strLst.toString()
                else -> intLst.toString()
            }
        }*/
    @TypeConverter
    fun fromIntListToString(intLst: List<Int>): String {
        return intLst.toString()
    }

    @TypeConverter
    fun fromStringListToString(strLst: List<String>): String {
        return strLst.toString()
    }

    @TypeConverter
    fun fromStringToIntList(str: String): List<Int> {
        val lst = str.toList()
        val intLst: MutableList<Int> = mutableListOf()
        lst.forEach {
            intLst.add(it.toInt())
        }
        return intLst.toList()
    }

    @TypeConverter
    fun fromStringToStringList(str: String): List<String> {
        val lst = str.toList()
        val intLst: MutableList<String> = mutableListOf()
        lst.forEach {
            intLst.add(it.toString())
        }
        return intLst.toList()
    }
}