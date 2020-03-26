package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reserva;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CONTRATO_HabHotel de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Kevin Becerra - Christian Forigua
 */
public class SQLContratoHabHotel {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
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
	public SQLContratoHabHotel(PersistenciaParranderos pp) {
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
	public long adicionarContratoHabHotel(PersistenceManager pm, long id, long id_contrato, int personas, String fecha_inicio, String fecha_fin, String fecha_limite, String fecha_realizacion, String tipo, long id_cliente) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReserva() + "(ID,ID_CONTRATO,PERSONAS,FECHA_INICIO,FECHA_FIN,FECHA_LIMITE,FECHA_REALIZACIOM,TIPO,ID_CLIENTE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar RESERVAS de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la reserva
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarContratoHabHotelPorId (PersistenceManager pm, long idReserva)
	{
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
	public Reserva darContratoHabHotelPorId (PersistenceManager pm, long idReserva) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva () + " WHERE id = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(idReserva);
		return (Reserva) q.executeUnique();
	}
	
}