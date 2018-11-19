package br.com.matheus.meutransporte.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.matheus.meutransporte.R;
import br.com.matheus.meutransporte.modelo.Gestor;


public class GestorAdapter extends BaseAdapter {
    private final List<Gestor> gestores;
    private final Context context;

    public GestorAdapter(Context context, List<Gestor> gestor) {
        this.context = context;
        this.gestores = gestor;
    }

    @Override
    public int getCount() {
        return gestores.size();
    }

    @Override
    public Object getItem(int position) {
        return gestores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return gestores.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Gestor gestor = gestores.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View testView = inflater.inflate(R.layout.list_item, null);

        TextView campoNome = testView.findViewById(R.id.item_nome);
        campoNome.setText(gestor.getNome());

        TextView campoTelefone = testView.findViewById(R.id.item_telefone);
        campoTelefone.setText(gestor.getTelefone());

        ImageView campoFoto = testView.findViewById(R.id.item_foto);
        String caminhoFoto = gestor.getCaminhoFoto();
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzida = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFoto.setImageBitmap(bitmapReduzida);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return testView;
    }
}
