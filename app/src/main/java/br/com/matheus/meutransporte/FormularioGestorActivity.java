package br.com.matheus.meutransporte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.matheus.meutransporte.dao.DefaultGestorDAO;
import br.com.matheus.meutransporte.modelo.Gestor;

public class FormularioGestorActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 568;
    private FormularioHelperGestor helperGestor;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helperGestor = new FormularioHelperGestor(this);

        final Intent intent = getIntent();
        Gestor gestor = (Gestor) intent.getSerializableExtra("gestor");
        if (gestor != null) {
            helperGestor.preencheFormulario(gestor);
        }

        Button botaoFoto = findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpeg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(FormularioGestorActivity.this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
            helperGestor.carregaImagemGestor(caminhoFoto);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                Gestor gestor = helperGestor.pegaGestor();

                DefaultGestorDAO dao = new DefaultGestorDAO(this, Gestor.class);
                if (gestor.getId() != null) {
                    dao.altera(gestor);
                } else {
                    dao.insereGestor(gestor);
                }
                dao.close();

                Toast.makeText(FormularioGestorActivity.this, "Gestor " + gestor.getNome() + " salvo com Sucesso!", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
