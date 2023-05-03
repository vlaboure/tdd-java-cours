package com.openclassrooms.testing.calcul.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.testing.calcul.domain.Calculator;
import com.openclassrooms.testing.calcul.domain.model.CalculationModel;
import com.openclassrooms.testing.calcul.domain.model.CalculationType;

@ExtendWith(MockitoExtension.class)
public class BatchCalculatorServiceTest {

	@Mock
	CalculatorService calculatorService;
	
	BatchCalculatorService batchCalculatorService;
	
	BatchCalculatorService batchCalculatorServiceNoMock;

	
	@BeforeEach
	public void init() {
		batchCalculatorService = new BatchCalculatorServiceImpl(calculatorService);	
		
		batchCalculatorServiceNoMock = new BatchCalculatorServiceImpl(
				new CalculatorServiceImpl(new Calculator(),
						new SolutionFormatterImpl()));	
	}
	
	@Test
	public void givenOperationList_whenCalculate_thenCallServicesWithCorrectsArguments() {
		
		//GIVEN
		final Stream<String> operations = 
				Arrays.asList("2+2","5-4","6x8","9/3").stream();
		
		final  ArgumentCaptor<CalculationModel> calculationModelCaptor =
			ArgumentCaptor.forClass(CalculationModel.class);
		
		//WHEN
		batchCalculatorService.batchCalculate(operations);
		
		//THEN
		verify(calculatorService,times(4)).calculate(calculationModelCaptor.capture());
		final List<CalculationModel> calculationModels =
				calculationModelCaptor.getAllValues();	
		assertThat(calculationModels)
			.extracting(CalculationModel::getLeftArgument,
					 CalculationModel::getType,
			         CalculationModel::getRightArgument)
			.containsExactly(
					tuple(2, CalculationType.ADDITION, 2),
					tuple(5, CalculationType.SUBTRACTION, 4),
					tuple(6, CalculationType.MULTIPLICATION, 8),
					tuple(9, CalculationType.DIVISION, 3));
	}
	
}
