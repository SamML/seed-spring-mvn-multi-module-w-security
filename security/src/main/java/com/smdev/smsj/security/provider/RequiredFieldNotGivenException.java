package com.smdev.smsj.security.provider;

/**
 * @author sm, in 2018
 *
 * |> RequiredFieldNotGivenException ~~ [com.smdev.smsj.security.provider]
 * 
 */
public class RequiredFieldNotGivenException extends Exception {

	private static final long serialVersionUID = -1992176494160086437L;
	RequiredFieldNotGivenException() {
		this("required field not given");
	}
	RequiredFieldNotGivenException(String field) {
		super(field);
	}
}
