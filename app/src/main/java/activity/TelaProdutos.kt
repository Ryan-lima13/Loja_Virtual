package activity

import adapter.AdapterProduto
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.rlds.lojavirtual.R
import com.rlds.lojavirtual.databinding.ActivityTelaProdutosBinding
import com.rlds.lojavirtual.databinding.DialogPerfilUsuarioBinding
import dialog.dialogPerfilUsuario
import model.DB
import model.Produto

class TelaProdutos : AppCompatActivity() {
    private lateinit var binding:ActivityTelaProdutosBinding
    private lateinit var adapterProduto:AdapterProduto
    var list_produto:MutableList<Produto> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerProduto = binding.recyclerViewProduto
        recyclerProduto.layoutManager = GridLayoutManager(this,2)
        recyclerProduto.setHasFixedSize(true)
        adapterProduto = AdapterProduto(this, list_produto)
        recyclerProduto.adapter = adapterProduto
        val db = DB()
        db.obeterListaProdutos(list_produto, adapterProduto)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.perfil ->iniciarDialogPerfilUsuario()
            R.id.deslogar -> deslogarUsuario()


        }
        return super.onOptionsItemSelected(item)
    }

    private fun iniciarDialogPerfilUsuario(){
        val dialogPerfilUsuario = dialogPerfilUsuario(this)
        dialogPerfilUsuario.inicializarPerfilUsuario()
        dialogPerfilUsuario.recuperarDadosUsuarioBanco()

    }
    private  fun deslogarUsuario(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)

    }
}