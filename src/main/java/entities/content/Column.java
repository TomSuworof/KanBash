package entities.content;

public enum Column {
    SHOULD_DO,
    IN_PROGRESS,
    DONE,
    ALL // special column for all tasks. Used for clearing, see ContentAdapter
}
