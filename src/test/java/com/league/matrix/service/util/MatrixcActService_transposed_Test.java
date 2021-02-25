package com.league.matrix.service.util;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.league.matrix.exception.BadContentException;
import com.league.matrix.service.AbstractMatrixServiceTest;

@SpringBootTest
@Import(MatrixActService.class)
public class MatrixcActService_transposed_Test extends AbstractMatrixServiceTest {

	@Autowired
	private MatrixActService matrixOperationService;

	@Test
	public void whenMatrixIsNull_thenBadContentException() {
		Assertions.assertThrows(BadContentException.class, () -> {
			matrixOperationService.transpose(null);
		});
	}

	@Test
	public void whenValidMatrix() {
		List<List<Integer>> originalMatrix, expectedMatrix;

		originalMatrix = matrixGenerator("1 2 3  4 5 6  7 8 9");
		expectedMatrix = matrixGenerator("1 4 7  2 5 8  3 6 9");

		try {
			Assertions.assertEquals(expectedMatrix, matrixOperationService.transpose(originalMatrix));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenValidNotSquareMatrix() {
		List<List<Integer>> originalMatrix, expectedMatrix;

		originalMatrix = matrixGenerator("1 2 3  4 5 6 10  7 8 9 20");
		expectedMatrix = matrixGenerator("1 4 7  2 5 8  3 6 9  10 20");

		try {
			Assertions.assertEquals(expectedMatrix, matrixOperationService.transpose(originalMatrix));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenSingleValueMatrix() {
		List<List<Integer>> originalMatrix, expectedMatrix;

		originalMatrix = matrixGenerator("1");
		expectedMatrix = matrixGenerator("1");

		try {
			Assertions.assertEquals(expectedMatrix, matrixOperationService.transpose(originalMatrix));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

}
