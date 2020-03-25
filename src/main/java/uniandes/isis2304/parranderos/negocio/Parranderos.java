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

package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Reserva.Tipo;
import uniandes.isis2304.parranderos.persistencia.PersistenciaParranderos;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Germán Bravo
 */
public class Parranderos 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Parranderos.class.getName());

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaParranderos pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Parranderos ()
	{
		pp = PersistenciaParranderos.getInstance ();
	}

	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Parranderos (JsonObject tableConfig)
	{
		pp = PersistenciaParranderos.getInstance (tableConfig);
	}

	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}

	/* ****************************************************************
	 * 			Métodos para manejar los TIPOS DE BEBIDA
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un tipo de bebida 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
	 */
	public TipoBebida adicionarTipoBebida (String nombre)
	{
		log.info ("Adicionando Tipo de bebida: " + nombre);
		TipoBebida tipoBebida = pp.adicionarTipoBebida (nombre);		
		log.info ("Adicionando Tipo de bebida: " + tipoBebida);
		return tipoBebida;
	}

	/**
	 * Elimina un tipo de bebida por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarTipoBebidaPorNombre (String nombre)
	{
		log.info ("Eliminando Tipo de bebida por nombre: " + nombre);
		long resp = pp.eliminarTipoBebidaPorNombre (nombre);		
		log.info ("Eliminando Tipo de bebida por nombre: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Elimina un tipo de bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El id del tipo de bebida a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarTipoBebidaPorId (long idTipoBebida)
	{
		log.info ("Eliminando Tipo de bebida por id: " + idTipoBebida);
		long resp = pp.eliminarTipoBebidaPorId (idTipoBebida);		
		log.info ("Eliminando Tipo de bebida por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todos los tipos de bebida en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos TipoBebida con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<TipoBebida> darTiposBebida ()
	{
		log.info ("Consultando Tipos de bebida");
		List<TipoBebida> tiposBebida = pp.darTiposBebida ();	
		log.info ("Consultando Tipos de bebida: " + tiposBebida.size() + " existentes");
		return tiposBebida;
	}

	/**
	 * Encuentra todos los tipos de bebida en Parranderos y los devuelve como una lista de VOTipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOTipoBebida con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
//	public List<VOTipoBebida> darVOTiposBebida ()
//	{
//		log.info ("Generando los VO de Tipos de bebida");        
//		List<VOTipoBebida> voTipos = new LinkedList<VOTipoBebida> ();
//		for (TipoBebida tb : pp.darTiposBebida ())
//		{
//			voTipos.add (tb);
//		}
//		log.info ("Generando los VO de Tipos de bebida: " + voTipos.size() + " existentes");
//		return voTipos;
//	}

	/**
	 * Encuentra el tipos de bebida en Parranderos con el nombre solicitado
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la bebida
	 * @return Un objeto TipoBebida con el tipos de bebida de ese nombre que conoce la aplicación, 
	 * lleno con su información básica
	 */
	public TipoBebida darTipoBebidaPorNombre (String nombre)
	{
		log.info ("Buscando Tipo de bebida por nombre: " + nombre);
		List<TipoBebida> tb = pp.darTipoBebidaPorNombre (nombre);
		return !tb.isEmpty () ? tb.get (0) : null;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las BEBIDAS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una bebida 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida de la bebida - Debe existir un TIPOBEBIDA con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la bebida (Mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
//	public Bebida adicionarBebida (String nombre, long idTipoBebida, int gradoAlcohol)
//	{
//		log.info ("Adicionando bebida " + nombre);
//		Bebida bebida = pp.adicionarBebida (nombre, idTipoBebida, gradoAlcohol);
//		log.info ("Adicionando bebida: " + bebida);
//		return bebida;
//	}

	/**
	 * Elimina una bebida por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la bebida a eliminar
	 * @return El número de tuplas eliminadas
	 */
//	public long eliminarBebidaPorNombre (String nombre)
//	{
//		log.info ("Eliminando bebida por nombre: " + nombre);
//		long resp = pp.eliminarBebidaPorNombre (nombre);
//		log.info ("Eliminando bebida por nombre: " + resp + " tuplas eliminadas");
//		return resp;
//	}

	/**
	 * Elimina una bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
//	public long eliminarBebidaPorId (long idBebida)
//	{
//		log.info ("Eliminando bebida por id: " + idBebida);
//		long resp = pp.eliminarBebidaPorId (idBebida);
//		log.info ("Eliminando bebida por id: " + resp + " tuplas eliminadas");
//		return resp;
//	}

	/**
	 * Encuentra todas las bebida en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebida con todos las bebidas que conoce la aplicación, llenos con su información básica
	 */
//	public List<Bebida> darBebidas ()
//	{
//		log.info ("Consultando Bebidas");
//		List<Bebida> bebidas = pp.darBebidas ();	
//		log.info ("Consultando Bebidas: " + bebidas.size() + " bebidas existentes");
//		return bebidas;
//	}

	/**
	 * Encuentra todos los tipos de bebida en Parranderos y los devuelve como una lista de VOTipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebida con todos las bebidas que conoce la aplicación, llenos con su información básica
	 */
//	public List<VOBebida> darVOBebidas ()
//	{
//		log.info ("Generando los VO de las bebidas");       
//		List<VOBebida> voBebidas = new LinkedList<VOBebida> ();
//		for (Bebida beb : pp.darBebidas ())
//		{
//			voBebidas.add (beb);
//		}
//		log.info ("Generando los VO de las bebidas: " + voBebidas.size() + " existentes");
//		return voBebidas;
//	}

	/**
	 * Elimina las bebidas que no son servidas en ningún bar (No son referenciadas en ninguna tupla de SIRVEN)
	 * Adiciona entradas al log de la aplicación
	 * @return El número de bebidas eliminadas
	 */
//	public long eliminarBebidasNoServidas ()
//	{
//		log.info ("Borrando bebidas no servidas");
//		long resp = pp.eliminarBebidasNoServidas ();
//		log.info ("Borrando bebidas no servidas: " + resp + " bebidas eliminadas");
//		return resp;
//	}

	/* ****************************************************************
	 * 			Métodos para manejar los BEBEDORES
	 *****************************************************************/


	/**
	 * Elimina un bebedor por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
//	public long eliminarBebedorPorNombre (String nombre)
//	{
//		log.info ("Eliminando bebedor por nombre: " + nombre);
//		long resp = pp.eliminarBebedorPorNombre (nombre);
//		log.info ("Eliminando bebedor por nombre: " + resp + " tuplas eliminadas");
//		return resp;
//	}

	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
//	public long eliminarBebedorPorId (long idBebedor)
//	{
//		log.info ("Eliminando bebedor por id: " + idBebedor);
//		long resp = pp.eliminarBebedorPorId (idBebedor);
//		log.info ("Eliminando bebedor por Id: " + resp + " tuplas eliminadas");
//		return resp;
//	}


	/**
	 * Encuentra todos los bebedores que conoce la aplicación y el número visitas realizadas por cada uno
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de parejas [Bebedor, numVisitas]
	 */
//	public List<Object []> darBebedoresYNumVisitasRealizadas ()
//	{
//		log.info ("Listando Bebedores y cuántas visitas ha realizado");
//		List<Object []> tuplas = pp.darBebedoresYNumVisitasRealizadas ();
//		log.info ("Listando Bebedores y cuántas visitas ha realizado: Listo!");
//		return tuplas;
//	}

	/**
	 * Dado el nombre de una ciudad, encuentra el número de bebedores de esa ciudad que han realizado por lo menos una visita a un bar
	 * Adiciona entradas al log de la aplicación
	 * @param ciudad - La ciudad de interés
	 * @return Un número que representa el número de bebedores de esa ciudad que hab realizado por lo menos una visita a un bar
//	 */
//	public long darCantidadBebedoresCiudadVisitanBares (String ciudad)
//	{
//		log.info ("Calculando cuántos Bebedores de una ciudad visitan bares");
//		long resp = pp.darCantidadBebedoresCiudadVisitanBares (ciudad);
//		log.info ("Calculando cuántos Bebedores de una ciudad visitan bares de " + ciudad +": " + resp);
//		return resp;
//	}

	/**
	 * Cambia la ciudad de un bebedor dado su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor que va a cambiar de ciudad
	 * @param ciudad - La nueva ciudad del bebedor
	 * @return El número de tuplas modificadas: 1 o 0. 0 significa que un bebedor con ese identificador no existe
	 */
//	public long cambiarCiudadBebedor (long idBebedor, String ciudad)
//	{
//		log.info ("Cambiando ciudad de bebedor: " + idBebedor);
//		long cambios = pp.cambiarCiudadBebedor (idBebedor, ciudad);
//		return cambios;
//	}

	/**
	 * Elimina un bebedor y las visitas a bares que haya realizado v1: 
	 * En caso que el bebedor esté referenciado por otra relación, NO SE BORRA NI EL BEBEDOR, NI SUS VISITAS
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El bebedor que se quiere eliminar
	 * @return Una pareja de números [número de bebedores eliminados, número de visitas eliminadas]
	 */
//	public long [] eliminarBebedorYVisitas_v1 (long idBebedor)
//	{
//		log.info ("Eliminando bebedor con sus visitas v1: " + idBebedor);
//		long [] resp = pp.eliminarBebedorYVisitas_v1 (idBebedor);
//		log.info ("Eliminando bebedor con sus visitas v1: " + resp [0] + " bebedor y " + resp [1] + " visitas");
//		return resp;
//	}


	/* ****************************************************************
	 * 			Métodos para manejar la relación SIRVEN
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente el hecho que una bebida es servida por un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar
	 * @param idBebida - El identificador de la bebida
	 * @param horario - El horario en el que se sirve la bebida (DIURNO, NOCTURNO, TODOS)
	 * @return Un objeto Sirven con los valores dados
	 */
//	public Sirven adicionarSirven (long idBar, long idBebida, String horario)
//	{
//		log.info ("Adicionando sirven [" + idBar + ", " + idBebida + "]");
//		Sirven resp = pp.adicionarSirven (idBar, idBebida, horario);
//		log.info ("Adicionando sirven: " + resp + " tuplas insertadas");
//		return resp;
//	}

	/**
	 * Elimina de manera persistente el hecho que una bebida es servida por un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas
	 */
//	public long eliminarSirven (long idBar, long idBebida)
//	{
//		log.info ("Eliminando sirven");
//		long resp = pp.eliminarSirven (idBar, idBebida);
//		log.info ("Eliminando sirven: " + resp + "tuplas eliminadas");
//		return resp;
//	}

	/**
	 * Encuentra todos los SIRVEN en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos SIRVEN con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
//	public List<Sirven> darSirven ()
//	{
//		log.info ("Listando Sirven");
//		List<Sirven> sirven = pp.darSirven ();	
//		log.info ("Listando Sirven: " + sirven.size() + " sirven existentes");
//		return sirven;
//	}

	/**
	 * Encuentra todos los sirven en Parranderos y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos SIRVEN con todos los SIRVEN que conoce la aplicación, llenos con su información básica
	 */
//	public List<VOSirven> darVOSirven ()
//	{
//		log.info ("Generando los VO de Sirven");
//		List<VOSirven> voGustan = new LinkedList<VOSirven> ();
//		for (VOSirven sirven: pp.darSirven ())
//		{
//			voGustan.add (sirven);
//		}
//		log.info ("Generando los VO de Sirven: " + voGustan.size () + " Sirven existentes");
//		return voGustan;
//	}


	/* ****************************************************************
	 * 			Métodos para manejar las RESERVAS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una reserva 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida de la bebida - Debe existir un TIPOBEBIDA con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la bebida (Mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Reserva adicionarReserva (long id_contrato, int personas, String fecha_inicio, String fecha_fin, String fecha_limite, String fecha_realizacion, Tipo tipo, long id_cliente)
	{
		log.info ("Adicionando reserva");
		Reserva reserva = pp.adicionarReserva(id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
		log.info ("Adicionando bebida: " + reserva);
		return reserva;
	}


	/**
	 * Elimina una bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarReservaPorId (long idReserva)
	{
		log.info ("Eliminando Reserva por id: " + idReserva);
		long resp = pp.eliminarReservaPorId (idReserva);
		log.info ("Eliminando Reserva por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	public List<Reserva> darReserva()
	{
		log.info ("Consultando Reservas");
		List<Reserva> reservas = pp.darReservas();	
		log.info ("Consultando Bebidas: " + reservas.size() + " reservas existentes");
		return reservas;
	}


	public List<VOReserva> darVOReserva ()
	{
		log.info ("Generando los VO de Reserva");        
		List<VOReserva> voReservas = new LinkedList<VOReserva> ();
		for (Reserva tb : pp.darReservas())
		{
			voReservas.add (tb);
		}
		log.info ("Generando los VO de Reserva " + voReservas.size() + " existentes");
		return voReservas;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los CONTRATOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un contrato
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Contrato adicionarContrato (int capacidad , int costo)
	{
		log.info ("Adicionando contrato");
		Contrato contrato = pp.adicionarContrato(capacidad, costo);
		log.info ("Adicionando bebida: " + contrato);
		return contrato;
	}


	/**
	 * Elimina una bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarContratoPorId (long idContrato)
	{
		log.info ("Eliminando bebida por id: " + idContrato);
		long resp = pp.eliminarReservaPorId (idContrato);
		log.info ("Eliminando contrato por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	public List<Contrato> darContrato()
	{
		log.info ("Consultando Contratos");
		List<Contrato> contratos = pp.darContratos();	
		log.info ("Consultando Contratos: " + contratos.size() + " reservas existentes");
		return contratos;
	}


	public List<VOContrato> darVOContrato ()
	{
		log.info ("Generando los VO de Contrato");        
		List<VOContrato> voContratos = new LinkedList<VOContrato> ();
		for (Contrato tb : pp.darContratos())
		{
			voContratos.add (tb);
		}
		log.info ("Generando los VO de Reserva " + voContratos.size() + " existentes");
		return voContratos;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los OPERADORES
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un operador
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Operador adicionarOperador (String nombre)
	{
		log.info ("Adicionando contrato");
		Operador operador = pp.adicionarOperador(nombre);
		log.info ("Adicionando operador: " + operador);
		return operador;
	}


	/**
	 * Elimina un operador por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarOperadorPorId (long idOperador)
	{
		log.info ("Eliminando bebida por id: " + idOperador);
		long resp = pp.eliminarReservaPorId (idOperador);
		log.info ("Eliminando operador por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	public List<Operador> darOperador()
	{
		log.info ("Consultando Operadores");
		List<Operador> operadores = pp.darOperadores();	
		log.info ("Consultando Operadores: " + operadores.size() + " reservas existentes");
		return operadores;
	}


	public List<VOOperador> darVOOperador()
	{
		log.info ("Generando los VO de Operador");        
		List<VOOperador> voOperadores = new LinkedList<VOOperador> ();
		for (Operador tb : pp.darOperadores())
		{
			voOperadores.add (tb);
		}
		log.info ("Generando los VO de Operador " + voOperadores.size() + " existentes");
		return voOperadores;
	}




	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
		log.info ("Limpiando la BD de Parranderos");
		long [] borrrados = pp.limpiarParranderos();	
		log.info ("Limpiando la BD de Parranderos: Listo!");
		return borrrados;
	}
}
