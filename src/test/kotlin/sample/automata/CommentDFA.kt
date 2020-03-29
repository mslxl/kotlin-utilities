package sample.automata

import io.github.mslxl.utilities.automata.DeterministicFiniteAutomata

class CommentDFA : DeterministicFiniteAutomata<Int, String>() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val list = CommentDFA().process("""
                /*This is a comment*/
                public class Main(){
                
                }
                /*This is a comment too*/
            """.trimIndent())
            list.filter { it != "NOT_A_COMMENT" }.forEach(::println)
        }
    }

    override val start = 0

    init {
        state(0) {
            // this.move('/', 1)
            state on '/' moveTo 1
            state defaultTo -1
        }
        finalState(-1) {
            // character which not is comment

            output {
                "NOT_A_COMMENT"
            }
        }
        state(1) {
            state on '*' moveTo 2
        }
        state(2) {
            state defaultTo 2
            state on '*' moveTo 3
        }
        state(3) {
            state defaultTo 2
            state on '*' moveTo 3
            state on '/' moveTo 4
        }
        finalState(4) {
            output {
                it
            }
        }
    }

}