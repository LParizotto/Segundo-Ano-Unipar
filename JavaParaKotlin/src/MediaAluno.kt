fun main(){

    var soma: Double = 0.0
    val qtdNotas: Int = 3

    for(i in 1..qtdNotas){
        print("Digite a nota $i :")
        soma += readln().toDouble()
    }
    val media: Double = soma / qtdNotas
    println("Media = $media")

    if(media>7){
        println("Parabéns você foi aprovado!")
    }else if(media >=5){
        println("Ficou de recuperação")
    }else{
        println("Foi reprovado seu burro!")
    }



}