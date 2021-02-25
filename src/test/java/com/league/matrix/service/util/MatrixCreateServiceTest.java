package com.league.matrix.service.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;

import com.league.matrix.exception.BadContentException;
import com.league.matrix.exception.InternalErrorException;
import com.league.matrix.service.AbstractMatrixServiceTest;

@SpringBootTest
@Import(MatrixCreateService.class)
public class MatrixCreateServiceTest extends AbstractMatrixServiceTest {

	@Autowired
	private MatrixCreateService matrixFactoryService;

	@Test
	public void whenMultipartFileIsNull_thenBadContentException() {
		Assertions.assertThrows(BadContentException.class, () -> {
			matrixFactoryService.create(null);
		});
	}

	@Test
	public void whenMultipartFileIsEmpty_thenBadContentException() {
		MockMultipartFile mockMultipartFile;

		mockMultipartFile = new MockMultipartFile("file.csv", "".getBytes());

		Assertions.assertThrows(BadContentException.class, () -> {
			matrixFactoryService.create(mockMultipartFile);
		});
	}

	@Test
	public void whenMultipartFileContentNotInteger_thenBadContentException() {
		MockMultipartFile mockMultipartFile;

		mockMultipartFile = new MockMultipartFile("file.csv", "1,a\n2,b".getBytes());

		Assertions.assertThrows(BadContentException.class, () -> {
			matrixFactoryService.create(mockMultipartFile);
		});
	}

	@Test
	public void whenMultipartFileContentOneOfValueLengthTooBig_thenBadContentException() {
		MockMultipartFile mockMultipartFile;

		mockMultipartFile = new MockMultipartFile("file.csv", "21474836470".getBytes());

		Assertions.assertThrows(BadContentException.class, () -> {
			matrixFactoryService.create(mockMultipartFile);
		});
	}

	@Test
	public void whenMultipartFileContentOneOfValueTooBig_thenBadContentException() {
		MockMultipartFile mockMultipartFile;

		mockMultipartFile = new MockMultipartFile("file.csv", "2147483648".getBytes());

		Assertions.assertThrows(BadContentException.class, () -> {
			matrixFactoryService.create(mockMultipartFile);
		});
	}

	@Test
	public void whenMultipartFileContentSingleValueMaximum() {
		MockMultipartFile mockMultipartFile;

		mockMultipartFile = new MockMultipartFile("file.csv", "2147483647".getBytes());

		List<List<Integer>> expectedMatrix;

		expectedMatrix = matrixGenerator("" + Integer.MAX_VALUE);

		try {
			Assertions.assertEquals(expectedMatrix, matrixFactoryService.create(mockMultipartFile));
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenMultipartFileHasSingleColumnAndSingleLine() {
		MockMultipartFile mockMultipartFile;

		mockMultipartFile = new MockMultipartFile("file.csv", "1".getBytes());

		List<List<Integer>> expectedMatrix;

		List<Integer> expectedLine;

		expectedMatrix = new ArrayList<List<Integer>>();

		expectedLine = new ArrayList<>();
		expectedLine.add(1);

		expectedMatrix.add(expectedLine);

		try {
			Assertions.assertEquals(expectedMatrix, matrixFactoryService.create(mockMultipartFile));
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail(exception);
		}
	}

	@Test
	public void whenMultipartFileHasThreeColumnAndThreeLine() {
		MockMultipartFile mockMultipartFile;

		mockMultipartFile = new MockMultipartFile("file.csv", "1,2,3\n4,5,6\n7,8,9".getBytes());

		List<List<Integer>> expectedMatrix;

		expectedMatrix = matrixGenerator("1 2 3  4 5 6  7 8 9");

		List<List<Integer>> parsedMatrix;

		parsedMatrix = null;

		try {
			parsedMatrix = matrixFactoryService.create(mockMultipartFile);
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail(exception);
		}

		Assertions.assertEquals(expectedMatrix, parsedMatrix);
	}

}
