package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            updateItemQuality(items[i]);
        }
    }

    private void updateItemQuality(Item item) {
        updateQualityBeforeExpiration(item);
        decreaseSellIn(item);
        updateQualityAfterExpiration(item);
    }

    private void updateQualityBeforeExpiration(Item item) {
        if (isAgedBrie(item)) {
            augmenterQuality(item);
        } else if (isBackstagePass(item)) {
            updateBackstagePassQuality(item);
        } else if (!isSulfuras(item)) {
            baisserQuality(item);
        }
    }

    private void updateQualityAfterExpiration(Item item) {
        if (item.sellIn < 0) {
            if (isAgedBrie(item)) {
                augmenterQuality(item);
            } else if (isBackstagePass(item)) {
                item.quality = 0;
            } else if (!isSulfuras(item)) {
                baisserQuality(item);
            }
        }
    }

    private void updateBackstagePassQuality(Item item) {
        augmenterQuality(item);

        if (item.sellIn < 11) {
            augmenterQuality(item);
        }

        if (item.sellIn < 6) {
            augmenterQuality(item);
        }
    }

    private void augmenterQuality(Item item) {
        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }
    }

    private void baisserQuality(Item item) {
        if (item.quality > 0) {
            item.quality = item.quality - 1;
        }
    }

    private void decreaseSellIn(Item item) {
        if (!isSulfuras(item)) {
            item.sellIn = item.sellIn - 1;
        }
    }

    private boolean isAgedBrie(Item item) {
        return item.name.equals("Aged Brie");
    }

    private boolean isBackstagePass(Item item) {
        return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    private boolean isSulfuras(Item item) {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }
}
