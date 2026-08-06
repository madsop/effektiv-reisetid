package no.madsopheim

import java.time.Duration

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
                type = startflyplass.reisemaateTilSentrum,
                varighet = startflyplass.tidTilSentrum
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
                type = sluttflyplass.reisemaateTilSentrum,
                varighet = sluttflyplass.tidTilSentrum
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