package com.gildedrose

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

internal class GildedRoseTest {

    @Test
    fun `Test that a classic item will decrease its quality the older it gets`() {
        // Given
        val items = listOf(
            Item("+5 Dexterity Vest", 10, 20),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = Item("+5 Dexterity Vest", 3, 13)
        assertEquals(expectedItem.quality, items[0].quality)
    }

    @Test
    fun `Test that a classic item with negative sellIn date will decrease its quality twice as fast`() {
        // Given
        val items = listOf(
            Item("+5 Dexterity Vest", -1, 20),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = Item("+5 Dexterity Vest", -8, 6)
        assertEquals(expectedItem.quality, items[0].quality)
    }

    @Test
    fun `Test that a Aged Brie will increase its quality the older it gets`() {
        // Given
        val items = listOf(
            Item("Aged Brie", 10, 0),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = Item("Aged Brie", 3, 7)
        assertEquals(expectedItem.quality, items[0].quality)
    }

    @Test
    fun `Test that a Aged Brie with negative sellIn date will increase its quality twice as fast`() {
        // Given
        val items = listOf(
            Item("Aged Brie", 10, 0),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = Item("Aged Brie", 3, 7)
        assertEquals(expectedItem.quality, items[0].quality)
    }

    @Test
    fun `Test that a Backstage passes will increase its quality the older it gets`() {
        // Given
        val items = listOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 18, 20),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = Item("Backstage passes to a TAFKAL80ETC concert", 11, 27)
        assertEquals(expectedItem.quality, items[0].quality)
    }

    @Test
    fun `Test that a Backstage passes will increase by two its quality when the sellIn date is between ten and six`() {
        // Given
        val items = listOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 10, 20),
        )

        // When
        val numberOfDays = 4
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = Item("Backstage passes to a TAFKAL80ETC concert", 6, 28)
        assertEquals(expectedItem.quality, items[0].quality)
    }

    @Test
    fun `Test that a Backstage passes will increase by three its quality when the sellIn date is less than six`() {
        // Given
        val items = listOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 5, 20),
        )

        // When
        val numberOfDays = 4
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = Item("Backstage passes to a TAFKAL80ETC concert", 1, 32)
        assertEquals(expectedItem.quality, items[0].quality)
    }

    @Test
    fun `Test that a Backstage passes with negative sellIn date will see its quality drops to zero`() {
        // Given
        val items = listOf(
            Item("Backstage passes to a TAFKAL80ETC concert", -1, 20),
        )

        // When
        val numberOfDays = 4
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = Item("Backstage passes to a TAFKAL80ETC concert", -5, 0)
        assertEquals(expectedItem.quality, items[0].quality)
    }

    @Test
    fun `Test that a Sulfuras always keeps its quality to eighty and the sellIn date never never changes`() {
        // Given
        val items = listOf(
            Item("Sulfuras, Hand of Ragnaros", 0, 80),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItem = Item("Sulfuras, Hand of Ragnaros", 0, 80)
        assertEquals(expectedItem.quality, items[0].quality)
    }

    @Test
    fun `Test that the quality of any item is never negative`() {
        // Given
        val items = listOf(
            Item("+5 Dexterity Vest", 10, 0),
            Item("Aged Brie", 2, 0),
            Item("Elixir of the Mongoose", 5, 0),
            Item("Sulfuras, Hand of Ragnaros", 0, 80),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        assert(items.all { it.quality >= 0 })
    }

    @Test
    fun `Test that the quality of any item is never greater than fifty, except for the Sulfuras which stays eighty`() {
        // Given
        val items = listOf(
            Item("+5 Dexterity Vest", 10, 49),
            Item("Aged Brie", 2, 49),
            Item("Elixir of the Mongoose", 5, 49),
            Item("Sulfuras, Hand of Ragnaros", 0, 80),
            Item("Backstage passes to a TAFKAL80ETC concert", -1, 49),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        assertAll(
            items.map { item ->
                Executable {
                    assert(
                        if (item.name == "Sulfuras, Hand of Ragnaros") {
                            item.quality == 80
                        } else {
                            item.quality <= 50
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
            Item("+5 Dexterity Vest", 10, 49),
            Item("Aged Brie", 2, 49),
            Item("Elixir of the Mongoose", 5, 49),
            Item("Sulfuras, Hand of Ragnaros", 0, 80),
        )

        // When
        val numberOfDays = 7
        val app = GildedRose(items)
        for (i in 0 until numberOfDays) {
            app.updateQuality()
        }

        // Then
        val expectedItems = listOf(
            Item("+5 Dexterity Vest", 10 - numberOfDays, 49),
            Item("Aged Brie", 2 - numberOfDays, 49),
            Item("Elixir of the Mongoose", 5 - numberOfDays, 49),
            Item("Sulfuras, Hand of Ragnaros", 0, 80),
        )

        assertAll(
            items.mapIndexed { index, item ->
                Executable { assertEquals(expectedItems[index].sellIn, item.sellIn) }
            }
        )
    }
}
