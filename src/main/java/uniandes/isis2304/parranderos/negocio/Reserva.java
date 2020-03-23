package uniandes.isis2304.parranderos.negocio;

import java.util.Date;

public class Reserva {

	public enum Tipo {
	HOTEL,
	HOSTAL,
	VIVENDA_UNIVERSITARIA,
	APARTAMENTO,
	VIVENDA_FAMILIAR,
	CLIENTE_ESPORADICO
	}
	private long id; 
	
	private long id_contrato; 
	
	private long id_cliente; 
	
	private int personas; 
	
	private Date fecha_inicio; 
	
	private Date fecha_fin; 
	
	private Date fecha_limite; 
	
	private Date fecha_realizacion; 
	
	private Tipo tipo;

	
	public Reserva() {
		super();
		this.id = 0;
		this.id_contrato = 0;
		this.id_cliente = 0;
		this.personas = 0;
		this.fecha_inicio =new Date() ;
		this.fecha_fin = new Date();
		this.fecha_limite = new Date();
		this.fecha_realizacion = new Date();
		this.tipo = null;
	}


	public Reserva(long id, long id_contrato, long id_cliente, int personas, Date fehca_inicio, Date fecha_fin,
			Date fecha_limite, Date fehca_realizacion, Tipo tipo) {
		super();
		this.id = id;
		this.id_contrato = id_contrato;
		this.id_cliente = id_cliente;
		this.personas = personas;
		this.fecha_inicio = fehca_inicio;
		this.fecha_fin = fecha_fin;
		this.fecha_limite = fecha_limite;
		this.fecha_realizacion = fehca_realizacion;
		this.tipo = tipo;
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


	public long getId_cliente() {
		return id_cliente;
	}


	public void setId_cliente(long id_cliente) {
		this.id_cliente = id_cliente;
	}


	public int getPersonas() {
		return personas;
	}


	public void setPersonas(int personas) {
		this.personas = personas;
	}


	public Date getFecha_inicio() {
		return fecha_inicio;
	}


	public void setFecha_inicio(Date fehca_inicio) {
		this.fecha_inicio = fehca_inicio;
	}


	public Date getFecha_fin() {
		return fecha_fin;
	}


	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}


	public Date getFecha_limite() {
		return fecha_limite;
	}


	public void setFecha_limite(Date fecha_limite) {
		this.fecha_limite = fecha_limite;
	}


	public Date getFecha_realizacion() {
		return fecha_realizacion;
	}


	public void setFecha_realizacion(Date fehca_realizacion) {
		this.fecha_realizacion = fehca_realizacion;
	}


	public Tipo getTipo() {
		return tipo;
	}


	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	} 
	
	
	
	
	
}
