package br.com.matheus.meutransporte;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.matheus.meutransporte.adapter.GestorAdapter;
import br.com.matheus.meutransporte.converter.GestorConverter;
import br.com.matheus.meutransporte.dao.DefaultGestorDAO;
import br.com.matheus.meutransporte.modelo.Gestor;

public class ListaGestorActivity extends AppCompatActivity {

    private ListView listaGestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_gestor);

        listaGestor = (ListView) findViewById(R.id.lista_gestor);

        listaGestor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Gestor gestor = (Gestor) listaGestor.getItemAtPosition(position);

                Intent intentVaiProFormulario = new Intent(ListaGestorActivity.this, FormularioGestorActivity.class);
                intentVaiProFormulario.putExtra("Gestor", gestor);
                startActivity(intentVaiProFormulario);
            }
        });


        Button novoGestor = (Button) findViewById(R.id.novo_gestor);
        novoGestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(ListaGestorActivity.this, FormularioGestorActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaGestor);
    }

    public void carregarListaGestor() {
        DefaultGestorDAO dao = new DefaultGestorDAO(this, Gestor.class);
        List<Gestor> gestor = dao.buscaGestor();
        dao.close();

        GestorAdapter adapter = new GestorAdapter(this, gestor);
        listaGestor.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListaGestor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_gestor, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
                DefaultGestorDAO dao = new DefaultGestorDAO(this, Gestor.class);
                List<Gestor> gestor = dao.buscaGestor();
                dao.close();

                GestorConverter conversor = new GestorConverter();
                String json = conversor.converteParaJSON(gestor);

                Toast.makeText(this,json, Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Gestor gestor = (Gestor) listaGestor.getItemAtPosition(info.position);

        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (ActivityCompat.checkSelfPermission(ListaGestorActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaGestorActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + gestor.getTelefone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });


        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + gestor.getTelefone()));
        itemSMS.setIntent(intentSMS);

        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + gestor.getEndereco()));
        itemMapa.setIntent(intentMapa);


        MenuItem itemSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = gestor.getSite();
        if (!site.startsWith("http://")) {
            site = "http://" + site;
        }

        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                DefaultGestorDAO dao = new DefaultGestorDAO(ListaGestorActivity.this, Gestor.class);
                dao.deleta(gestor);
                dao.close();
                carregarListaGestor();

                Toast.makeText(ListaGestorActivity.this, "Gestor " + gestor.getNome() + " deletado!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
