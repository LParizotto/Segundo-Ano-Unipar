fun main() {

    println("Digite o número para calcular o fatorial: ")
    val num : Int = readln().toInt();

    var fatorial: Long = 1

    for(i in num downTo 2) {

        fatorial *= i
    }

    println("O fatorial de !$num é $fatorial")
}