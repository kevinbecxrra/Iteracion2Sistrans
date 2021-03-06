package uniandes.isis2304.parranderos.persistencia;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.log4j.Logger;

import jdk.internal.org.jline.utils.Log;
import sun.util.logging.resources.logging;
import uniandes.isis2304.parranderos.negocio.Contrato;
import uniandes.isis2304.parranderos.negocio.Parranderos;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.ReservaColectiva;
import uniandes.isis2304.parranderos.negocio.UsosVinculo;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto RESERVA de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Kevin Becerra - Christian Forigua
 */
public class SQLReserva {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	private static Logger log = Logger.getLogger(Parranderos.class.getName());

	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaParranderos.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaParranderos pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLReserva(PersistenciaParranderos pp) {
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una RESERVA a la base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @param id - Id de la reserva
	 * @param id_contrato - Id del contrato
	 * @param personas - Cantidad de persona para la reserva
	 * @param fecha_inicio - Fecha de inicio de la reserva
	 * @param fecha_fin - Fecha de fin de la reserva
	 * @param fecha_limite - Fecha de limite para entregar con descuento
	 * @param fecha_realizacion - Fecha de realizacion de la reserva
	 * @param tipo -  Tipo de alojamiento a reservar
	 * @param id_cliente -  Id de cliente 
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarReserva (PersistenceManager pm, long id, long id_contrato, int personas, String fecha_inicio, String fecha_fin, String fecha_limite, String fecha_realizacion, String tipo, long id_cliente) 
	{
		//Ontener reservas del contrato
		Query q3 = pm.newQuery(SQL, "SELECT * FROM CONTRATO WHERE id = ?");
		q3.setResultClass(Contrato.class);
		q3.setParameters(id_contrato);
		Contrato ct= (Contrato) q3.executeUnique();
		if (ct.getHabilitada().equals("YES")){
		Query q1=pm.newQuery(SQL, "SELECT * FROM RESERVA WHERE ID_CONTRATO=?");
		q1.setResultClass(Reserva.class);
		q1.setParameters(id_contrato); 
		List<Reserva> reservas=(List<Reserva>)q1.executeList();
		DateFormat fechaHora = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		long rta=(long) 0;
		try {
			Date fecha_fin_date = fechaHora.parse(fecha_fin);
			Date fecha_inicio_date=fechaHora.parse(fecha_inicio);
			boolean Sepuede=true; 
			for (int i=0; i<reservas.size() && Sepuede;i++) {
				Date fecha_fin_date_reserva = fechaHora.parse(reservas.get(i).getFecha_fin());
				Date fecha_inicio_date_reserva=fechaHora.parse(reservas.get(i).getFecha_inicio());
				if (!(fecha_fin_date.before(fecha_inicio_date_reserva)||fecha_inicio_date.after(fecha_fin_date_reserva))){
					Sepuede=false;
				}

			}
			if (Sepuede) {
				Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReserva() + "(ID,ID_CONTRATO,PERSONAS,FECHA_INICIO,FECHA_FIN,FECHA_LIMITE,FECHA_REALIZACIOM,TIPO,ID_CLIENTE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				q.setParameters(id, id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
				rta= (long) q.executeUnique();  
			}} catch (ParseException e) {
				System.out.println("Error al convertir las fechas");
				e.printStackTrace();
			}
		return rta;
		}else {
			return (long)0;
		}
	}


	/**
	 * Crea y ejecuta la sentencia SQL para eliminar RESERVAS de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la reserva
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarReservaPorId (PersistenceManager pm, long idReserva)
	{	
		Reserva eliminada=darReservaPorId(pm, idReserva);
		log.info ("Eliminando Reserva: " + eliminada.toString());
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva () + " WHERE id = ?");
		q.setParameters(idReserva);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA RESERVA de la 
	 * base de datos de Alohandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReserva - El identificador de la reserva
	 * @return El objeto RESERVA que tiene el identificador dado
	 */
	public Reserva darReservaPorId (PersistenceManager pm, long idReserva) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva () + " WHERE id = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(idReserva);
		return (Reserva) q.executeUnique();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS RESERVAS de la 
	 * base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos RESERVA
	 */
	public List<Reserva> darReservas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM RESERVA");
		q.setResultClass(Reserva.class);
		return (List<Reserva>) q.executeList();
	}

