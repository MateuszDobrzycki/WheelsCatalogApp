package com.example.wheelscatalogapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.firestore.FieldValue

class FirebaseRepository {

    private val REPO_DEBUG = "REPO_DEBUG"
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val cloud = FirebaseFirestore.getInstance()

    fun getUserData(): LiveData<User> {
        val cloudResult = MutableLiveData<User>()
        val uid = auth.currentUser?.uid
        cloud.collection("users")
                .document(uid!!)
                .get()
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    cloudResult.postValue(user)
                }
                .addOnFailureListener {
                    Log.d(REPO_DEBUG, it.message.toString())
                }
        return cloudResult
    }
    fun getWheel(): LiveData<List<Wheel>> {
        val cloudResult = MutableLiveData<List<Wheel>>()
        cloud.collection("wheel")
                .get()
                .addOnSuccessListener {
                    val user = it.toObjects(Wheel::class.java)
                    cloudResult.postValue(user)
                }
                .addOnFailureListener {
                    Log.d(REPO_DEBUG, it.message.toString())
                }
        return cloudResult
    }

    fun getFavWheel(list: List<String>?): LiveData<List<Wheel>> {
        val cloudResult = MutableLiveData<List<Wheel>>()
        if (!list.isNullOrEmpty())
            cloud.collection("wheel")
                    .whereIn("id", list)
                    .get()
                    .addOnSuccessListener {
                        val listObject = it.toObjects(Wheel::class.java)
                        cloudResult.postValue(listObject)
                    }
                    .addOnFailureListener {
                        Log.d(REPO_DEBUG, it.message.toString())
                    }
        return cloudResult
    }

    fun addFavWheel(wheel: Wheel) {
        cloud.collection("users")
                .document(auth.currentUser?.uid!!)
                .update("favWheel", FieldValue.arrayUnion(wheel.id))
                .addOnSuccessListener {
                    Log.d(REPO_DEBUG, "Dodano do listy wybranych felg")
                }
                .addOnFailureListener {
                    Log.d(REPO_DEBUG, it.message.toString())
                }
    }

    fun removeFavWheel(wheel: Wheel) {
        cloud.collection("users")
                .document(auth.currentUser?.uid!!)
                .update("favWheel", FieldValue.arrayRemove(wheel.id))
                .addOnSuccessListener {
                    Log.d(REPO_DEBUG, "Usunięto z listy wybranych felg")
                }
                .addOnFailureListener {
                    Log.d(REPO_DEBUG, it.message.toString())
                }
    }

    fun editProfileDane(map: Map<String, String>) {
        cloud.collection("users")
                .document(auth.currentUser!!.uid)
                .update(map)
                .addOnSuccessListener {
                    Log.d(REPO_DEBUG, "Pomyślnie zapisano dane")
                }
                .addOnFailureListener {
                    Log.d(REPO_DEBUG, it.message.toString())
                }
    }

    fun createNewUser(user: User) {
        cloud.collection("users")
                .document(user.uid!!)
                .set(user)
    }
}