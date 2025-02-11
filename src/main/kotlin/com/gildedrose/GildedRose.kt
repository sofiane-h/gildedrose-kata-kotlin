package com.gildedrose

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (i in items.indices) {
            // Checks if it is not an Aged Brie, or Backstage, or a Sulfuras as all of these never decreases in quality
            if (items[i].name != "Aged Brie" && items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
                if (items[i].quality > 0) {
                    if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                        items[i].quality = items[i].quality - 1
                    }
                }
            } else {
                // Item is either Aged brie or Backstage, so if quality is less than 50, we can increase it
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1

                    if (items[i].name == "Backstage passes to a TAFKAL80ETC concert") {
                        // if it is a Backstage and there are 10 days or less, it increases by 2, so we add again 1 in quality
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1
                            }
                        }

                        // if it is a Backstage and there are 5 days or less, it increases by 3, so we add again 1 in quality
                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1
                            }
                        }
                    }
                }
            }

            // All items except Sulfuras see there sellIn decrease
            if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                items[i].sellIn = items[i].sellIn - 1
            }

            // The date has passed
            if (items[i].sellIn < 0) {
                if (items[i].name != "Aged Brie") {
                    if (items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
                        if (items[i].quality > 0) {
                            if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                                // If it is not Aged Brie, not Backstage and not Sulfuras, then it decreases once more
                                items[i].quality = items[i].quality - 1
                            }
                        }
                    } else {
                        // The item is Backstage, so it drops to 0 after the date
                        items[i].quality = items[i].quality - items[i].quality
                    }
                } else {
                    // If it is Aged Brie and the quality is below 50 we can increase its quality once more
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1
                    }
                }
            }
        }
    }

}

