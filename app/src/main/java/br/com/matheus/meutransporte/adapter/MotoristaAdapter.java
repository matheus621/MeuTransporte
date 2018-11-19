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
import br.com.matheus.meutransporte.modelo.Motorista;

public class MotoristaAdapter extends BaseAdapter {
    private final List<Motorista> motoristas;
    private final Context context;

    public MotoristaAdapter(Context context, List<Motorista> motoristas) {
        this.context = context;
        this.motoristas = motoristas;
    }

    @Override
    public int getCount() {
        return motoristas.size();
    }

    @Override
    public Object getItem(int position) {
        return motoristas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return motoristas.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Motorista motorista = motoristas.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View testView = inflater.inflate(R.layout.list_item, null);

        TextView campoNome = testView.findViewById(R.id.item_nome);
        campoNome.setText(motorista.getNome());

        TextView campoTelefone = testView.findViewById(R.id.item_telefone);
        campoTelefone.setText(motorista.getTelefone());

        ImageView campoFoto = testView.findViewById(R.id.item_foto);
        String caminhoFoto = motorista.getCaminhoFoto();
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzida = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFoto.setImageBitmap(bitmapReduzida);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return testView;
    }
}
