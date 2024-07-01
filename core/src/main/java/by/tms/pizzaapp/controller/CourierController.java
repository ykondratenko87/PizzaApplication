package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.courier.CourierRequest;
import by.tms.pizzaapp.entity.courier.Courier;
import by.tms.pizzaapp.service.courier.CourierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/couriers")
@Tag(name = "Courier Controller", description = "Endpoints for managing courier controller")
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;

    @Operation(summary = "Register courier", description = "Регистрация курьера")
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("courierRequest", new CourierRequest());
        return "courier/register";
    }

    @PostMapping("/register")
    public String registerCourier(@ModelAttribute CourierRequest courierRequest, Model model) {
        log.info("Registering new courier: {}", courierRequest.getLogin());
        courierService.registrationCourier(courierRequest);
        model.addAttribute("message", "Courier registered successfully");
        return "courier/register";
    }

    @Operation(summary = "Login courier", description = "Авторизация курьера")
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("courierRequest", new CourierRequest());
        return "courier/login";
    }

    @PostMapping("/login")
    public String loginCourier(@ModelAttribute CourierRequest courierRequest, Model model) {
        log.info("Courier logging in: {}", courierRequest.getLogin());
        Courier courier = courierService.loginCourier(courierRequest.getLogin(), courierRequest.getPassword());
        model.addAttribute("courier", courier);
        return "courier/login";
    }

    @Operation(summary = "Deliver order", description = "Заказ доставлен")
    @GetMapping("/deliver")
    public String showDeliverPage() {
        return "courier/deliver";
    }

    @PostMapping("/deliver")
    public String deliverOrder(@RequestParam Long orderId, @RequestParam Long courierId, Model model) {
        log.info("Courier {} delivering order {}", courierId, orderId);
        courierService.deliverOrder(orderId, courierId);
        model.addAttribute("message", "Order delivered successfully");
        return "courier/deliver";
    }
}