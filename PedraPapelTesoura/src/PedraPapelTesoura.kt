fun main(){

    var vitorias: Int = 0
    var derrotas: Int = 0

    do{

        val lista = listOf("Pedra", "Papel", "Tesoura")

        val escolhaAleatoria = lista.random()


        println("_____________________________________")
        println("Vamos jogar Pedra, Papel e Tesoura.")
        println("1- Pedra")
        println("2- Papel")
        println("3- Tesoura")
        println("Escolha entre 1, 2 ou 3: ")
        print("")
        println("_____________________________________")
        val opcao = readln()

        val escolhaUsuario = when(opcao){
            "1" -> "Pedra"
            "2" -> "Papel"
            "3" -> "Tesoura"
            else -> {
                println("Opção inválida, tente novamente.")
                continue
            }
        }

        println("Você escolheu $escolhaUsuario")
        println("--------------------------------")
        println("A máquina escolheu $escolhaAleatoria")
        println("--------------------------------")

        when {
            escolhaUsuario == escolhaAleatoria -> println("Empate")
            (escolhaUsuario == "Pedra" && escolhaAleatoria == "Tesoura") ||
                    (escolhaUsuario == "Papel" && escolhaAleatoria == "Pedra") ||
                    (escolhaUsuario == "Tesoura" && escolhaAleatoria == "Papel") -> {
                        println("Ganhou!!")
                    vitorias++}
            else -> {
                println("Perdeu!!")
                derrotas++
            }

        }

            println("Total de vitorias: $vitorias")
            println("Total de derrotas: $derrotas")

        println("Deseja jogar novamente? (s/n)")
        val resposta = readln().lowercase()
        if (resposta == "n"){

            break
        }


    }while(true)

    println("Obrigado por jogar! Placar final -> Vitórias: $vitorias | Derrotas: $derrotas")





}