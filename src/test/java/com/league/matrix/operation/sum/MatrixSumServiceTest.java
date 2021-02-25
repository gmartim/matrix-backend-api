package com.league.matrix.operation.sum;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;

import com.league.matrix.exception.BadContentException;
import com.league.matrix.exception.InternalErrorException;
import com.league.matrix.service.AbstractMatrixServiceTest;
import com.league.matrix.service.MatrixOperationService;
import com.league.matrix.service.util.MatrixCreateService;

@SpringBootTest
@Import(MatrixSumService.class)
public class MatrixSumServiceTest extends AbstractMatrixServiceTest {

	@Autowired
	private MatrixOperationService matrixSumService;

	@MockBean
	private MatrixCreateService matrixFactoryService;

	@Test
	public void whenUsingNullMatrix_thenBadContentException() {
		try {
			BDDMockito.given(matrixFactoryService.create(BDDMockito.any())).willReturn(null);
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}

		Assertions.assertThrows(BadContentException.class, () -> {
			matrixSumService.run(new MockMultipartFile("file1.csv", "".getBytes()));
		});
	}

	@Test
	public void whenUsingNotSquareMatrix_thenBadContentException() {
		List<List<Integer>> notSquareMatrix;

		notSquareMatrix = matrixGenerator("1 2 3  4 5  7 8 9 10");

		try {
			BDDMockito.given(matrixFactoryService.create(BDDMockito.any())).willReturn(notSquareMatrix);
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}

		Assertions.assertThrows(BadContentException.class, () -> {
			matrixSumService.run(new MockMultipartFile("file1.csv", "".getBytes()));
		});
	}

	@Test
	public void whenUsingValidMatrix() {
		List<List<Integer>> notSquareMatrix;

		notSquareMatrix = matrixGenerator("1 2 3 11  4 5 6 12  7 8 9 10  1 2 3 4");

		try {
			BDDMockito.given(matrixFactoryService.create(BDDMockito.any())).willReturn(notSquareMatrix);
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}

		try {
			Assertions.assertEquals("88", matrixSumService.run(new MockMultipartFile("file1.csv", "".getBytes())));
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}
	}

}
