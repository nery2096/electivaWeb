/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import REST.bolsaPuntosREST;
import REST.clienteREST;
import REST.conceptoREST;
import REST.usoPuntosCabREST;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import modelos.BolsaPuntos;
import modelos.Cliente;
import modelos.Concepto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Adrián
 */
public class funciones{
    
    public JSONArray returnSearch(JSONArray array, String searchValue){
            JSONArray filtedArray = new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj= null;
                try {
                    obj = array.getJSONObject(i);
                    if(obj.getString("nombre").contains(searchValue))
                    {
                        filtedArray.put(obj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    return filtedArray;
   }
    
   //RETORNA LA CANTIDAD DE PUNTOS DE ACUERDO AL MONTO DE LA OPERACION
   public int returnPuntos(int monto){
        int m=0;
        if (monto>=5000 && monto<=20000){
            m=5;
        }else if (monto>=21000 && monto<=50000){
            m=10;
        }else if (monto>=51000 && monto<=100000){ 
            m=20;
        }
        else if (monto>=101000 && monto<=300000){ 
            m=40;
        }
        return m;
    }
   
   //FUNCION QUE DESCUENTA DE LA BOLSA EL SALDO DE PUNTOS UTILIZADO
   public List<BolsaPuntos> descuentoBolsa(Integer idCliente,String idConcepto) throws ParseException{
        List<BolsaPuntos> results = new ArrayList<BolsaPuntos>();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        bolsaPuntosREST bolsaCliente = new bolsaPuntosREST();
        BolsaPuntos bolsa = new BolsaPuntos();
        conceptoREST concepto = new conceptoREST();
        JSONArray json = new JSONArray(bolsaCliente.findAll_JSON(String.class));
        JSONObject jsonConcepto =  new JSONObject(concepto.find_JSON(String.class, idConcepto));
        //int flag=0;
        int contador= jsonConcepto.getInt("puntosRequeridos");//SE DEFINE LOS PUNTOS REQUERIDOS DEL CONCEPTO DE USO
        for (int i = 0; i < json.length(); i++) {
            JSONObject row = json.getJSONObject(i);
            JSONObject idC = row.getJSONObject("idCliente");
            if (idC.getInt("id") == (idCliente)){
                if(row.getInt("saldoPuntos")>0){    //SI EL SALDO DE PUNTOS ES MAYOR A CERO SE VERIFICARA SI PUEDE SER USADO 
                    if(contador > row.getInt("saldoPuntos")){// SI LOS PUNTOS REQUERIDOS ES MAYOR AL SALDO 
                       bolsa.setIdCliente(returnCliente(idCliente.toString()));
                       bolsa.setId(row.getInt("id"));
                       Date date=formatter.parse(row.getString("fechaAsignacionPuntaje"));
                       bolsa.setFechaAsignacionPuntaje(date);
                       Date dateC=formatter.parse(row.getString("fechaCaducidadPuntaje"));
                       bolsa.setFechaCaducidadPuntaje(dateC);
                       bolsa.setMontoOperacion(row.getInt("montoOperacion"));
                       bolsa.setPuntajeAsignado(row.getInt("puntajeAsignado"));
                       bolsa.setPuntajeUtilizado(row.getInt("saldoPuntos"));// LO UTILIZADO ES IGUAL A LA TOTALIDAD DEL SALDO
                       bolsa.setSaldoPuntos(0);// EL SALDO DE PUNTOS ES UTILIZADO EN SU 100%
                       bolsaCliente.edit_JSON(bolsa,String.valueOf(row.getInt("id")));
                       contador=contador-row.getInt("saldoPuntos");// SE DESCUENTA DE LOS PUNTOS REQUERIDOS EL SALDO QUE FUE USADO
                       results.add(bolsa);
                    }else if(contador <= row.getInt("puntajeAsignado")&& contador>0){ //SI LOS PUNTOS REQUERIDOS ES MENOR AL SALDO
                        bolsa.setIdCliente(returnCliente(idCliente.toString()));
                        bolsa.setId(row.getInt("id"));
                        Date date=formatter.parse(row.getString("fechaAsignacionPuntaje"));
                        bolsa.setFechaAsignacionPuntaje(date);
                        Date dateC=formatter.parse(row.getString("fechaCaducidadPuntaje"));
                        bolsa.setFechaCaducidadPuntaje(dateC);
                        bolsa.setMontoOperacion(row.getInt("montoOperacion"));
                        bolsa.setPuntajeAsignado(row.getInt("puntajeAsignado"));
                        bolsa.setPuntajeUtilizado(contador);// LO UTIILIZADO ES IGUAL AL CONTADOR
                        bolsa.setSaldoPuntos(row.getInt("saldoPuntos") - contador);// EL SALDO DE PUNTOS ES IGUAL AL SALDO - EL CONTADOR
                        bolsaCliente.edit_JSON(bolsa,String.valueOf(row.getInt("id")));
                        contador=0;
                        results.add(bolsa);
                    }
                }
            }
        }
    //SE RETORNA TODAS LAS BOLSAS QUE FUERON USADAS
    return results; 
    }
   
   public Concepto returnConcepto(String idConcepto) throws ParseException{
      
       conceptoREST c = new conceptoREST();
       JSONObject jsonObject = new JSONObject(c.find_JSON(String.class,idConcepto));
       Concepto concepto = new Concepto();
       concepto.setDescripcionConcepto(jsonObject.getString("descripcionConcepto"));
       concepto.setPuntosRequeridos(jsonObject.getInt("puntosRequeridos"));
       concepto.setId(jsonObject.getInt("id"));
       
       
       return concepto;
   }
   public Cliente returnCliente(String idCliente) throws ParseException{
       SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");  
       clienteREST c = new clienteREST();
       JSONObject jsonObject = new JSONObject(c.find(String.class,idCliente));
       Cliente cliente = new Cliente();
       cliente.setApellido(jsonObject.getString("apellido"));
       cliente.setNombre(jsonObject.getString("nombre"));
       cliente.setEmail(jsonObject.getString("email"));
       Date date=formatter.parse(jsonObject.getString("fechaNacimiento"));
       cliente.setFechaNacimiento(date);
       cliente.setId(jsonObject.getInt("id"));
       cliente.setNacionalidad(jsonObject.getString("nacionalidad"));
       cliente.setNumeroDocumento(jsonObject.getString("numeroDocumento"));
       cliente.setTipoDocumento(jsonObject.getInt("tipoDocumento"));
       cliente.setTelefono(jsonObject.getString("telefono"));
       
       return cliente;
   }
   //RETORNA UNA FECHA MAS TREINTA DIAS
   public Date returnDaysPlus30(Date fecha){
       Calendar calendar = new GregorianCalendar();
       calendar.setTime(fecha);
       calendar.add(Calendar.DATE, 30);
       fecha = calendar.getTime();
       
       return fecha;
   }
   public List<Object> usoPuntosFecha(String fecha){
       
       usoPuntosCabREST usoCab = new usoPuntosCabREST();
       JSONArray json = new JSONArray(usoCab.findAll(String.class));
       JSONArray jsonFechas = new JSONArray();
       List<Object> results = new ArrayList<Object>();
       for (int i = 0; i < json.length(); i++) {
            JSONObject row = json.getJSONObject(i);
            if(row.getString("fecha").equals(fecha)){
               
               jsonFechas.put(row);
           }
       }
       results.add(((JSONArray)jsonFechas).toList());
       return results;
       
   }
   public List<Object> usoPuntosClientes(Integer idCliente){
       usoPuntosCabREST usoCab = new usoPuntosCabREST();
       JSONArray json = new JSONArray(usoCab.findAll(String.class));
       JSONArray jsonClientes = new JSONArray();
       List<Object> results = new ArrayList<Object>();
       for (int i = 0; i < json.length(); i++) {
            JSONObject row = json.getJSONObject(i);
            JSONObject idC = row.getJSONObject("idCliente");
            if(idC.getInt("id")== idCliente){
               jsonClientes.put(row);
           }
       }
       results.add(((JSONArray)jsonClientes).toList());
       return results;
       
   }
   public List<Object> puntosAvencer(Integer dias) throws ParseException{
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date); 
        bolsaPuntosREST b = new bolsaPuntosREST();
        JSONArray jsonArray = new JSONArray(b.findAll_JSON(String.class));
        JSONArray json = new JSONArray();
        List<Object> results = new ArrayList<Object>();
        for (int i = 0; i < jsonArray.length(); i++) {
           JSONObject row = jsonArray.getJSONObject(i);
           LocalDate dateAfter = LocalDate.parse(row.getString("fechaCaducidadPuntaje"));
           LocalDate dateBefore = LocalDate.parse(strDate);
           long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
           if (noOfDaysBetween==dias){ // SI LA CANTIDAD DE DIAS ENTRE LA FECHA DE CADUCIDAD Y LA FECHA ACTUAL ES IGUAL A LA CANTIDAD DE DIAS
               json.put(row);           //PASADO EN EL PARAMETRO SE AGREGA LA BOLSA EN EL json para enviar
           }
        }
        results.add(((JSONArray)json).toList());
    return results;
   }
    public void borrarSaldoPuntos() throws ParseException{
        Gson gson=new GsonBuilder().create();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-mm-dd"); 
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date); 
        bolsaPuntosREST b = new bolsaPuntosREST();
        BolsaPuntos bolsa =  new BolsaPuntos();
        JSONArray jsonArray = new JSONArray(b.findAll_JSON(String.class));
        for (int i = 0; i < jsonArray.length(); i++) {
           JSONObject row = jsonArray.getJSONObject(i);
           JSONObject jsonResponse= row.getJSONObject("idCliente");
           LocalDate dateAfter = LocalDate.parse(row.getString("fechaCaducidadPuntaje"));
           LocalDate dateBefore = LocalDate.parse(strDate);
           long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
           if(noOfDaysBetween==0){
               //UserIdentifier userIdentifier = gson.fromJson(jsonResponse.getJSONObject("userIdentifier").toString(), UserIdentifier.class);
               System.out.println("Se borro el dia de hoy un registro de saldo de puntos");
               bolsa.setId(row.getInt("id"));
               Cliente cliente=gson.fromJson(jsonResponse.toString() ,Cliente.class);
               bolsa.setIdCliente(cliente);
               bolsa.setMontoOperacion(row.getInt("montoOperacion"));
               Date dateAsignacion=formatter.parse(row.getString("fechaAsignacionPuntaje"));
               bolsa.setFechaAsignacionPuntaje(dateAsignacion);
               Date dateCaducidad=formatter.parse(row.getString("fechaCaducidadPuntaje"));
               bolsa.setFechaCaducidadPuntaje(dateCaducidad);
               bolsa.setPuntajeAsignado(row.getInt("puntajeAsignado"));
               bolsa.setPuntajeUtilizado(row.getInt("puntajeUtilizado"));
               bolsa.setSaldoPuntos(0);
               b.edit_JSON(bolsa,String.valueOf(row.getInt("id")));
           }
        }
    }
   
   public List<Object> bolsaCliente(Integer idCliente ){
        bolsaPuntosREST bolsaCliente = new bolsaPuntosREST();
        JSONArray json = new JSONArray(bolsaCliente.findAll_JSON(String.class));
        JSONArray jsonC= new JSONArray();
        List<Object> results = new ArrayList<Object>();
            for (int i = 0; i < json.length(); i++) {
                JSONObject row = json.getJSONObject(i);
                JSONObject idC = row.getJSONObject("idCliente");
                if (idC.getInt("id") == (idCliente)){
                    jsonC.put(row);
                }
            }
           results.add(((JSONArray)jsonC).toList());
        return results;
       }
   
}

   
    

