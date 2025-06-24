package com.gildedrose;

/**
 * Gilded Rose inventory management system.
 * Manages a collection of items and updates their quality and sell-in dates daily.
 * Uses the Strategy pattern to handle different item types with specific update behaviors.
 *
 * @author Saravanamuthukumar S
 */
public class GildedRose {

    private final Item[] items;

    /**
     * Constructs a new GildedRose with the specified items.
     *
     * @param items the array of items to manage
     */
    public GildedRose(Item[] items) {
        this.items = items;
    }

    /**
     * Updates the quality and sell-in dates for all items in the inventory.
     * This method applies the appropriate strategy for each item type.
     */
    public void updateQuality() {
        for (Item item : items) {
            updateItem(item);
        }
    }

    /**
     * Updates a single item using the appropriate strategy.
     *
     * @param item the item to update
     */
    private void updateItem(Item item) {
        QualityUpdateStrategy strategy = QualityUpdateStrategyFactory.createStrategy(item);

        // Update quality based on current state
        strategy.updateQuality(item);

        // Update sell-in date
        strategy.updateSellIn(item);

        // Handle expired items
        if (isExpired(item)) {
            strategy.handleExpiredItem(item);
        }
    }

    /**
     * Checks if an item has expired (sell-in date is negative).
     *
     * @param item the item to check
     * @return true if the item has expired
     */
    private boolean isExpired(Item item) {
        return item.getSellIn() < 0;
    }

    /**
     * Gets the array of items managed by this GildedRose instance.
     *
     * @return the array of items
     */
    public Item[] getItems() {
        return items;
    }
}
