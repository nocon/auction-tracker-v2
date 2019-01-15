import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("In-Memory Auction Tracker")
class InMemoryAuctionTrackerTest {
    AuctionTracker unit;

    @BeforeEach
    void setUp() {
        unit = new InMemoryAuctionTracker();
    }

    @Test
    @DisplayName("records and returns multiple bids on an item")
    void recordsMultipleBidsOnAnItem() {
        var bid1 = new Bid(1, 1, 1);
        var bid2 = new Bid(2, 2, 1);

        assertThat(unit.recordBid(bid1)).isEqualTo(BidResult.SUCCESS);
        assertThat(unit.recordBid(bid2)).isEqualTo(BidResult.SUCCESS);

        assertThat(unit.getBids(bid1.getItemId()))
                .isNotNull()
                .contains(bid1)
                .contains(bid2);
    }

    @Test
    @DisplayName("given bid amount under 1, returns error bid result")
    void givenBidAmountUnder1ReturnsErrorBidResult() {
        var bid = new Bid(1, -1, 1);

        assertThat(unit.recordBid(bid)).isEqualTo(BidResult.AMOUNT_UNDER_MINIMUM);
    }

    @Test
    @DisplayName("given bid amount under current winning, returns error bid result")
    void givenBidAmountUnderCurrentWinningReturnsErrorBidResult() {
        var winningBid = new Bid(1, 2, 1);
        var lowerBid = new Bid(2,1, 1);

        unit.recordBid(winningBid);
        assertThat(unit.recordBid(lowerBid)).isEqualTo(BidResult.AMOUNT_EQUAL_OR_BELOW_CURRENT_WINNER);
    }

    @Test
    @DisplayName("gets current winning bid for an item")
    void getsCurrentWinningBidForAnItem() {
        var bid1 = new Bid(1, 1, 1);
        var bid2 = new Bid(2, 2, 1);

        unit.recordBid(bid1);
        unit.recordBid(bid2);

        assertThat(unit.getWinningBid(1))
                .hasValue(bid2);
    }

    @Test
    @DisplayName("given no bids for an item, returns empty winning bid")
    void givenNoBidsForAnItemReturnsEmptyWinningBid() {
        assertThat(unit.getWinningBid(1)).isEmpty();
    }

    @Test
    @DisplayName("gets all the items on which a user has bid")
    void getsAllTheItemsOnWhichAUserHasBid() {
        var bid1 = new Bid(1, 1, 1);
        var bid2 = new Bid(1, 1, 2);
        var anotherUsersBid = new Bid(2, 1, 4);

        unit.recordBid(bid1);
        unit.recordBid(bid2);
        unit.recordBid(anotherUsersBid);

        assertThat(unit.getItemsWithBidsByUser(1))
                .isNotNull()
                .isNotEmpty()
                .contains(bid1.getItemId())
                .contains(bid2.getItemId())
                .doesNotContain(anotherUsersBid.getItemId());
    }

    @Test
    @DisplayName("given no bids by the user, returns empty list of items")
    void givenNoBidsByTheUserReturnsEmptyListOfItems() {
        assertThat(unit.getItemsWithBidsByUser(1)).isEmpty();
    }

}