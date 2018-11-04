package com.tokelon.toktales.test.guava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.TypeVariable;
import java.util.List;

import org.junit.Test;

import com.google.common.reflect.TypeToken;

@SuppressWarnings("serial")
public class TestInheritedTypeToken {


	private static class BadKnowMyOwnType<T> {
		TypeToken<T> typeToken = new TypeToken<T>(BadKnowMyOwnType.class) {};
	}
	
	private static class BadKnowMyParentType<K> extends BadKnowMyOwnType<K> {
		
	}
	
	
	@Test
	public void BadKnowMyOwnType_Simple_ShouldNotCaptureType() {
		BadKnowMyOwnType<String> badKnowMyOwnType = new BadKnowMyOwnType<String>() { };
		
		assertNotEquals(new TypeToken<String>() {}, badKnowMyOwnType.typeToken);
		assertTrue(badKnowMyOwnType.typeToken.getType() instanceof TypeVariable);
		assertEquals("T", badKnowMyOwnType.typeToken.getType().getTypeName());
	}
	
	@Test
	public void BadKnowMyOwnType_ShouldNotCaptureType() {
		BadKnowMyOwnType<List<String>> badKnowMyOwnType = new BadKnowMyOwnType<List<String>>() { };

		assertNotEquals(new TypeToken<List<String>>() {}, badKnowMyOwnType.typeToken);
		assertTrue(badKnowMyOwnType.typeToken.getType() instanceof TypeVariable);
		assertEquals("T", badKnowMyOwnType.typeToken.getType().getTypeName());
	}
	
	
	@Test
	public void BadKnowMyParentType_Simple_ShouldNotCaptureType() {
		BadKnowMyParentType<String> badKnowMyParentType = new BadKnowMyParentType<String>() { };

		assertNotEquals(new TypeToken<String>() {}, badKnowMyParentType.typeToken);
		assertTrue(badKnowMyParentType.typeToken.getType() instanceof TypeVariable);
		assertEquals("T", badKnowMyParentType.typeToken.getType().getTypeName());
	}
	
	@Test
	public void BadKnowMyParentType_ShouldNotCaptureType() {
		BadKnowMyParentType<String> badKnowMyParentType = new BadKnowMyParentType<String>() { };

		assertNotEquals(new TypeToken<List<String>>() {}, badKnowMyParentType.typeToken);
		assertTrue(badKnowMyParentType.typeToken.getType() instanceof TypeVariable);
		assertEquals("T", badKnowMyParentType.typeToken.getType().getTypeName());
	}
	
}
