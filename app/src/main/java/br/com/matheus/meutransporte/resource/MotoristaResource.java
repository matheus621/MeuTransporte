package br.com.matheus.meutransporte.resource;
import java.util.List;

import br.com.matheus.meutransporte.modelo.Motorista;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface MotoristaResource {

    @GET("/motorista")
    Call<List<Motorista>> get();

    @POST("/motorista")
    Call<Motorista> post(@Body Motorista motorista);

    @PUT("/motorista")
    Call<Motorista> put(@Body Motorista motorista);

    @DELETE("/motorista")
    Call<Void> delete(@Body Motorista motorista);
}