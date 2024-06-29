package by.tms.pizzaapp.service.courier;

import by.tms.pizzaapp.dto.courier.CourierRequest;
import by.tms.pizzaapp.entity.courier.Courier;

public interface CourierService {
    void registrationCourier(CourierRequest courierRequest);

    Courier loginCourier(String login, String password);

    void deliverOrder(Long orderId, Long courierId);
}