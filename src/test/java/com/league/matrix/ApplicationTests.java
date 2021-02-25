package com.league.matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.league.matrix.operation.echo.MatrixEchoController;
import com.league.matrix.operation.echo.MatrixEchoService;
import com.league.matrix.operation.flatten.MatrixFlattenController;
import com.league.matrix.operation.flatten.MatrixFlattenService;
import com.league.matrix.operation.invert.MatrixInvertController;
import com.league.matrix.operation.invert.MatrixInvertService;
import com.league.matrix.operation.multiply.MatrixMultiplyController;
import com.league.matrix.operation.multiply.MatrixMultiplyService;
import com.league.matrix.operation.sum.MatrixSumController;
import com.league.matrix.operation.sum.MatrixSumService;
import com.league.matrix.service.util.MatrixActService;
import com.league.matrix.service.util.MatrixCreateService;
import com.league.matrix.service.util.MatrixPrintService;

@WebMvcTest(controllers = { MatrixEchoController.class, MatrixFlattenController.class, MatrixInvertController.class,
		MatrixMultiplyController.class, MatrixSumController.class })
@Import({ MatrixActService.class, MatrixCreateService.class, MatrixPrintService.class, MatrixEchoService.class,
		MatrixFlattenService.class, MatrixInvertService.class, MatrixMultiplyService.class, MatrixSumService.class })
class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void echo() {
		MockMultipartHttpServletRequestBuilder mockHttpServletRequestBuilder;

		ResultActions resultActions;

		mockHttpServletRequestBuilder = MockMvcRequestBuilders.multipart("/echo");
		mockHttpServletRequestBuilder = mockHttpServletRequestBuilder
				.file(new MockMultipartFile("file", "1,2,3\n4,5,6\n7,8,9".getBytes()));

		try {
			resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
			resultActions.andDo(MockMvcResultHandlers.print());
			resultActions.andExpect(MockMvcResultMatchers.status().isOk());
			resultActions.andExpect(MockMvcResultMatchers.content().string("1,2,3\n4,5,6\n7,8,9"));
		} catch (Exception exception) {
			Assertions.fail();
		}
	}

	@Test
	public void flatten() {
		MockMultipartHttpServletRequestBuilder mockHttpServletRequestBuilder;

		ResultActions resultActions;

		mockHttpServletRequestBuilder = MockMvcRequestBuilders.multipart("/flatten");
		mockHttpServletRequestBuilder = mockHttpServletRequestBuilder
				.file(new MockMultipartFile("file", "1,2,3\n4,5,6\n7,8,9".getBytes()));

		try {
			resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
			resultActions.andDo(MockMvcResultHandlers.print());
			resultActions.andExpect(MockMvcResultMatchers.status().isOk());
			resultActions.andExpect(MockMvcResultMatchers.content().string("1,2,3,4,5,6,7,8,9"));
		} catch (Exception exception) {
			Assertions.fail();
		}
	}

	@Test
	public void invert() {
		MockMultipartHttpServletRequestBuilder mockHttpServletRequestBuilder;

		ResultActions resultActions;

		mockHttpServletRequestBuilder = MockMvcRequestBuilders.multipart("/invert");
		mockHttpServletRequestBuilder = mockHttpServletRequestBuilder
				.file(new MockMultipartFile("file", "1,2,3\n4,5,6\n7,8,9".getBytes()));

		try {
			resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
			resultActions.andDo(MockMvcResultHandlers.print());
			resultActions.andExpect(MockMvcResultMatchers.status().isOk());
			resultActions.andExpect(MockMvcResultMatchers.content().string("1,4,7\n2,5,8\n3,6,9"));
		} catch (Exception exception) {
			Assertions.fail();
		}
	}

	@Test
	public void multiply() {
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
	public void sum() {
		MockMultipartHttpServletRequestBuilder mockHttpServletRequestBuilder;

		ResultActions resultActions;

		mockHttpServletRequestBuilder = MockMvcRequestBuilders.multipart("/sum");
		mockHttpServletRequestBuilder = mockHttpServletRequestBuilder
				.file(new MockMultipartFile("file", "1,2,3\n4,5,6\n7,8,9".getBytes()));

		try {
			resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
			resultActions.andDo(MockMvcResultHandlers.print());
			resultActions.andExpect(MockMvcResultMatchers.status().isOk());
			resultActions.andExpect(MockMvcResultMatchers.content().string("45"));
		} catch (Exception exception) {
			Assertions.fail();
		}
	}

}
