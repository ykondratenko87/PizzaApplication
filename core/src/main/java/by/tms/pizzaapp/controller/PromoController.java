package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.promo.PromoRequest;
import by.tms.pizzaapp.dto.promo.PromoResponse;
import by.tms.pizzaapp.service.promo.PromoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/promos")
@Tag(name = "Promo Controller")
@RequiredArgsConstructor
public class PromoController {
    private final PromoService promoService;

    @Operation(summary = "Add promo", description = "Добавляет промокод")
    @PostMapping
    public PromoResponse addPromo(@Valid @RequestBody PromoRequest promoRequest) {
        log.info("Add promo: {}", promoRequest);
        return promoService.addPromo(promoRequest);
    }
    @Operation(summary = "Delete promo", description = "Удаляет промокод")
    @DeleteMapping("/{id}")
    public void deletePromo(@PathVariable Long id) {
        log.info("Delete promo with id: {}", id);
        promoService.deletePromo(id);
    }
}