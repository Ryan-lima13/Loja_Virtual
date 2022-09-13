package activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.rlds.lojavirtual.R
import com.rlds.lojavirtual.databinding.ActivityFormCadastroBinding

class FormCadastro : AppCompatActivity() {
    private lateinit var binding:ActivityFormCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        binding.btCadastrar.setOnClickListener {

            val nome = binding.txtNome.text.toString()
            val email = binding.txtEmail.text.toString()
            val senha = binding.txtSenha.text.toString()

            if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                val snakbar = Snackbar.make(
                    it, "Preencha todos os campos",
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(Color.RED)
                snakbar.setTextColor(Color.WHITE)
                snakbar.show()

            }else{
                // cadastrar usuario
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email, senha
                ).addOnCompleteListener { tarefa ->
                    if (tarefa.isSuccessful){
                        val snakbar = Snackbar.make(
                            it, "Usuario cadastrado com sucesso!",
                            Snackbar.LENGTH_SHORT
                        ).setBackgroundTint(Color.BLUE)
                        snakbar.setTextColor(Color.WHITE)
                        snakbar.show()

                    }
                }

            }
        }

    }
}