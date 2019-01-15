public class Bid implements Comparable<Bid> {
    final private int userId;
    final private long amount;
    final private int itemId;

    public Bid(int userId, long amount, int itemId) {
        this.userId = userId;
        this.amount = amount;
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public long getAmount() {
        return amount;
    }

    public int getItemId() {
        return itemId;
    }

    @Override
    public int compareTo(Bid other) {
        return Long.compare(this.amount, other.amount);
    }
}
