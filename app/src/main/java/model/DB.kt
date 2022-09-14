package model

import adapter.AdapterProduto
import android.util.Log
import android.widget.TextView
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
    fun recuperDadosUsuarioPerfil(nomeUsuario:TextView, emailUsuario:TextView){
        val usuarioId = FirebaseAuth.getInstance().currentUser!!.uid
        val email = FirebaseAuth.getInstance().currentUser!!.email
        val db = FirebaseFirestore.getInstance()
        val documentReference:DocumentReference = db.collection("Usuarios").document(usuarioId)
        documentReference.addSnapshotListener { documento, error ->
            if(documento != null){
                nomeUsuario.text = documento.getString("nome")
                emailUsuario.text = email
            }
        }


    }
      fun obeterListaProdutos(lis_produtos:MutableList<Produto>, adapter_produto:AdapterProduto){
        val db = FirebaseFirestore.getInstance()
        db.collection("Produtos").get().addOnCompleteListener { tarefa->
          if(tarefa.isSuccessful) {
              for (documento in tarefa.result!!){
                  val produto = documento.toObject(Produto::class.java)
                  lis_produtos.add(produto)
                  adapter_produto.notifyDataSetChanged()

              }
          }

        }


    }
}