package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLReserva {
	
	private final static String SQL = PersistenciaAlohandes.SQL;
	
	private PersistenciaAlohandes pp;

	public SQLReserva(PersistenciaAlohandes pp) {
		this.pp = pp;
	}
	
	public long adicionarReserva (PersistenceManager pm, long id, long id_contrato, long id_cliente,int personas,  String nombre, long idTipoBebida, int gradoAlcohol) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReserva() + "(id, nombre, idTipoBebida, gradoalcohol) values (?, ?, ?, ?)");
        q.setParameters(id, nombre, idTipoBebida, gradoAlcohol);
        return (long) q.executeUnique();            
	}
	
	
}
