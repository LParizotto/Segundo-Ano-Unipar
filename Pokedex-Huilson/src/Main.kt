import TipoPokemon
import Efetividade
import Pokemon

const val limitePokemonTime = 6
val jogadores = mutableListOf<Jogador>()


fun main() {
    do {

        println("== MENU == ")
        println("-- 1 - Catalogar jogador --  ") // fiz a func que cria o obj
        println("-- 2 - Catalogar Time -- ") // criar uma func que add na mutableList do obj
        println("-- 3 - Remover pokemon --  ") // remover da mtblist
        println("-- 4 - Iniciar batalha  --  ") // escolher 2 jogadores, e começar o duelo de acordo com oq o professor passou
        println("-- 5 - Listar Jogadores  --  ") // mostra o jogador ( nome e lista o time )
        println("-- 0 - Sair -- ")

        val opcao = readln()
        when(opcao.toInt()) {

            1 -> {
                val jogador = cadastrar()
                jogadores.add(jogador) // deixa guardado o novo jogador na memória.
                println("o jogador ${jogador.nome} foi cadastrado com sucesso")
            }
            2 -> {
                println("No time de qual jogador deseja adicionar pokemons?")
                println("Jogadores disponíveis:")
                for (pessoa in jogadores) { println(pessoa.nome) }
                catalogarTime(readln())

            }
            3 -> {
                removerPkmTime()
            }
            4 -> {
                if (jogadores.size < 2) {
                    println("é necessário pelo menos 2 jogadores para iniciar a batalha!!")
                } else {
                    val jogador1 = escolherJogador(" Jogador 1")
                    if (jogador1 != null) {
                        val jogador2 = escolherJogador("Jogador 2")
                        if (jogador2 != null) {
                            val batalha = Batalha(jogador1, jogador2)
                            batalha.iniciarBatalha()
                        }
                    }
                }
            }
            5 -> {
                showTimePk()
            }

        }



    }while (opcao != "0" )






}

private fun cadastrar() : Jogador{

    val regex = Regex("^[A-ZÀ-ÖØ-Ÿ][a-zà-ÿ ]+\$") // primeira letra maiuscula, e sem numeros, para o nome
    var nome: String

    do{
        println("Qual o nome do jogador?")
        nome = readln()

        if (!regex.matches(nome)){
            println("Coloque um nome valido para o jogador")
        }


    } while (!regex.matches(nome))

    val jogador = Jogador(nome, mutableListOf())

    return jogador
}

private fun catalogarTime(reposta:String){


    val jogador = jogadores.find { it.nome.equals(reposta, ignoreCase = true) }

    if (jogador == null) {
        println("Jogador não encontrado")
        return
    }

    while (jogador.timePokemon.size < limitePokemonTime){

        println("Qual pokemon deseja adicionar ao seu time?")
        val guardarNomePk = readln().lowercase()


        val pokemonAdicionar = Pokemon.todosPoke[guardarNomePk] // tem que fzr isso pq a var de cima é uma string e esse retorna um obj

        // aqui o motivo de usar data class
        if (pokemonAdicionar != null) { // checa se o pokeon existe.
            val copia = pokemonAdicionar.copy() // cria uma nova instância do pokemon.
            copia.atribuirAtaques() // usa a func para atribuir os atks com base nos tipos
            jogador.addPokemon(copia) // add o pokemon no time do jogador.

        }else{
            println("Pokemon `$guardarNomePk` não foi encontrado na pokedex")
        }

        println("Deseja continuar a adicionar pokemons?")
        println("s / n")
        val resposta = readln()
        if (resposta!= "s") break // forma expressiva de usar o if em kotlin


    }

    if (jogador.timePokemon.size == limitePokemonTime) {
        println("time pokemon completo!")
    }


}

private fun removerPkmTime() {

    println("No time de qual jogador será feita a mudança?")
    for (imundo in jogadores){
        println("- ${imundo.nome}")
    }

    var resposta = readln()
    val jogadorEscolhido = jogadores.find { it.nome.equals(resposta, ignoreCase = true) }

    if (jogadorEscolhido == null) {
        println("Jogador não encontrado, escreva direito:")
        return
    }

    if(jogadorEscolhido.timePokemon.isEmpty()) {
        println("o time do ${jogadorEscolhido.nome} se encontra vazio!")
        return
    }


    println("Qual pokemon deseja remover do time?")
    for (poke in jogadorEscolhido.timePokemon) {
        println(poke.nome)
    }
    resposta = readln() // é string quem q transformar em obj
    val pokemonRemover = jogadorEscolhido.timePokemon.find { it.nome.equals(resposta, ignoreCase = true) }
    if (pokemonRemover == null){
        println("pokemon não existe, tente novamente.")
    } else{
        jogadorEscolhido.timePokemon.remove(pokemonRemover)
        println("o pokemon ${pokemonRemover.nome} foi removido do time.")
    }
}

private fun showTimePk(){
    if (jogadores.isEmpty()){
        println("nenhum jogador cadastrado")
        return
    }
    println("Jogadores cadastrados")
    // como as listas não são grandes ( a de pokemon tem no max 6 )
    // acho que não tem problema iterar sobre iteração, pq fica O(n * m ) ent n é mto ruim eu acho
    // e dava para usar for tbm, mas quis diferenciar
    jogadores.forEach { jogador ->
        println(jogador.nome)
        if (jogador.timePokemon.isEmpty()){
            println("o time se encontra vazio!")
        } else {
            jogador.timePokemon.forEach{ pokemon ->
                val tipagem =
                    when {
                        pokemon.tipoSecundario != null -> "${pokemon.tipoPrimario}, ${pokemon.tipoSecundario}"
                        else -> pokemon.tipoPrimario.toString()
                    }
                println(" - ${pokemon.nome} - $tipagem ")
                println(" Ataques - ${pokemon.ataques.joinToString { it.nome  }}")
            }
        }
        println("----------------------------------------")
    }



}

fun escolherJogador(entrada: String): Jogador? {
    println(" Escolha o $entrada para a batalha:")
    jogadores.forEachIndexed { index, jogador ->
        println("${index + 1}. ${jogador.nome}")
    }
    print("Digite o número do jogador: ")
    val escolha = readlnOrNull()?.toIntOrNull()
    if (escolha != null && escolha in 1..jogadores.size) {
        return jogadores[escolha - 1]
    } else {
        println("Escolha inválida.")
        return null
    }
}
