package br.com.matheus.meutransporte;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.matheus.meutransporte.modelo.Gestor;
import br.com.matheus.meutransporte.modelo.Motorista;

public class FormularioHelperGestor {

    private EditText campoEndereco;
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoSite;
    private RatingBar campoNota;
    private ImageView campoFoto;

    private Gestor gestor;
    ;

    public FormularioHelperGestor(FormularioGestorActivity activityGestor) {
        campoNome = (EditText) activityGestor.findViewById(R.id.formulario_nome);
        campoEndereco = (EditText) activityGestor.findViewById(R.id.formulario_endereco);
        campoTelefone = (EditText) activityGestor.findViewById(R.id.formulario_telefone);
        campoSite = (EditText) activityGestor.findViewById(R.id.formulario_site);
        campoNota = (RatingBar) activityGestor.findViewById(R.id.formulario_nota);
        campoFoto = (ImageView) activityGestor.findViewById(R.id.formulario_foto);
        gestor = new Gestor();
    }

    public Gestor pegaGestor() {
        gestor.setNome(campoNome.getText().toString());
        gestor.setEndereco(campoEndereco.getText().toString());
        gestor.setTelefone(campoTelefone.getText().toString());
//        gestor.setSite(campoSite.getText().toString());
//        gestor.setNota(Double.valueOf(campoNota.getProgress()));
//        gestor.setCaminhoFoto((String) campoFoto.getTag());

        return gestor;
    }

    public void preencheFormulario(Gestor gestor) {
        campoNome.setText(gestor.getNome());
        campoEndereco.setText(gestor.getEndereco());
        campoTelefone.setText(gestor.getTelefone());
//        campoSite.setText(gestor.getSite());
//        campoNota.setProgress(gestor.getNota().intValue());
//        carregaImagemGestor(gestor.getCaminhoFoto());
        this.gestor = gestor;
    }

    public void carregaImagemGestor(String caminhoFoto) {
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzida = Bitmap.createScaledBitmap(bitmap, 300, 200, true);
            campoFoto.setImageBitmap(bitmapReduzida);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }

    }
}
