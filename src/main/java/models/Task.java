package models;

import java.util.UUID;

public record Task(UUID id, String taskName, Boolean completed) {
}
