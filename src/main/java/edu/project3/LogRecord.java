package edu.project3;

import java.time.OffsetDateTime;

public record LogRecord(String clientIp, OffsetDateTime timestamp, String method, String resource, int statusCode,
                        int responseSize, String userAgent) {
}
