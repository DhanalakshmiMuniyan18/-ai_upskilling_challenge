package com.gildedrose;

/**
 * Represents an item in the Gilded Rose inventory system.
 * Each item has a name, sell-in value (days), and quality value.
 *
 * @author Saravanamuthukumar S
 */
public class Item {
    public String name;
    public int sellIn;
    public int quality;

    /**
     * Creates a new Item with the specified attributes.
     *
     * @param name    the name of the item
     * @param sellIn  the number of days to sell the item
     * @param quality the quality of the item
     */
    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    @Override
    public String toString() {
        return String.format("%s, %d, %d", name, sellIn, quality);
    }
}
