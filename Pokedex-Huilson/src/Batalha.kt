class Batalha(val jogador1: Jogador, val jogador2: Jogador) {

    var pokemonEmCampoJ1: Pokemon? = null
    var pokemonEmCampoJ2: Pokemon? = null
    var turno = 1



    fun iniciarBatalha(){

        if (!verificarTime()) return

        pokemonEmCampoJ1 = escolherPokemon(jogador1)
        if (pokemonEmCampoJ1 == null ) return

        pokemonEmCampoJ2 = escolherPokemon(jogador2)
        if(pokemonEmCampoJ2 == null) return

        println("--- BATALHA INICIADA ---")
        println("${jogador1.nome} enviou ${pokemonEmCampoJ1?.nome}!")
        println("${jogador2.nome} enviou ${pokemonEmCampoJ2?.nome}!!")
        println("--- ---------------- ---")

        turnosBatalha()

    }

    private fun verificarTime():Boolean{
        if (jogador1.timePokemon.isEmpty()){
            println("o jogador: ${jogador1.nome} não possui pokemons no time")
            return false
        }
        if (jogador2.timePokemon.isEmpty()){
            println("o jogador: ${jogador2.nome} não possui pokemons no time")
            return false
        }

        return true
    }

    private fun escolherPrimeiroPoke(jogador: Jogador): Pokemon? {
        println(" ${jogador.nome} escolha o seu primeiro pokemon a entrar em campo! ")
        jogador.mostrarTime()
        println("Qual o número do pokemon escolhido?")
        val escolha = readlnOrNull()?.toIntOrNull()
        if (escolha != null && escolha in 1..jogador.timePokemon.size){
            return jogador.timePokemon[escolha -1]
        } else  {
            println("escolha invalida.")
            return null
        }
    }

    private fun escolherAtaque(jogador: Jogador, pokemon: Pokemon): Ataque? {

        println("Escolha um ataque para o pokemon ${pokemon.nome}: ")
        pokemon.ataques.forEachIndexed{ index, ataque ->
            println(" ${index + 1}. ${ataque.nome} (${ataque.tipo ?: "Normal"} - Dano ${ataque.danoBase}")
            // o ataque.tipo ?: -> "Normal" faz o uso do operador Elvis -> se ataque.tipo não for null, usa o valor do mesmo, se for, usa "normal"
        }
        println("Digite o número do ataque")
        val escolha = readlnOrNull()?.toIntOrNull()
        if (escolha != null && escolha in 1..pokemon.ataques.size){
            return pokemon.ataques[escolha - 1]
        } else {
            println("Escolha inválida")
            return null
        }
    }

    private fun ordenarExecutarAtaque( ataqueJogador1:Ataque, ataqueJogador2: Ataque) {
        val primeiroJogador : Jogador
        val primeiroPokemon: Pokemon
        val primeiroAtaque: Ataque
        val segundoJogador: Jogador
        val segundoPokemon : Pokemon
        val segundoAtaque: Ataque

        if (pokemonEmCampoJ1!!.moveSpd >= pokemonEmCampoJ2!!.moveSpd){
            primeiroJogador = jogador1
            primeiroPokemon = pokemonEmCampoJ1!!
            primeiroAtaque = ataqueJogador1
            segundoJogador = jogador2
            segundoPokemon = pokemonEmCampoJ2!!
            segundoAtaque = ataqueJogador2
        }else {
            primeiroJogador = jogador2
            primeiroPokemon= pokemonEmCampoJ2!!
            primeiroAtaque = ataqueJogador2
            segundoJogador = jogador1
            segundoPokemon = pokemonEmCampoJ1!!
            segundoAtaque = ataqueJogador1
        }

        executarAtaque(primeiroJogador,primeiroPokemon,primeiroAtaque,segundoPokemon)
        if (segundoPokemon.pokemonVivo()){
            executarAtaque(segundoJogador,segundoPokemon,segundoAtaque,primeiroPokemon)
        }

    }

    private fun executarAtaque(jogadorAtacante: Jogador, pokemonAtacante: Pokemon, ataque: Ataque, pokemonDefensor: Pokemon) {

        println("O pokemon ${pokemonAtacante.nome} usou ${ataque.nome}!! ")

        var dano = ataque.danoBase.toDouble()
        val multiplicador = pokemonAtacante.multiplicadorDeDano(ataque.tipo,pokemonDefensor.tipoPrimario,pokemonDefensor.tipoSecundario)
        dano *= multiplicador
        when{
            multiplicador > 1.0 -> println("Super Efetivo")
            multiplicador < 1.0 && multiplicador < 0.0 -> println("não e mto efetivo")
            multiplicador == 0.0 -> {
                println("não teve efeito")
                return
            }
        }

        if(kotlin.random.Random.nextDouble() < 0.05) {
            dano *= 2
            println("Golpe Critíco!")
        }

        if (kotlin.random.Random.nextDouble() >= 0.05) {
            val danoFinal = dano.toInt()
            pokemonDefensor.vida -= danoFinal
            println("${pokemonDefensor.nome} recebeu $danoFinal de dano.")
        }else {
            println("${pokemonDefensor.nome} desviou do ataque!")
        }

        if (pokemonDefensor.vida < 0) pokemonDefensor.vida = 0
    }

    private fun escolherPokemon(jogador: Jogador):Pokemon? {
        val pokemonsVivos = jogador.timePokemon.filter { it.pokemonVivo() }
        if(pokemonsVivos.isEmpty()){
            println(" ${jogador.nome} não tem mais pokemons vivos!")
            return null
        }
        println("${jogador.nome} escolha o próximo pokemon a enviar:")
        pokemonsVivos.forEachIndexed { index, pokemon ->
            println(" ${index + 1}. ${pokemon.nome}  (${pokemon.tipoPrimario}" +
                    " ${if (pokemon.tipoSecundario != null) ", ${pokemon.tipoSecundario}" else ""} - Vida: ${pokemon.vida})\")")
        }
        println("Qual o número do pokemon escolhido?")
        val escolha = readlnOrNull()?.toIntOrNull()
        if (escolha != null && escolha in 1..pokemonsVivos.size){
            return pokemonsVivos[escolha-1]
        }else{
            println("Escolha inválida!")
            return null
        }


    }

    private fun turnosBatalha() {


        while (pokemonEmCampoJ1?.pokemonVivo() == true && pokemonEmCampoJ2?.pokemonVivo() == true) {

            println("--- Turno $turno ---")
            println(" - ${jogador1.nome} -- VS -- ${jogador2.nome} ")
            println("- ${pokemonEmCampoJ1?.nome} --    -- ${pokemonEmCampoJ2?.nome}")
            println(" vida: ${pokemonEmCampoJ1?.vida} --      -- vida: ${pokemonEmCampoJ2?.vida}")

            val ataqueJogador1 =
                escolherAtaque(jogador1, pokemonEmCampoJ1!!) // é 2 exclamação pq já fora verificado q n é nulo
            if (ataqueJogador1 != null) {
                val ataqueJogador2 = escolherAtaque(jogador2, pokemonEmCampoJ2!!)
                if (ataqueJogador2 != null) {
                    ordenarExecutarAtaque(ataqueJogador1, ataqueJogador2)
                }
            }

            if (pokemonEmCampoJ1?.vida ?: 0 <= 0) {
                println(" ${pokemonEmCampoJ1?.nome} foi derrotado!")
                val proximoPokemon = escolherPokemon(jogador1)
                if (proximoPokemon != null) {
                    pokemonEmCampoJ1 = proximoPokemon
                    println("o ${jogador1.nome} enviou ${pokemonEmCampoJ1?.nome}!")
                } else {
                    println("o ${jogador1.nome} não tem mais pokemons! ${jogador2.nome} venceu!!")
                    break
                }
            }
            if (pokemonEmCampoJ2?.vida ?: 0 <= 0) {
                println(" ${pokemonEmCampoJ2?.nome} foi derrotado!")
                val proximoPokemon = escolherPokemon(jogador2)
                if (proximoPokemon != null) {
                    pokemonEmCampoJ2 = proximoPokemon
                    println("o ${jogador2.nome} enviou ${pokemonEmCampoJ2?.nome}!")
                } else {
                    println("o ${jogador2.nome} não tem mais pokemons! ${jogador1.nome} venceu!!")
                    break
                }


            }
            turno++
        }
        definirVencedor()
    }

    private fun definirVencedor() {
        if (pokemonEmCampoJ1?.pokemonVivo() == true && pokemonEmCampoJ2?.pokemonVivo() == false){
            println(" ${jogador1.nome} venceu a batalha!!")
        }
        if (pokemonEmCampoJ1?.pokemonVivo() == false && pokemonEmCampoJ2?.pokemonVivo() == true){
            println(" ${jogador2.nome} venceu a batalha!!")
        }else if(pokemonEmCampoJ1?.pokemonVivo() == false && pokemonEmCampoJ2?.pokemonVivo() == false){
            println("batalha terminou em empate!")
        }
    }
}
