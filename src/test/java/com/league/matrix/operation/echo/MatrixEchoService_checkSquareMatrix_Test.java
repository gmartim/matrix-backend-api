package com.league.matrix.operation.echo;

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
import com.league.matrix.exception.NotSquareMatrixException;
import com.league.matrix.service.AbstractMatrixServiceTest;
import com.league.matrix.service.MatrixOperationService;
import com.league.matrix.service.util.MatrixCreateService;

@SpringBootTest
@Import(MatrixEchoService.class)
public class MatrixEchoService_checkSquareMatrix_Test extends AbstractMatrixServiceTest {

	@Autowired
	private MatrixOperationService matrixEchoService;

	@MockBean
	private MatrixCreateService matrixCreateService;

	@Test
	public void whenMatrixIsNotSquare_thenNotSquareMatrixException() {
		try {
			BDDMockito.given(matrixCreateService.create(BDDMockito.any()))
					.willReturn(matrixGenerator("1 2 3  4 5  6 7 8"));
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}
		Assertions.assertThrows(NotSquareMatrixException.class, () -> {
			matrixEchoService.run(new MockMultipartFile("file1.csv", "".getBytes()));
		});
	}

}
