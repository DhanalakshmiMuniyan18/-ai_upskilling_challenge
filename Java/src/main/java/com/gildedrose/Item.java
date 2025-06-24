package com.gildedrose;

/**
 * Represents an item in the Gilded Rose inventory system.
 * Each item has a name, sell-in value (days), and quality value.
 *
 * @author Saravanamuthukumar S
 */
public class Item {
    private String name;
    private int sellIn;
    private int quality;

    /**
     * Creates a new Item with the specified attributes.
     *
     * @param name    the name of the item (must not be null or empty)
     * @param sellIn  the number of days to sell the item
     * @param quality the quality of the item (must be non-negative)
     * @throws IllegalArgumentException if name is null/empty or quality is negative
     */
    public Item(String name, int sellIn, int quality) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (quality < 0) {
            throw new IllegalArgumentException("Quality cannot be negative");
        }
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public String getName() {
        return name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        if (quality < 0) {
            throw new IllegalArgumentException("Quality cannot be negative");
        }
        this.quality = quality;
    }

    @Override
    public String toString() {
        return String.format("%s, %d, %d", name, sellIn, quality);
    }
}
