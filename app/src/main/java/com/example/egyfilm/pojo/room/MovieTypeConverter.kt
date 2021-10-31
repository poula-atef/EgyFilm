package com.example.egyfilm.pojo.room

import androidx.room.TypeConverter
import com.example.egyfilm.pojo.classes.*
import com.google.gson.Gson
import java.lang.StringBuilder
import com.google.gson.reflect.TypeToken


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

    @TypeConverter
    fun convertProductionCompaniesListToString(productionCompanies: List<ProductionCompanies>): String {
        return ""
    }

    @TypeConverter
    fun convertProductionCountriesListToString(productionCompanies: List<ProductionCountries>): String {
        return ""
    }

    @TypeConverter
    fun convertSpokenLanguagesListToString(spokenLanguages: List<SpokenLanguages>): String {
        return ""
    }

    @TypeConverter
    fun convertStringToProductCompaniesList(str: String): List<ProductionCompanies> {
        return listOf<ProductionCompanies>()
    }

    @TypeConverter
    fun convertStringToProductionCountriesList(str: String): List<ProductionCountries> {
        return listOf<ProductionCountries>()
    }

    @TypeConverter
    fun convertStringToSpokennLanguagesList(str: String): List<SpokenLanguages> {
        return listOf<SpokenLanguages>()
    }

    @TypeConverter
    fun convertBelongsToCollectionToString(btc: BelongsToCollection): String {
        return Gson().toJson(btc)
    }

    @TypeConverter
    fun convertStringToBelongToCollection(str: String): BelongsToCollection {
        return Gson().fromJson(str, BelongsToCollection::class.java)
    }

    @TypeConverter
    fun convertGenreListToString(lst: List<Genre>): String {
        return Gson().toJson(lst)
    }

    @TypeConverter
    fun convertStringToGenreList(str: String): List<Genre> {
        return Gson().fromJson(str, object : TypeToken<ArrayList<Genre?>?>() {}.type)
    }

    @TypeConverter
    fun convertActorListToString(lst: List<Actor>): String {
        return Gson().toJson(lst)
    }

    @TypeConverter
    fun convertStringToActorList(str: String): List<Actor> {
        return Gson().fromJson(str, object : TypeToken<ArrayList<Actor?>?>() {}.type)
    }

    @TypeConverter
    fun convertStringToCrewList(str: String): List<Crew> {
        return Gson().fromJson(str, object : TypeToken<ArrayList<Crew?>?>() {}.type)
    }

    @TypeConverter
    fun convertCrewListToString(lst: List<Crew>): String {
        return Gson().toJson(lst)
    }

    @TypeConverter
    fun convertStringToActorMovieList(str: String): List<ActorMovie> {
        return Gson().fromJson(str, object : TypeToken<ArrayList<ActorMovie?>?>() {}.type)
    }

    @TypeConverter
    fun convertActorMovieListToString(lst : List<ActorMovie>) : String{
        return Gson().toJson(lst)
    }

}