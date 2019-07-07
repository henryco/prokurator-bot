package dev.tindersamurai.prokurator;

import dev.tindersamurai.prokurator.mvc.service.configuration.db.converter.PrimitiveConfigConverter;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

public class PrimitiveConverterTests {

	@Test
	public void testToString() {
		val converter = new PrimitiveConfigConverter();

		Assert.assertEquals("2.0", converter.toString(2f));
		Assert.assertEquals("true", converter.toString(true));
		Assert.assertEquals("23", converter.toString(23L));
		Assert.assertEquals("123", converter.toString("123"));
		Assert.assertEquals("34.0", converter.toString(34D));
		Assert.assertEquals("12", converter.toString(12));
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidToString() {
		new PrimitiveConfigConverter().toString(new Object());
	}

	@Test
	public void testFromString() {
		val converter = new PrimitiveConfigConverter();

		val a = converter.fromString("2.0", Float.class);
		Assert.assertEquals(Float.class, a.getClass());

		val b = converter.fromString("true", Boolean.class);
		Assert.assertEquals(Boolean.class, b.getClass());

		val c = converter.fromString("23", Long.class);
		Assert.assertEquals(Long.class, c.getClass());

		val d = converter.fromString("123", String.class);
		Assert.assertEquals(String.class, d.getClass());

		val e = converter.fromString("34.34", Double.class);
		Assert.assertEquals(Double.class, e.getClass());

		val f = converter.fromString("12", Integer.class);
		Assert.assertEquals(Integer.class, f.getClass());
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidFromString() {
		new PrimitiveConfigConverter().fromString("123", Object.class);
	}
}
