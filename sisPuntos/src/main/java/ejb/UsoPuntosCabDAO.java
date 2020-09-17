/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.text.ParseException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import modelos.UsoPuntosCab;

/**
 *
 * @author Adrián
 */
@Stateless
@Path("modelos.usopuntoscab")
public class UsoPuntosCabDAO extends AbstractFacade<UsoPuntosCab> {

    @PersistenceContext(unitName = "com.sistemapuntos_sisPuntos_war_v1PU")
    private EntityManager em;

    public UsoPuntosCabDAO() {
        super(UsoPuntosCab.class);
    }

    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(UsoPuntosCab entity) {
        super.create(entity);
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Integer id, UsoPuntosCab entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UsoPuntosCab find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    //DEVUELVE LAS USO DE PUNTOS POR FECHA
    @GET
    @Path("usopuntosfecha/{fechaUso}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Object> findUsoFecha(@PathParam("fechaUso") String fechaUso) throws ParseException {
        return super.findUsoFecha(fechaUso);
    }
    //DEVUELVE LAS USO DE PUNTOS POR CLIENTE
    @GET
    @Path("usopuntoscliente/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Object> findUsoClientes(@PathParam("idCliente") Integer idCliente) throws ParseException {
        return super.findUsoClientes(idCliente);
    }
    //DEVUELVE LAS USO DE PUNTOS POR CONCEPTO DE USO
    @GET
    @Path("usopuntosconcepto/{idConcepto}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Object> findUsoConcepto(@PathParam("idConcepto") Integer idConcepto){
        return super.findUsoConcepto(idConcepto);
    }
    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<UsoPuntosCab> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UsoPuntosCab> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
