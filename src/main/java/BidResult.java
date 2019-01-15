public enum BidResult {
    SUCCESS(0),
    AMOUNT_UNDER_MINIMUM(1),
    AMOUNT_EQUAL_OR_BELOW_CURRENT_WINNER(2);

    private final int id;
    BidResult(int id) { this.id = id; }
    public int getValue() { return id; }
}
