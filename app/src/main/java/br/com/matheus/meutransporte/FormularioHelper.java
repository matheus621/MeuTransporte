package br.com.matheus.meutransporte;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.matheus.meutransporte.modelo.Motorista;

public class FormularioHelper {

    private EditText campoEndereco;
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoSite;
    private RatingBar campoNota;
    private ImageView campoFoto;

    private Motorista motorista;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        motorista = new Motorista();
    }

    public Motorista pegaMotorista() {
        motorista.setNome(campoNome.getText().toString());
        motorista.setEndereco(campoEndereco.getText().toString());
        motorista.setTelefone(campoTelefone.getText().toString());
//        motorista.setSite(campoSite.getText().toString());
//        motorista.setNota(Double.valueOf(campoNota.getProgress()));
//        motorista.setCaminhoFoto((String) campoFoto.getTag());

        return motorista;
    }

    public void preencheFormulario(Motorista motorista) {
        campoNome.setText(motorista.getNome());
        campoEndereco.setText(motorista.getEndereco());
        campoTelefone.setText(motorista.getTelefone());
//        campoSite.setText(motorista.getSite());
//        campoNota.setProgress(motorista.getNota().intValue());
//        carregaImagem(motorista.getCaminhoFoto());
        this.motorista = motorista;
    }

    public void carregaImagem(String caminhoFoto) {
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzida = Bitmap.createScaledBitmap(bitmap, 300, 200, true);
            campoFoto.setImageBitmap(bitmapReduzida);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }

    }
}
