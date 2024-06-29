package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.courier.CourierRequest;
import by.tms.pizzaapp.entity.courier.Courier;
import by.tms.pizzaapp.service.courier.CourierService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/couriers")
@Tag(name = "Courier Controller", description = "Endpoints for managing courier controller")
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCourier(@RequestBody CourierRequest courierRequest) {
        log.info("Registering new courier: {}", courierRequest.getLogin());
        courierService.registrationCourier(courierRequest);
    }

    @PostMapping("/login")
    public Courier loginCourier(@RequestBody CourierRequest courierRequest) {
        log.info("Courier logging in: {}", courierRequest.getLogin());
        return courierService.loginCourier(courierRequest.getLogin(), courierRequest.getPassword());
    }

    @PostMapping("/deliver/{orderId}")
    public void deliverOrder(@PathVariable Long orderId, @RequestParam Long courierId) {
        log.info("Courier {} delivering order {}", courierId, orderId);
        courierService.deliverOrder(orderId, courierId);
    }
}