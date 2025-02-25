package models;

import java.util.UUID;

public record Fruit(UUID id, String name, boolean isSweet) {
}
