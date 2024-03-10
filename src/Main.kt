package br.ifpb.pdm.praticas

class Livro(var titulo: String, var preco: Double) {
    override fun toString(): String {
        return "Livro: Título = $titulo, Preço = %.2f".format(preco)
    }
}

fun menu() {
    println("1 - Cadastrar livro")
    println("2 - Excluir livro")
    println("3 - Buscar livro")
    println("4 - Editar livro")
    println("5 - Listar livros")
    println("6 - Listar livros que começam com letra escolhida")
    println("7 - Listar livros com preço abaixo do informado")
    println("8 - Sair")
}

fun inputTitulo(): String {
    print("Digite o título do livro: ")
    return readLine() ?: ""
}

fun inputPreco(): Double {
    var preco: Double
    do {
        print("Digite o preço do livro: ")
        preco = readLine()?.toDoubleOrNull() ?: 0.0
        if (preco < 0) {
            println("O preço do livro não pode ser negativo. Tente novamente.")
        }
    } while (preco < 0)
    return preco
}

fun cadastrarLivro(repositorio: MutableList<Livro>) {
    val titulo = inputTitulo()
    val preco = inputPreco()

    repositorio.add(Livro(titulo, preco))
    println("\nCadastrado com sucesso!\n")
}

fun excluirLivro(repositorio: MutableList<Livro>) {
    val livro = buscarNome(repositorio)
    if (livro != null) {
        repositorio.remove(livro)
        println("Livro removido com sucesso!")
    } else {
        println("Nenhum livro encontrado para exclusão.")
    }
}

fun buscarNome(repositorio: MutableList<Livro>): Livro? {
    val titulo = inputTitulo()
    return repositorio.find { it.titulo == titulo }
}

fun editarLivro(repositorio: MutableList<Livro>) {
    val livro = buscarNome(repositorio)
    if (livro != null) {
        println("O que você deseja editar?")
        println("1 - Título")
        println("2 - Preço")
        print("Escolha uma opção: ")
        val opcao = readLine()?.toIntOrNull()

        when (opcao) {
            1 -> {
                print("Digite o novo título: ")
                val novoTitulo = readLine() ?: ""
                livro.titulo = novoTitulo
                println("Título atualizado com sucesso!")
            }
            2 -> {
                val novoPreco = inputPreco()
                livro.preco = novoPreco
                println("Preço atualizado com sucesso!")
            }
            else -> println("Opção inválida.")
        }
    } else {
        println("Livro não encontrado.")
    }
}

fun listar(repositorio: MutableList<Livro>) {
    println("Lista de Livros:")
    for ((index, livro) in repositorio.withIndex()) {
        println("$index - $livro")
    }
}

fun listarComLetraInicial(repositorio: MutableList<Livro>) {
    print("Informe a letra: ")
    val letra = readLine()?.firstOrNull()?.uppercaseChar() ?: return

    val livros = repositorio.filter { it.titulo.startsWith(letra) }
    if (livros.isNotEmpty()) {
        println("Livros que começam com '$letra':")
        livros.forEach { println(it) }
    } else {
        println("Nenhum livro encontrado com a letra '$letra'.")
    }
}

fun listarComPrecoAbaixo(repositorio: MutableList<Livro>) {
    val preco = inputPreco()
    val livros = repositorio.filter { it.preco < preco }
    if (livros.isNotEmpty()) {
        println("Livros com preço abaixo de R$$preco:")
        livros.forEach { println(it) }
    } else {
        println("Nenhum livro encontrado com preço abaixo de R$$preco.")
    }
}

fun main() {
    val repositorioLivros = mutableListOf<Livro>()
    repositorioLivros.add(Livro("Livro dos Livros", 999999.99))
    repositorioLivros.add(Livro("Turma da Monica", 4.99))
    repositorioLivros.add(Livro("Kotlin for Dummies", 29.99))
    repositorioLivros.add(Livro("A", 59.99))

    var opcao = 0
    while (opcao != 8) {
        menu()
        print("Digite a opção: ")
        opcao = readLine()?.toIntOrNull() ?: 8

        when (opcao) {
            1 -> cadastrarLivro(repositorioLivros)
            2 -> excluirLivro(repositorioLivros)
            3 -> {
                val livro = buscarNome(repositorioLivros)
                println(livro ?: "Livro não encontrado.")
            }
            4 -> editarLivro(repositorioLivros)
            5 -> listar(repositorioLivros)
            6 -> listarComLetraInicial(repositorioLivros)
            7 -> listarComPrecoAbaixo(repositorioLivros)
            8 -> println("Até a próxima :)")
            else -> println("Opção inválida.")
        }
        Thread.sleep(3000)
    }
}
