package uniandes.isis2304.alohandes.persistencia;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.alohandes.negocio.Reserva;
import uniandes.isis2304.alohandes.negocio.Reserva.Tipo;

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
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaAlohandes.SQL;
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaAlohandes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLReserva(PersistenciaAlohandes pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una BEBIDA a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la bebida
	 * @param nombre - El nombre de la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida de la bebida
	 * @param gradoAlcohol - El grado de alcohol de la bebida (Mayor que 0)
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarReserva (PersistenceManager pm, long id, long id_contrato, int personas, Date fecha_inicio, Date fecha_fin, Date fecha_limite, Date fecha_realizacion, Tipo tipo, long id_cliente) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReserva() + "(id, id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar RESERVAS de la base de datos de Alohandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la reserva
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarReservaPorId (PersistenceManager pm, long idReserva)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva () + " WHERE id = ?");
        q.setParameters(idReserva);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
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
	public List<Reserva> darReserva (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva ());
		q.setResultClass(Reserva.class);
		return (List<Reserva>) q.executeList();
	}
	
	
	public long dar20Reservas (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT CONTRATO_ID FROM ( SELECT CONTRATO_ID,COUNT(CONTRATO_ID) AS VECES FROM" + pp.darTablaReserva () + "GROUP_BY CONTRATO_ID ORDER BY VECES DESC ) WHERE ROWNUM<=20");
        return (long) q.executeUnique();            
    }
	
	
}
