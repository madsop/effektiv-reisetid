package no.madsopheim

import java.time.Duration

object HardkodaData {
    fun medFly(startby: Plass, sluttby: Plass, startflyplass: Plass, sluttflyplass: Plass) = listOf(
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
            varighet = Duration.ofMinutes(10)
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

    fun medTog(startby: Plass, sluttby: Plass) = listOf(
        Delstrekning(
            fra = startby,
            til = sluttby,
            type = Type.TOG,
            varighet = Duration.ofHours(6).plusMinutes(25)
        )
    )
}