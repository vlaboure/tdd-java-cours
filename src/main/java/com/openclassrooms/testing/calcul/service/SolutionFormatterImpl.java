package com.openclassrooms.testing.calcul.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class SolutionFormatterImpl implements SolutionFormatter {

	@Override
	public String format(Integer response)
	{
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRENCH);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

		symbols.setGroupingSeparator(' ');
		formatter.setDecimalFormatSymbols(symbols);
		return formatter.format(response);
	}

}
