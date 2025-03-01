package by.tms.pizzaapp.service.promo;

import by.tms.pizzaapp.dto.promo.*;

public interface PromoService {
    PromoResponse addPromo(PromoRequest promoRequest);

    void deletePromo(Long id);

    boolean isPromoValid(String name);
}