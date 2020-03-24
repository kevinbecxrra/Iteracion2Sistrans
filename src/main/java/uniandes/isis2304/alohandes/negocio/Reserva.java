package uniandes.isis2304.alohandes.negocio;

import java.util.Date;

/**
 * Clase para modelar el concepto RESERVA del negocio de AlohAndes
 *
 * @author Germán Bravo
 */
public class Reserva {

	public enum Tipo {
	HOTEL,
	HOSTAL,
	VIVENDA_UNIVERSITARIA,
	APARTAMENTO,
	VIVENDA_FAMILIAR,
	CLIENTE_ESPORADICO
	}
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	
	private long id; 
	
	private long id_contrato; 
	
	private int personas; 
	
	private String fecha_inicio; 
	
	private String fecha_fin; 
	
	private String fecha_limite; 
	
	private String fecha_realizacion; 
	
	private Tipo tipo;
	
	private long id_cliente; 
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	
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


	public Reserva(long id, long id_contrato, int personas, String fehca_inicio, String fecha_fin,
			String fecha_limite, String fehca_realizacion, Tipo tipo, long id_cliente) {
		super();
		this.id = id;
		this.id_contrato = id_contrato;
		this.personas = personas;
		this.fecha_inicio = fehca_inicio;
		this.fecha_fin = fecha_fin;
		this.fecha_limite = fecha_limite;
		this.fecha_realizacion = fehca_realizacion;
		this.tipo = tipo;
		this.id_cliente = id_cliente;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getId_contrato() {
		return id_contrato;
	}


	public void setId_contrato(long id_contrato) {
		this.id_contrato = id_contrato;
	}


	public int getPersonas() {
		return personas;
	}


	public void setPersonas(int personas) {
		this.personas = personas;
	}


	public String getFecha_inicio() {
		return fecha_inicio;
	}


	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}


	public String getFecha_fin() {
		return fecha_fin;
	}


	public void setFecha_fin(String fecha_fin) {
		this.fecha_fin = fecha_fin;
	}


	public String getFecha_limite() {
		return fecha_limite;
	}


	public void setFecha_limite(String fecha_limite) {
		this.fecha_limite = fecha_limite;
	}


	public String getFecha_realizacion() {
		return fecha_realizacion;
	}


	public void setFecha_realizacion(String fecha_realizacion) {
		this.fecha_realizacion = fecha_realizacion;
	}


	public Tipo getTipo() {
		return tipo;
	}


	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}


	public long getId_cliente() {
		return id_cliente;
	}


	public void setId_cliente(long id_cliente) {
		this.id_cliente = id_cliente;
	}


	@Override
	public String toString() {
		return "Reserva [id=" + id + ", id_contrato=" + id_contrato + ", personas=" + personas + ", fecha_inicio="
				+ fecha_inicio + ", fecha_fin=" + fecha_fin + ", fecha_limite=" + fecha_limite + ", fecha_realizacion="
				+ fecha_realizacion + ", tipo=" + tipo + ", id_cliente=" + id_cliente + "]";
	}



	
	
}
