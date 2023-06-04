package com.saadi.stickfigure.feature_message.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.saadi.stickfigure.databinding.FragmentMessageBinding


class FragmentMessage : Fragment() {

    //binding
    private lateinit var mBinding: FragmentMessageBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Retrieve data from the "ChatRooms" collection
        firestore.collection("ChatRooms")
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    Log.e("FragmentMessage", "Error getting chat messages: ${exception.message}")
                    return@addSnapshotListener
                }

                // Clear the existing list
                val chatMessages: MutableList<String> = mutableListOf()

                // Iterate through the documents and retrieve the "recieveUser" object
                for (document in querySnapshot?.documents.orEmpty()) {
                    val receiveUserObject = document.get("recieveUser") as? Map<*, *>
                    val receiveUser = receiveUserObject?.get("imageUrl") as? String
                    if (receiveUser != null) {
                        chatMessages.add(receiveUser)
                    }
                }

                Log.d("FragmentMessage", "Chat Messages: $chatMessages")

                // Update your UI with the new chat list
                // For example, you can use an Adapter and RecyclerView
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMessageBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}