package uniandes.isis2304.alohandes.negocio;

import uniandes.isis2304.alohandes.negocio.Reserva.Tipo;

public interface VOReserva {
	/**
	 * @return El id del tipo de bebida
	 */
	public long getId_contrato();

	public long getId() ;
	
	public int getPersonas() ;
	
	public String getFecha_inicio() ;
	
	public String getFecha_fin();
	
	public String getFecha_limite();
	
	public String getFecha_realizacion() ;
	
	public Tipo getTipo();
	/**
	 * @return El nombre del tipo de bebida
	 */
	
	public long getId_cliente();
	
	




	/**
	 * @return Una cadena de caracteres con la información del tipo de bebida
	 */
	@Override
	public String toString(); 

	/**
	 * Define la igualdad dos Tipos de bebida
	 * @param tb - El tipo de bebida a comparar
	 * @return true si tienen el mismo identificador y el mismo nombre
	 */
	@Override
	public boolean equals (Object tb); 
}
