package no.madsopheim

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