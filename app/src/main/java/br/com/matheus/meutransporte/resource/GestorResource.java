package br.com.matheus.meutransporte.resource;
import java.util.List;

import br.com.matheus.meutransporte.modelo.Gestor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface GestorResource {

    @GET("/gestor")
    Call<List<Gestor>> get();

    @POST("/gestor")
    Call<Gestor> post(@Body Gestor gestor);

    @PUT("/gestor")
    Call<Gestor> put(@Body Gestor gestor);

    @DELETE("/gestor")
    Call<Void> delete(@Body Gestor gestor);
}