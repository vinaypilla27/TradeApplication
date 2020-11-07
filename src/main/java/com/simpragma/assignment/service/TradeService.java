package com.simpragma.assignment.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.simpragma.assignment.exception.InvalidInputException;
import com.simpragma.assignment.exception.NotFoundException;
import com.simpragma.assignment.model.StockPriceRange;
import com.simpragma.assignment.model.Trade;
import com.simpragma.assignment.repository.TradeRepository;
import com.simpragma.assignment.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class TradeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeService.class);

    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;

    @Autowired
    public TradeService(final TradeRepository tradeRepository,
            final UserRepository userRepository) {
        this.tradeRepository = tradeRepository;
        this.userRepository = userRepository;
    }

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public void save(final Trade trade) {
        if (isNull(trade.getId())) {
            throw new InvalidInputException("id attribute is null in the Trade payload");
        }
        Optional<Trade> mayBeTrade = tradeRepository.findById(trade.getId());
        if (mayBeTrade.isPresent()) {
            throw new InvalidInputException(
                    String.format("Trade already exists with the given id: %s", trade.getId()));
        }
        tradeRepository.save(trade);
    }

    public void deleteAll() {
        tradeRepository.deleteAll();
    }

    public List<Trade> getTradesByUserId(final Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id: '%s' does not exist", userId));
        }
        return tradeRepository.fetchAllTradesByUser(userId);
    }

    public List<Trade> getTradesByStockSymbol(final String stockSymbol,
            final String tradeType, final Date startDate, final Date endDate) {
        return tradeRepository.fetchAllTradesByStockSymbolAndType(stockSymbol, tradeType, startDate, endDate);
    }

    public StockPriceRange getMaxAndMinTradePriceOfStockSymbol(final String stockSymbol, final Date startDate, final Date endDate) {
        Double maxPriceOfStockSymbol = tradeRepository.getMaxPriceOfStockSymbol(stockSymbol, startDate, endDate);
        Double minPriceOfStockSymbol = tradeRepository.getMinPriceOfStockSymbol(stockSymbol, startDate, endDate);
        if(maxPriceOfStockSymbol == null || minPriceOfStockSymbol == null) {
            return null;
        }
        return new StockPriceRange(stockSymbol, maxPriceOfStockSymbol, minPriceOfStockSymbol);
    }

}
