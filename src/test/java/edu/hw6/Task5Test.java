package edu.hw6;

import java.net.http.HttpClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task5Test {
    static Arguments[] hacker() {
        HackerNews hackerNews = new HackerNews(HttpClient.newHttpClient());
        return new Arguments[] {Arguments.of(hackerNews)};
    }

    @ParameterizedTest
    @MethodSource("hacker")
    @DisplayName("hackerNewsTopStoriesTest")
    void hackerNewsTopStoriesTest(HackerNews hackerNews) {
        assertThat(hackerNews.hackerNewsTopStories()).isNotEmpty();
    }

    @ParameterizedTest
    @MethodSource("hacker")
    @DisplayName("newsTest")
    void newsTest(HackerNews hackerNews) {
        assertThat(hackerNews.news(1)).isEqualTo("Y Combinator");
        assertThat(hackerNews.news(37570037)).isEqualTo("JDK 21 Release Notes");
    }

    @ParameterizedTest
    @MethodSource("hacker")
    @DisplayName("newsTestException")
    void newsTestException(HackerNews hackerNews) {
        assertThatThrownBy(() -> {
            hackerNews.news(-1);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Negative id");

        assertThatThrownBy(() -> {
            hackerNews.news(99999999);
        }).isInstanceOf(NullPointerException.class).hasMessageContaining("There is no news for this id");
    }
}
