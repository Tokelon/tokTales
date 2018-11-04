package com.tokelon.toktales.test.guava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.List;

import org.junit.Test;

import com.google.common.reflect.TypeToken;

@SuppressWarnings("serial")
public class TestIKnowMyTypeTypeToken {

	
	private interface IKnowMyType<S> {
		
	}
	
	private static class KnowMyType<T> implements IKnowMyType<T> {
		TypeToken<T> typeToken = new TypeToken<T>(getClass()) {};
	}
	
	
	private static class AlsoKnowMyType<K> extends KnowMyType<K> {
		
	}
	
	private static class AlwaysKnowMyType extends KnowMyType<List<String>> {
		
	}
	
	private static class AlsoAlwaysKnowMyType extends AlsoKnowMyType<List<String>> {
		
	}
	
	
	@Test
	public void KnowMyType_AnonSubclass_ShouldBeSubtypedCorrectly() {
		KnowMyType<KnowMyType<String>> knowMyType = new KnowMyType<KnowMyType<String>>() { };

		assertTrue(knowMyType.typeToken.isSubtypeOf(Object.class));
		assertTrue(knowMyType.typeToken.isSubtypeOf(new TypeToken<IKnowMyType<String>>() {}));
		assertTrue(knowMyType.typeToken.isSubtypeOf(new TypeToken<KnowMyType<String>>() {}));
	}
	
	@Test
	public void KnowMyType_Instance_ShouldNotBeSubtyped() {
		KnowMyType<KnowMyType<String>> notKnowMyType = new KnowMyType<>();

		assertTrue(notKnowMyType.typeToken.isSubtypeOf(Object.class));
		assertFalse(notKnowMyType.typeToken.isSubtypeOf(new TypeToken<IKnowMyType<String>>() {}));
		assertFalse(notKnowMyType.typeToken.isSubtypeOf(new TypeToken<KnowMyType<String>>() {}));
	}
	
	
	@Test
	public void KnowMyType_AnonSubclassSimple_ShouldBeOfTypeClass() {
		KnowMyType<String> knowMyTypeSimple = new KnowMyType<String>() { };
		
		assertEquals(TypeToken.of(String.class), knowMyTypeSimple.typeToken);
		assertTrue(knowMyTypeSimple.typeToken.getType() instanceof Class);
	}
	
	@Test
	public void KnowMyType_AnonSubclass_ShouldKnowItsType() {
		KnowMyType<List<String>> knowMyType = new KnowMyType<List<String>>() { };
		
		assertEquals(new TypeToken<List<String>>() {}, knowMyType.typeToken);
		assertTrue(knowMyType.typeToken.getType() instanceof ParameterizedType);
	}
	
	@Test
	public void KnowMyType_Instance_ShouldNotKnowItsType() {
		KnowMyType<List<String>> notKnowMyType = new KnowMyType<>();
		
		assertNotEquals(new TypeToken<List<String>>() {}, notKnowMyType.typeToken);
		assertTrue(notKnowMyType.typeToken.getType() instanceof TypeVariable);
		assertEquals("T", notKnowMyType.typeToken.getType().getTypeName());
	}
	
	
	@Test
	public void AlsoKnowMyType_AnonSubclassSimple_ShouldBeOfTypeClass() {
		AlsoKnowMyType<String> alsoKnowMyTypeSimple = new AlsoKnowMyType<String>() { };
		
		assertEquals(TypeToken.of(String.class), alsoKnowMyTypeSimple.typeToken);
		assertTrue(alsoKnowMyTypeSimple.typeToken.getType() instanceof Class);
	}
	
	@Test
	public void AlsoKnowMyType_AnonSubclass_ShouldKnowItsType() {
		AlsoKnowMyType<List<String>> alsoKnowMyType = new AlsoKnowMyType<List<String>>() { };
		
		assertEquals(new TypeToken<List<String>>() {}, alsoKnowMyType.typeToken);
		assertTrue(alsoKnowMyType.typeToken.getType() instanceof ParameterizedType);
	}
	
	@Test
	public void AlsoKnowMyType_Instance_ShouldNotKnowItsType() {
		AlsoKnowMyType<List<String>> alsoNotKnowMyType = new AlsoKnowMyType<>();
		
		assertNotEquals(new TypeToken<List<String>>() {}, alsoNotKnowMyType.typeToken);
		assertTrue(alsoNotKnowMyType.typeToken.getType() instanceof TypeVariable);
		assertEquals("K", alsoNotKnowMyType.typeToken.getType().getTypeName());
	}
	
	
	@Test
	public void AlwaysKnowMyType_AnonSubclass_ShouldKnowItsType() {
		AlwaysKnowMyType alwaysKnowMyType = new AlwaysKnowMyType() { };
		assertEquals(new TypeToken<List<String>>() {}, alwaysKnowMyType.typeToken);
	}
	
	@Test
	public void AlwaysKnowMyType_Instance_ShouldKnowItsType() {
		AlwaysKnowMyType alwaysKnowMyType = new AlwaysKnowMyType();
		assertEquals(new TypeToken<List<String>>() {}, alwaysKnowMyType.typeToken);
	}
	
	
	@Test
	public void AlsoAlwaysKnowMyType_AnonSubclass_ShouldKnowItsType() {
		AlsoAlwaysKnowMyType alsoAlwaysKnowMyType = new AlsoAlwaysKnowMyType() { };
		assertEquals(new TypeToken<List<String>>() {}, alsoAlwaysKnowMyType.typeToken);
	}
	
	@Test
	public void AlsoAlwaysKnowMyType_Instance_ShouldKnowItsType() {
		AlsoAlwaysKnowMyType alsoAlwaysKnowMyType = new AlsoAlwaysKnowMyType();
		assertEquals(new TypeToken<List<String>>() {}, alsoAlwaysKnowMyType.typeToken);
	}
	
}
