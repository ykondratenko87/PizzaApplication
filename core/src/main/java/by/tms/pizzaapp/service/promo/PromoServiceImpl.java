package by.tms.pizzaapp.service.promo;

import by.tms.pizzaapp.dto.promo.PromoRequest;
import by.tms.pizzaapp.dto.promo.PromoResponse;
import by.tms.pizzaapp.entity.promo.Promo;
import by.tms.pizzaapp.mapper.PromoMapper;
import by.tms.pizzaapp.repository.PromoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PromoServiceImpl implements PromoService {
    private final PromoRepository promoRepository;
    private final PromoMapper promoMapper;

    @Override
    public PromoResponse addPromo(PromoRequest promoRequest) {
        Promo promo = promoMapper.toEntity(promoRequest);
        Promo savedPromo = promoRepository.save(promo);
        return promoMapper.toResponse(savedPromo);
    }

    @Override
    public void deletePromo(Long id) {
        if (!promoRepository.existsById(id)) {
            throw new NoSuchElementException("Promo not found");
        }
        promoRepository.deleteById(id);
    }

    @Override
    public boolean isPromoValid(String name) {
        return promoRepository.findByName(name).isPresent();
    }
}