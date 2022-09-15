package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.rlds.lojavirtual.R;
import com.rlds.lojavirtual.databinding.ActivityPagamentoBinding;

public class Pagamento extends AppCompatActivity {
    private ActivityPagamentoBinding binding;
    private String tamaho_calcado ;
    private String nome;
    private String preco;
    private  final String PUBLIC_KEY = "TEST-2a346797-1083-49dd-aca5-08e15b15d31e";
    private  final String ACCESS_TOKEN ="TEST-4469866560841153-091422-c1921800ac445edcd78c9f5c0c95b059-1198698876";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tamaho_calcado = getIntent().getExtras().getString("tamanho_calcado");
        nome = getIntent().getExtras().getString("nome");
        preco = getIntent().getExtras().getString("preco");

        binding.btPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bairro = binding.txtBairro.getText().toString();
                String rua = binding.txtRua.getText().toString();
                String cidade = binding.txtBairro.getText().toString();
                String celular = binding.celular.getText().toString();

                if (bairro.isEmpty() || rua.isEmpty() || cidade.isEmpty() || celular.isEmpty()){
                    Snackbar snackbar = Snackbar.make( view,
                            "Preencha todos os campos",
                            Snackbar.LENGTH_SHORT
                            );
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();

                }else {
                    // fazer paagemnto
                    Snackbar snackbar = Snackbar.make( view,
                            "fazer paagmento",
                            Snackbar.LENGTH_SHORT
                    );
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                }
            }
        });
    }
}