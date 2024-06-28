package by.tms.pizzaapp.scheduler;

import by.tms.pizzaapp.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {
    private final OrderService orderService;

    @Scheduled(cron = "0 0 0 * * ?") // Этот cron выражение запускает задачу каждый день в полночь
    public void clearOrdersAndBaskets() {
        log.info("Scheduled task to clear orders 'ORDERING' and baskets started");
        orderService.clearOrdersAndBaskets();
        log.info("Scheduled task to clear orders 'ORDERING' and baskets completed");
    }
}