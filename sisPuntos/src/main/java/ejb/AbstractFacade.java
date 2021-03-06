/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import REST.bolsaPuntosREST;
import REST.clienteREST;
import REST.conceptoREST;
import REST.usoPuntosCabREST;
import REST.usoPuntosDetREST;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Schedule;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import modelos.BolsaPuntos;
import modelos.Cliente;
import modelos.Concepto;
import modelos.UsoPuntosCab;
import modelos.UsoPuntosDet;
import org.json.JSONObject;
import utils.funciones;

/**
 *
 * @author Adrián
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();
    
    public void create(T entity) {
        getEntityManager().persist(entity);
    }
    public void edit(T entity) {
        getEntityManager().merge(entity);
        
    }
    public void cargaPuntos(String idCliente,int monto) throws ParseException{
        Date fecha = new Date();// Se define la fecha actual
        funciones f = new funciones();
        bolsaPuntosREST bolsaREST = new bolsaPuntosREST();//Se instancia un cliente que utiliza los recursos de BolsaPuntosDAO
        BolsaPuntos bolsa = new BolsaPuntos();
        bolsa.setIdCliente(f.returnCliente(idCliente));//Se guarda el cliente con el cual se registro la operacion de carga de bolsa de puntos
        bolsa.setMontoOperacion(monto);
        bolsa.setPuntajeAsignado(f.returnPuntos(monto));
        bolsa.setFechaAsignacionPuntaje(fecha);
        bolsa.setFechaCaducidadPuntaje(f.returnDaysPlus30(fecha));//Se guarda la caducidad del puntaje que es 30 dias despues de la fecha de 
                                                                  //de la asignacion de puntaje
        bolsa.setPuntajeUtilizado(0);
        bolsa.setSaldoPuntos(f.returnPuntos(monto));//Se define la cantidad de puntos de acuerdo al monto de la operacion
        bolsaREST.create_JSON(bolsa);//Se persiste en la base de datos
    }
    public void utilizarPuntos(String idCliente,String idConcepto) throws ParseException{
        Date fecha = new Date();// Se define la fecha actual
        funciones f = new funciones();
        usoPuntosCabREST usoCrest = new usoPuntosCabREST();
        UsoPuntosCab usoC = new UsoPuntosCab();
        usoC.setFecha(fecha);
        usoC.setIdCliente(f.returnCliente(String.valueOf(idCliente)));
        usoC.setIdConcepto(f.returnConcepto(idConcepto));
        usoC.setPuntajeUtilizado(f.returnConcepto(idConcepto).getPuntosRequeridos());
        usoCrest.create(usoC);
    }
    public void utilizarPuntosDetalles(String idCliente,String idConcepto,Integer usoCab) throws ParseException{
        funciones f = new funciones();
        usoPuntosDetREST usoDrest = new usoPuntosDetREST();
        UsoPuntosDet usoD = new UsoPuntosDet();
        for (BolsaPuntos item : (f.descuentoBolsa(Integer.parseInt(idCliente),idConcepto))) {
            usoD.setIdUsoPuntosCab(usoCab);
            usoD.setPuntajeUtilizado(f.returnConcepto(idConcepto).getPuntosRequeridos());
            usoD.setIdBolsa(item);
            usoDrest.create(usoD);
        }
    }
    public String puntosMontos(Integer monto){
        funciones f =  new funciones();
        return "Equivale"+" a "+String.valueOf(f.returnPuntos(monto)) + " Puntos";
    }
    
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public List<T> findNombres(String nombre) {
        Query query = getEntityManager().createQuery(
              "SELECT c FROM Cliente c WHERE c.nombre LIKE"+"'%"+nombre+"%'");
        List<Cliente> resultList = query.getResultList();
        return (List<T>) resultList;
    }
    public List<T> findApellidos(String apellido) {
        Query query = getEntityManager().createQuery(
              "SELECT c FROM Cliente c WHERE c.apellido LIKE"+"'%"+apellido+"%'");
        List<Cliente> resultList = query.getResultList();
        return (List<T>) resultList;
    }
    public List<T> findBday(String fecha) {
        Query query = getEntityManager().createQuery(
              "SELECT c FROM Cliente c WHERE to_char(c.fechaNacimiento,'dd-mm-yyyy') LIKE"+"'%"+fecha+"%'");
        List<Cliente> resultList = query.getResultList();
        return (List<T>) resultList;
    }
    
    List<Object> findPuntosAvencer(Integer dias) throws ParseException {
        funciones f = new funciones();
        return f.puntosAvencer(dias);
    }
    @Schedule(dayOfWeek="Mon", hour="13",minute="28")
    public void borrarSaldo() throws ParseException{
        funciones f = new funciones();
        f.borrarSaldoPuntos();
    }
    List<BolsaPuntos> bolsaPorCliente(Integer idCliente) {
        Gson gson=new GsonBuilder().create();
        clienteREST clienteFunciones = new clienteREST();
        JSONObject clienteJson = new JSONObject(clienteFunciones.find(String.class, String.valueOf(idCliente)));
        Cliente cliente =gson.fromJson(clienteJson.toString(),Cliente.class);
        
        Query query = getEntityManager().createQuery(
              "SELECT c FROM BolsaPuntos c WHERE c.idCliente=:idCliente");
        query.setParameter("idCliente",cliente);
        List<BolsaPuntos> resultList = query.getResultList();
        return resultList;
    }

    List<Object> findUsoFecha(String fechaUso) throws ParseException {
        funciones f =  new funciones();
        return f.usoPuntosFecha(fechaUso);
    }
    List<UsoPuntosCab> findUsoClientes(Integer idCliente){
        Gson gson=new GsonBuilder().create();
        clienteREST clienteFunciones = new clienteREST();
        JSONObject clienteJson = new JSONObject(clienteFunciones.find(String.class, String.valueOf(idCliente)));
        Cliente cliente =gson.fromJson(clienteJson.toString(),Cliente.class);
        
        Query query = getEntityManager().createQuery(
              "SELECT c FROM UsoPuntosCab c WHERE c.idCliente=:idCliente");
        query.setParameter("idCliente",cliente);
        List<UsoPuntosCab> resultList = query.getResultList();
        return resultList;
        
    }
    List<UsoPuntosCab> findUsoConcepto(Integer idConcepto) {
        Gson gson=new GsonBuilder().create();
        conceptoREST conceptoFunciones = new conceptoREST();
        JSONObject conceptoJson = new JSONObject(conceptoFunciones.find_JSON(String.class, String.valueOf(idConcepto)));
        Concepto concepto =gson.fromJson(conceptoJson.toString(),Concepto.class);
        
        Query query = getEntityManager().createQuery(
              "SELECT c FROM UsoPuntosCab c WHERE c.idConcepto=:idConcepto");
        query.setParameter("idConcepto",concepto);
        List<UsoPuntosCab> resultList = query.getResultList();
        return resultList;
    }

    List<BolsaPuntos> findPuntosPorRango(Integer from, Integer to) {
        Query query = getEntityManager().createQuery(
              "SELECT c FROM BolsaPuntos c WHERE c.saldoPuntos BETWEEN :from AND :to");
        query.setParameter("from", from);
        query.setParameter("to", to);
        List<BolsaPuntos> resultList = query.getResultList();
        return resultList;

        //funciones f = new funciones();
        //return f.puntosPorRango(from, to);
    }
    
}   
   
     
    

    
    

