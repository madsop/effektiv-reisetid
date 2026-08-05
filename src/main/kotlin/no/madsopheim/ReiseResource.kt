package no.madsopheim

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType

@Path("/reise")
class ReiseResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun finnReise(
        @QueryParam("startby") startby: String,
        @QueryParam("sluttby") sluttby: String,
    ): Reiserute {
        return Reiserute(listOf())
    }
}

data class Reiserute(val strekninger: List<Delstrekning>)

data class Delstrekning(
    val fra: Plass,
    val til: Plass,
    val type: Type
)

@JvmInline
value class Plass(val value: String)

enum class Type {
    TOG, FLY
}