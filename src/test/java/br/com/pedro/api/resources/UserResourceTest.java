package br.com.pedro.api.resources;

import br.com.pedro.api.domain.User;
import br.com.pedro.api.domain.UserDTO;
import br.com.pedro.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

class UserResourceTest {

    @InjectMocks
    UserResource resource;

    @Mock
    UserServiceImpl service;

    @Mock
    ModelMapper mapper;

    public static final Integer ID = 1;
    public static final String PEDRO = "Pedro";
    public static final String EMAIL = "pedro@email.com";
    public static final String PASSWORD = "123";

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp(){
        openMocks(this);
        user = new User(ID, PEDRO, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, PEDRO, EMAIL, PASSWORD);
        var mockRequest = new MockHttpServletRequest();
        var attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    void findByIdTest() {
        when(service.findById(anyInt())).thenReturn(user);
        assertNotNull(resource.findById(anyInt()));
    }

    @Test
    void findAllTest() {
        when(service.findAll()).thenReturn(List.of(user));
        assertNotNull(resource.findAll());
    }

    @Test
    void createTest() {
        when(service.create(any(UserDTO.class))).thenReturn(user);
        var response = resource.create(userDTO);
        assertEquals(CREATED, response.getStatusCode());
    }

    @Test
    void updateTest() {
        when(service.update(any(UserDTO.class))).thenReturn(user);
        assertNotNull(resource.update(anyInt(), userDTO));
    }

    @Test
    void deleteTest() {
        var response = resource.delete(anyInt());
        assertEquals(NO_CONTENT, response.getStatusCode());
    }
}