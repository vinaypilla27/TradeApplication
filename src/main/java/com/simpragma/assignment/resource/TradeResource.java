package com.simpragma.assignment.resource;

import java.util.Date;
import java.util.List;

import com.simpragma.assignment.model.StockPriceRange;
import com.simpragma.assignment.model.Trade;
import com.simpragma.assignment.service.StockInfoMessage;
import com.simpragma.assignment.service.TradeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping
@Validated
public class TradeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeResource.class);

    private final TradeService tradeService;

    @Autowired
    public TradeResource(final TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @DeleteMapping("/trades")
    @ApiOperation(value = "Delete all the trades")
    public ResponseEntity<Void> deleteTrades() {
        LOGGER.info("Deleting all the trades");
        tradeService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/trades")
    @ApiOperation(value = "Add a new trade")
    public ResponseEntity<Void> saveTrade(@RequestBody @Validated final Trade trade) {
        LOGGER.info("Adding a new trade with id: {}", trade.getId());
        tradeService.save(trade);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/trades")
    @ApiOperation(value = "Get a list of all trades")
    public ResponseEntity<List<Trade>> allTrades() {
        LOGGER.info("Fetching all the trades");
        return ResponseEntity.ok(tradeService.getAllTrades());
    }

    @GetMapping("/trades/users/{userID}")
    @ApiOperation(value = "Fetch all the trades of a user")
    public ResponseEntity<List<Trade>> getTradesByUserId(@PathVariable("userID") final Long userId) {
        LOGGER.info("Fetching all the trades of the user {}", userId);
        return ResponseEntity.ok(tradeService.getTradesByUserId(userId));
    }

    @GetMapping("/stocks/{stockSymbol}/trades")
    @ApiOperation(value = "Fetch all the trades of a stock symbol")
    public ResponseEntity<List<Trade>> getTradesByStockSymbol(@PathVariable("stockSymbol") final String stockSymbol,
            final @RequestParam("type") String tradeType,
            final @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            final @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        LOGGER.info("Fetching all the trades of the stock symbol '{}' between the range '{}' and '{}'", stockSymbol, startDate, endDate);
        return ResponseEntity.ok(tradeService.getTradesByStockSymbol(stockSymbol, tradeType, startDate, endDate));
    }

    @GetMapping("/stocks/{stockSymbol}/price")
    @ApiOperation(value = "Fetch max and min price of a stock symbol")
    public ResponseEntity<?> getMaxAndMinStockPrice(@PathVariable("stockSymbol") final String stockSymbol,
            final @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            final @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        LOGGER.info("Fetching Max and Min price of a stock symbol '{}' between the range '{}' and '{}'", stockSymbol, startDate, endDate);
        StockPriceRange stockPriceRange = tradeService.getMaxAndMinTradePriceOfStockSymbol(stockSymbol, startDate, endDate);
        if (isNull(stockPriceRange)) {
            return ResponseEntity.ok(new StockInfoMessage("There are no trades in the given date range"));
        }
        return ResponseEntity.ok(stockPriceRange);
    }

}
