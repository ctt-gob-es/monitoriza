/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2018 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.monitoriza.utilidades.UtilsIdentifierGenerators.java.</p>
 * <b>Description:</b><p>Utility class for generate uniques identifiers.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * <b>Date:</b><p>28/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 28/03/2019.
 */
package es.gob.monitoriza.utilidades;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Calendar;

import es.gob.monitoriza.constant.NumberConstants;

/** 
 * <p>Class that defines the methods in charge of send alarms or store it according to the defined block time.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 28/03/2019.
 */
public final class UtilsIdentifierGenerators {

	/**
	 * Constant attribute that represents the number 0 how the base number.
	 */
	private static int baseNumber = 0;

	/**
	 * Attribute that represents the counter of numbered sequences generated at this loop.
	 */
	private static int counter = (int) (Math.random() * NumberConstants.NUM5000);

	/**
	 * Attribute that represents the object to format decimal numbers.
	 */
	private static DecimalFormat formatter = new DecimalFormat("0000");

	/**
	 * Attribute that represents the current time millis representation in a string
	 * captured for last time.
	 */
	private static String currentTimeMillis = String.valueOf(Calendar.getInstance().getTimeInMillis());

	/**
	 * Synchronized method that generates a unique identifier builded in characters
	 * and a ending number.
	 * @return String that represents the generated unique identifier.
	 */
	public static synchronized String generateCharactersUniqueId() {

		String currentTimeMillisLocal = String.valueOf(Calendar.getInstance().getTimeInMillis());
		StringBuffer buffer = new StringBuffer(currentTimeMillisLocal.length());

		// Calculamos los caracteres que formarán el identificador, a partir del
		// caracter base anterior y el valor en milisegundos del tiempo de
		// máquina actual.
		for (int index = 0; index < currentTimeMillisLocal.length(); index++) {
			buffer.append((char) ((int) currentTimeMillisLocal.charAt(index) + NumberConstants.NUM17));
		}

		// Nos aseguramos que si se solicitan 2 (o más) consecutivamente, no se
		// generen los mismos valores (si se realizaran las operaciones en el
		// mismo milisegundo).
		buffer.append(baseNumber);

		baseNumber = (baseNumber + 1) % NumberConstants.NUM1000;

		return buffer.toString();

	}

	/**
	 * Attribute that represents a virtual number (two ciphers) that represents
	 * the IP of the machine for generate uniques numbers.
	 */
	private static String twoCipherIpRepresentation = null;

	/**
	 * Gets an IP representation in two ciphers for this node.
	 * @return {@link String} with a IP representation in two ciphers.
	 */
	private static String getTwoCipherIpRepresentation() {

		// Si el atributo que determina la representación de la IP en dos cifras
		// aún no ha sido calculado, lo hacemos ahora.
		if (UtilsStringChar.isNullOrEmpty(twoCipherIpRepresentation)) {

			String ipNode = null;
			int addingResult = -1;
			try {
				// Obtenemos la IP del nodo.
				ipNode = InetAddress.getLocalHost().getHostAddress().toString();
				String[ ] parts = ipNode.split("\\.");
				// Calculamos el número resumido.
				int part0 = Integer.parseInt(parts[0]) * NumberConstants.NUM5;
				int part1 = Integer.parseInt(parts[1]) * NumberConstants.NUM3;
				int part2 = Integer.parseInt(parts[2]) * 2;
				int part3 = Integer.parseInt(parts[NumberConstants.NUM3]);
				addingResult = part0 + part1 + part2 + part3;
				while (addingResult >= NumberConstants.NUM100) {
					int rest = addingResult / NumberConstants.NUM100;
					addingResult = addingResult % NumberConstants.NUM100;
					addingResult = addingResult + rest;
				}
			} catch (UnknownHostException e) {
				// Si se produce un error al obtener la IP del nodo, se genera
				// un número aleatorio
				// entre 00 y 99.
				addingResult = (int) (Math.random() * NumberConstants.NUM99);
			}

			// Si el número es menor de 10, se le añade un 0 por delante.
			if (addingResult < NumberConstants.NUM10) {
				twoCipherIpRepresentation = Integer.toString(0) + Integer.toString(addingResult);
			} else {
				twoCipherIpRepresentation = Integer.toString(addingResult);
			}

		}

		return twoCipherIpRepresentation;

	}

	/**
	 * Synchronized method that generates a unique identifier builded in characters
	 * and a ending number.
	 * @return String that represents the generated unique identifier.
	 */
	public static synchronized String generateNumbersUniqueId() {

		counter++;

		if (counter >= NumberConstants.NUM10000) {
			counter = (int) (Math.random() * NumberConstants.NUM5000);
			currentTimeMillis = String.valueOf(Calendar.getInstance().getTimeInMillis());
		}

		String indice = formatter.format(counter);

		return currentTimeMillis + getTwoCipherIpRepresentation() + indice;

	}

	/**
	 * Constructor method for the class UtilsIdentifierGenerators.java.
	 */
	private UtilsIdentifierGenerators() {
		super();
	}

}
