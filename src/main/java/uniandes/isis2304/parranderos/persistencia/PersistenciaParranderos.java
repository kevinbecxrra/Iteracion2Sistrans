/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.persistencia;


import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uniandes.isis2304.parranderos.negocio.Contrato;
import uniandes.isis2304.parranderos.negocio.ContratoHabUniversitaria;
import uniandes.isis2304.parranderos.negocio.Operador;
import uniandes.isis2304.parranderos.negocio.Reserva;

/**
 * Clase para el manejador de persistencia del proyecto Alohandes
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLGustan, SQLSirven, SQLTipoBebida y SQLVisitan, que son 
 * las que realizan el acceso a la base de datos
 * 
 */
public class PersistenciaParranderos 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaParranderos.class.getName());

	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaParranderos instance;

	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;

	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;

	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;

	/**
	 * Atributo para el acceso a la tabla CONTRATO de la base de datos
	 */
	private SQLContrato sqlContrato;
	
	/**
	 * Atributo para el acceso a la tabla CONTRATO_APARTAMENTO de la base de datos
	 */
	private SQLContrato_Apartamento sqlContratoApartamento;
	
	/**
	 * Atributo para el acceso a la tabla CONTRATO_CLIENTE_ESPORADICO de la base de datos
	 */
	private SQLContrato_Cliente_Esporadico sqlContratoClienteEsporadico;
	
	/**
	 * Atributo para el acceso a la tabla CONTRATO_HAB_VIVIENDA de la base de datos
	 */
	private SQLContrato_Hab_Vivienda sqlContratoHabVivienda;
	
	/**
	 * Atributo para el acceso a la tabla CONTRATOHABHOSTAL de la base de datos
	 */
	private SQLContratoHabHostal sqlContratoHabHostal;
	
	/**
	 * Atributo para el acceso a la tabla CONTRATOHABHOTEL de la base de datos
	 */
	private SQLContratoHabHotel sqlContratoHabHotel;
	
	/**
	 * Atributo para el acceso a la tabla CONTRATOHABUNIVERSITARIA de la base de datos
	 */
	private SQLContratoHabUniversitaria sqlContratoHabUniversitaria;

	/**
	 * Atributo para el acceso a la tabla OPERADOR de la base de datos
	 */
	private SQLOperador sqlOperador;
	
	/**
	 * Atributo para el acceso a la tabla RESERVAN de la base de datos
	 */
	private SQLReserva sqlReserva; 


	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaParranderos ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();

		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("Parranderos_sequence");
		tablas.add("CONTRATO");
		tablas.add("CONTRATO_APARTAMENTO");
		tablas.add("CONTRATO_CLIENTE_ESPORADICO");
		tablas.add("CONTRATO_HAB_VIVIENDA");
		tablas.add("CONTRATOHABHOSTAL");
		tablas.add("CONTRATOHABHOTEL");
		tablas.add("CONTRATOHABUNIVERSITARIA");
		tablas.add("OPERADOR");
		tablas.add("RESERVA");

	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaParranderos (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		System.out.println(unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaParranderos getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaParranderos ();
		}
		return instance;
	}

	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaParranderos getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaParranderos (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlUtil = new SQLUtil(this);
		sqlContrato = new SQLContrato(this);
		sqlContratoApartamento = new SQLContrato_Apartamento(this);
		sqlContratoClienteEsporadico = new SQLContrato_Cliente_Esporadico(this);
		sqlContratoHabVivienda = new SQLContrato_Hab_Vivienda(this);
		sqlContratoHabHostal = new SQLContratoHabHostal(this);
		sqlContratoHabHotel = new SQLContratoHabHotel(this);
		sqlContratoHabUniversitaria = new SQLContratoHabUniversitaria(this);
		sqlOperador = new SQLOperador(this);
		sqlReserva = new SQLReserva(this);
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de parranderos
	 */
	public String darSeqParranderos ()
	{
		return tablas.get (0);
	}


	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebedor de parranderos
	 */
	public String darTablaContrato()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaContrato_Apartamento ()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sirven de parranderos
	 */
	public String darTablaContrato_Cliente_Esporadico ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaContrato_Hab_Vivienda ()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de Alohandes
	 */
	public String darTablaContratoHabHostal() 
	{
		return tablas.get(7);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de Alohandes
	 */
	public String darTablaContratoHabHotel() 
	{
		return tablas.get(8);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de Alohandes
	 */
	public String darTablaContratoHabUniversitaria() 
	{
		return tablas.get(9);
	}


	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de Alohandes
	 */
	public String darTablaOperador() {
		return tablas.get(12);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de Alohandes
	 */
	public String darTablaReserva() {
		return tablas.get(14);
	}



	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar la relación RESERVA
	 *****************************************************************/

	public Reserva adicionarReserva(long id_contrato, int personas, String fecha_inicio, String fecha_fin, String fecha_limite, String fecha_realizacion, String tipo, long id_cliente) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idReserva = nextval ();
			long tuplasInsertadas = sqlReserva.adicionarReserva(pm, idReserva,id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
			tx.commit();

			log.trace ("Inserción reserva: " + idReserva+ ": " + tuplasInsertadas + " tuplas insertadas");
			return new Reserva (idReserva,id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla TipoBebida, dado el identificador del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarReservaPorId (long idReserva) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlReserva.eliminarReservaPorId(pm, idReserva);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Reserva> darReservas()
	{
		return sqlReserva.darReservas(pmf.getPersistenceManager());
	}


	/* ****************************************************************
	 * 			Métodos para manejar la relación CONTRATO
	 *****************************************************************/

	public Contrato adicionarContrato(int capacidad, int costo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idContrato = nextval ();
			long tuplasInsertadas = sqlContrato.adicionarContrato(pm, idContrato ,capacidad, costo);
			tx.commit();

			log.trace ("Inserción contrato: " + idContrato+ ": " + tuplasInsertadas + " tuplas insertadas");
			return new Contrato (idContrato, capacidad, costo);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla TipoBebida, dado el identificador del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarContratoPorId (long idContrato) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContrato.eliminarContratoPorId(pm, idContrato);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Contrato> darContratos ()
	{
		return sqlContrato.darContratos(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar la relación OPERADOR
	 *****************************************************************/

	public Operador adicionarOperador(String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idOperador = nextval ();
			long tuplasInsertadas = sqlOperador.adicionarOperador(pm, idOperador, nombre);
			tx.commit();

			log.trace ("Inserción contrato: " + idOperador+ ": " + tuplasInsertadas + " tuplas insertadas");
			return new Operador (idOperador, nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla TipoBebida, dado el identificador del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOperadorPorId (long idOperador) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContrato.eliminarContratoPorId(pm, idOperador);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Operador> darOperadores ()
	{
		return sqlOperador.darOperadores(pmf.getPersistenceManager());
	}



	/* ****************************************************************
	 * 			Métodos para manejar la relación CONTRATOHABUNIVERSITARIA
	 *****************************************************************/

	public ContratoHabUniversitaria adicionarContratoHabUniversitaria(String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idContratoHabUniversitaria = nextval ();
			long tuplasInsertadas = sqlContratoHabUniversitaria.adicionarContratoHabUniversitaria(pm, idContratoHabUniversitaria, nombre);
			tx.commit();

			log.trace ("Inserción contrato: " + idContratoHabUniversitaria+ ": " + tuplasInsertadas + " tuplas insertadas");
			return new ContratoHabUniversitaria (idContratoHabUniversitaria, nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla TipoBebida, dado el identificador del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarContratoHabUniversitariaPorId (long idContratoHabUniversitaria) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContratoHabUniversitaria.eliminarContratoHabUniversitariaPorId(pm, idContratoHabUniversitaria);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<ContratoHabUniversitaria> darContratoHabUniversitariaes ()
	{
		return sqlContratoHabUniversitaria.darContratosHabUniversitaria(pmf.getPersistenceManager());
	}

	
//
//	/**
//	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
//	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
//	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
//	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
//	 */
//	//public long [] limpiarParranderos ()
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//		Transaction tx=pm.currentTransaction();
//		try
//		{
//			tx.begin();
//		//	long [] resp = sqlUtil.limpiarParranderos (pm);
//			tx.commit ();
//			log.info ("Borrada la base de datos");
//			//return resp;
//		}
//		catch (Exception e)
//		{
//			//        	e.printStackTrace();
//			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//			return new long[] {-1, -1, -1, -1, -1, -1, -1};
//		}
//		finally
//		{
//			if (tx.isActive())
//			{
//				tx.rollback();
//			}
//			pm.close();
//		}
//
//	}



}
