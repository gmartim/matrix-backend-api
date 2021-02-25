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
public class MatrixcActService_multiplyTotal_Test extends AbstractMatrixServiceTest {

	@Autowired
	private MatrixActService matrixOperationService;

	@Test
	public void whenMatrixIsNull_thenBadContentException() {
		Assertions.assertThrows(BadContentException.class, () -> {
			matrixOperationService.multiplyTotal(null);
		});
	}

	@Test
	public void whenValidMatrix() {
		List<List<Integer>> originalMatrix;

		originalMatrix = matrixGenerator("1 2 3  4 5 6  7 8 9");

		try {
			Assertions.assertEquals(362880, matrixOperationService.multiplyTotal(originalMatrix));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenValidNotSquareMatrix() {
		List<List<Integer>> originalMatrix;

		originalMatrix = matrixGenerator("1 2 3  4 5 6 10  7 8 9 20");

		try {
			Assertions.assertEquals(72576000, matrixOperationService.multiplyTotal(originalMatrix));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenValidHugeMatrix() {
		List<List<Integer>> originalMatrix;

		originalMatrix = matrixGenerator(
				"1111111 2222222 3333333  4444444 5555555 6666666 10101010  7777777 8888888 9999999 20202020");

		Assertions.assertThrows(OperationNotSupportedException.class, () -> {
			matrixOperationService.multiplyTotal(originalMatrix);
		});
	}

	@Test
	public void whenSingleValueMatrix() {
		List<List<Integer>> originalMatrix;

		originalMatrix = matrixGenerator("20");

		try {
			Assertions.assertEquals(20, matrixOperationService.multiplyTotal(originalMatrix));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

}
