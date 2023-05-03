package com.openclassrooms.testing.calcul.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.openclassrooms.testing.calcul.domain.Calculator;
import com.openclassrooms.testing.calcul.domain.model.CalculationModel;
import com.openclassrooms.testing.calcul.domain.model.CalculationType;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {
	
	@Mock
	Calculator calculator;
	CalculatorService classUnderTest;
	
	@Mock
	SolutionFormatter solutionFormatter;
	
	@BeforeEach
	public void init() {
		classUnderTest = new CalculatorServiceImpl(calculator, solutionFormatter);
	}
	// Calculator IS CALLED BY CalculatorService


	@Test
	public void calculate_shouldUseCalculator_forAddition() {
		//GIVEN -->param mock
		when(calculator.add(1, 2)).thenReturn(3); 
		//WHEN -->act
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.ADDITION, 1, 2)).getSolution();
		//THEN
		verify(calculator).add(1, 2);
		assertThat(result).isEqualTo(3);
	}

	@Test
	public void calculate_shouldUseCalculator_forAnyAddition() {
		//GIVEN -->param mock
		final Random r = new Random();
		when(calculator.add(any(Integer.class),any(Integer.class))).thenReturn(3); 
		//WHEN -->act
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.ADDITION, r.nextInt(),r.nextInt())).getSolution();
		//THEN
		verify(calculator,times(1)).add(any(Integer.class), any(Integer.class));//une fois add
		verify(calculator,times(0)).sub(any(Integer.class), any(Integer.class));//0 fois sub
		verify(calculator,never()).multiply(any(Integer.class), any(Integer.class));//jamais mult
		assertThat(result).isEqualTo(3);
	}

	@Test
	public void calculate_shouldUseCalculator_divideByZero() {
		//GIVEN -->param mock
		when(calculator.divide(1, 0)).thenThrow(new ArithmeticException()); 
		//WHEN -->act
		assertThrows(IllegalArgumentException.class, ()-> classUnderTest.calculate(
				new CalculationModel(CalculationType.DIVISION,1, 0)));
		//THEN
		verify(calculator,times(1)).divide(1,0);

	}
	
	
			
	@Test
	public void calculate_shouldUseCalculator_forMultiplication() {
		//GIVEN -->param mock
		when(calculator.multiply(3, 2)).thenReturn(6); 
		//WHEN -->act
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.MULTIPLICATION, 3, 2)).getSolution();
		//THEN
		verify(calculator).multiply(3,2);
		assertThat(result).isEqualTo(6);
	}
	
	@Test
	public void calculate_shouldUseCalculator_forSoustraction() {
		//GIVEN -->param mock
		when(calculator.sub(2, 1)).thenReturn(1); 
		//WHEN -->act
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.SUBTRACTION, 2, 1)).getSolution();
		//THEN
		verify(calculator).sub(2,1);
		assertThat(result).isEqualTo(1);
	}
	
	@Test
	public void calculate_shouldUseCalculator_forDivision() {
		//GIVEN -->param mock
		when(calculator.divide(10, 2)).thenReturn(5); 
		//WHEN -->act
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.DIVISION, 10, 2)).getSolution();
		//THEN
		verify(calculator).divide(10, 2);
		assertThat(result).isEqualTo(5);
	}
	
	@Test
	public void calculate_ShouldFormatSolution_forAddition() {
		when(calculator.add(10000, 3000)).thenReturn(13000);
		when(solutionFormatter.format(13000)).thenReturn("13 000");
		
		final String formatedRes = classUnderTest.calculate(
				new CalculationModel(CalculationType.ADDITION, 10000,3000)).getFormattedSolution();
		
		assertThat(formatedRes).isEqualTo("13 000");
	}
}
