package activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.ktx.Firebase
import com.rlds.lojavirtual.R
import com.rlds.lojavirtual.databinding.ActivityFormCadastroBinding
import model.DB

class FormCadastro : AppCompatActivity() {
    private lateinit var binding:ActivityFormCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        val db  = DB()
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
                        db.salvar(nome)
                        val snakbar = Snackbar.make(
                            it, "Usuario cadastrado com sucesso!",
                            Snackbar.LENGTH_SHORT
                        ).setBackgroundTint(Color.BLUE)
                        snakbar.setTextColor(Color.WHITE)
                        snakbar.show()

                    }
                }.addOnFailureListener { erroCadastro ->
                    val mensagemErro = when(erroCadastro){
                        is FirebaseAuthWeakPasswordException ->"Digite uma senha com no mínimo 6 caracteres"
                        is FirebaseAuthUserCollisionException ->"Esta conta ja foi cadastrada"
                        is FirebaseNetworkException ->"Sem conexão com a internet"
                        else -> "Erro ao cadastrar usuário"
                    }
                    val snackbar = Snackbar.make(
                        it, mensagemErro, Snackbar.LENGTH_SHORT
                    )
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()

                }

            }
        }

    }
}