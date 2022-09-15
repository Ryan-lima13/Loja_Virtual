package activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.rlds.lojavirtual.R
import com.rlds.lojavirtual.databinding.ActivityDetahesProdutosBinding

class DetahesProdutos : AppCompatActivity() {
    private lateinit var binding:ActivityDetahesProdutosBinding
    var tamanhoCalcado= ""
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

        binding.btFinalizarProduto.setOnClickListener {
            val  tamanho_calacado_38 = binding.txt38
            val tamanho_calcado_39 = binding.txt39
            val tamanho_calcado_40 = binding.txt40
            val tamanho_calcado_41 = binding.txt41
            val tamanho_calcado_42 = binding.txt42
            when(true){
                tamanho_calacado_38.isChecked ->tamanhoCalcado = "38"
                tamanho_calcado_39.isChecked->tamanhoCalcado = "39"
                tamanho_calcado_40.isChecked->tamanhoCalcado = "40"
                tamanho_calcado_41.isChecked->tamanhoCalcado = "41"
                tamanho_calcado_42.isChecked-> tamanhoCalcado = "42"
                else->{

                }
            }
            if( !tamanho_calacado_38.isChecked && !tamanho_calcado_39.isChecked &&! tamanho_calcado_40.isChecked &&
            !tamanho_calcado_41.isChecked && !tamanho_calcado_42.isChecked
            ){
                val snackbar = Snackbar.make(
                    it, "Escolha o tamanho do calçado",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
        }else{
             val intent = Intent(this, Pagamento::class.java)
                intent.putExtra("tamanho_calcado",tamanhoCalcado)
                intent.putExtra("nome", nome)
                intent.putExtra("preco",preco)
                startActivity(intent)

            }

        }

    }
}