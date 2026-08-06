package no.madsopheim

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import java.time.Duration

@Path("/reise")
class ReiseResource {
    @Suppress("unused")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun finnReise(
        @QueryParam("startby") startby: Plass,
        @QueryParam("sluttby") sluttby: Plass,
    ): Reiserute = Reiserute(
        medFly = MedFly(HardkodaData.medFly(startby, sluttby)),
        medTog = MedTog(HardkodaData.medTog(startby, sluttby))
    )
}

data class Reiserute(val medFly: MedFly, val medTog: MedTog)

data class MedFly(val strekninger: List<Delstrekning>, val tekst: String = "Med fly")
data class MedTog(val strekninger: List<Delstrekning>, val tekst: String = "Med tog")

data class Delstrekning(
    @JsonProperty("fra") val fra: Destinasjon,
    @JsonProperty("til") val til: Destinasjon,
    val type: Type,
    @JsonSerialize(using = DurationMinuttSerializer::class)
    val varighet: Duration
)

class DurationMinuttSerializer : JsonSerializer<Duration>() {
    override fun serialize(value: Duration, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeNumber(value.toMinutes())
    }
}

enum class Type {
    TOG, FLYTOG, SIKKERHEITSKONTROLL, BUFFER, VENTE_PAA_BOARDING, VENTE_OMBORD, FLY, GAA, VENTE_PAA_TOG
}

interface Destinasjon {
    val namn: String
}