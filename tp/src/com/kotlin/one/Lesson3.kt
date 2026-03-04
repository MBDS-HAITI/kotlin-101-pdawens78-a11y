package com.android.one

// Interfaces

interface Attacker {
    fun attack(target: Character)
}

interface Healer {
    fun heal(target: Character)
}

// Armes

class Weapon(val name: String, val power: Int)

// Classe abstraite

abstract class Character(
    val name: String,
    var hp: Int,   // corrigé (val → var)
    val weapon: Weapon
) {

    // Indiquer si le personnage est encore en vie
    val isAlive: Boolean
        get() = hp > 0

    // Reduit les points de vie
    fun takeDamage(amount: Int) {
        hp -= amount
        if (hp < 0) hp = 0
    }

    // Augmente les points de vie
    fun receiveHeal(amount: Int) {
        hp += amount
    }

    // Retourne les points de vie actuels
    fun getHP(): Int = hp

    // Methode abstraite que chaque sous-classe va implementer independamment
    abstract fun action(target: Character)
}

// Classes concretes

// Warrior
class Warrior(name: String) :
    Character(name, 100, Weapon("Sword", 20)),
    Attacker {

    override fun attack(target: Character) {
        target.takeDamage(weapon.power)
        println("$name attacks ${target.name} for ${weapon.power} damage.") // corrigé
    }

    override fun action(target: Character) {
        attack(target)
    }
} // <-- accolade manquante ajoutée ici

// Magus
class Magus(name: String) :
    Character(name, 120, Weapon("Magic Staff", 10)),
    Attacker,
    Healer {

    override fun attack(target: Character) {
        target.takeDamage(weapon.power)
        println("$name attacks ${target.name} for ${weapon.power} damage.")
    }

    override fun heal(target: Character) {
        val healPower = 25
        target.receiveHeal(healPower)
        println("$name heals ${target.name} for $healPower HP.")
    }

    override fun action(target: Character) {
        attack(target)
    }
}

// Colossus
class Colossus(name: String) :
    Character(name, 150, Weapon("Hammer", 15)),
    Attacker {

    override fun attack(target: Character) {
        target.takeDamage(weapon.power)
        println("$name smashes ${target.name} for ${weapon.power} damage.")
    }

    override fun action(target: Character) {
        attack(target)
    }
}

// Dwarf
class Dwarf(name: String) :
    Character(name, 70, Weapon("Axe", 35)),
    Attacker {

    override fun attack(target: Character) {
        target.takeDamage(weapon.power)
        println("$name strikes ${target.name} for ${weapon.power} damage.")
    }

    override fun action(target: Character) {
        attack(target)
    }
}

// Players

class Player(val name: String) {
    val team = mutableListOf<Character>()

    fun hasAliveCharacter(): Boolean =
        team.any { it.isAlive }

    fun showTeam() {
        team.forEachIndexed { index, character ->
            println("$index - ${character.name} (${character::class.simpleName}) HP: ${character.getHP()}")
        }
    }
}

// Activite du jeu

class Game {
    var turnCount = 0

    fun start(player1: Player, player2: Player) {
        println("--- Debut du Battle Arena ---")

        while (player1.hasAliveCharacter() && player2.hasAliveCharacter()) {
            turnCount++

            playTurn(player1, player2)
            if (!player2.hasAliveCharacter()) break

            playTurn(player2, player1)
        }

        println("\n=== Fin de la partie ===")
        println("Nombre total de tours joués : $turnCount")

        val winner = if (player1.hasAliveCharacter()) player1.name else player2.name
        println("Vainqueur : $winner")

        println("\nStatut final :")
        showFinalStatus(player1)
        showFinalStatus(player2)
    }

    private fun playTurn(active: Player, opponent: Player) {

        println("\nTour de ${active.name}")
        active.showTeam()

        println("Choisissez l’index de votre personnage :")
        val choice = readln().toInt()
        val character = active.team[choice]

        if (!character.isAlive) {
            println("Ce personnage est mort. Tour annulé.")
            return
        }

        println("Choisissez l’index de la cible :")
        opponent.showTeam()
        val targetChoice = readln().toInt()
        val target = opponent.team[targetChoice]

        if (!target.isAlive) {
            println("La cible est déjà morte.")
            return
        }

        character.action(target)

        println("${target.name} HP restants : ${target.getHP()}")
    }

    private fun showFinalStatus(player: Player) {
        println("${player.name} :")
        player.team.forEach {
            println("${it.name} - ${it::class.simpleName} - HP: ${it.getHP()}")
        }
    }
}

// MAIN

fun main() {

    val player1 = Player("Wedter Jerome")
    val player2 = Player("Dawens Pierre")

    // Équipe joueur 1
    player1.team.add(Warrior("Tanley Lafleur"))
    player1.team.add(Magus("Sachy Barreau"))
    player1.team.add(Dwarf("Farah Oreste"))

    // Équipe joueur 2
    player2.team.add(Colossus("Rikenson Leveille"))
    player2.team.add(Warrior("Dawens Pierre Jr"))
    player2.team.add(Magus("Wedter Jerome Jr"))

    val game = Game()
    game.start(player1, player2)
}