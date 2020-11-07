package com.simpragma.assignment;

import java.util.Arrays;
import java.util.Random;
import javax.annotation.PostConstruct;

import com.simpragma.assignment.configuration.SwaggerConfiguration;
import com.simpragma.assignment.model.Trade;
import com.simpragma.assignment.model.User;
import com.simpragma.assignment.service.TradeService;
import com.simpragma.assignment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Import({SwaggerConfiguration.class})
public class AssignmentApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentApplication.class);

    private final UserService userService;
    private final TradeService tradeService;

    public AssignmentApplication(final UserService userService,
            final TradeService tradeService) {
        this.userService = userService;
        this.tradeService = tradeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

    @PostConstruct
    public void loadData() {
        User user1 = userService.saveUser(new User("User1"));
        tradeService.save(createTrade(user1, "A1", 1L));
        tradeService.save(createTrade(user1, "A2", 2L));
        tradeService.save(createTrade(user1, "A3", 3L));

        User user2 = userService.saveUser(new User("User2"));
        tradeService.save(createTrade(user2, "A1", 4L));
        tradeService.save(createTrade(user2, "A2", 5L));
        tradeService.save(createTrade(user2, "A3", 6L));

        User user3 = userService.saveUser(new User("User3"));
        tradeService.save(createTrade(user3, "A1", 7L));
        tradeService.save(createTrade(user3, "A2", 8L));
        tradeService.save(createTrade(user3, "A3", 9L));
    }

    private Trade createTrade(final User user, final String symbol, final Long id) {



        Trade trade = new Trade();
        trade.setId(id);
        trade.setPrice(Arrays.asList(140, 146, 150, 189, 187).get(new Random().nextInt(5)));
        trade.setShares(20);
        trade.setSymbol(symbol);
        trade.setType("buy");
        trade.setUser(user);
        return trade;
    }


}
