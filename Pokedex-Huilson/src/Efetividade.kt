import TipoPokemon

class Efetividade {


    val vantagens = mapOf(
        TipoPokemon.Fogo to listOf(TipoPokemon.Planta, TipoPokemon.Inseto, TipoPokemon.Gelo),
        TipoPokemon.Planta to listOf(TipoPokemon.Terrestre, TipoPokemon.Pedra, TipoPokemon.Agua),
        TipoPokemon.Agua to listOf(TipoPokemon.Fogo, TipoPokemon.Terrestre, TipoPokemon.Fogo),
        TipoPokemon.Eletrico to listOf(TipoPokemon.Agua, TipoPokemon.Voador),
        TipoPokemon.Voador to listOf(TipoPokemon.Inseto, TipoPokemon.Lutador, TipoPokemon.Planta),
        TipoPokemon.Gelo to listOf(TipoPokemon.Dragao, TipoPokemon.Voador, TipoPokemon.Planta, TipoPokemon.Terrestre),
        TipoPokemon.Pedra to listOf(TipoPokemon.Inseto, TipoPokemon.Fogo, TipoPokemon.Voador, TipoPokemon.Gelo),
        TipoPokemon.Terrestre to listOf(TipoPokemon.Eletrico, TipoPokemon.Fogo, TipoPokemon.Venenoso, TipoPokemon.Pedra),
        TipoPokemon.Lutador to listOf(TipoPokemon.Gelo, TipoPokemon.Normal, TipoPokemon.Pedra),
        TipoPokemon.Psiquico to listOf(TipoPokemon.Lutador, TipoPokemon.Venenoso),
        TipoPokemon.Venenoso to listOf(TipoPokemon.Planta),
        TipoPokemon.Inseto to listOf(TipoPokemon.Planta, TipoPokemon.Psiquico),
        TipoPokemon.Dragao to listOf(TipoPokemon.Dragao),
        TipoPokemon.Normal to listOf()


    )

}
