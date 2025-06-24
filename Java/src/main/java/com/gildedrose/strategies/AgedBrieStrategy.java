package com.gildedrose.strategies;

import com.gildedrose.AbstractQualityUpdateStrategy;
import com.gildedrose.Item;

/**
 * Strategy for Aged Brie items that increase in quality over time.
 * Quality increases by 1 each day, and by 2 after the sell-by date.
 * 
 * @author Saravanamuthukumar S
 */
public class AgedBrieStrategy extends AbstractQualityUpdateStrategy {
    
    /**
     * Updates the quality of an "Aged Brie" item.
     * The quality of "Aged Brie" increases by 1 each day. The quality of an item is never more than 50.
     *
     * <p><b>Usage Example:</b></p>
     * <pre>
     * Item brie = new Item("Aged Brie", 10, 20);
     * AgedBrieStrategy strategy = new AgedBrieStrategy();
     * strategy.updateQuality(brie);
     * // brie.getQuality() will be 21
     *
     * Item maxQualityBrie = new Item("Aged Brie", 10, 50);
     * strategy.updateQuality(maxQualityBrie);
     * // maxQualityBrie.getQuality() will still be 50
     * </pre>
     *
     * @param item The "Aged Brie" item to update. Must not be null.
     */
    @Override
    public void updateQuality(Item item) {
        increaseQuality(item, QUALITY_CHANGE);
    }
    
    /**
     * Handles the quality update for an expired "Aged Brie" item.
     * The quality increases by an additional 1 (total of 2) after the sell-by date has passed.
     * The quality of an item is never more than 50.
     *
     * @param item The expired "Aged Brie" item to update. Must not be null.
     */
    @Override
    public void handleExpiredItem(Item item) {
        increaseQuality(item, QUALITY_CHANGE);
    }
} 