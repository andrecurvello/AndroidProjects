package com.example.conversor;

/**
 * Esta clase se dedica a realizar la conversión de importes
 * @author Oscar
 *
 */
public class Conversor {
	
	private float importe;
	private float equivalencia;
	private String error;

	public double getImporte() {
		return importe;
	}
	public void setImporte(float importe) {
		this.importe = importe;
	}
	public Number getEquivalencia() {
		return equivalencia;
	}
	public void setEquivalencia(float equivalencia) {
		this.equivalencia = equivalencia;
	}
	
	public float getImporteConvertido() {

		float res;
		try {
			res = importe * equivalencia;
		}
		catch(Exception e) {
			res = -1;
		    error = "Se ha producido un error al realizar la equivalencia: " + e.getMessage();
		}
		
		return res;
		
	}
	
	public String getError() {
		return error;
	}
	
	

}
