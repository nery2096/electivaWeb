/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import REST.usoPuntosCabREST;
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
import modelos.BolsaPuntos;

/**
 *
 * @author Adrián
 */
@Stateless
@Path("modelos.bolsapuntos")
public class BolsaPuntosDAO extends AbstractFacade<BolsaPuntos> {
    
    
    @PersistenceContext(unitName = "com.sistemapuntos_sisPuntos_war_v1PU")
    private EntityManager em;

    public BolsaPuntosDAO() {
        super(BolsaPuntos.class);
    }

    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(BolsaPuntos entity) {
        super.create(entity);
    }
    //UTILIZA LOS PUNTOS DE LA BOLSA DE UN CLIENTE
    @POST
    @Path("utilizarpuntos/{idCliente}/{idConcepto}")
    public void utilizarPuntos(@PathParam("idCliente") String idCliente,@PathParam("idConcepto") String idConcepto) throws ParseException {
        usoPuntosCabREST usoCrest = new usoPuntosCabREST();
        super.utilizarPuntos(idCliente, idConcepto);
        Integer usoCab = Integer.parseInt(usoCrest.countREST());
        super.utilizarPuntosDetalles(idCliente,idConcepto,usoCab);
        
    }
    //CARGA UNA BOLSA CON EL IDCLIENTE Y MONTO 
    @POST
    @Path("carga/{idCliente}/{monto}")
    public void cargaPuntos(@PathParam("idCliente") String idCliente,@PathParam("monto") Integer monto) throws ParseException {
        super.cargaPuntos(idCliente,monto);
    }
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Integer id, BolsaPuntos entity) {
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
    public BolsaPuntos find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    //DEVUELVE LA CANTIDAD DE PUNTOS DE ACUERDO AL MONTO DE LA COMPRA
    @GET
    @Path("puntosmontos/{monto}")
    @Produces(MediaType.TEXT_PLAIN)
    public String puntosMontos(@PathParam("monto") Integer monto) {
        return super.puntosMontos(monto);
    }
    //DEVUELVE LAS BOLSAS A VENCER EN X DIAS
    @GET
    @Path("puntosavencer/{dias}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Object> findPuntosAvencer(@PathParam("dias") Integer dias) throws ParseException {
        return super.findPuntosAvencer(dias);
    }
    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<BolsaPuntos> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BolsaPuntos> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    //DEVUELVE LAS BOLSAS POR CLIENTE
    @GET
    @Path("bolsacliente/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Object> bolsaPorCliente(@PathParam("idCliente") Integer idCliente) {
        return super.bolsaPorCliente(idCliente);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
