package no.madsopheim

import java.time.Duration

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
                varighet = Duration.ofHours(1)
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