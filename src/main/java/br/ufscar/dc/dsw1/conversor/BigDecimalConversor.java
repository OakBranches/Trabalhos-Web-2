package br.ufscar.dc.dsw1.conversor;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

public class BigDecimalConversor implements Converter<String, BigDecimal> {

	@Override
	public BigDecimal convert(String text) {

		if (text.isEmpty()) {
			return null;
		} else {
			text = text.replace(',','.');	
		}
		
		return new BigDecimal(Double.parseDouble(text));
	}

}
