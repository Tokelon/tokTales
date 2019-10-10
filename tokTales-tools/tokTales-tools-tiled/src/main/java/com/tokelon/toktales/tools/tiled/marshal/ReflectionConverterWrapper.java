package com.tokelon.toktales.tools.tiled.marshal;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class ReflectionConverterWrapper extends ReflectionConverter {

	private ConverterListener listener;
	
	
	@SuppressWarnings("rawtypes")
	public ReflectionConverterWrapper(Mapper mapper, ReflectionProvider reflectionProvider, Class type) {
		super(mapper, reflectionProvider, type);
	}

	public ReflectionConverterWrapper(Mapper mapper, ReflectionProvider reflectionProvider) {
		super(mapper, reflectionProvider);
	}

	
	
	@Override
	public void marshal(Object original, HierarchicalStreamWriter writer, MarshallingContext context) {
		//System.out.println("Marshal for object: " +original);
		
		Object obj = original;
		if(listener != null) {
			obj = listener.beforeMarshal(original);
		}
		
		super.marshal(obj, writer, context);
	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Object result = super.unmarshal(reader, context);
		
		if(listener != null) {
			result = listener.afterUnmarshal(result);
		}
		

		//System.out.println("Unmarshal for object: " +result);
		return result;
	}
	
	
	
	public void setListener(ConverterListener listener) {
		this.listener = listener; 
	}
	
	public ConverterListener getListener() {
		return listener;
	}
	
	

	public interface ConverterListener {
		
		public Object beforeMarshal(Object original);
		
		public Object afterUnmarshal(Object original);
	}

}
