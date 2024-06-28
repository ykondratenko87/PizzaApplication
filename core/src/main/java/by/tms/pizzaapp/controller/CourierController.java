package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.courier.CourierRequest;
import by.tms.pizzaapp.entity.courier.Courier;
import by.tms.pizzaapp.service.courier.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/couriers")
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCourier(@RequestBody CourierRequest courierRequest) {
        courierService.registrationCourier(courierRequest);
    }

    @PostMapping("/login")
    public Courier loginCourier(@RequestBody CourierRequest courierRequest) {
        return courierService.loginCourier(courierRequest.getLogin(), courierRequest.getPassword());
    }

    @PostMapping("/deliver/{orderId}")
    public void deliverOrder(@PathVariable Long orderId, @RequestParam Long courierId) {
        courierService.deliverOrder(orderId, courierId);
    }
}