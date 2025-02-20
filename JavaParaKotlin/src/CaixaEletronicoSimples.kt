fun main(){

    var saldo: Double = 1000.0;
    var opcao: Int = 0;

        while (true){

            println("Saldo atual: R$$saldo");
            println("1 - Sacar");
            println("2 - Depositar");
            println("3 - Sair");
            println("Escolha uma opção: ");
            opcao = readln().toInt();

            if (opcao == 1){

                println("Digite o valor para sacar: ")

                var valor : Double = readln().toDouble()

                if (valor > saldo){
                    println("Valor inválido")
                }else{
                    saldo -= valor;
                    println("Saque realizado!")
                }

            }else if(opcao == 2){
                println("Digite o valor para depositar: ")
                var valor: Double = readln().toDouble()
                saldo += valor;
                println("Depósito realizado!");
            }else if(opcao == 3){
                println("Saindo!!")
                break
            }else{
                println("Opção inválida")
            }

        }
    }
