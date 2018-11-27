package br.com.matheus.meutransporte;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class TransporteActivity extends AppCompatActivity {

    private static final String TAG = "TransporteActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporte);

        if (isServicesOK()) {
            init();
        }
    }

    public void carregarListaMotorista(View view){
        Intent intent = new Intent(this,ListaMotoristasActivity.class);
        startActivity(intent);
    }

    public void carregarListaGestor(View view){
        Intent intent = new Intent(this,ListaGestorActivity.class);
        startActivity(intent);
    }

    public void carregarListaPedidos(View view){
        Intent intent = new Intent(this,ListaPedidosActivity.class);
        startActivity(intent);
    }

    private void init(){
        Button maps = (Button) findViewById(R.id.maps);
        maps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransporteActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean isServicesOK() {
        Log.d(TAG, "Verificando versão do Google Services");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(TransporteActivity.this);

        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG,"Google Services Funcionando");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOK: Ocorreu um erro, mas podemos corrigi-lo");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(TransporteActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "Você não pode acessar o Maps", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
