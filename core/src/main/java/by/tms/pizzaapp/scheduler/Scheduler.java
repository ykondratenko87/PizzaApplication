package by.tms.pizzaapp.scheduler;

import by.tms.pizzaapp.service.order.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Tag(name = "Scheduler", description = "Clear 'ordering' orders and baskets at 00:00")
@RequiredArgsConstructor
public class Scheduler {
    private final OrderService orderService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void clearOrdersAndBaskets() {
        log.info("Scheduled task to clear orders 'ORDERING' and baskets started");
        orderService.clearOrdersAndBaskets();
        log.info("Scheduled task to clear orders 'ORDERING' and baskets completed");
    }
}