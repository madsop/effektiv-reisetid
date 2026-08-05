package no.madsopheim

import java.time.Duration

object HardkodaData {
    val byer = mapOf(
        Plass("Oslo") to Flyplass.Flyplasskode("OSL"),
        Plass("Trondheim") to Flyplass.Flyplasskode("TRD"),
        Plass("Bergen") to Flyplass.Flyplasskode("BGO"),
        Plass("Stavanger") to Flyplass.Flyplasskode("SVG"),
        Plass("Kristiansand") to Flyplass.Flyplasskode("KRS"),
        Plass("Tromsø") to Flyplass.Flyplasskode("TOS")
    )


    fun medFly(startby: Plass, sluttby: Plass): List<Delstrekning> {
        val startflyplass = byer[startby]
            ?.let { Plass(it.kode) }
            ?: throw IllegalArgumentException("Ukjent startby: $startby")
        val sluttflyplass = byer[sluttby]
            ?.let { Plass(it.kode) }
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