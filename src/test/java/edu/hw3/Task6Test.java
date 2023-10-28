package edu.hw3;

import edu.hw3.task6.Stock;
import edu.hw3.task6.StockMarket;
import edu.hw3.task6.StockMarketImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task6Test {
    @Test
    @DisplayName("stockMarket")
    void stockMarket() {
        Stock stock1 = new Stock("Sberbank", 100);
        Stock stock2 = new Stock("Tinkoff", 200);
        Stock stock3 = new Stock();

        StockMarket stockMarket = new StockMarketImpl();

        stockMarket.add(stock1);
        assertThat(stockMarket.mostValuableStock().getPrice()).isEqualTo(100);

        stockMarket.add(stock2);
        assertThat(stockMarket.mostValuableStock().getPrice()).isEqualTo(200);

        stockMarket.add(stock3);
        assertThat(stockMarket.mostValuableStock().getPrice()).isEqualTo(200);

        stockMarket.remove(stock2);
        stockMarket.remove(stock1);
        assertThat(stockMarket.mostValuableStock().getPrice()).isEqualTo(0);
    }
}
