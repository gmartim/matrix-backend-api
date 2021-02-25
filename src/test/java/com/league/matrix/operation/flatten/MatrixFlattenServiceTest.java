package com.league.matrix.operation.flatten;

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
import com.league.matrix.service.util.MatrixPrintService;

@SpringBootTest
@Import(MatrixFlattenService.class)
public class MatrixFlattenServiceTest extends AbstractMatrixServiceTest {

	@Autowired
	private MatrixOperationService matrixFlattenService;

	@MockBean
	private MatrixCreateService matrixCreateService;

	@MockBean
	private MatrixPrintService matrixPrintService;

	@Test
	public void whenUsingNullMatrix_thenBadContentException() {
		try {
			BDDMockito.given(matrixCreateService.create(BDDMockito.any())).willReturn(null);
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}

		Assertions.assertThrows(BadContentException.class, () -> {
			matrixFlattenService.run(new MockMultipartFile("file1.csv", "".getBytes()));
		});
	}

	@Test
	public void whenUsingNotSquareMatrix_thenBadContentException() {
		List<List<Integer>> notSquareMatrix;

		notSquareMatrix = matrixGenerator("1 2 3  4 5  7 8 9 10");

		try {
			BDDMockito.given(matrixCreateService.create(BDDMockito.any())).willReturn(notSquareMatrix);
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}

		Assertions.assertThrows(BadContentException.class, () -> {
			matrixFlattenService.run(new MockMultipartFile("file1.csv", "".getBytes()));
		});
	}

	@Test
	public void whenUsingValidMatrix() {
		List<List<Integer>> notSquareMatrix;

		notSquareMatrix = matrixGenerator("1 2 3 11  4 5 6 12  7 8 9 10  1 2 3 4");

		try {
			BDDMockito.given(matrixCreateService.create(BDDMockito.any())).willReturn(notSquareMatrix);
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}

		try {
			BDDMockito.given(matrixPrintService.join(BDDMockito.any(), BDDMockito.any(), BDDMockito.any()))
					.willReturn("1,4,7,2,5,8,3,6,9");
		} catch (BadContentException exception) {
			Assertions.fail();
		}

		try {
			Assertions.assertEquals("1,4,7,2,5,8,3,6,9",
					matrixFlattenService.run(new MockMultipartFile("file1.csv", "".getBytes())));
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}
	}

}
