fun main(){

    val numeroAleatorio = (1..100).random()

    println("Tenta adivinhar um número de 1 a 100, Boa sorte!!")

    var tentativas: Int = 0

    while (true){

        println("Digite seu palpite: ")
        val numeroPalpite = readln().toInt()
        tentativas++

        if (numeroAleatorio == numeroPalpite){
            println("Parabéns você acertou o número em $tentativas tentativas!")
            break
        }else if(numeroPalpite < numeroAleatorio){
            println("Tente um número maior")
        }else{
            println("Tente um número menor")
        }

    }

}