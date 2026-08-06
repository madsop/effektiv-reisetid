package no.madsopheim

import java.time.Duration

enum class Plass(override val namn: String) : Destinasjon {
    Oslo("Oslo"),
    Trondheim("Trondheim"),
    Bergen("Bergen"),
    Stavanger("Stavanger"),
    Kristiansand("Kristiansand")
}

enum class By(val plass: Plass, val flyplass: Flyplass) : Destinasjon {
    Oslo(Plass.Oslo, Flyplass.OSL),
    Trondheim(Plass.Trondheim, Flyplass.TRD),
    Bergen(Plass.Bergen, Flyplass.BGO),
    Stavanger(Plass.Stavanger, Flyplass.SVG),
    Kristiansand(Plass.Kristiansand, Flyplass.KRS);

    override val namn = plass.namn

    companion object {
        fun fraPlass(plass: Plass) = entries.firstOrNull { it.plass == plass }
    }
}

enum class Flyplass(kode: String) : Destinasjon {
    OSL("OSL"),
    TRD("TRD"),
    BGO("BGO"),
    SVG("SVG"),
    KRS("KRS");

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

object HardkodaData {
    fun medFly(startby: Plass, sluttby: Plass): List<Delstrekning> {
        val startflyplass = By.fraPlass(startby)
            ?.flyplass
            ?: throw IllegalArgumentException("Ukjent startby: $startby")
        val sluttflyplass = By.fraPlass(sluttby)
            ?.flyplass
            ?: throw IllegalArgumentException("Ukjent sluttby: $sluttby")
        return listOf(
            Delstrekning(
                fra = startby,
                til = startflyplass,
                type = Type.FLYTOG,
                varighet = Duration.ofMinutes(22)
            ),
            Delstrekning(
                fra = startflyplass,
                til = startflyplass,
                type = Type.SIKKERHEITSKONTROLL,
                varighet = Duration.ofMinutes(5)
            ),
            Delstrekning(
                fra = startflyplass,
                til = startflyplass,
                type = Type.VENTE_PAA_BOARDING,
                varighet = Duration.ofMinutes(20)
            ),
            Delstrekning(
                fra = startflyplass,
                til = startflyplass,
                type = Type.VENTE_OMBORD,
                varighet = Duration.ofMinutes(20)
            ),
            Delstrekning(
                fra = startflyplass,
                til = sluttflyplass,
                type = Type.FLY,
                varighet = startflyplass.flyavstandTil(sluttflyplass)
            ),
            Delstrekning(
                fra = sluttflyplass,
                til = sluttflyplass,
                type = Type.VENTE_OMBORD,
                varighet = Duration.ofMinutes(15)
            ),
            Delstrekning(
                fra = sluttflyplass,
                til = sluttflyplass,
                type = Type.GAA,
                varighet = Duration.ofMinutes(15)
            ),
            Delstrekning(
                fra = sluttflyplass,
                til = sluttflyplass,
                type = Type.VENTE_PAA_TOG,
                varighet = Duration.ofMinutes(15)
            ),
            Delstrekning(
                fra = sluttflyplass,
                til = sluttby,
                type = Type.FLYTOG,
                varighet = Duration.ofMinutes(30)
            )
        )
    }

    fun medTog(startby: Plass, sluttby: Plass) = listOf(
        Delstrekning(
            fra = startby,
            til = sluttby,
            type = Type.TOG,
            varighet = Duration.ofHours(6).plusMinutes(25)
        )
    )
}