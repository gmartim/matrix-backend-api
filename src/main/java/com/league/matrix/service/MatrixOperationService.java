package com.league.matrix.service;

import org.springframework.web.multipart.MultipartFile;

import com.league.matrix.exception.BadContentException;
import com.league.matrix.exception.InternalErrorException;
import com.league.matrix.exception.NotSquareMatrixException;

public interface MatrixOperationService {

	/**
	 * Operates the multipart file converting the content to a matrix of integers
	 * operating additional actions.
	 * 
	 * @param multipartFile the multipart file containing the integers
	 * 
	 * @return the operated value for the matrix
	 * 
	 * @throws NotSquareMatrixException
	 * @throws BadContentException
	 * @throws InternalErrorException
	 */
	public String run(MultipartFile multipartFile)
			throws NotSquareMatrixException, BadContentException, InternalErrorException;

}
