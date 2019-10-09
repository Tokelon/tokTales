package com.tokelon.toktales.tools.core.inject;

import java.io.Serializable;
import java.lang.annotation.Annotation;

class InjectParametersImpl implements InjectParameters, Serializable {

	private static final long serialVersionUID = -420609618212505330L;
	
	
	private final Class<?> value;

	public InjectParametersImpl(Class<?> value) {
		if(value == null) {
			throw new NullPointerException("key");
		}

		this.value = value;
	}

	
	@Override
	public Class<?> value() {
		return this.value;
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return InjectParameters.class;
	}
	
	
	@Override
	public int hashCode() {
		// This is specified in java.lang.Annotation.
		return (127 * "value".hashCode()) ^ value.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof InjectParameters)) {
			return false;
		}

		InjectParameters other = (InjectParameters) o;
		return value.equals(other.value());
	}

	
	@Override
	public String toString() {
		return "@" + InjectParameters.class.getName() + "(value=" + value + ")";
	}

}
