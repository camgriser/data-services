package org.acg.db.service.batch

import kotlin.math.min

class BatchCreator {

    companion object {

        fun createRange(batchSize: Int, totalSize: Int): Set<IntRange> {
            var startRange = 0
            var endRange = batchSize - 1
            val ranges = HashSet<IntRange>()
            do {
                ranges.add(IntRange(startRange, min(endRange, totalSize-1)))
                endRange += batchSize
                startRange += batchSize
            } while (startRange < totalSize)
            return ranges
        }
    }
}