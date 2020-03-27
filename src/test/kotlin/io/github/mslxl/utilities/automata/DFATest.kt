package io.github.mslxl.utilities.automata

import org.junit.Test

class DFATest {
    @Test
    fun test() {
        val dfa = DFA()

        val tokens = dfa.process("1+234=hoshiro")
        println(tokens)
    }
}

class DFA : DeterministicFiniteAutomata<String, Token>() {
    override val start = state("START") {
        ('0'..'9').map(this::on).forEach { state ->
            state moveTo "digit"
        }
        "+-*/%".map(this::on).forEach { state ->
            state moveTo "op"
        }
        state on '=' moveTo "assign"
    }

    init {
        finalState("digit") {
            ('0'..'9').map(this::on).forEach { state ->
                state moveTo "digit"
                output {
                    return@output Token("number", it)
                }
            }
        }
        finalState("op") {
            output {
                Token(it, "")
            }
        }
    }
}


data class Token(val name: String, val attr: String) {
    override fun toString(): String {
        return "<$name,${if (attr.isEmpty()) " - " else attr}>"
    }
}