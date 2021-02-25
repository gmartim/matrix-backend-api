package com.league.matrix.operation.multiply;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.league.matrix.Constant;
import com.league.matrix.exception.BadContentException;
import com.league.matrix.exception.InternalErrorException;
import com.league.matrix.service.MatrixOperationService;

@WebMvcTest(controllers = { MatrixMultiplyController.class })
public class MatrixMultiplyControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean(name = "matrixMultiplyService")
	private MatrixOperationService matrixMultiplyService;

	@Test
	public void whenRequestIsValid() {
		try {
			BDDMockito.given(matrixMultiplyService.run(BDDMockito.any())).willReturn("362880");
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}

		MockMultipartHttpServletRequestBuilder mockHttpServletRequestBuilder;

		ResultActions resultActions;

		mockHttpServletRequestBuilder = MockMvcRequestBuilders.multipart("/multiply");
		mockHttpServletRequestBuilder = mockHttpServletRequestBuilder
				.file(new MockMultipartFile("file", "1,2,3\n4,5,6\n7,8,9".getBytes()));

		try {
			resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
			resultActions.andDo(MockMvcResultHandlers.print());
			resultActions.andExpect(MockMvcResultMatchers.status().isOk());
			resultActions.andExpect(MockMvcResultMatchers.content().string("362880"));
		} catch (Exception exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenFileIsEmpty() {
		try {
			BDDMockito.given(matrixMultiplyService.run(BDDMockito.any()))
					.willThrow(new BadContentException(Constant.BAD_CONTENT_MESSAGE_NULL_OR_EMPTY));
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}

		MockMultipartHttpServletRequestBuilder mockHttpServletRequestBuilder;

		ResultActions resultActions;

		mockHttpServletRequestBuilder = MockMvcRequestBuilders.multipart("/multiply");
		mockHttpServletRequestBuilder = mockHttpServletRequestBuilder
				.file(new MockMultipartFile("file", "".getBytes()));

		try {
			resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
			resultActions.andDo(MockMvcResultHandlers.print());
			resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
			resultActions.andExpect(MockMvcResultMatchers.content().string(Constant.BAD_CONTENT_MESSAGE_NULL_OR_EMPTY));
		} catch (Exception exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenAnInternalError() {
		try {
			BDDMockito.given(matrixMultiplyService.run(BDDMockito.any())).willThrow(new InternalErrorException(
					Constant.INTERNAL_ERRROR_MESSAGE_ERROR_PROCESSING_CONTENT, new Exception()));
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}

		MockMultipartHttpServletRequestBuilder mockHttpServletRequestBuilder;

		ResultActions resultActions;

		mockHttpServletRequestBuilder = MockMvcRequestBuilders.multipart("/multiply");
		mockHttpServletRequestBuilder = mockHttpServletRequestBuilder
				.file(new MockMultipartFile("file", "".getBytes()));

		try {
			resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
			resultActions.andDo(MockMvcResultHandlers.print());
			resultActions.andExpect(MockMvcResultMatchers.status().isInternalServerError());
			resultActions.andExpect(
					MockMvcResultMatchers.content().string(Constant.INTERNAL_ERRROR_MESSAGE_ERROR_PROCESSING_CONTENT));
		} catch (Exception exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenNotIntegerContent() {
		try {
			BDDMockito.given(matrixMultiplyService.run(BDDMockito.any()))
					.willThrow(new BadContentException(Constant.BAD_CONTENT_MESSAGE_NOT_INTEGER_VALUE));
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}

		MockMultipartHttpServletRequestBuilder mockHttpServletRequestBuilder;

		ResultActions resultActions;

		mockHttpServletRequestBuilder = MockMvcRequestBuilders.multipart("/multiply");
		mockHttpServletRequestBuilder = mockHttpServletRequestBuilder
				.file(new MockMultipartFile("file", "1,a\n3,b".getBytes()));

		try {
			resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
			resultActions.andDo(MockMvcResultHandlers.print());
			resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
			resultActions
					.andExpect(MockMvcResultMatchers.content().string(Constant.BAD_CONTENT_MESSAGE_NOT_INTEGER_VALUE));
		} catch (Exception exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenNullMatrix() {
		try {
			BDDMockito.given(matrixMultiplyService.run(BDDMockito.any()))
					.willThrow(new BadContentException(Constant.BAD_CONTENT_MESSAGE_NULL_MATRIX));
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}

		MockMultipartHttpServletRequestBuilder mockHttpServletRequestBuilder;

		ResultActions resultActions;

		mockHttpServletRequestBuilder = MockMvcRequestBuilders.multipart("/multiply");
		mockHttpServletRequestBuilder = mockHttpServletRequestBuilder
				.file(new MockMultipartFile("file", "".getBytes()));

		try {
			resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
			resultActions.andDo(MockMvcResultHandlers.print());
			resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
			resultActions.andExpect(MockMvcResultMatchers.content().string(Constant.BAD_CONTENT_MESSAGE_NULL_MATRIX));
		} catch (Exception exception) {
			Assertions.fail();
		}
	}

	@Test
	public void whenNotSquareMatrix() {
		try {
			BDDMockito.given(matrixMultiplyService.run(BDDMockito.any()))
					.willThrow(new BadContentException(Constant.NOT_SQUARE_MATRIX_MESSAGE_NOT_SQUARE_MATRIX));
		} catch (InternalErrorException | BadContentException exception) {
			Assertions.fail();
		}

		MockMultipartHttpServletRequestBuilder mockHttpServletRequestBuilder;

		ResultActions resultActions;

		mockHttpServletRequestBuilder = MockMvcRequestBuilders.multipart("/multiply");
		mockHttpServletRequestBuilder = mockHttpServletRequestBuilder
				.file(new MockMultipartFile("file", "".getBytes()));

		try {
			resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
			resultActions.andDo(MockMvcResultHandlers.print());
			resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
			resultActions.andExpect(
					MockMvcResultMatchers.content().string(Constant.NOT_SQUARE_MATRIX_MESSAGE_NOT_SQUARE_MATRIX));
		} catch (Exception exception) {
			Assertions.fail();
		}
	}

}
