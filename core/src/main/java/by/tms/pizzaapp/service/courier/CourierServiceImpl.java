package by.tms.pizzaapp.service.courier;

import by.tms.pizzaapp.dto.courier.CourierRequest;
import by.tms.pizzaapp.entity.courier.Courier;
import by.tms.pizzaapp.entity.order.Order;
import by.tms.pizzaapp.entity.order.OrderStatus;
import by.tms.pizzaapp.exception.CourierAlreadyExistsException;
import by.tms.pizzaapp.exception.CourierNotFoundException;
import by.tms.pizzaapp.exception.OrderNotFoundException;
import by.tms.pizzaapp.mapper.CourierMapper;
import by.tms.pizzaapp.repository.CourierRepository;
import by.tms.pizzaapp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final CourierRepository courierRepository;
    private final CourierMapper courierMapper;
    private final OrderRepository orderRepository;

    @Override
    public void registrationCourier(CourierRequest courierRequest) {
        // Проверяем, существует ли курьер с таким логином
        if (courierRepository.findByLogin(courierRequest.getLogin()).isPresent()) {
            throw new CourierAlreadyExistsException("Courier with login " + courierRequest.getLogin() + " already exists");
        }

        // Маппим CourierRequest в Courier и сохраняем
        Courier courier = courierMapper.toEntity(courierRequest);
        courierRepository.save(courier);
    }

    @Override
    public Courier loginCourier(String login, String password) {
        return courierRepository.findByLogin(login)
                .filter(courier -> courier.getPassword().equals(password))
                .orElseThrow(() -> new CourierNotFoundException("Courier not found or invalid credentials"));
    }

    @Override
    public void deliverOrder(Long orderId, Long courierId) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new CourierNotFoundException("Courier not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        order.setStatus(OrderStatus.COMPLETED);
        order.setCourier(courier);
        orderRepository.save(order);
    }
}