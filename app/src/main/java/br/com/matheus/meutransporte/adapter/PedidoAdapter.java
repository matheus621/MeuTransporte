package br.com.matheus.meutransporte.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.matheus.meutransporte.R;
import br.com.matheus.meutransporte.modelo.Pedido;

public class PedidoAdapter extends BaseAdapter {
    private final List<Pedido> pedidos;
    private final Context context;

    public PedidoAdapter(Context context, List<Pedido> pedidos) {
        this.context = context;
        this.pedidos = pedidos;
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pedidos.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Pedido pedido = pedidos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View testView = inflater.inflate(R.layout.list_item, null);

        TextView campoNome = testView.findViewById(R.id.item_nome);
        campoNome.setText(pedido.getNome());

        return testView;
    }
}
