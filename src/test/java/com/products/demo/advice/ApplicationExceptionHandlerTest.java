package com.products.demo.advice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.products.demo.exception.SimilarProductsNotFoundException;

class ApplicationExceptionHandlerTest {
	
	@InjectMocks
	private ApplicationExceptionHandler applicationExceptionHandler = new ApplicationExceptionHandler();
	
	Map<String, String> errorMapResult;
	
	@BeforeEach
	void setUp() {
		errorMapResult = new HashMap<>();
	}

	@Test
	void mapResponseTest() throws Exception {
		// prepare
		Map<String, String> expectedResult = new HashMap<String, String>();
		expectedResult.put("errorMessage", "Test Exception");
		
		// execute
		errorMapResult = applicationExceptionHandler.mapResponse(new Exception("Test Exception"));
		
		// assert
		assertNotNull(errorMapResult);
		assertThat(expectedResult).usingRecursiveComparison().isEqualTo(errorMapResult);
		
	}
	
	@Test
	void NotFoundErrorTest() throws HttpClientErrorException {
		// prepare
		Map<String, String> expectedResult = new HashMap<String, String>();
		expectedResult.put("errorMessage", "404 Not Found");		
		
		HttpClientErrorException.NotFound exception = (NotFound) HttpClientErrorException.NotFound.create(HttpStatus.NOT_FOUND, "Not Found", null, null, null);
		
		// execute
		errorMapResult = applicationExceptionHandler.handleBusinessException(exception);
		
		// assert
		assertNotNull(errorMapResult);
		assertThat(expectedResult).usingRecursiveComparison().isEqualTo(errorMapResult);
		
	}
	
	@Test
	void SimilarProductsNotFoundErrorTest() throws SimilarProductsNotFoundException {
		// prepare
		Map<String, String> expectedResult = new HashMap<String, String>();
		expectedResult.put("errorMessage", "Similar Products Not Found");
		
		// execute
		errorMapResult = applicationExceptionHandler.handleBusinessException(new SimilarProductsNotFoundException("Similar Products Not Found"));
		
		// assert
		assertNotNull(errorMapResult);
		assertThat(expectedResult).usingRecursiveComparison().isEqualTo(errorMapResult);
		
	}
	
	@Test
	void HandleExpectionErrorTest() throws Exception {
		// prepare
		Map<String, String> expectedResult = new HashMap<String, String>();
		expectedResult.put("errorMessage", "Internal Server Error");
		
		// execute
		errorMapResult = applicationExceptionHandler.handleException(new Exception("Internal Server Error"));
		
		// assert
		assertNotNull(errorMapResult);
		assertThat(expectedResult).usingRecursiveComparison().isEqualTo(errorMapResult);
		
	}

}
