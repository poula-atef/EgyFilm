package com.example.egyfilm.pojo.viewModelUtils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.egyfilm.pojo.classes.MovieFullData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavouriteViewModel : CoroutineViewModel() {

    var moviesFullData = mutableListOf<MovieFullData>()

    private val _dataFromFirebase = MutableLiveData<Boolean>()
    val dataFromFirebase: LiveData<Boolean>
        get() = _dataFromFirebase

    fun getDataFromFirebase() {
        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().uid!!).child("favs")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (shot in snapshot.children) {
                        val value = shot.getValue(MovieFullData::class.java) ?: return
                        moviesFullData.add(value)
                    }
                    _dataFromFirebase.value = true
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }

}