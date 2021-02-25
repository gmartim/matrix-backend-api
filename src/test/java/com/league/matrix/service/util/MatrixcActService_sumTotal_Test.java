package com.league.matrix.service.util;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.league.matrix.exception.BadContentException;
import com.league.matrix.exception.OperationNotSupportedException;
import com.league.matrix.service.AbstractMatrixServiceTest;

@SpringBootTest
@Import(MatrixActService.class)
public class MatrixcActService_sumTotal_Test extends AbstractMatrixServiceTest {

	@Autowired
	private MatrixActService matrixOperationService;

	@Test
	public void whenMatrixIsNull_thenBadContentException() {
		Assertions.assertThrows(BadContentException.class, () -> {
			matrixOperationService.sumTotal(null);
		});
	}

	@Test
	public void whenMatrixTooHighNumber_thenBadContentException() {
		List<List<Integer>> originalMatrix;

		originalMatrix = matrixGenerator("1 2  3 " + (Integer.MAX_VALUE - 3));

		Assertions.assertThrows(OperationNotSupportedException.class, () -> {
			matrixOperationService.sumTotal(originalMatrix);
		});
	}

	@Test
	public void whenMatrixTooHighNumberValid() {
		List<List<Integer>> originalMatrix;

		originalMatrix = matrixGenerator("1 2  3 " + (Integer.MAX_VALUE - 7));

		try {
			Assertions.assertEquals((Integer.MAX_VALUE - 7 + 6), matrixOperationService.sumTotal(originalMatrix));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenValidMatrix() {
		List<List<Integer>> originalMatrix;

		originalMatrix = matrixGenerator("1 2 3  4 5 6  7 8 9");

		try {
			Assertions.assertEquals(45, matrixOperationService.sumTotal(originalMatrix));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenValidNotSquareMatrix() {
		List<List<Integer>> originalMatrix;

		originalMatrix = matrixGenerator("1 2 3  4 5 6 10  7 8 9 20");

		try {
			Assertions.assertEquals(75, matrixOperationService.sumTotal(originalMatrix));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenSingleValueMatrix() {
		List<List<Integer>> originalMatrix;

		originalMatrix = matrixGenerator("20");

		try {
			Assertions.assertEquals(20, matrixOperationService.sumTotal(originalMatrix));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

}
