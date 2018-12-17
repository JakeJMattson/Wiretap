package io.github.jakejmattson.wiretap.extensions

private val unitStrings = arrayOf("day", "hour", "minute", "second")

fun Number.asTimeString(noTimeFallback: String = "No time") = when (val seconds = kotlin.math.abs(toLong())) {
	0L -> noTimeFallback
	else -> buildString {
		val info = Array(4) { if (it == 3) seconds else 0L }
		val stack = java.util.ArrayDeque<String>()

		fun applyStr(x: Int) = stack.push("${info[x]} ${unitStrings[x]}${if (info[x] == 1L) "" else "s"}")

		fun evaluate(x: Int, z: Int) = with(x + 1) {
			info[x] = info[this] / z
			info[this] = info[this] % z
			applyStr(this)
			info[x] != 0L
		}

		if (evaluate(2, 60) && evaluate(1, 60) && evaluate(0, 24))
			applyStr(0)

		append(stack.joinToString(" "))
	}
}