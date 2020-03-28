package uniandes.isis2304.parranderos.negocio;


/**
 * Clase para modelar el concepto RESERVA del negocio de AlohAndes
 *
 */
public class Reserva implements VOReserva {


	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	
	/**
	 * Id de la reserva
	 */
	private long id; 

	/**
	 * Id del contrato
	 */
	private long id_contrato; 

	/**
	 * Cantidad de personas para la reserva
	 */
	private int personas; 

	/**
	 * Fecha de inicio de la reserva
	 */
	private String fecha_inicio; 

	/**
	 * Fecha de fin de la reserva
	 */
	private String fecha_fin; 

	/**
	 * Fecha limite para entregar con descuento
	 */
	private String fecha_limite; 

	/**
	 * Fecha de realizacion de la reserva
	 */
	private String fecha_realizacion; 

	/**
	 * Tipo de alojamiento a elegir
	 */
	private String tipo;

	/**
	 * Id del cliente
	 */
	private long id_cliente; 

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor por defecto
	 */
	public Reserva() {
		super();
		this.id = 0;
		this.id_contrato = 0;
		this.personas = 0;
		this.fecha_inicio ="";
		this.fecha_fin = "";
		this.fecha_limite = "";
		this.fecha_realizacion = "";
		this.tipo = null;
		this.id_cliente = 0;

	}

	/**
	 * Constructor con valores
	 * @param id - Id de la reserva
	 * @param id_contrato - Id del contrato
	 * @param personas - Cantidad de personas para la reserva
	 * @param fecha_inicio - Fecha de inicio de la reserva
	 * @param fecha_fin - Fecha de fin de la reserva
	 * @param fecha_limite - Fecha límite para entregarla con descuento
	 * @param fecha_realizacion - Fecha de realizacion de la reserva
	 * @param tipo - Tipo de alojamiento para la reserva actual
	 * @param id_cliente - El id del cliente
	 */
	public Reserva(long id, long id_contrato, int personas, String fehca_inicio, String fecha_fin,
			String fecha_limite, String fecha_realizacion, String tipo, long id_cliente) {
		super();
		this.id = id;
		this.id_contrato = id_contrato;
		this.personas = personas;
		this.fecha_inicio = fehca_inicio;
		this.fecha_fin = fecha_fin;
		this.fecha_limite = fecha_limite;
		this.fecha_realizacion = fecha_realizacion;
		this.tipo = tipo;
		this.id_cliente = id_cliente;
	}

	/**
	 * @return  El id de la reserva
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return  El id de la reserva
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return El id del Contrato de la reserva
	 */
	public long getId_contrato() {
		return id_contrato;
	}

	/**
	 * @return El id del Contrato de la reserva
	 */
	public void setId_contrato(long id_contrato) {
		this.id_contrato = id_contrato;
	}

	/**
	 * 
	 * @return Cantidad de personas que ocupan la reserva
	 */
	public int getPersonas() {
		return personas;
	}

	/**
	 * 
	 * @return Cantidad de personas que ocupan la reserva
	 */
	public void setPersonas(int personas) {
		this.personas = personas;
	}

	/**
	 * 
	 * @return Fecha de inicio de la reserva
	 */
	public String getFecha_inicio() {
		return fecha_inicio;
	}

	/**
	 * 
	 * @return Fecha de inicio de la reserva
	 */
	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	/**
	 * 
	 * @return Fecha de fin de la reserva
	 */
	public String getFecha_fin() {
		return fecha_fin;
	}

	/**
	 * 
	 * @return Fecha de fin de la reserva
	 */
	public void setFecha_fin(String fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	/**
	 * 
	 * @return Fecha límite para entregarla con descuento
	 */
	public String getFecha_limite() {
		return fecha_limite;
	}

	/**
	 * 
	 * @return Fecha límite para entregarla con descuento
	 */
	public void setFecha_limite(String fecha_limite) {
		this.fecha_limite = fecha_limite;
	}

	/**
	 * 
	 * @return Fecha de realizacion de la reserva
	 */
	public String getFecha_realizacion() {
		return fecha_realizacion;
	}

	/**
	 * 
	 * @return Fecha de realizacion de la reserva
	 */
	public void setFecha_realizacion(String fecha_realizacion) {
		this.fecha_realizacion = fecha_realizacion;
	}


	/**
	 * 
	 * @return Tipo de alojamiento para la reserva actual
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * 
	 * @return Tipo de alojamiento para la reserva actual
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return El id del cliente
	 */
	public long getId_cliente() {
		return id_cliente;
	}


	/**
	 * @return El id del cliente
	 */
	public void setId_cliente(long id_cliente) {
		this.id_cliente = id_cliente;
	}


	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() {
		return "Reserva [id=" + id + ", id_contrato=" + id_contrato + ", personas=" + personas + ", fecha_inicio="
				+ fecha_inicio + ", fecha_fin=" + fecha_fin + ", fecha_limite=" + fecha_limite + ", fecha_realizacion="
				+ fecha_realizacion + ", tipo=" + tipo + ", id_cliente=" + id_cliente + "]";
	}





}
