package com.tokelon.toktales.test.guava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.List;

import org.junit.Test;

import com.google.common.reflect.TypeToken;

@SuppressWarnings("serial")
public class TestDynamicTypeToken {

	
	private interface IKnowDynamicType<E> {
		
	}
	
	private static class KnowDynamicType<T> implements IKnowDynamicType<T> {
		TypeToken<T> typeToken;
		
		public KnowDynamicType(TypeToken<T> typeToken) {
			this.typeToken = typeToken;
		}

	}
	
	private static class AlsoKnowDynamicType<K> extends KnowDynamicType<K> {
		
		public AlsoKnowDynamicType(TypeToken<K> typeToken) {
			super(typeToken);
		}
	}
	

	private static class KnowMyType<T> {
		TypeToken<T> typeToken = new TypeToken<T>(getClass()) {};
	}
	

	private static <X> KnowMyType<X> createBadKnowMyType() {
		return new KnowMyType<X>() {};
	}
	
	private static <Z> KnowDynamicType<Z> createCorrectKnowDynamicType(TypeToken<Z> token) {
		return new KnowDynamicType<Z>(token);
	}
	
	
	@Test
	public void CreateBadKnowMyType_ShouldNotCaptureType() {
		KnowMyType<String> dynamicKnowMyType = createBadKnowMyType();

		assertNotEquals(new TypeToken<String>() {}, dynamicKnowMyType.typeToken);
		assertTrue(dynamicKnowMyType.typeToken.getType() instanceof TypeVariable);
		assertEquals("X", dynamicKnowMyType.typeToken.getType().getTypeName());
	}

	@Test
	public void CreateCorrectKnowDynamicType_ShouldCaptureType() {
		KnowDynamicType<String> knowDynamicType = createCorrectKnowDynamicType(TypeToken.of(String.class));

		assertEquals(TypeToken.of(String.class), knowDynamicType.typeToken);
		assertTrue(knowDynamicType.typeToken.getType() instanceof Class);
	}
	

	@Test
	public void KnowDynamicType_AnonSubclassSimple_ShouldBeOfTypeClass() {
		KnowDynamicType<String> knowDynamicTypeSimple = new KnowDynamicType<String>(TypeToken.of(String.class)) { };
		
		assertEquals(TypeToken.of(String.class), knowDynamicTypeSimple.typeToken);
		assertTrue(knowDynamicTypeSimple.typeToken.getType() instanceof Class);
	}
	
	@Test
	public void KnowDynamicType_AnonSubclass_ShouldKnowItsType() {
		KnowDynamicType<List<String>> knowDynamicType = new KnowDynamicType<List<String>>(new TypeToken<List<String>>() {}) { };
		
		assertEquals(new TypeToken<List<String>>() {}, knowDynamicType.typeToken);
		assertTrue(knowDynamicType.typeToken.getType() instanceof ParameterizedType);
	}
	
	@Test
	public void KnowDynamicType_Instance_ShouldNotKnowItsType() {
		KnowDynamicType<List<String>> knowDynamicTypeInstance = new KnowDynamicType<>(new TypeToken<List<String>>() {});
		
		assertEquals(new TypeToken<List<String>>() {}, knowDynamicTypeInstance.typeToken);
		assertTrue(knowDynamicTypeInstance.typeToken.getType() instanceof ParameterizedType);
	}
	
	

	@Test
	public void AlsoKnowDynamicType_AnonSubclassSimple_ShouldBeOfTypeClass() {
		AlsoKnowDynamicType<String> alsoKnowDynamicTypeSimple = new AlsoKnowDynamicType<String>(TypeToken.of(String.class)) { };
		
		assertEquals(TypeToken.of(String.class), alsoKnowDynamicTypeSimple.typeToken);
		assertTrue(alsoKnowDynamicTypeSimple.typeToken.getType() instanceof Class);
	}
	
	@Test
	public void AlsoKnowDynamicType_AnonSubclass_ShouldKnowItsType() {
		AlsoKnowDynamicType<List<String>> alsoKnowDynamicType = new AlsoKnowDynamicType<List<String>>(new TypeToken<List<String>>() {}) { };
		
		assertEquals(new TypeToken<List<String>>() {}, alsoKnowDynamicType.typeToken);
		assertTrue(alsoKnowDynamicType.typeToken.getType() instanceof ParameterizedType);
	}
	
	@Test
	public void AlsoKnowDynamicType_Instance_ShouldNotKnowItsType() {
		AlsoKnowDynamicType<List<String>> alsoKnowDynamicTypeInstance = new AlsoKnowDynamicType<>(new TypeToken<List<String>>() {});
		
		assertEquals(new TypeToken<List<String>>() {}, alsoKnowDynamicTypeInstance.typeToken);
		assertTrue(alsoKnowDynamicTypeInstance.typeToken.getType() instanceof ParameterizedType);
	}
	
}
