package br.com.matheus.meutransporte;

import android.content.Intent;
import android.os.Bundle;
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

import br.com.matheus.meutransporte.adapter.PedidoAdapter;
import br.com.matheus.meutransporte.bootstrap.APIClient;
import br.com.matheus.meutransporte.converter.PedidoConverter;
import br.com.matheus.meutransporte.modelo.Pedido;
import br.com.matheus.meutransporte.resource.PedidoResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaPedidosActivity extends AppCompatActivity {

    private ListView listViewPedido;
    private List<Pedido> listaPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        //metodo de busca pedidos
        PedidoResource apiPedidoResource = APIClient.getClient().create(PedidoResource.class);

        Call<List<Pedido>> get = apiPedidoResource.get();

        get.enqueue(new Callback<List<Pedido>>() {


            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                listViewPedido = findViewById(R.id.lista_pedidos);

                listaPedidos = response.body();

                PedidoAdapter pessoaAdapter = new PedidoAdapter(getApplicationContext(), listaPedidos);
                listViewPedido.setAdapter(pessoaAdapter);
            }


            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(),Toast.LENGTH_LONG).show();
            }
        });


        Button novoPedido = (Button) findViewById(R.id.novo_pedido);
        novoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(ListaPedidosActivity.this, FormularioPedidoActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });
        registerForContextMenu(listViewPedido);


//        apiPedidoResource = APIClient.getClient().create(PedidoResource.class);
//
//        txtNome = FormularioPedidoActivity.;
//        txtEndereço = dadosGeraisFragment.txtEmail;
//
//
//        final Pedido user = Pedido.builder()
//                .nome(txtNome.getText().toString())
//                .email(txtEmail.getText().toString())
//                .pais(txtPais.getText().toString())
//                .nomeUsuario(txtLogin.getText().toString())
//                .senha(txtSenha.getText().toString()).build();
//
//        Call<Usuario> post = apiUserResource.post(user);
//
//        post.enqueue(new Callback<Usuario>() {
//                         @Override
//                         public void onResponse(Call<Usuario> call, Response<Usuario> response) {
//                             Usuario u = response.body();
//                             Toast.makeText(getApplicationContext(),
//                                     u.toString(),
//                                     Toast.LENGTH_LONG).show();
//
//                         }

    }

//    public void carregaListaPedidos() {
//        DefaultPedidoDAO dao = new DefaultPedidoDAO(this, Pedido.class);
//        List<Pedido> pedidos = dao.buscaPedido();
//        dao.close();
//
//        PedidoAdapter adapter = new PedidoAdapter(this, pedidos);
//        listaPedidos.setAdapter(adapter);
//    }




    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_pedidos, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_enviar_notas:

                PedidoConverter conversor = new PedidoConverter();
                String json = conversor.converteParaJSON(listaPedidos);

                Toast.makeText(this,json, Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//        final Pedido pedido = (Pedido) listaPedidos.getItemAtPosition(info.position);
//
//
//        MenuItem itemMapa = menu.add("Visualizar no mapa");
//        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
//        intentMapa.setData(Uri.parse("geo:0,0?q=" + pedido.getEndereco()));
//        itemMapa.setIntent(intentMapa);
//
//
//        MenuItem deletar = menu.add("Deletar");
//        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//
//
//                DefaultPedidoDAO dao = new DefaultPedidoDAO(ListaPedidosActivity.this, Pedido.class);
//                dao.deleta(pedido);
//                dao.close();
//                carregaListaPedidos();
//
//                Toast.makeText(ListaPedidosActivity.this, "Pedido " + pedido.getNome() + " deletado!", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }
}
