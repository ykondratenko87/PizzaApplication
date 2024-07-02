package by.tms.pizzaapp.service.promo;

import by.tms.pizzaapp.dto.promo.*;
import by.tms.pizzaapp.entity.promo.Promo;
import by.tms.pizzaapp.mapper.PromoMapper;
import by.tms.pizzaapp.repository.PromoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class PromoServiceImpl implements PromoService {
    private final PromoRepository promoRepository;
    private final PromoMapper promoMapper;

    @Override
    @CachePut(value = "promos", key = "#promoRequest.name")
    public PromoResponse addPromo(PromoRequest promoRequest) {
        Promo promo = promoMapper.toEntity(promoRequest);
        Promo savedPromo = promoRepository.save(promo);
        return promoMapper.toResponse(savedPromo);
    }

    @Override
    @CacheEvict(value = "promos", allEntries = true)
    public void deletePromo(Long id) {
        if (!promoRepository.existsById(id)) {
            throw new NoSuchElementException("Promo not found");
        }
        promoRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "promos", key = "#name")
    public boolean isPromoValid(String name) {
        return promoRepository.findByName(name).isPresent();
    }
}