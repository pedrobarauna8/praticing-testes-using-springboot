package br.com.pedro.api.resources.exceptions;

import br.com.pedro.api.services.exceptions.DataIntegratyViolationException;
import br.com.pedro.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void objectNotFoundExceptionTest() {
        var response = exceptionHandler
                .objectNotFound(
                        new ObjectNotFoundException(""),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    void dataIntegrityViolationExceptionTest(){
        var response = exceptionHandler
                .dataIntegrityViolationException(
                        new DataIntegratyViolationException(""),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }
}