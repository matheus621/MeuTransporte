package br.com.matheus.meutransporte.resource;
import java.util.List;

import br.com.matheus.meutransporte.modelo.Pedido;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface PedidoResource {

    @GET("/pedido")
    Call<List<Pedido>> get();

    @POST("/pedido")
    Call<Pedido> post(@Body Pedido pedido);

    @PUT("/pedido")
    Call<Pedido> put(@Body Pedido pedido);

    @DELETE("/pedido")
    Call<Void> delete(@Body Pedido pedido);
}