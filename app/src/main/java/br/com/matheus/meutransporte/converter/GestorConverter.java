package br.com.matheus.meutransporte.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.matheus.meutransporte.modelo.Gestor;


public class GestorConverter {

    public String converteParaJSON(List<Gestor> gestor) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("gestor").array();
            for (Gestor gestores : gestor){
                js.object();
                js.key("nome").value(gestores.getNome());
                js.key("nota").value(gestores.getNota());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }
}
