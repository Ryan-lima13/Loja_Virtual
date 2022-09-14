package activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.rlds.lojavirtual.R
import com.rlds.lojavirtual.databinding.ActivityDetahesProdutosBinding

class DetahesProdutos : AppCompatActivity() {
    private lateinit var binding:ActivityDetahesProdutosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetahesProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val foto = intent.extras?.getString("foto")
        val nome = intent.extras?.getString("nome")
        val preco = intent.extras?.getString("preco")
        Glide.with(this).load(foto).into(binding.dtFotoProduto)
        binding.dtNomeProduto.text = nome
        binding.dtPrecoProduto.text = " R$ ${preco}"

    }
}