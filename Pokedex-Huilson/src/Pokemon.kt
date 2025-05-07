// tive que mudar para uma data class.
/*
 data class tem a função copy() já inclusa.
 eu precisava dessa func para "clonar" os objetos aqui criados para o time dos treinadores, se não
 ex: jogador1 e jogador2 escolheram o mesmo pokemon, da maneira q eu estava fzndo antes, ia ser o mesmo objeto, ou seja
 eles iam dividir o mesmo pokemon, tanto em questão de vida qnto de ataques, ent poderia dar ruim.

 funcao data tem algumas características únicas.
 */
data class Pokemon ( // passando os parametros para cade pokemon ser uma classe
    val numPokedex: Int,
    val nome : String,
    val tipoPrimario: TipoPokemon,
    val tipoSecundario: TipoPokemon? = null,
    var vida: Int,
    var moveSpd: Int
){
    val ataques: MutableList<Ataque> = mutableListOf()

    fun atribuirAtaques(){
        if(ataques.isNotEmpty()) return
        if (tipoPrimario!= TipoPokemon.Normal) {
            ataques.add(Ataque("Ataque normal", null, 2))
            ataques.add(Ataque("Ataque ${tipoPrimario.toString()}", tipoPrimario, 3))
            tipoSecundario?.let { // let é uma funcao que permite executar algo se o valor que ele fora aplicado não for NULL.
                ataques.add(Ataque("Ataque ${tipoSecundario.toString()}", tipoSecundario, 3))
            }
        }
        if (tipoPrimario==TipoPokemon.Normal){
            ataques.add(Ataque("Ataque normal",null,2))
            ataques.add(Ataque("Ataque normal Boladão",null,4))
        }

    }

    fun pokemonVivo(): Boolean = vida > 0

    fun escolherAtaque(pokemonEscolhido: Pokemon): Ataque {
        println("Escolha um dos ataques do ${pokemonEscolhido.nome}:")
        pokemonEscolhido.ataques.forEachIndexed { index, ataque ->
            println("${index + 1}. ${ataque.nome}")
        }
        while (true) {
            println("Digite o número do ataque:")
            val escolha = readln().toInt()

            if(escolha in 1..pokemonEscolhido.ataques.size) {
                return pokemonEscolhido.ataques[escolha-1]
            } else{ println("Escolha inválida, tente novamente.") }

        }
    }

    fun multiplicadorDeDano // add caso n os pkm nao tenham efetividade ( eletric x ground )
                (tipoAtaque: TipoPokemon?, tipoPrimarioOponente: TipoPokemon, tipoSecundarioOponente: TipoPokemon?): Double {

        if (tipoAtaque == null) return 1.0 // atk normal -> n muda dano

        var multiplicador = 1.0

        val efetividade = Efetividade()


        if (efetividade.vantagens[tipoAtaque]?.contains(tipoPrimarioOponente) == true) { // verifica se o tipo do inimigo ta listado nas vantagens
            multiplicador *= 2.0
        } else if (efetividade.vantagens.any { it.value.contains(tipoAtaque) && it.key == tipoPrimarioOponente }) {
            // verifica se o tipo do oponente tem vantagem contra o ataque.
            multiplicador *= 0.5
        }

        // Verifica a efetividade contra o tipo secundário do oponente, se houver
        tipoSecundarioOponente?.let { tipoSec -> // lembra que o let funfa qndo NAO se tem valor null
            if (efetividade.vantagens[tipoAtaque]?.contains(tipoSec) == true) {
                multiplicador *= 2.0
            } else if (efetividade.vantagens.any { it.value.contains(tipoAtaque) && it.key == tipoSec }) {
                multiplicador *= 0.5
            }
        }

        return multiplicador
    }




    companion object {
        val todosPoke = mapOf(
            "bulbasaur" to Pokemon(1, "Bulbasaur", TipoPokemon.Planta, TipoPokemon.Venenoso, 5, 5),
            "ivysaur" to Pokemon(2, "Ivysaur", TipoPokemon.Planta, TipoPokemon.Venenoso, 8, 15),
            "venusaur" to Pokemon(3, "Venusaur", TipoPokemon.Planta, TipoPokemon.Venenoso, 12, 10),
            "charmander" to Pokemon(4, "Charmander", TipoPokemon.Fogo, null, 5, 10),
            "charmeleon" to Pokemon(5, "Charmeleon", TipoPokemon.Fogo, null, 8, 15),
            "charizard" to Pokemon(6, "Charizard", TipoPokemon.Fogo, TipoPokemon.Voador, 12, 20),
            "squirtle" to Pokemon(7, "Squirtle", TipoPokemon.Agua, null, 5, 10),
            "wartortle" to Pokemon(8, "Wartortle", TipoPokemon.Agua, null, 8, 15),
            "blastoise" to Pokemon(9, "Blastoise", TipoPokemon.Agua, null, 12, 15),
            "caterpie" to Pokemon(10, "Caterpie", TipoPokemon.Inseto, null, 5, 5),
            "metapod" to Pokemon(11, "Metapod", TipoPokemon.Inseto, null, 8, 10),
            "butterfree" to Pokemon(12, "Butterfree", TipoPokemon.Inseto, TipoPokemon.Voador, 12, 20),
            "weedle" to Pokemon(13, "Weedle", TipoPokemon.Inseto, TipoPokemon.Venenoso, 5, 5),
            "kakuna" to Pokemon(14, "Kakuna", TipoPokemon.Inseto, TipoPokemon.Venenoso, 8, 10),
            "beedrill" to Pokemon(15, "Beedrill", TipoPokemon.Inseto, TipoPokemon.Venenoso, 12, 20),
            "pidgey" to Pokemon(16, "Pidgey", TipoPokemon.Normal, TipoPokemon.Voador, 5, 10),
            "pidgeotto" to Pokemon(17, "Pidgeotto", TipoPokemon.Normal, TipoPokemon.Voador, 8, 15),
            "pidgeot" to Pokemon(18, "Pidgeot", TipoPokemon.Normal, TipoPokemon.Voador, 12, 25),
            "rattata" to Pokemon(19, "Rattata", TipoPokemon.Normal, null, 9, 10),
            "raticate" to Pokemon(20, "Raticate", TipoPokemon.Normal, null, 9, 15),
            "spearow" to Pokemon(21, "Spearow", TipoPokemon.Normal, TipoPokemon.Voador, 6, 15),
            "fearow" to Pokemon(22, "Fearow", TipoPokemon.Normal, TipoPokemon.Voador, 10, 25),
            "ekans" to Pokemon(23, "Ekans", TipoPokemon.Venenoso, null, 6, 15),
            "arbok" to Pokemon(24, "Arbok", TipoPokemon.Venenoso, null, 10, 20),
            "pikachu" to Pokemon(25, "Pikachu", TipoPokemon.Eletrico, null, 6, 10),
            "raichu" to Pokemon(26, "Raichu", TipoPokemon.Eletrico, null, 10, 30),
            "sandshrew" to Pokemon(27, "Sandshrew", TipoPokemon.Terrestre, null, 6, 10),
            "sandslash" to Pokemon(28, "Sandslash", TipoPokemon.Terrestre, null, 10, 15),
            "nidoran" to Pokemon(29, "Nidoran♀", TipoPokemon.Venenoso, null, 5, 10),
            "nidorina" to Pokemon(30, "Nidorina", TipoPokemon.Venenoso, null, 8, 15),
            "nidoqueen" to Pokemon(31, "Nidoqueen", TipoPokemon.Venenoso, TipoPokemon.Terrestre, 12, 25),
            "nidoran" to Pokemon(32, "Nidoran♂", TipoPokemon.Venenoso, null, 5, 10),
            "nidorino" to Pokemon(33, "Nidorino", TipoPokemon.Venenoso, null, 8, 15),
            "nidoking" to Pokemon(34, "Nidoking", TipoPokemon.Venenoso, TipoPokemon.Terrestre, 12, 25),
            "clefairy" to Pokemon(35, "Clefairy", TipoPokemon.Normal, null, 6, 10),
            "clefable" to Pokemon(36, "Clefable", TipoPokemon.Normal, null, 10, 10),
            "vulpix" to Pokemon(37, "Vulpix", TipoPokemon.Fogo, null, 6, 15),
            "ninetales" to Pokemon(38, "Ninetales", TipoPokemon.Fogo, null, 10, 25),
            "jigglypuff" to Pokemon(39, "Jigglypuff", TipoPokemon.Normal, null, 6, 5),
            "wigglytuff" to Pokemon(40, "Wigglytuff", TipoPokemon.Normal, null, 10, 15),
            "zubat" to Pokemon(41, "Zubat", TipoPokemon.Venenoso, TipoPokemon.Voador, 6, 15),
            "golbat" to Pokemon(42, "Golbat", TipoPokemon.Venenoso, TipoPokemon.Voador, 10, 25),
            "oddish" to Pokemon(43, "Oddish", TipoPokemon.Planta, TipoPokemon.Venenoso, 5, 15),
            "gloom" to Pokemon(44, "Gloom", TipoPokemon.Planta, TipoPokemon.Venenoso, 8, 15),
            "vileplume" to Pokemon(45, "Vileplume", TipoPokemon.Planta, TipoPokemon.Venenoso, 12, 10),
            "paras" to Pokemon(46, "Paras", TipoPokemon.Inseto, TipoPokemon.Planta, 6, 10),
            "parasect" to Pokemon(47, "Parasect", TipoPokemon.Inseto, TipoPokemon.Planta, 10, 15),
            "venonat" to Pokemon(48, "Venonat", TipoPokemon.Inseto, TipoPokemon.Venenoso, 6, 10),
            "venomoth" to Pokemon(49, "Venomoth", TipoPokemon.Inseto, TipoPokemon.Venenoso, 10, 20),
            "diglett" to Pokemon(50, "Diglett", TipoPokemon.Terrestre, null, 6, 5),
            "dugtrio" to Pokemon(51, "Dugtrio", TipoPokemon.Terrestre, null, 10, 10),
            "meowth" to Pokemon(52, "Meowth", TipoPokemon.Normal, null, 6, 15),
            "persian" to Pokemon(53, "Persian", TipoPokemon.Normal, null, 10, 25),
            "psyduck" to Pokemon(54, "Psyduck", TipoPokemon.Agua, null, 6, 50),
            "golduck" to Pokemon(55, "Golduck", TipoPokemon.Agua, null, 10, 20),
            "mankey" to Pokemon(56, "Mankey", TipoPokemon.Lutador, null, 6, 10),
            "primeape" to Pokemon(57, "Primeape", TipoPokemon.Lutador, null, 10, 15),
            "growlithe" to Pokemon(58, "Growlithe", TipoPokemon.Fogo, null, 6, 15),
            "arcanine" to Pokemon(59, "Arcanine", TipoPokemon.Fogo, null, 10, 30),
            "poliwag" to Pokemon(60, "Poliwag", TipoPokemon.Agua, null, 5, 5),
            "poliwhirl" to Pokemon(61, "Poliwhirl", TipoPokemon.Agua, null, 8, 15),
            "poliwrath" to Pokemon(62, "Poliwrath", TipoPokemon.Agua, TipoPokemon.Lutador, 12, 20),
            "Abra" to Pokemon(63, "Abra", TipoPokemon.Psiquico, null, 5, 10),
            "kadabra" to Pokemon(64, "Kadabra", TipoPokemon.Psiquico, null, 8, 15),
            "alakazam" to Pokemon(65, "Alakazam", TipoPokemon.Psiquico, null, 12, 20),
            "machop" to Pokemon(66, "Machop", TipoPokemon.Lutador, null, 5, 10),
            "machoke" to Pokemon(67, "Machoke", TipoPokemon.Lutador, null, 8, 15),
            "machamp" to Pokemon(68, "Machamp", TipoPokemon.Lutador, null, 12, 25),
            "bellsprout" to Pokemon(69, "Bellsprout", TipoPokemon.Planta, TipoPokemon.Venenoso, 5, 5),
            "weepinbell" to Pokemon(70, "Weepinbell", TipoPokemon.Planta, TipoPokemon.Venenoso, 8, 10),
            "victreebel" to Pokemon(71, "Victreebel", TipoPokemon.Planta, TipoPokemon.Venenoso, 12, 10),
            "tentacool" to Pokemon(72, "Tentacool", TipoPokemon.Agua, TipoPokemon.Venenoso, 6, 10),
            "tentacruel" to Pokemon(73, "Tentacruel", TipoPokemon.Agua, TipoPokemon.Venenoso, 10, 15),
            "geodude" to Pokemon(74, "Geodude", TipoPokemon.Pedra, TipoPokemon.Terrestre, 5, 5),
            "graveler" to Pokemon(75, "Graveler", TipoPokemon.Pedra, TipoPokemon.Terrestre, 8, 10),
            "golem" to Pokemon(76, "Golem", TipoPokemon.Pedra, TipoPokemon.Terrestre, 12, 10),
            "ponyta" to Pokemon(77, "Ponyta", TipoPokemon.Fogo, null, 6, 20),
            "rapidash" to Pokemon(78, "Rapidash", TipoPokemon.Fogo, null, 10, 30),
            "slowpoke" to Pokemon(79, "Slowpoke", TipoPokemon.Agua, TipoPokemon.Psiquico, 6, 10),
            "slowbro" to Pokemon(80, "Slowbro", TipoPokemon.Agua, TipoPokemon.Psiquico, 10, 15),
            "magnemite" to Pokemon(81, "Magnemite", TipoPokemon.Eletrico, null, 6, 10),
            "magneton" to Pokemon(82, "Magneton", TipoPokemon.Eletrico, null, 10, 15),
            "farfetch'd" to Pokemon(83, "Farfetch'd", TipoPokemon.Normal, TipoPokemon.Voador, 8, 25),
            "doduo" to Pokemon(84, "Doduo", TipoPokemon.Normal, TipoPokemon.Voador, 6, 10),
            "dodrio" to Pokemon(85, "Dodrio", TipoPokemon.Normal, TipoPokemon.Voador, 10, 25),
            "seel" to Pokemon(86, "Seel", TipoPokemon.Agua, null, 6, 10),
            "dewgong" to Pokemon(87, "Dewgong", TipoPokemon.Agua, TipoPokemon.Gelo, 10, 15),
            "grimer" to Pokemon(88, "Grimer", TipoPokemon.Venenoso, null, 6, 5),
            "muk" to Pokemon(89, "Muk", TipoPokemon.Venenoso, null, 10, 10),
            "shellder" to Pokemon(90, "Shellder", TipoPokemon.Agua, null, 6, 10),
            "cloyster" to Pokemon(91, "Cloyster", TipoPokemon.Agua, TipoPokemon.Gelo, 10, 15),
            "gastly" to Pokemon(92, "Gastly", TipoPokemon.Fantasma, TipoPokemon.Venenoso, 5, 10),
            "haunter" to Pokemon(93, "Haunter", TipoPokemon.Fantasma, TipoPokemon.Venenoso, 8, 15),
            "gengar" to Pokemon(94, "Gengar", TipoPokemon.Fantasma, TipoPokemon.Venenoso, 12, 15),
            "onix" to Pokemon(95, "Onix", TipoPokemon.Pedra, TipoPokemon.Terrestre, 12, 10),
            "drowzee" to Pokemon(96, "Drowzee", TipoPokemon.Psiquico, null, 6, 5),
            "hypno" to Pokemon(97, "Hypno", TipoPokemon.Psiquico, null, 10, 15),
            "krabby" to Pokemon(98, "Krabby", TipoPokemon.Agua, null, 6, 10),
            "kingler" to Pokemon(99, "Kingler", TipoPokemon.Agua, null, 10, 15),
            "voltorb" to Pokemon(100, "Voltorb", TipoPokemon.Eletrico, null, 6, 10),
            "electrode" to Pokemon(101, "Electrode", TipoPokemon.Eletrico, null, 10, 20),
            "exeggcute" to Pokemon(102, "Exeggcute", TipoPokemon.Planta, TipoPokemon.Psiquico, 6, 5),
            "exeggutor" to Pokemon(103, "Exeggutor", TipoPokemon.Planta, TipoPokemon.Psiquico, 12, 10),
            "cubone" to Pokemon(104, "Cubone", TipoPokemon.Terrestre, null, 6, 10),
            "marowak" to Pokemon(105, "Marowak", TipoPokemon.Terrestre, null, 10, 15),
            "hitmonlee" to Pokemon(106, "Hitmonlee", TipoPokemon.Lutador, null, 9, 25),
            "hitmonchan" to Pokemon(107, "Hitmonchan", TipoPokemon.Lutador, null, 9, 25),
            "lickitung" to Pokemon(108, "Lickitung", TipoPokemon.Normal, null, 8, 10),
            "koffing" to Pokemon(109, "Koffing", TipoPokemon.Venenoso, null, 6, 10),
            "weezing" to Pokemon(110, "Weezing", TipoPokemon.Venenoso, null, 10, 15),
            "rhyhorn" to Pokemon(111, "Rhyhorn", TipoPokemon.Terrestre, TipoPokemon.Pedra, 6, 10),
            "rhydon" to Pokemon(112, "Rhydon", TipoPokemon.Terrestre, TipoPokemon.Pedra, 12, 15),
            "chansey" to Pokemon(113, "Chansey", TipoPokemon.Normal, null, 9, 10),
            "tangela" to Pokemon(114, "Tangela", TipoPokemon.Planta, null, 7, 10),
            "kangaskhan" to Pokemon(115, "Kangaskhan", TipoPokemon.Normal, null, 12, 10),
            "horsea" to Pokemon(116, "Horsea", TipoPokemon.Agua, null, 6, 15),
            "seadra" to Pokemon(117, "Seadra", TipoPokemon.Agua, null, 9, 20),
            "goldeen" to Pokemon(118, "Goldeen", TipoPokemon.Agua, null, 6, 5),
            "seaking" to Pokemon(119, "Seaking", TipoPokemon.Agua, null, 10, 15),
            "staryu" to Pokemon(120, "Staryu", TipoPokemon.Agua, null, 6, 5),
            "starmie" to Pokemon(121, "Starmie", TipoPokemon.Agua, TipoPokemon.Psiquico, 10, 10),
            "mr. mime" to Pokemon(122, "Mr. Mime", TipoPokemon.Psiquico, null, 10, 15),
            "scyther" to Pokemon(123, "Scyther", TipoPokemon.Inseto, TipoPokemon.Voador, 10, 25),
            "jynx" to Pokemon(124, "Jynx", TipoPokemon.Gelo, TipoPokemon.Psiquico, 10, 10),
            "electabuzz" to Pokemon(125, "Electabuzz", TipoPokemon.Eletrico, null, 10, 15),
            "magmar" to Pokemon(126, "Magmar", TipoPokemon.Fogo, null, 10, 15),
            "pinsir" to Pokemon(127, "Pinsir", TipoPokemon.Inseto, null, 10, 15),
            "tauros" to Pokemon(128, "Tauros", TipoPokemon.Normal, null, 10, 25),
            "magikarp" to Pokemon(129, "Magikarp", TipoPokemon.Agua, null, 3, 1),
            "gyarados" to Pokemon(130, "Gyarados", TipoPokemon.Agua, TipoPokemon.Voador, 12, 30),
            "lapras" to Pokemon(131, "Lapras", TipoPokemon.Agua, TipoPokemon.Gelo, 10, 25),
            "ditto" to Pokemon(132, "Ditto", TipoPokemon.Normal, null, 3, 1),
            "eevee" to Pokemon(133, "Eevee", TipoPokemon.Normal, null, 5, 10),
            "vaporeon" to Pokemon(134, "Vaporeon", TipoPokemon.Agua, null, 8, 20),
            "jolteon" to Pokemon(135, "Jolteon", TipoPokemon.Eletrico, null, 8, 20),
            "flareon" to Pokemon(136, "Flareon", TipoPokemon.Fogo, null, 8, 20),
            "porygon" to Pokemon(137, "Porygon", TipoPokemon.Normal, null, 8, 5),
            "omanyte" to Pokemon(138, "Omanyte", TipoPokemon.Pedra, TipoPokemon.Agua, 6, 5),
            "omastar" to Pokemon(139, "Omastar", TipoPokemon.Pedra, TipoPokemon.Agua, 10, 10),
            "kabuto" to Pokemon(140, "Kabuto", TipoPokemon.Pedra, TipoPokemon.Agua, 6, 5),
            "kabutops" to Pokemon(141, "Kabutops", TipoPokemon.Pedra, TipoPokemon.Agua, 10, 15),
            "aerodactyl" to Pokemon(142, "Aerodactyl", TipoPokemon.Pedra, TipoPokemon.Voador, 10, 20),
            "snorlax" to Pokemon(143, "Snorlax", TipoPokemon.Normal, null, 15, 5),
            "articuno" to Pokemon(144, "Articuno", TipoPokemon.Gelo, TipoPokemon.Voador, 12, 35),
            "zapdos" to Pokemon(145, "Zapdos", TipoPokemon.Eletrico, TipoPokemon.Voador, 12, 35),
            "moltres" to Pokemon(146, "Moltres", TipoPokemon.Fogo, TipoPokemon.Voador, 12, 35),
            "dratini" to Pokemon(147, "Dratini", TipoPokemon.Dragao, null, 5, 10),
            "dragonair" to Pokemon(148, "Dragonair", TipoPokemon.Dragao, null, 8, 15),
            "dragonite" to Pokemon(149, "Dragonite", TipoPokemon.Dragao, TipoPokemon.Voador, 12, 10),
            "mewtwo" to Pokemon(150, "Mewtwo", TipoPokemon.Psiquico, null, 12, 40),
            "mew" to Pokemon(151, "Mew", TipoPokemon.Psiquico, null, 6, 25)
        )

    }
}