package com.openclassrooms.testing.calcul.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class SolutionFormatterTest {
	private SolutionFormatter solutionFormatter;
	
	@BeforeEach
	public void init() {
		solutionFormatter = new SolutionFormatterImpl();
	}
	
	@Test
	public void format_shouldFromatAnyBigNumber() {
		//GIVEN
		final int number = 1245690;
		//WHEN
		final String res = solutionFormatter.format(number);
		//
		assertThat(res).isEqualTo("1 245 690");
	}
}
