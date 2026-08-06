package no.madsopheim

import java.time.Duration

enum class Flyplass(kode: String, val reisemaateTilSentrum: Type, val tidTilSentrum: Duration) : Destinasjon {
    OSL("OSL", Type.TOG, Duration.ofMinutes(22)),
    TRD("TRD", Type.TOG, Duration.ofMinutes(30)),
    BGO("BGO", Type.BYBANE, Duration.ofMinutes(35)),
    SVG("SVG", Type.BUSS, Duration.ofMinutes(40)),
    KRS("KRS", Type.BUSS, Duration.ofMinutes(50));

    override val namn = kode

    fun flyavstandTil(flyplass: Flyplass): Duration {
        if (this == flyplass) return Duration.ZERO
        return avstander[this]!![flyplass]!!
    }

    companion object {
        val avstander = mapOf(
            OSL to mapOf(
                TRD to Duration.ofHours(1),
                BGO to Duration.ofHours(1),
                SVG to Duration.ofMinutes(45),
                KRS to Duration.ofMinutes(30)
            ),
            TRD to mapOf(
                OSL to Duration.ofHours(1),
                BGO to Duration.ofMinutes(80),
                SVG to Duration.ofMinutes(90),
                KRS to Duration.ofMinutes(90)
            ),
            BGO to mapOf(
                OSL to Duration.ofHours(1),
                TRD to Duration.ofMinutes(80),
                SVG to Duration.ofMinutes(30),
                KRS to Duration.ofMinutes(30)
            ),
            SVG to mapOf(
                OSL to Duration.ofMinutes(45),
                BGO to Duration.ofMinutes(30),
                TRD to Duration.ofMinutes(90),
                KRS to Duration.ofMinutes(20)
            ),
            KRS to mapOf(
                OSL to Duration.ofMinutes(30),
                TRD to Duration.ofMinutes(90),
                BGO to Duration.ofMinutes(30),
                SVG to Duration.ofMinutes(20),
            )
        )
    }
}