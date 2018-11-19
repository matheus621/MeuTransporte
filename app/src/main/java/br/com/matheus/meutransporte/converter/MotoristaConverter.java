package br.com.matheus.meutransporte.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.matheus.meutransporte.modelo.Motorista;

public class MotoristaConverter {

    public String converteParaJSON(List<Motorista> motoristas) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("motorista").array();
            for (Motorista motorista : motoristas){
                js.object();
                js.key("nome").value(motorista.getNome());
                js.key("nota").value(motorista.getNota());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }
}
