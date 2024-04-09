package com.example.applicationquizforstudents.data.impl

import android.util.Log
import com.example.applicationquizforstudents.data.Reference
import com.example.applicationquizforstudents.data.service.AccountService
import com.example.applicationquizforstudents.data.service.FirebaseService
import com.example.applicationquizforstudents.domain.models.Image
import com.example.applicationquizforstudents.domain.models.User
import com.example.applicationquizforstudents.domain.state.RealtimeCRUDState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.values
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: AccountService
) : FirebaseService {

    private val referencesUser = firebaseDatabase.getReference(Reference.USER.value)
    private val referencesImage = firebaseDatabase.getReference(Reference.IMAGE.value)
    private val referencesCountImages = referencesImage.child("count")
    private val state = MutableStateFlow<RealtimeCRUDState>(RealtimeCRUDState.Off)
    private val stateForImages = MutableStateFlow<RealtimeCRUDState>(RealtimeCRUDState.Off)

    override fun getStateImages() = stateForImages



    override fun createImage(image: Image){
        referencesCountImages.get().addOnSuccessListener {
            val retrievedCount = it.getValue(Int::class.java)
            retrievedCount?.let {count->
                referencesImage.child(count.toString()).setValue(image.copy(id = count))
                incrementImageCounter(count)
            }
        }
    }
    private fun incrementImageCounter(count:Int){
        referencesCountImages.setValue(count+1)
    }



    override fun getState() = state

    override suspend fun createUser(user: User.Base) {
        val userReference = referencesUser.child(user.id)
        userReference.setValue(user)
            .addOnSuccessListener {
                state.value = RealtimeCRUDState.Success("Create User")
            }
            .addOnFailureListener {
                state.value = RealtimeCRUDState.Error(it.message.toString())
            }
    }

    override fun updateImage(image: Image, id:Int) {
        val imageReference = referencesImage.child(id.toString())
        val updates = hashMapOf<String, Any>(
            "description" to image.description,
            "url" to image.url
        )
        imageReference.updateChildren(updates)
//            .addOnSuccessListener {
//            stateForImages.value = RealtimeCRUDState.SuccessForImages(listOf())
//        }
//            .addOnFailureListener { error ->
//                stateForImages.value = RealtimeCRUDState.Error(error.message.toString())
//            }
    }


    override suspend fun updateUser(user: User.Base) {
        val userReference = referencesUser.child(auth.currentUserId)

        val updates =  if(user.email.isNotEmpty()) {
            auth.updateEmail(user.email)
            auth.updatePassword(user.password)
            hashMapOf<String, Any>(
                "name" to user.name,
                "surname" to user.surname,
                "email" to user.email,
                "password" to user.password
            )
        }else {
            hashMapOf<String, Any>(
            "isit" to user.isit,
            "economy" to user.economy,
            "accounting" to user.accounting,
            "electronic_economy" to user.electronic_economy
            )
        }

        userReference.updateChildren(updates)
            .addOnSuccessListener {
                state.value = RealtimeCRUDState.Success("Update User")
            }
            .addOnFailureListener { error ->
                state.value = RealtimeCRUDState.Error(error.message.toString())
            }

    }

    override suspend fun readUser() {
        val userReference = referencesUser.child(auth.currentUserId)
        userReference.get()
            .addOnSuccessListener {
                val retrievedUser = it.getValue(User.Base::class.java)
                retrievedUser?.let {
                    state.value = RealtimeCRUDState.Success(user = retrievedUser)
                }
            }
            .addOnFailureListener {
                state.value = RealtimeCRUDState.Error(it.message.toString())
            }
    }

    override fun removeImage(id: Int) {
        val imageReference = referencesImage.child(id.toString())
        imageReference.removeValue()
            .addOnSuccessListener {
                stateForImages.value = RealtimeCRUDState.Delete
            }
    }

    override fun getImageById(id:Int){
        val imageReference = referencesImage.child(id.toString())
        stateForImages.value = RealtimeCRUDState.Loading
        imageReference.get()
            .addOnSuccessListener {searchedImage ->
                val retrievedImage = searchedImage.getValue(Image::class.java)
                retrievedImage?.let{image->
                    stateForImages.value = RealtimeCRUDState.SuccessForImages(listOf(image))
                }
            }
            .addOnFailureListener {
                stateForImages.value = RealtimeCRUDState.Error(it.message.toString())
            }
    }

    override fun getImages(){
        referencesImage.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                stateForImages.value = RealtimeCRUDState.Loading
                val list = mutableListOf<Image>()
                for (snapshot in dataSnapshot.children) {
                    if (snapshot.value !is Number) {
                        val value = snapshot.getValue(Image::class.java)
                        value?.let {
                            list.add(it)
                            Log.d("TTT", "images: ${list.toString()}")
                            Log.d("TTT", "imagesSize: ${list.size}")
                        }
                    }
                }
                stateForImages.value = RealtimeCRUDState.SuccessForImages(images = list)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}