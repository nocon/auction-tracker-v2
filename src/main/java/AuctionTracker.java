import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

public interface AuctionTracker {
    BidResult recordBid(Bid bid);
    SortedSet<Bid> getBids(int itemId);
    Optional<Bid> getWinningBid(int itemId);
    List<Integer> getItemsWithBidsByUser(int userId);
}
