package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mercadopago.android.px.configuration.AdvancedConfiguration;
import com.mercadopago.android.px.core.MercadoPagoCheckout;
import com.mercadopago.android.px.model.Payment;
import com.rlds.lojavirtual.R;
import com.rlds.lojavirtual.databinding.ActivityPagamentoBinding;

import interfaceMercadoPago.ComunicacaoServidorMP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
                   criarJsonObject();
                }
            }
        });
    }
    private void  criarJsonObject(){
        JsonObject dados = new JsonObject();
        // priemiro item
        JsonArray itemLista = new  JsonArray();
        JsonObject item;

        // segundo item
        JsonObject email = new JsonObject();



        item = new JsonObject();
        item.addProperty("title",nome);
        item.addProperty("quantity,",1);
        item.addProperty("currency_id","BRL");
        item.addProperty("unit_price",Double.parseDouble(preco));
        itemLista.add(item);
        dados.add("items", itemLista);


        String emailUsuario = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        email.addProperty("email", emailUsuario);
        dados.add("payer", email);
        Log.d("j", dados.toString());

        criarPreferenciaPagamento(dados);




    }
    private  void criarPreferenciaPagamento(JsonObject dados){
        String site = "https://api.mercadopago.com";
        String url = "/checkout/preferences?acess_token=" + ACCESS_TOKEN;
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(site).addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        ComunicacaoServidorMP conexao_pamento = retrofit.create(ComunicacaoServidorMP.class);
        Call<JsonObject> request = conexao_pamento.enviarPagamento(url,dados);
        request.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String preferenceId = response.body().get("id").getAsString();
                criarPagamento(preferenceId);


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
    private void criarPagamento(String preferenceId){
        final AdvancedConfiguration advancedConfiguration =
                new AdvancedConfiguration.Builder().setBankDealsEnabled(false).build();
        new MercadoPagoCheckout
                .Builder(PUBLIC_KEY, preferenceId)
                .setAdvancedConfiguration(advancedConfiguration).build()
                .startPayment(this, 123);


    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                final Payment pagamento = (Payment) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT);
                respostaMercadoPago(pagamento);

            } else if (resultCode == RESULT_CANCELED) {

            } else {

            }
        }
    }
    private  void respostaMercadoPago(Payment pagamento){
        String status = pagamento.getPaymentStatus();

        if(status.equalsIgnoreCase("approved")){
            Snackbar snackbar = Snackbar.make(binding.container, "Sucesso ao fazer  pagamento!",
                    Snackbar.LENGTH_SHORT

            );
            snackbar.setBackgroundTint(Color.BLUE);
            snackbar.setTextColor(Color.WHITE);
            snackbar.show();


        }else if( status.equalsIgnoreCase("rejected")){

            Snackbar snackbar = Snackbar.make(binding.container, "Erro ao fazer pagamento!",
                    Snackbar.LENGTH_SHORT

            );
            snackbar.setBackgroundTint(Color.RED);
            snackbar.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }

}
