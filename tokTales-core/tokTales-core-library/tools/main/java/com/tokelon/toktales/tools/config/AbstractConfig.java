package com.tokelon.toktales.tools.config;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConfig {


	private final Logger logger;
	
	public AbstractConfig() {
		this(LoggerFactory.getILoggerFactory());
	}
	
	public AbstractConfig(ILoggerFactory loggerFactory) {
		this.logger = loggerFactory.getLogger(getClass().getName());
	}
	
	
	public Logger getLogger() {
		return logger;
	}
	
	
	/**
	 * @param value
	 * @return
	 * @throws ConfigDataException If value is null.
	 */
	protected String parseStringOrError(String value) throws ConfigDataException {
		if(value == null) {
			throw new ConfigDataException("Config value null is not a valid String");
		}
		else {
			return value;	
		}
	}
	
	protected String parseString(String value, String defaultValue) {
		if(value == null) {
			logger.warn("Config value null is not a valid String. Fallback to default value: {}", defaultValue);
			return defaultValue;
		}
		else {
			return value;
		}
	}
	
	protected String parseString(String value, String defaultValue, boolean logError) {
		if(value == null) {
			if(logError) logger.warn("Config value null is not a valid String. Fallback to default value: {}", defaultValue);
			return defaultValue;
		}
		else {
			return value;
		}
	}
	
	
	
	/**
	 * @param value
	 * @return
	 * @throws ConfigDataException If parsing fails.
	 */
	protected int parseIntOrError(String value) throws ConfigDataException {
		try {
			return Integer.parseInt(value);
		}
		catch(NumberFormatException nfe) {
			throw new ConfigDataException(String.format("Config value \"%s\" is not a valid int", value));
		}
	}

	protected int parseInt(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		}
		catch(NumberFormatException nfe) {
			logger.warn("Config value \"{}\" is not a valid int. Fallback to default value: {}", value, defaultValue);
			return defaultValue;
		}
	}
	
	protected int parseInt(String value, int defaultValue, boolean logError) {
		try {
			return Integer.parseInt(value);
		}
		catch(NumberFormatException nfe) {
			if(logError) logger.warn("Config value \"{}\" is not a valid int. Fallback to default value: {}", value, defaultValue);
			return defaultValue;
		}
		
	}
	
	
	/**
	 * @param value
	 * @return
	 * @throws ConfigDataException If parsing fails.
	 */
	protected long parseLong(String value) throws ConfigDataException {
		try {
			return Long.parseLong(value);
		}
		catch(NumberFormatException nfe) {
			throw new ConfigDataException(String.format("Config value \"%s\" is not a valid long", value));
		}
	}
	
	protected long parseLong(String value, long defaultValue) {
		try {
			return Long.parseLong(value);
		}
		catch(NumberFormatException nfe) {
			logger.warn("Config value \"{}\" is not a valid long. Fallback to default value: {}", value, defaultValue);
			return defaultValue;
		}
	}
	
	protected long parseLong(String value, long defaultValue, boolean logError) {
		try {
			return Long.parseLong(value);
		}
		catch(NumberFormatException nfe) {
			if(logError) logger.warn("Config value \"{}\" is not a valid long. Fallback to default value: {}", value, defaultValue);
			return defaultValue;
		}
	}
	
	
	/**
	 * @param value
	 * @return True if, ignoring case, value equals "true" and false if, ignoring case, value equals "false".
	 * @throws ConfigDataException If parsing fails.
	 */
	protected boolean parseBoolean(String value) throws ConfigDataException {
		if(value == null) {
			throw new ConfigDataException("Config value null is not a valid boolean");
		}
		
		if(value.toLowerCase().equals("true")) {
			return true;
		}
		else if(value.toLowerCase().equals("false")) {
			return false;
		}
		else {
			throw new ConfigDataException(String.format("Config value \"%s\" is not a valid boolean", value));
		}
	}
	
	
	protected boolean parseBoolean(String value, boolean defaultValue) {
		if(value == null) {
			logger.warn("Config value null is not a valid boolean");
			return defaultValue;
		}
		
		if(value.toLowerCase().equals("true")) {
			return true;
		}
		else if(value.toLowerCase().equals("false")) {
			return false;
		}
		else {
			logger.warn("Config value \"{}\" is not a valid boolean. Fallback to default value: {}", value, defaultValue);
			return defaultValue;
		}
	}
	
	protected boolean parseBoolean(String value, boolean defaultValue, boolean logError) {
		if(value == null) {
			if(logError) logger.warn("Config value null is not a valid boolean");
			return defaultValue;
		}
		
		if(value.toLowerCase().equals("true")) {
			return true;
		}
		else if(value.toLowerCase().equals("false")) {
			return false;
		}
		else {
			if(logError) logger.warn("Config value \"{}\" is not a valid boolean. Fallback to default value: {}", value, defaultValue);
			return defaultValue;
		}
	}
	
	
	
	/**
	 * @param value
	 * @return
	 * @throws ConfigDataException If parsing fails.
	 */
	protected float parseFloat(String value) throws ConfigDataException {
		try {
			return Float.parseFloat(value);			// For some reason this can also throw a NullpointerException along with the regular NumberFormatException
		}
		catch(NumberFormatException nfe) {
			throw new ConfigDataException(String.format("Config value \"%s\" is not a valid float", value));
		}
		catch(NullPointerException npe) {
			throw new ConfigDataException("Config value null is not a valid float");
		}
	}
	
	protected float parseFloat(String value, float defaultValue) {
		try {
			return Float.parseFloat(value);
		}
		catch(NumberFormatException nfe) {
			logger.warn("Config value \"{}\" is not a valid float. Fallback to default value: {}", value, defaultValue);
			return defaultValue;
		}
		catch(NullPointerException npe) {
			logger.warn("Config value null is not a valid float");
			return defaultValue;
		}
	}
	
	protected float parseFloat(String value, float defaultValue, boolean logError) {
		try {
			return Float.parseFloat(value);
		}
		catch(NumberFormatException nfe) {
			if(logError) logger.warn("Config value \"{}\" is not a valid float. Fallback to default value: {}", value, defaultValue);
			return defaultValue;
		}
		catch(NullPointerException npe) {
			if(logError) logger.warn("Config value null is not a valid float");
			return defaultValue;
		}
	}
	
	
	/**
	 * @param value
	 * @return
	 * @throws ConfigDataException If parsing fails.
	 */
	protected double parseDouble(String value) throws ConfigDataException {
		try {
			return Double.parseDouble(value);
		}
		catch(NumberFormatException nfe) {
			throw new ConfigDataException(String.format("Config value \"%s\" is not a valid double", value));
		}
		catch(NullPointerException npe) {
			throw new ConfigDataException("Config value null is not a valid double");
		}
	}
	
	protected double parseDouble(String value, double defaultValue) {
		try {
			return Double.parseDouble(value);
		}
		catch(NumberFormatException nfe) {
			logger.warn("Config value \"{}\" is not a valid double. Fallback to default value: {}", value, defaultValue);
			return defaultValue;
		}
		catch(NullPointerException npe) {
			logger.warn("Config value null is not a valid double");
			return defaultValue;
		}
	}
	
	protected double parseDouble(String value, double defaultValue, boolean logError) {
		try {
			return Double.parseDouble(value);
		}
		catch(NumberFormatException nfe) {
			if(logError) logger.warn("Config value \"{}\" is not a valid double. Fallback to default value: {}", value, defaultValue);
			return defaultValue;
		}
		catch(NullPointerException npe) {
			if(logError) logger.warn("Config value null is not a valid double");
			return defaultValue;
		}
	}
	
}
