package com.league.matrix.service.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.league.matrix.exception.BadContentException;
import com.league.matrix.service.AbstractMatrixServiceTest;

@SpringBootTest
@Import(MatrixPrintService.class)
public class MatrixPrintServiceTest extends AbstractMatrixServiceTest {

	@Autowired
	private MatrixPrintService matrixPrintService;

	@Test
	public void whenMatrixIsNull_thenBadContentException() {
		Assertions.assertThrows(BadContentException.class, () -> {
			matrixPrintService.join(null, StringUtils.EMPTY, StringUtils.EMPTY);
		});
	}

	@Test
	public void whenHasColumnSeparatorAndRowSeparator() {
		List<List<Integer>> matrix;

		matrix = matrixGenerator("1 2 3  4 5 6  7 8 9");

		try {
			Assertions.assertEquals("1-2-3_4-5-6_7-8-9", matrixPrintService.join(matrix, "-", "_"));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenNullToColumnSeparatorAndRowSeparator() {
		List<List<Integer>> matrix;

		matrix = matrixGenerator("1 2 3  4 5 6  7 8 9");

		try {
			Assertions.assertEquals("123456789", matrixPrintService.join(matrix, null, null));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenNotSquareMatrix() {
		List<List<Integer>> matrix;

		matrix = matrixGenerator("1 2  4 5 6  7 8");

		try {
			Assertions.assertEquals("1-2_4-5-6_7-8", matrixPrintService.join(matrix, "-", "_"));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenSingleValueMatrix() {
		List<List<Integer>> matrix;

		matrix = matrixGenerator("1");

		try {
			Assertions.assertEquals("1", matrixPrintService.join(matrix, "-", "_"));
		} catch (BadContentException exception) {
			Assertions.fail();
		}
	}

}
