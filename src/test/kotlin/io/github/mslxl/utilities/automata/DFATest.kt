package io.github.mslxl.utilities.automata

import org.junit.Test

class DFATest {
    @Test
    fun test() {
        val dfa = DFA()

        val tokens = dfa.process("1+234=95")
        println(tokens)
    }
}

class DFA : DeterministicFiniteAutomata<String, Token>() {
    override val start = state("START") {
        state on ('0'..'9') moveTo "digit"
        state onOneOf "+-*/%" moveTo "op"
        state on '=' moveTo "assign"
    }

    init {
        finalState("digit") {
            state on '0'..'9' moveTo "digit"
            output {
                return@output Token("number", it)
            }
        }
        finalState("op") {
            output {
                Token(it, "")
            }
        }
        finalState("assign") {
            output {
                Token("=", "")
            }
        }
    }
}


data class Token(val name: String, val attr: String) {
    override fun toString(): String {
        return "<$name,${if (attr.isEmpty()) " - " else attr}>"
    }
}