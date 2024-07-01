package repository;

import by.tms.pizzaapp.entity.promo.Promo;
import by.tms.pizzaapp.repository.PromoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromoRepositoryTest {

    @Mock
    private PromoRepository promoRepository;

    @Test
    public void testFindByName() {
        String promoName = "SUMMER10";
        Promo promo = new Promo();
        promo.setId(1L);
        promo.setName(promoName);

        when(promoRepository.findByName(promoName)).thenReturn(Optional.of(promo));

        Optional<Promo> foundPromo = promoRepository.findByName(promoName);

        assertTrue(foundPromo.isPresent());
        assertEquals(promoName, foundPromo.get().getName());
    }

    @Test
    public void testExistsByName() {
        String promoName = "WINTER20";

        when(promoRepository.existsByName(promoName)).thenReturn(true);

        boolean exists = promoRepository.existsByName(promoName);

        assertTrue(exists);
    }

    @Test
    public void testSavePromo() {
        Promo promo = new Promo();
        promo.setId(1L);
        promo.setName("AUTUMN30");

        when(promoRepository.save(promo)).thenReturn(promo);

        Promo savedPromo = promoRepository.save(promo);

        assertNotNull(savedPromo);
        assertEquals(promo.getName(), savedPromo.getName());
    }
}