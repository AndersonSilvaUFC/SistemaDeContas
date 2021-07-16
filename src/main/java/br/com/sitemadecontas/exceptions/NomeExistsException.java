package br.com.sitemadecontas.exceptions;

public class NomeExistsException extends Exception{

	public NomeExistsException(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 1L;
}
