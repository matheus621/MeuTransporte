package br.com.matheus.meutransporte.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;


import br.com.matheus.meutransporte.modelo.Pedido;

public class PedidoConverter {

    public String converteParaJSON(List<Pedido> pedidos) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("pedidos").array();
            for (Pedido pedido : pedidos){
                js.object();
                js.key("nome").value(pedido.getNome());
                js.key("endereco").value(pedido.getEndereco());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }
}
