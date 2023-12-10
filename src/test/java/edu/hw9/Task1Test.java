package edu.hw9;

import edu.hw9.task1.StatsCollector;
import edu.hw9.task1.StatsResult;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task1Test {
    @Test
    @DisplayName("testStatsCollector")
    void testStatsCollector() throws InterruptedException {
        StatsCollector collector = new StatsCollector(4);

        collector.push("metric1", new double[]{0.1, 0.05, 1.4, 5.1, 0.3});
        collector.push("metric2", new double[]{1.0, 2.0, 3.0, 4.0, 5.0});

        List<StatsResult> resultList = collector.stats();

        assertEquals(6.95, resultList.get(0).stats().getSum(), 0.0001);
        assertEquals(1.39,  resultList.get(0).stats().getAverage(), 0.0001);
        assertEquals(5.1,  resultList.get(0).stats().getMax(), 0.0001);
        assertEquals(0.05,  resultList.get(0).stats().getMin(), 0.0001);

        assertEquals(15, resultList.get(1).stats().getSum(), 0.0001);
        assertEquals(3,  resultList.get(1).stats().getAverage(), 0.0001);
        assertEquals(5,  resultList.get(1).stats().getMax(), 0.0001);
        assertEquals(1,  resultList.get(1).stats().getMin(), 0.0001);
    }

    @Test
    @DisplayName("testStatsCombine")
    void testStatsCombine() throws InterruptedException {
        StatsCollector collector = new StatsCollector(4);

        collector.push("metric1", new double[]{0.1, 0.05, 1.4, 5.1, 0.3});
        collector.push("metric1", new double[]{1.0, 2.0, 3.0, 4.0, 5.0});

        List<StatsResult> resultList = collector.stats();

        assertEquals(21.95, resultList.get(0).stats().getSum(), 0.0001);
        assertEquals(2.195,  resultList.get(0).stats().getAverage(), 0.0001);
        assertEquals(5.1,  resultList.get(0).stats().getMax(), 0.0001);
        assertEquals(0.05,  resultList.get(0).stats().getMin(), 0.0001);
    }
}
