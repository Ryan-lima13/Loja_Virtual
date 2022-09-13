package model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class DB {
    val ususarioId = FirebaseAuth.getInstance().currentUser!!.uid
    fun salvar(nome:String){
        val db = FirebaseFirestore.getInstance()
        val usuarios = hashMapOf(
            "nome" to nome
        )
         val documentReference:DocumentReference = db.collection("Usuarios")
             .document(ususarioId)
        documentReference.set(usuarios).addOnSuccessListener {
            Log.d("DB","Sucesso ao salvar dados")
        }.addOnFailureListener { erro ->
            Log.d("DB_ERROR", "Erro ao salvar os dados${erro.printStackTrace()}")

        }

    }
}