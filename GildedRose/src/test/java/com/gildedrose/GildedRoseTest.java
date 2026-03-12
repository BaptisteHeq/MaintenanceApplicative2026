package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    // ===== ARTICLES REGULIERS TESTS =====

    @Test
    void articleRegulier_qualiteDiminue() {
        Item[] items = new Item[] { new Item("Regular Item", 5, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].quality);
        assertEquals(4, app.items[0].sellIn);
    }

    @Test
    void articleRegulier_qualiteJamaisNegative() {
        Item[] items = new Item[] { new Item("Regular Item", 5, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void articleRegulier_apresExpiration_qualiteDiminueDeuxFois() {
        Item[] items = new Item[] { new Item("Regular Item", -1, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(8, app.items[0].quality);
        assertEquals(-2, app.items[0].sellIn);
    }

    @Test
    void articleRegulier_apresExpiration_qualiteJamaisNegative() {
        Item[] items = new Item[] { new Item("Regular Item", -1, 1) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void articleRegulier_apresExpiration_qualiteZero() {
        Item[] items = new Item[] { new Item("Regular Item", -1, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    // ===== AGED BRIE TESTS =====

    @Test
    void agedBrie_qualiteAugmente() {
        Item[] items = new Item[] { new Item("Aged Brie", 5, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(11, app.items[0].quality);
        assertEquals(4, app.items[0].sellIn);
    }

    @Test
    void agedBrie_qualiteJamaisPlusDe50() {
        Item[] items = new Item[] { new Item("Aged Brie", 5, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void agedBrie_qualiteJamaisPlusDe50_presqueMax() {
        Item[] items = new Item[] { new Item("Aged Brie", 5, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void agedBrie_apresExpiration_qualiteAugmenteDeuxFois() {
        Item[] items = new Item[] { new Item("Aged Brie", -1, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(12, app.items[0].quality);
        assertEquals(-2, app.items[0].sellIn);
    }

    @Test
    void agedBrie_apresExpiration_qualiteJamaisPlusDe50() {
        Item[] items = new Item[] { new Item("Aged Brie", -1, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void agedBrie_apresExpiration_qualiteJamaisPlusDe50_presqueMax() {
        Item[] items = new Item[] { new Item("Aged Brie", -1, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    // ===== SULFURAS TESTS =====

    @Test
    void sulfuras_qualiteNeChangeJamais() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
    }

    @Test
    void sulfuras_sellInNeChangeJamais() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 10, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(10, app.items[0].sellIn);
    }

    @Test
    void sulfuras_sellInNegatif_neChangeJamais() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", -1, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(80, app.items[0].quality);
    }

    // ===== BACKSTAGE PASSES TESTS =====

    @Test
    void backstagePass_plusDe10Jours_qualiteAugmenteDe1() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(11, app.items[0].quality);
        assertEquals(14, app.items[0].sellIn);
    }

    @Test
    void backstagePass_10Jours_qualiteAugmenteDe2() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(12, app.items[0].quality);
        assertEquals(9, app.items[0].sellIn);
    }

    @Test
    void backstagePass_6Jours_qualiteAugmenteDe2() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 6, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(12, app.items[0].quality);
        assertEquals(5, app.items[0].sellIn);
    }

    @Test
    void backstagePass_5Jours_qualiteAugmenteDe3() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(13, app.items[0].quality);
        assertEquals(4, app.items[0].sellIn);
    }

    @Test
    void backstagePass_1Jour_qualiteAugmenteDe3() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 1, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(13, app.items[0].quality);
        assertEquals(0, app.items[0].sellIn);
    }

    @Test
    void backstagePass_jourDuConcert_qualiteAugmenteDe3() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void backstagePass_apresConcert_qualiteTombeAZero() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", -1, 30) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        assertEquals(-2, app.items[0].sellIn);
    }

    @Test
    void backstagePass_qualiteJamaisPlusDe50_plusDe10Jours() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void backstagePass_qualiteJamaisPlusDe50_10Jours() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void backstagePass_qualiteJamaisPlusDe50_5Jours() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void backstagePass_qualiteJamaisPlusDe50_10Jours_presqueMax() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void backstagePass_qualiteJamaisPlusDe50_5Jours_presqueMax() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    // ===== ARTICLES MULTIPLES TEST =====

    @Test
    void multipleArticles_miseAJourQualite() {
        Item[] items = new Item[] {
                new Item("Regular Item", 5, 10),
                new Item("Aged Brie", 3, 20),
                new Item("Sulfuras, Hand of Ragnaros", 0, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 2, 15)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(9, app.items[0].quality);
        assertEquals(21, app.items[1].quality);
        assertEquals(80, app.items[2].quality);
        assertEquals(18, app.items[3].quality);
    }

}
