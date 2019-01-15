import java.util.*;
import java.util.stream.Collectors;

public class InMemoryAuctionTracker implements AuctionTracker {
    public static final long MINIMUM_BID_AMOUNT = 1;

    private final HashMap<Integer, SortedSet<Bid>> allBids;

    public InMemoryAuctionTracker() {
        allBids = new HashMap<>();
    }

    @Override
    public BidResult recordBid(Bid bid) {
        if (bid.getAmount() < MINIMUM_BID_AMOUNT)
            return BidResult.AMOUNT_UNDER_MINIMUM;

        var itemBids = allBids.getOrDefault(bid.getItemId(), new TreeSet<>());

        if (!itemBids.isEmpty() && bid.getAmount() <= itemBids.last().getAmount())
            return BidResult.AMOUNT_EQUAL_OR_BELOW_CURRENT_WINNER;

        itemBids.add(bid);
        allBids.put(bid.getItemId(), itemBids);
        return BidResult.SUCCESS;
    }

    @Override
    public SortedSet<Bid> getBids(int itemId) {
        return allBids.get(itemId);
    }

    @Override
    public Optional<Bid> getWinningBid(int itemId) {
        var itemBids = allBids.get(itemId);
        if (itemBids == null) return Optional.empty();
        return Optional.of(itemBids.last());
    }

    @Override
    public List<Integer> getItemsWithBidsByUser(int userId) {
        return allBids
                .values()
                .stream()
                .flatMap(c -> c.stream())
                .filter(b -> b.getUserId() == userId)
                .map(b -> b.getItemId())
                .distinct()
                .collect(Collectors.toList());
    }
}
