package activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.rlds.lojavirtual.R
import com.rlds.lojavirtual.databinding.ActivityFormLoginBinding
import dialog.DialogCarregando

class FormLogin : AppCompatActivity() {
    private lateinit var binding:ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        val dialogCArregando  = DialogCarregando(this)
        binding.txtTelaCadastro.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }
        binding.btEntrar.setOnClickListener { view ->
            val email = binding.txtEmail.text.toString()
            val senha = binding.txtSenha.text.toString()
            if(email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(
                    view, "Preencha todos os campos",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }else{
                // fazer login
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    email,senha
                ).addOnCompleteListener { tarefa ->
                    if(tarefa.isSuccessful){
                        dialogCArregando.iniciarCArregamentoAlertDialog()
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this, TelaProdutos::class.java)
                            startActivity(intent)
                            finish()
                            dialogCArregando.liberarAlertDialog()

                        },3000)


                    }
                }.addOnFailureListener {
                    val snackbar = Snackbar.make(
                        view, "Erro ao fazer Login",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()

                }

            }
        }

    }

    override fun onStart() {
        super.onStart()
        val usuarioAtual = FirebaseAuth.getInstance().currentUser
        if(usuarioAtual != null){
            val intent = Intent(this, TelaProdutos::class.java)
            startActivity(intent)
        }
    }

}