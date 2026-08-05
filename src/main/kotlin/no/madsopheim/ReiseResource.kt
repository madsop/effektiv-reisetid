package no.madsopheim

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import java.time.Duration

@Path("/reise")
class ReiseResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun finnReise(
        @QueryParam("startby") startby: Plass,
        @QueryParam("sluttby") sluttby: Plass,
    ): Reiserute {
        val startflyplass = Plass("OSL")
        val sluttflyplass = Plass("TRD")
        return Reiserute(
            medFly = MedFly(HardkodaData.medFly(startby, sluttby, startflyplass, sluttflyplass)),
            medTog = MedTog(HardkodaData.medTog(startby, sluttby))
        )
    }
}

data class Reiserute(val medFly: MedFly, val medTog: MedTog)

data class MedFly(val strekninger: List<Delstrekning>, val tekst: String = "Med fly")
data class MedTog(val strekninger: List<Delstrekning>, val tekst: String = "Med tog")

data class Delstrekning(
    @JsonProperty("fra") val fra: Plass,
    @JsonProperty("til") val til: Plass,
    val type: Type,
    val varighet: Duration
)

@JvmInline
value class Plass(val value: String)

enum class Type {
    TOG, FLYTOG, SIKKERHEITSKONTROLL, BUFFER, VENTE_PAA_BOARDING, VENTE_OMBORD, FLY, GAA, VENTE_PAA_TOG
}