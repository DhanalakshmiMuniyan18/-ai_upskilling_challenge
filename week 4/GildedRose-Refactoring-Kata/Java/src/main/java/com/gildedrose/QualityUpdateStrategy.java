package com.gildedrose;

/**
 * Strategy interface for updating item quality based on item type.
 * This follows the Strategy pattern to encapsulate different quality update algorithms.
 * 
 * @author Saravanamuthukumar S
 */
public interface QualityUpdateStrategy {
    
    /**
     * Updates the quality of an item based on its type and current state.
     * 
     * @param item the item to update
     */
    void updateQuality(Item item);
    
    /**
     * Updates the sell-in date of an item.
     * 
     * @param item the item to update
     */
    void updateSellIn(Item item);
    
    /**
     * Handles the quality update when an item has passed its sell-by date.
     * 
     * @param item the item to update
     */
    void handleExpiredItem(Item item);
} 