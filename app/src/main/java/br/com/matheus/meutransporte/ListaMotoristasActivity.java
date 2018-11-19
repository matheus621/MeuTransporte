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

import br.com.matheus.meutransporte.adapter.MotoristaAdapter;
import br.com.matheus.meutransporte.converter.MotoristaConverter;
import br.com.matheus.meutransporte.dao.DefaultMotoristaDAO;
import br.com.matheus.meutransporte.modelo.Motorista;

public class ListaMotoristasActivity extends AppCompatActivity {

    private ListView listaMotoristas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_motoristas);

        listaMotoristas = (ListView) findViewById(R.id.lista_motoristas);

        listaMotoristas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Motorista motorista = (Motorista) listaMotoristas.getItemAtPosition(position);

                Intent intentVaiProFormulario = new Intent(ListaMotoristasActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("motorista", motorista);
                startActivity(intentVaiProFormulario);
            }
        });


        Button novoMotorista = (Button) findViewById(R.id.novo_motorista);
        novoMotorista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(ListaMotoristasActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaMotoristas);
    }

    public void carregaListaMotorista() {
        DefaultMotoristaDAO dao = new DefaultMotoristaDAO(this, Motorista.class);
        List<Motorista> motoristas = dao.buscaMotoristas();
        dao.close();

        MotoristaAdapter adapter = new MotoristaAdapter(this, motoristas);
        listaMotoristas.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaListaMotorista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_motoristas, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
                DefaultMotoristaDAO dao = new DefaultMotoristaDAO(this, Motorista.class);
                List<Motorista> motoristas = dao.buscaMotoristas();
                dao.close();

                MotoristaConverter conversor = new MotoristaConverter();
                String json = conversor.converteParaJSON(motoristas);

                Toast.makeText(this,json, Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Motorista motorista = (Motorista) listaMotoristas.getItemAtPosition(info.position);

        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (ActivityCompat.checkSelfPermission(ListaMotoristasActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaMotoristasActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + motorista.getTelefone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });


        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + motorista.getTelefone()));
        itemSMS.setIntent(intentSMS);

        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + motorista.getEndereco()));
        itemMapa.setIntent(intentMapa);


        MenuItem itemSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = motorista.getSite();
        if (!site.startsWith("http://")) {
            site = "http://" + site;
        }

        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                DefaultMotoristaDAO dao = new DefaultMotoristaDAO(ListaMotoristasActivity.this, Motorista.class);
                dao.deleta(motorista);
                dao.close();
                carregaListaMotorista();

                Toast.makeText(ListaMotoristasActivity.this, "Motorista " + motorista.getNome() + " deletado!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
