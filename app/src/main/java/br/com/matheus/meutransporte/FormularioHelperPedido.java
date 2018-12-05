package br.com.matheus.meutransporte;

import android.widget.EditText;

import br.com.matheus.meutransporte.modelo.Pedido;

public class FormularioHelperPedido {

    private EditText campoEndereco;
    private EditText campoNome;


    private Pedido pedido;

    public FormularioHelperPedido(FormularioPedidoActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.formulario_pedido_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_pedido_endereco);

        pedido = new Pedido();
    }

    public Pedido pegaPedido() {
        pedido.setNome(campoNome.getText().toString());
        pedido.setEndereco(campoEndereco.getText().toString());


        return pedido;
    }

    public void preencheFormularioPedido(Pedido pedido) {
        campoNome.setText(pedido.getNome());
        campoEndereco.setText(pedido.getEndereco());

        this.pedido = pedido;
    }
}

