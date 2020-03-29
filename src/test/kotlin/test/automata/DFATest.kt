package test.automata

import org.junit.Test
import shouldBe

class DFATest {
    @Test
    fun number() {
        val dfa = object : DeterministicFiniteAutomata<String, Token>() {
            override val start = "START"

            init {
                state("START") {
                    state on ('0'..'9') moveTo "digit"
                    state onOneOf "+-*/%" moveTo "op"
                    state on '=' moveTo "assign"
                }
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

        val tokens = dfa.process("1+234=95")
        tokens.size shouldBe 5
    }

    @Test
    fun numberSystem() {
        val dfa = object : DeterministicFiniteAutomata<Int, Token>() {
            override val start = 0

            init {
                state(0) {
                    state on '1'..'9' moveTo 1
                    state on '0' moveTo 2
                }
                finalState(1) {
                    output {
                        Token("DEC", it)
                    }
                    state on '1'..'9' moveTo 1
                }
                finalState(2) {
                    output {
                        Token("DEC", it)
                    }
                    state onOneOf "xX" moveTo 6
                    state on '1'..'7' moveTo 4
                }
                finalState(4) {
                    state on '0'..'7' moveTo 4
                    output {
                        Token("OCT", it)
                    }
                }
                state(6) {
                    state on '1'..'9' moveTo 7
                    state on 'a'..'f' moveTo 7
                    state on 'A'..'F' moveTo 7
                }
                finalState(7) {
                    state on '1'..'9' moveTo 7
                    state on 'a'..'f' moveTo 7
                    state on 'A'..'F' moveTo 7
                    output {
                        Token("HEX", it)
                    }
                }
            }
        }

        dfa.process("1234").first().run {
            name shouldBe "DEC"
            attr shouldBe "1234"
        }
        dfa.process("0").first().run {
            name shouldBe "DEC"
            attr shouldBe "0"
        }
        dfa.process("0x66ccff").first().run {
            name shouldBe "HEX"
            attr shouldBe "0x66ccff"
        }
        dfa.process("0X66CCFF").first().run {
            name shouldBe "HEX"
            attr shouldBe "0X66CCFF"
        }
        dfa.process("012412").first().run {
            name shouldBe "OCT"
            attr shouldBe "012412"
        }
    }
}


internal data class Token(val name: String, val attr: String) {
    override fun toString(): String {
        return "<$name,${if (attr.isEmpty()) " - " else attr}>"
    }
}