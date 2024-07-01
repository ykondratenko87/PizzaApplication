package service;

import by.tms.pizzaapp.dto.courier.CourierRequest;
import by.tms.pizzaapp.entity.courier.Courier;
import by.tms.pizzaapp.entity.order.*;
import by.tms.pizzaapp.entity.order.Order;
import by.tms.pizzaapp.exception.ApplicationExceptions;
import by.tms.pizzaapp.mapper.CourierMapper;
import by.tms.pizzaapp.repository.*;
import by.tms.pizzaapp.service.courier.CourierServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourierServiceImplTest {

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private CourierMapper courierMapper;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CourierServiceImpl courierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrationCourier_ShouldSaveCourier_WhenLoginDoesNotExist() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin("testLogin");
        Courier courier = new Courier();
        when(courierRepository.findByLogin("testLogin")).thenReturn(Optional.empty());
        when(courierMapper.toEntity(courierRequest)).thenReturn(courier);

        courierService.registrationCourier(courierRequest);

        verify(courierRepository).save(courier);
    }

    @Test
    void registrationCourier_ShouldThrowException_WhenLoginExists() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin("testLogin");
        when(courierRepository.findByLogin("testLogin")).thenReturn(Optional.of(new Courier()));

        assertThrows(ApplicationExceptions.CourierAlreadyExistsException.class, () ->
                courierService.registrationCourier(courierRequest));
    }

    @Test
    void loginCourier_ShouldReturnCourier_WhenCredentialsAreCorrect() {
        String login = "testLogin";
        String password = "testPassword";
        Courier courier = new Courier();
        courier.setPassword(password);
        when(courierRepository.findByLogin(login)).thenReturn(Optional.of(courier));

        Courier result = courierService.loginCourier(login, password);

        assertEquals(courier, result);
    }

    @Test
    void loginCourier_ShouldThrowException_WhenCredentialsAreInvalid() {
        String login = "testLogin";
        String password = "testPassword";
        when(courierRepository.findByLogin(login)).thenReturn(Optional.empty());

        assertThrows(ApplicationExceptions.CourierNotFoundException.class, () ->
                courierService.loginCourier(login, password));
    }

    @Test
    void deliverOrder_ShouldUpdateOrderStatusToCompleted_WhenOrderAndCourierExist() {
        Long orderId = 1L;
        Long courierId = 1L;
        Courier courier = new Courier();
        Order order = new Order();
        when(courierRepository.findById(courierId)).thenReturn(Optional.of(courier));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        courierService.deliverOrder(orderId, courierId);

        assertEquals(OrderStatus.COMPLETED, order.getStatus());
        assertEquals(courier, order.getCourier());
        verify(orderRepository).save(order);
    }

    @Test
    void deliverOrder_ShouldThrowException_WhenCourierDoesNotExist() {
        Long orderId = 1L;
        Long courierId = 1L;
        when(courierRepository.findById(courierId)).thenReturn(Optional.empty());

        assertThrows(ApplicationExceptions.CourierNotFoundException.class, () ->
                courierService.deliverOrder(orderId, courierId));
    }

    @Test
    void deliverOrder_ShouldThrowException_WhenOrderDoesNotExist() {
        Long orderId = 1L;
        Long courierId = 1L;
        Courier courier = new Courier();
        when(courierRepository.findById(courierId)).thenReturn(Optional.of(courier));
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ApplicationExceptions.OrderNotFoundException.class, () ->
                courierService.deliverOrder(orderId, courierId));
    }
}