package br.com.pedro.api.services.impl;

import br.com.pedro.api.domain.User;
import br.com.pedro.api.domain.UserDTO;
import br.com.pedro.api.repository.UserRepository;
import br.com.pedro.api.services.exceptions.DataIntegratyViolationException;
import br.com.pedro.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    public static final Integer ID = 1;
    public static final String PEDRO = "Pedro";
    public static final String EMAIL = "pedro@email.com";
    public static final String PASSWORD = "123";

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        user = new User(ID, PEDRO, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, PEDRO, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, PEDRO, EMAIL, PASSWORD));
    }

    @Test
    void findByIdTest() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        User response = service.findById(ID);
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
    }

    @Test
    void findByIdFailedTest(){
        assertThrows(ObjectNotFoundException.class, () -> service.findById(anyInt()));
    }

    @Test
    void createTest() {
        when(repository.save(mapper.map(any(), eq(User.class)))).thenReturn(user);
        var response = service.create(userDTO);
        assertNotNull(response);
    }

    @Test
    void createFailedTest(){
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);
        optionalUser.get().setId(2);
        assertThrows(DataIntegratyViolationException.class, () -> service.create(userDTO));
    }

    @Test
    void updateTest() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        when(repository.save(mapper.map(any(), eq(User.class)))).thenReturn(user);
        assertNotNull(service.update(userDTO));
    }

    @Test
    void updateFailedTest(){
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);
        optionalUser.get().setId(2);
        assertThrows(DataIntegratyViolationException.class, () -> service.update(userDTO));
    }

    @Test
    void deleteTest(){
       when(repository.findById(anyInt())).thenReturn(optionalUser);
       doNothing().when(repository).deleteById(anyInt());
       service.delete(ID);
       verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteFailedTest(){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
        assertThrows(ObjectNotFoundException.class, () -> service.delete(ID));
    }

    @Test
    void findAllTest() {
        when(repository.findAll()).thenReturn(List.of(user));
        var response = service.findAll();
        assertNotNull(response);
        assertEquals(User.class, response.get(0).getClass());
    }
}