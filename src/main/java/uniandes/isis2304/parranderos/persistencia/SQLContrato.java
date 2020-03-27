package uniandes.isis2304.parranderos.persistencia;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Contrato;
import uniandes.isis2304.parranderos.negocio.Reserva;



/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CONTRATO de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Kevin Becerra - Christian Forigua
 */
public class SQLContrato{
	
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
	public SQLContrato(PersistenciaParranderos pp) {
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
	public long adicionarContrato (PersistenceManager pm, long id, int capacidad, int costo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaContrato() + "(id, capacidad, costo) values (?, ?, ?)");
        q.setParameters(id, capacidad, costo);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ContratoS de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la Contrato
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarContratoPorId (PersistenceManager pm, long idContrato) throws Exception
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");  
		LocalDateTime actual = LocalDateTime.now();
		String fecha_actual=dtf.format(actual);
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva()+ " WHERE ID_CONTRATO =? AND "
        																		+ "TO_DATE(FECHA_FIN,'DD/MM/YYYY HH24;MI:SS')<TO_DATE("+fecha_actual+",'DD/MM/YYYY HH24;MI:SS')");                                             
        q.setResultClass(Reserva.class);
        q.setParameters(idContrato);
        List<Reserva> reservas=q.executeList();
        
        if(reservas.size()>0) {
        	throw new Exception("La oferta que quiere retirar tiene reservas vigente");
        }
        else {
        	
        }
        
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idContrato - El identificador de la Contrato
	 * @return El objeto Contrato que tiene el identificador dado
	 */
	public Contrato darContratoPorId (PersistenceManager pm, long idContrato) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato () + " WHERE id = ?");
		q.setResultClass(Contrato.class);
		q.setParameters(idContrato);
		return (Contrato) q.executeUnique();
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS ContratoS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Contrato
	 */
	public List<Contrato> darContratos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato ());
		q.setResultClass(Contrato.class);
		return (List<Contrato>) q.executeList();
	}
	
	
}
