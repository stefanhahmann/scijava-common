/*-
 * #%L
 * SciJava Common shared library for SciJava software.
 * %%
 * Copyright (C) 2009 - 2024 SciJava developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.scijava.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.Test;

public class GenericArrayTypesTest {

	@Test
	public void testArrayFields() throws NoSuchFieldException, SecurityException {
		Field rawField = Types.field(ClassWithFields.class, "rawListArray");
		Field genericWildcardField = Types.field(ClassWithFields.class, "wildcardListArray");
		Field genericTypedField = Types.field(ClassWithFields.class, "integerListArray");

		Type rawFieldType = Types.fieldType(rawField, ClassWithFields.class);
		Type genericWildcardFieldType = Types.fieldType(genericWildcardField, ClassWithFields.class);
		Type genericTypedFieldType = Types.fieldType(genericTypedField, ClassWithFields.class);

		// raw type
		assertFalse(rawFieldType instanceof GenericArrayType);
		assertTrue(rawFieldType instanceof Class);

		// generic array types
		assertTrue(genericWildcardFieldType instanceof GenericArrayType);
		assertTrue(genericTypedFieldType instanceof GenericArrayType);

		assertEquals(rawField.getGenericType(), rawFieldType);
		assertEquals(genericWildcardField.getGenericType(), genericWildcardFieldType);
		assertEquals(genericTypedField.getGenericType(), genericTypedFieldType);

		assertSame(List[].class, Types.raw(rawFieldType));
		assertSame(List[].class, Types.raw(genericWildcardFieldType));
		assertSame(List[].class, Types.raw(genericTypedFieldType));
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private static class ClassWithFields {
		public List[] rawListArray;
		public List<?>[] wildcardListArray;
		public List<Integer>[] integerListArray;
	}
}
