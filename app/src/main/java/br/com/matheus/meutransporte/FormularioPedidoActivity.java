package br.com.matheus.meutransporte;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.matheus.meutransporte.dao.DefaultPedidoDAO;
import br.com.matheus.meutransporte.modelo.Pedido;

public class FormularioPedidoActivity extends AppCompatActivity {

    private FormularioHelperPedido helperPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_pedido);

        helperPedidos = new FormularioHelperPedido(this);

        final Intent intent = getIntent();
        Pedido pedido = (Pedido) intent.getSerializableExtra("motorista");
        if (pedido != null) {
            helperPedidos.preencheFormularioPedido(pedido);
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
                Pedido pedido = helperPedidos.pegaPedido();

                DefaultPedidoDAO dao = new DefaultPedidoDAO(this, Pedido.class);
                if (pedido.getId() != null) {
                    dao.altera(pedido);
                } else {
                    dao.inserePedido(pedido);
                }
                dao.close();

                Toast.makeText(FormularioPedidoActivity.this, "Pedido " + pedido.getNome() + " salvo com Sucesso!", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
