package dialog

import activity.FormLogin
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.rlds.lojavirtual.databinding.DialogPerfilUsuarioBinding
import model.DB

class dialogPerfilUsuario(private val activity:Activity) {
    lateinit var  dialog:AlertDialog
    lateinit var binding:DialogPerfilUsuarioBinding

    fun inicializarPerfilUsuario(){
        val builder = AlertDialog.Builder(activity)
        binding = DialogPerfilUsuarioBinding.inflate(activity.layoutInflater)
        builder.setView(binding.root)
        builder.setCancelable(true)
        dialog = builder.create()
        dialog.show()
    }
    fun recuperarDadosUsuarioBanco(){
        val nomeUsuario = binding.textNomeUsuario
        val emailUsuario = binding.textEmailUsuario
        val db = DB()
        db.recuperDadosUsuarioPerfil(nomeUsuario, emailUsuario)
         binding.buttonSairUsuario.setOnClickListener {
             FirebaseAuth.getInstance().signOut()
             val intent = Intent(activity, FormLogin::class.java)
             activity.startActivity(intent)
             activity.finish()
         }

    }

}