package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ContratoHabHostal;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CONTRATOHABHOSTAL de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Kevin Becerra - Christian Forigua
 */
public class SQLContratoHabHostal {
	
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
	public SQLContratoHabHostal(PersistenciaParranderos pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una ContratoHabHostal a la base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @param id - Id de la ContratoHabHostal
	 * @param id_contrato - Id del contrato
	 * @param personas - Cantidad de persona para la ContratoHabHostal
	 * @param fecha_inicio - Fecha de inicio de la ContratoHabHostal
	 * @param fecha_fin - Fecha de fin de la ContratoHabHostal
	 * @param fecha_limite - Fecha de limite para entregar con descuento
	 * @param fecha_realizacion - Fecha de realizacion de la ContratoHabHostal
	 * @param tipo -  Tipo de alojamiento a ContratoHabHostalr
	 * @param id_cliente -  Id de cliente 
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarContratoHabHostal(PersistenceManager pm, long id_contrato, long id_hostal) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaContratoHabHostal() + "(ID_CONTRATO,ID_HOSTAL) VALUES (?, ?)");
        q.setParameters(id_contrato, id_hostal);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ContratoHabHostalS de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la ContratoHabHostal
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarContratoHabHostalPorId (PersistenceManager pm, long idContratoHabHostal)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContratoHabHostal () + " WHERE id = ?");
        q.setParameters(idContratoHabHostal);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA ContratoHabHostal de la 
	 * base de datos de Alohandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idContratoHabHostal - El identificador de la ContratoHabHostal
	 * @return El objeto ContratoHabHostal que tiene el identificador dado
	 */
	public ContratoHabHostal darContratoHabHostalPorId (PersistenceManager pm, long idContratoHabHostal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContratoHabHostal () + " WHERE id = ?");
		q.setResultClass(ContratoHabHostal.class);
		q.setParameters(idContratoHabHostal);
		return (ContratoHabHostal) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS ContratoHabHostales de la 
	 * base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ContratoHabHostal
	 */
	public List<ContratoHabHostal> darContratosHabHostal (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContratoHabHostal());
		q.setResultClass(ContratoHabHostal.class);
		return (List<ContratoHabHostal>) q.executeList();
	}
	
	
}