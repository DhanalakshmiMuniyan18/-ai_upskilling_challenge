package com.gildedrose.strategies;

import com.gildedrose.AbstractQualityUpdateStrategy;
import com.gildedrose.Item;

/**
 * Strategy for Backstage Pass items that increase in quality as the concert approaches.
 * Quality increases by 1 normally, by 2 when 10 days or less, by 3 when 5 days or less,
 * and drops to 0 after the concert.
 * 
 * @author Saravanamuthukumar S
 */
public class BackstagePassStrategy extends AbstractQualityUpdateStrategy {
    
    /**
     * Updates the quality of a Backstage Pass item based on its sell-in value.
     * The quality increases as the concert date approaches:
     * <ul>
     *     <li>Increases by 1 when there are more than 10 days left.</li>
     *     <li>Increases by 2 when there are 10 days or less.</li>
     *     <li>Increases by 3 when there are 5 days or less.</li>
     * </ul>
     * The quality of an item is never more than 50.
     *
     * <p><b>Usage Example:</b></p>
     * <pre>
     * Item pass = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20);
     * BackstagePassStrategy strategy = new BackstagePassStrategy();
     * strategy.updateQuality(pass);
     * // pass.getQuality() will be 21
     *
     * pass.setSellIn(10);
     * strategy.updateQuality(pass);
     * // pass.getQuality() will be 23 (21 + 2)
     *
     * pass.setSellIn(5);
     * strategy.updateQuality(pass);
     * // pass.getQuality() will be 26 (23 + 3)
     * </pre>
     *
     * @param item The Backstage Pass item to update. Must not be null.
     */
    @Override
    public void updateQuality(Item item) {
        increaseQuality(item, QUALITY_CHANGE);
        
        // Additional quality increase when 10 days or less
        if (item.getSellIn() <= BACKSTAGE_PASS_THRESHOLD_1) {
            increaseQuality(item, QUALITY_CHANGE);
        }
        
        // Additional quality increase when 5 days or less
        if (item.getSellIn() <= BACKSTAGE_PASS_THRESHOLD_2) {
            increaseQuality(item, QUALITY_CHANGE);
        }
    }
    
    /**
     * Handles the case where a Backstage Pass has expired.
     * The quality of the pass drops to 0 after the concert date has passed.
     *
     * @param item The expired Backstage Pass item. Must not be null.
     */
    @Override
    public void handleExpiredItem(Item item) {
        // Quality drops to 0 after the concert
        setQualityToZero(item);
    }
} 