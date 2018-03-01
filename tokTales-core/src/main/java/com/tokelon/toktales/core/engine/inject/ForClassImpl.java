package com.tokelon.toktales.core.engine.inject;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public class ForClassImpl implements ForClass, Serializable {

	private static final long serialVersionUID = 0;

	
	private final Class<?> value;

	public ForClassImpl(Class<?> value) {
		if(value == null) {
			throw new NullPointerException("class");
		}

		this.value = value;
	}

	static ForClass inClass(Class<?> clazz) {
		return new ForClassImpl(clazz);
	}

	@Override
	public Class<?> value() {
		return this.value;
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return ForClass.class;
	}
	
	
	@Override
	public int hashCode() {
		// This is specified in java.lang.Annotation.
		return (127 * "value".hashCode()) ^ value.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ForClass)) {
			return false;
		}

		ForClass other = (ForClass) o;
		return value.equals(other.value());
	}

	
	@Override
	public String toString() {
		return "@" + ForClass.class.getName() + "(value=" + value + ")";
	}

}