	public List<UsosVinculo> darUsosPorVinculo(PersistenceManager pm){
		Query q =pm.newQuery(SQL,"SELECT C.VINCULO AS VINCULO,COUNT(C.VINCULO) AS VECES FROM RESERVA R JOIN (SELECT * FROM CLIENTE) C ON R.ID_CLIENTE=C.ID GROUP BY C.VINCULO");
		q.setResultClass(UsosVinculo.class);
		return (List<UsosVinculo>) q.executeList(); 
	}
	
	public List<String> darUso(PersistenceManager pm){
		Integer[] ganancia=new Integer[12];
		Integer[] ocupacion= new Integer[12];
		ganancia[0]=0;
		ganancia[1]=0;
		ganancia[2]=0;
		ganancia[3]=0;
		ganancia[4]=0;
		ganancia[5]=0;
		ganancia[6]=0;
		ganancia[7]=0;
		ganancia[8]=0;
		ganancia[9]=0;
		ganancia[10]=0;
		ganancia[11]=0;
		ocupacion[0]=0;
		ocupacion[1]=0;
		ocupacion[2]=0;
		ocupacion[3]=0;
		ocupacion[4]=0;
		ocupacion[5]=0;
		ocupacion[6]=0;
		ocupacion[7]=0;
		ocupacion[8]=0;
		ocupacion[9]=0;
		ocupacion[10]=0;
		ocupacion[11]=0;
		List<Reserva> reservas= darReservas(pm);
		for (int i=0; i<reservas.size();i++) {
			Query q = pm.newQuery(SQL, "SELECT * FROM CONTRATO WHERE id = ?");
			q.setResultClass(Contrato.class);
			q.setParameters(reservas.get(i).getId_contrato());
			Contrato ct= (Contrato) q.executeUnique();
			String mes =reservas.get(i).getFecha_inicio().split("/")[1];
			if (mes.startsWith("0")) {
				mes=mes.substring(1);
			}
			int mesnum=Integer.parseInt(mes);
			mesnum--;
			ganancia[mesnum]=ganancia[mesnum]+ct.getCosto(); 
			ocupacion[mesnum]=ocupacion[mesnum]+reservas.get(i).getPersonas();
		}
		List<Integer> lista_ganancias= new ArrayList<>(ganancia.length);
		for (int i:ganancia) {
			lista_ganancias.add(Integer.valueOf(i));
		}
		List<Integer> lista_ocupacion= new ArrayList<>(ocupacion.length);
		for (int i:ocupacion) {
			lista_ocupacion.add(Integer.valueOf(i));
		}
		int max_ganancia=Collections.max(lista_ganancias); 
		int max_ocup=Collections.max(lista_ocupacion);
		int min_ocup=Collections.min(lista_ocupacion); 
		List<String> retornar = new LinkedList<>();
		retornar.add("El Mes de mayor ganancia ha sido "+ darMes(lista_ganancias.indexOf(max_ganancia)));
		retornar.add("El Mes de mayor ocupación ha sido" + darMes(lista_ocupacion.indexOf(max_ocup)));
		retornar.add("El Mes de menor ocupación ha sido" + darMes(lista_ocupacion.indexOf(min_ocup)));
		return retornar;
	}
	public String darMes(int a) {
		String res="";
		switch(a) {
		case 0:
			res= "Enero";
		case 1:
			res= "Febrero"; 
		case 2:
			res= "Marzo"; 
		case 3:
			res= "Abril"; 
		case 4:
			res= "Mayo"; 
		case 5:
			res= "Junio"; 
		case 6:
			res= "Julio"; 
		case 7:
			res= "Agosto"; 
		case 8:
			res= "Septiembre"; 
		case 9:
			res= "Octubre"; 
		case 10:
			res= "Noviembre"; 
		case 11:
			res= "Diciembre"; 
		}
		return res; 
	}



}

