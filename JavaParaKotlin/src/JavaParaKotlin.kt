fun main() {

    println("Digite o número desejado: ")
    val num : Int = readln().toInt();

    var numPrimo: Boolean = true

    if(num <= 1){
        numPrimo = false
    } else {
        for (i in 2..<num){
            if (num % i == 0){
                numPrimo = false
            break;
            }
        }
    }

    if(numPrimo){
        println("$num é um número primo")
    }
    else{
        println("$num não é um número primo")
    }
}