package com.simpragma.assignment.repository;

import java.util.Date;
import java.util.List;

import com.simpragma.assignment.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query(value = "SELECT t FROM Trade t where t.user.id = ?1 order by t.id asc")
    List<Trade> fetchAllTradesByUser(final Long userId);

    @Query(value = "SELECT t FROM Trade t where t.symbol = ?1 and t.type = ?2 and t.timestamp >= ?3 and t.timestamp <= ?4 order by t.id asc")
    List<Trade> fetchAllTradesByStockSymbolAndType(final String stockSymbol,
            final String tradeType, final Date startDate, final Date endDate);

    @Query(value = "SELECT max(t.price) FROM Trade t where t.symbol = ?1 and t.timestamp >= ?2 and t.timestamp <= ?3")
    Double getMaxPriceOfStockSymbol(final String stockSymbol, final Date startDate, final Date endDate);

    @Query(value = "SELECT min(t.price) FROM Trade t where t.symbol = ?1 and t.timestamp >= ?2 and t.timestamp <= ?3")
    Double getMinPriceOfStockSymbol(final String stockSymbol, final Date startDate, final Date endDate);

}
