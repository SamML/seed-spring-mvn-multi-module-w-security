package com.smdev.smsj.security.provider;

/**
 * @author sm, in 2018
 *
 * |> UserAlreadyExistsException ~~ [com.smdev.smsj.security.provider]
 * 
 */
public class AlreadyExistsException extends Exception {

	
	private static final long serialVersionUID = 7824766188271112047L;
	public AlreadyExistsException () {
		this(AlreadyExistsException.class.toString());
	}
	public AlreadyExistsException (String msg) {
		super(msg);
	}
	public AlreadyExistsException (Object obj) {
		super(obj.toString());
	}
}
