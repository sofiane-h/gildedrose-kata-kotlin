package com.gildedrose

import com.gildedrose.model.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

internal class GildedRoseTest {

    @Test
    fun `Test that a classic item will decrease its quality the older it gets`() {
        // Given
        val items = listOf(
            ClassicItem(Item("+5 Dexterity Vest", 10, 20)),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = ClassicItem(Item("+5 Dexterity Vest", 3, 13))
        assertEquals(expectedItem.item.quality, items[0].item.quality)
    }

    @Test
    fun `Test that a classic item with negative sellIn date will decrease its quality twice as fast`() {
        // Given
        val items = listOf(
            ClassicItem(Item("+5 Dexterity Vest", -1, 20)),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = ClassicItem(Item("+5 Dexterity Vest", -8, 6))
        assertEquals(expectedItem.item.quality, items[0].item.quality)
    }

    @Test
    fun `Test that a Aged Brie will increase its quality the older it gets`() {
        // Given
        val items = listOf(
            AgedBrieItem(Item("Aged Brie", 10, 0)),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = AgedBrieItem(Item("Aged Brie", 3, 7))
        assertEquals(expectedItem.item.quality, items[0].item.quality)
    }

    @Test
    fun `Test that a Aged Brie with negative sellIn date will increase its quality twice as fast`() {
        // Given
        val items = listOf(
            AgedBrieItem(Item("Aged Brie", 10, 0)),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = AgedBrieItem(Item("Aged Brie", 3, 7))
        assertEquals(expectedItem.item.quality, items[0].item.quality)
    }

    @Test
    fun `Test that a Backstage passes will increase its quality the older it gets`() {
        // Given
        val items = listOf(
            BackstagePassesItem(Item("Backstage passes to a TAFKAL80ETC concert", 18, 20))
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem =
            BackstagePassesItem(Item("Backstage passes to a TAFKAL80ETC concert", 11, 27))
        assertEquals(expectedItem.item.quality, items[0].item.quality)
    }

    @Test
    fun `Test that a Backstage passes will increase by two its quality when the sellIn date is between ten and six`() {
        // Given
        val items = listOf(
            BackstagePassesItem(Item("Backstage passes to a TAFKAL80ETC concert", 10, 20)),
        )

        // When
        val numberOfDays = 4
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem =
            BackstagePassesItem(Item("Backstage passes to a TAFKAL80ETC concert", 6, 28))
        assertEquals(expectedItem.item.quality, items[0].item.quality)
    }

    @Test
    fun `Test that a Backstage passes will increase by three its quality when the sellIn date is less than six`() {
        // Given
        val items = listOf(
            BackstagePassesItem(Item("Backstage passes to a TAFKAL80ETC concert", 5, 20)),
        )

        // When
        val numberOfDays = 4
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem =
            BackstagePassesItem(Item("Backstage passes to a TAFKAL80ETC concert", 1, 32))
        assertEquals(expectedItem.item.quality, items[0].item.quality)
    }

    @Test
    fun `Test that a Backstage passes with negative sellIn date will see its quality drops to zero`() {
        // Given
        val items = listOf(
            BackstagePassesItem(Item("Backstage passes to a TAFKAL80ETC concert", -1, 20)),
        )

        // When
        val numberOfDays = 4
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem =
            BackstagePassesItem(Item("Backstage passes to a TAFKAL80ETC concert", -5, 0))
        assertEquals(expectedItem.item.quality, items[0].item.quality)
    }

    @Test
    fun `Test that a Sulfuras always keeps its quality to eighty and the sellIn date never changes`() {
        // Given
        val items = listOf(
            SulfurasItem(Item("Sulfuras, Hand of Ragnaros", 0, 80)),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = SulfurasItem(Item("Sulfuras, Hand of Ragnaros", 0, 80))
        assertEquals(expectedItem.item.quality, items[0].item.quality)
    }

    @Test
    fun `Test that the quality of any item is never negative`() {
        // Given
        val items = listOf(
            ClassicItem(Item("+5 Dexterity Vest", 10, 0)),
            AgedBrieItem(Item("Aged Brie", 2, 0)),
            ClassicItem(Item("Elixir of the Mongoose", 5, 0)),
            SulfurasItem(Item("Sulfuras, Hand of Ragnaros", 0, 80)),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        assertAll(
            items.map { inventoryItem ->
                Executable {
                    assert(inventoryItem.item.quality >= 0)
                }
            }
        )
    }

    @Test
    fun `Test that the quality of any item is never greater than fifty, except for the Sulfuras which stays eighty`() {
        // Given
        val items = listOf(
            ClassicItem(Item("+5 Dexterity Vest", 10, 49)),
            AgedBrieItem(Item("Aged Brie", 2, 49)),
            ClassicItem(Item("Elixir of the Mongoose", 5, 49)),
            SulfurasItem(Item("Sulfuras, Hand of Ragnaros", 0, 80)),
            BackstagePassesItem(Item("Backstage passes to a TAFKAL80ETC concert", -1, 49)),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        assertAll(
            items.map { inventoryItem ->
                Executable {
                    assert(
                        if (inventoryItem is SulfurasItem) {
                            inventoryItem.item.quality == 80
                        } else {
                            inventoryItem.item.quality <= 50
                        }
                    )
                }
            }
        )
    }

    @Test
    fun `Test that the sellIn date of any item decrease by one per day, except for the Sulfuras which stays the same`() {
        // Given
        val items = listOf(
            ClassicItem(Item("+5 Dexterity Vest", 10, 49)),
            AgedBrieItem(Item("Aged Brie", 2, 49)),
            ClassicItem(Item("Elixir of the Mongoose", 5, 49)),
            SulfurasItem(Item("Sulfuras, Hand of Ragnaros", 0, 80)),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItems = listOf(
            ClassicItem(Item("+5 Dexterity Vest", 10 - numberOfDays, 49)),
            AgedBrieItem(Item("Aged Brie", 2 - numberOfDays, 49)),
            ClassicItem(Item("Elixir of the Mongoose", 5 - numberOfDays, 49)),
            SulfurasItem(Item("Sulfuras, Hand of Ragnaros", 0, 80)),
        )

        assertAll(
            items.mapIndexed { index, inventoryItem ->
                Executable { assertEquals(expectedItems[index].item.sellIn, inventoryItem.item.sellIn) }
            }
        )
    }

    @Test
    fun `Test that a Conjured decreases its quality twice as fast than classic items`() {
        // Given
        val items = listOf(
            ConjuredItem(Item("Conjured", 10, 20)),
        )

        // When
        val numberOfDays = 4
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        assertEquals(items[0].item.quality, 12)
    }

    @Test
    fun `Test that a Conjured with negative sellIn date will decrease its quality two times faster than classic items`() {
        // Given
        val items = listOf(
            ConjuredItem(Item("Conjured", -1, 20)),
        )

        // When
        val numberOfDays = 4
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        assertEquals(items[0].item.quality, 4)
    }

    @Test
    fun `Test that if we increase the quality and the result is greater than the max (fifty), then we set the quality to the max`() {
        // Given
        val items = listOf(
            AgedBrieItem(Item("Aged Brie", -5, 45)),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = AgedBrieItem(Item("Aged Brie", 3, 50))
        assertEquals(expectedItem.item.quality, items[0].item.quality)
    }

    @Test
    fun `Test that if we decrease the quality and the result is lower than the min (zero), then we set the quality to the min`() {
        // Given
        val items = listOf(
            ClassicItem(Item("+5 Dexterity Vest", -5, 3)),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = ClassicItem(Item("+5 Dexterity Vest", 3, 0))
        assertEquals(expectedItem.item.quality, items[0].item.quality)
    }
}
