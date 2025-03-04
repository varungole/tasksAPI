package models;

import utils.Util.*;

import java.util.UUID;

public record Task(UUID id, String taskName, Boolean completed, Priority priority) {
}
