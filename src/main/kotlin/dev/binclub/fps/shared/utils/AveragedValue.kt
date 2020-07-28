package dev.binclub.fps.shared.utils

/**
 * @author cookiedragon234 28/Jul/2020
 */
class AveragedFloat(averageNum: Int) {
	var index = 0
	val vals = FloatArray(averageNum) { Float.NaN }
	
	fun add(num: Float) {
		vals[index] = num
		index += 1
		index %= vals.size
	}
	
	fun get(): Double {
		var sum = 0.0
		var count = 0
		for (element in vals) {
			if (element.isNaN()) continue
			sum += element
			++count
		}
		return if (count == 0) Double.NaN else sum / count
	}
}

class AveragedDouble(averageNum: Int) {
	var index = 0
	val vals = DoubleArray(averageNum) { Double.NaN }
	
	fun add(num: Double) {
		vals[index] = num
		index += 1
		index %= vals.size
	}
	
	fun get(): Double {
		var sum = 0.0
		var count = 0
		for (element in vals) {
			if (element.isNaN()) continue
			sum += element
			++count
		}
		return if (count == 0) Double.NaN else sum / count
	}
}

class AveragedInt(averageNum: Int) {
	var index = 0
	val vals = IntArray(averageNum) { Int.MIN_VALUE }
	
	fun add(num: Int) {
		vals[index] = num
		index += 1
		index %= vals.size
	}
	
	fun get(): Double {
		var sum = 0.0
		var count = 0
		for (element in vals) {
			if (element == Int.MIN_VALUE) continue
			sum += element
			++count
		}
		return if (count == 0) Double.NaN else sum / count
	}
}

class AveragedLong(averageNum: Int) {
	var index = 0
	val vals = LongArray(averageNum) { Long.MIN_VALUE }
	
	fun add(num: Long) {
		vals[index] = num
		index += 1
		index %= vals.size
	}
	
	fun get(): Double {
		var sum = 0.0
		var count = 0
		for (element in vals) {
			if (element == Long.MIN_VALUE) continue
			sum += element
			++count
		}
		return if (count == 0) Double.NaN else sum / count
	}
}
