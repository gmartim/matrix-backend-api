package com.league.matrix.service.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.league.matrix.Constant;
import com.league.matrix.exception.BadContentException;
import com.league.matrix.exception.InternalErrorException;
import com.opencsv.CSVReader;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatrixCreateService {

	/**
	 * Creates a matrix reading the content from a multipart file. The file should
	 * contains just integers split by comma (",") for columns and new line ("\n")
	 * for rows. The maximum value for the integer is 2147483647.
	 * 
	 * @param multipartFile the multipart file containing the integers
	 * 
	 * @return the matrix
	 * 
	 * @throws InternalErrorException when an internal error occurs while processing
	 *                                multipart file's content
	 * 
	 * @throws BadContentException    when multipart file is null or empty, when a
	 *                                non integer value is found or an integer
	 *                                greater then 2147483647
	 */
	public List<List<Integer>> create(MultipartFile multipartFile) throws InternalErrorException, BadContentException {
		log.info("Started creating the matrix from a multpart file");

		if (multipartFile == null || multipartFile.isEmpty()) {
			log.error(Constant.BAD_CONTENT_MESSAGE_NULL_OR_EMPTY);

			throw new BadContentException(Constant.BAD_CONTENT_MESSAGE_NULL_OR_EMPTY);
		}

		List<List<Integer>> matrix;

		matrix = new ArrayList<>();

		try (Reader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
			CSVReader csvReader;

			csvReader = new CSVReader(reader);

			csvReader.forEach(new Consumer<String[]>() {

				@Override
				public void accept(String values[]) {
					List<Integer> row;

					row = new ArrayList<>();

					int column;

					for (String value : values) {
						if (value.length() > 11) {
							throw new IllegalArgumentException(
									String.format(Constant.BAD_CONTENT_MESSAGE_HIGH_VALUE_LENGTH, value));
						}

						if (Long.parseLong(value) > Integer.MAX_VALUE) {
							throw new IllegalArgumentException(
									String.format(Constant.BAD_CONTENT_MESSAGE_VALUE_TOO_HIGH, value));
						}

						column = Integer.parseInt(value);

						row.add(column);
					}

					matrix.add(row);
				}

			});

			csvReader.close();
		} catch (IOException exception) {
			log.error(Constant.INTERNAL_ERRROR_MESSAGE_ERROR_PROCESSING_CONTENT, exception);

			throw new InternalErrorException(Constant.INTERNAL_ERRROR_MESSAGE_ERROR_PROCESSING_CONTENT, exception);
		} catch (NumberFormatException exception) {
			log.error(Constant.BAD_CONTENT_MESSAGE_NOT_INTEGER_VALUE, exception);

			throw new BadContentException(Constant.BAD_CONTENT_MESSAGE_NOT_INTEGER_VALUE, exception);
		} catch (IllegalArgumentException exception) {
			log.error(exception.getMessage());

			throw new BadContentException(exception.getMessage());
		}

		log.info("Created the matrix, matrix.size: {}", matrix.size());

		return matrix;
	}

}
