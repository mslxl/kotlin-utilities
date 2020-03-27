package io.github.mslxl.utilities.automata

abstract class DeterministicFiniteAutomata<StateName, Output> {
    private val stateSet = emptyMap<StateName, State<StateName>>().toMutableMap()

    abstract val start: State<StateName>
    private val statePath = arrayListOf<StateName>()
    val currentState: State<StateName>
        get() = stateSet[statePath.last()]!!


    /**
     * DFA 开机自检
     */
    private fun autoCheck() {
        stateSet.values.forEach { state ->
            state.transitionMap.values.forEach { targetState ->
                if (targetState !in stateSet) {
                    error("State '$targetState' is not exist in this DFA")
                }
            }
        }
    }

    private fun resetState() {
        statePath.clear()
        statePath.add(start.stateName)
    }

    inline fun state(stateTag: StateName, block: MutableState<StateName>.() -> Unit): State<StateName> {
        val state = MutableState(stateTag)
        block.invoke(state)
        addState(state)
        return state
    }

    inline fun finalState(stateTag: StateName, block: FinalState<StateName, Output>.() -> Unit): State<StateName> {
        val state = FinalState<StateName, Output>(stateTag)
        block.invoke(state)
        addState(state)
        return state
    }

    fun addState(state: State<StateName>) {
        stateSet[state.stateName] = state
    }

    fun process(str: String): List<Output> {
        this.autoCheck()
        this.resetState()
        val output = arrayListOf<Output>()
        // 读取头
        var head = 0
        // 当前token的开始位置
        var offset = 0

        while (head + offset != str.length) {
            val curChar = str[offset + head]
            // 当前状态对于 [curChar] 的有向边
            // 若为 null 则表示无对应
            val targetState = currentState.transitionMap[curChar]
            if (targetState == null) {
                // 出现错误：无对应有向边
                @Suppress("UNCHECKED_CAST")
                // 寻找上一个终态
                // 若不为 null 则找到
                // state to index
                // index 是上次 offset 下的 head
                val lastFinalState = statePath.mapIndexed { index, stateName -> stateName to index }
                        .findLast { stateSet[it.first] is FinalState<*, *> }
                        ?.let { stateSet[it.first]!! as FinalState<StateName, Output> to it.second }

                if (lastFinalState == null) {
                    error("No corresponding directed edge")
                } else {
                    // 恐慌模式

                    // 找到了上一个终态，进行输出
                    val tokenStr = str.substring(offset, offset + lastFinalState.second)
                    val ret = lastFinalState.first.getOutput(tokenStr)
                    output.add(ret)
                    // 重置 DFA 状态
                    this.resetState()
                    offset += lastFinalState.second
                    head = 0
                }
            } else {
                // 找到了对应的有向边，进入对应状态并再读一位
                head++
                statePath.add(targetState)
            }
        }
        // 处理最后数据
        if (currentState is FinalState<*, *>) {
            @Suppress("UNCHECKED_CAST")
            val currentState = currentState as FinalState<StateName, Output>
            val tokenStr = str.substring(offset, offset + statePath.size - 1)
            val ret = currentState.getOutput(tokenStr)
            output.add(ret)
        } else {
            error("No corresponding directed edge")
        }


        return output
    }


}

open class State<T>(val stateName: T) {
    protected val transitionMapMutable = hashMapOf<Char, T>()
    val transitionMap: Map<Char, T> = transitionMapMutable
}

inline class StateArrow<T>(val data: Pair<Char, MutableState<T>>) {
    infix fun moveTo(stateTag: T) {
        data.second.move(data.first, stateTag)
    }
}

open class MutableState<T>(stateTag: T) : State<T>(stateTag) {
    val state = this
    fun move(condition: Char, stateTag: T) {
        transitionMapMutable[condition] = stateTag
    }

    infix fun on(condition: Char): StateArrow<T> {
        return StateArrow(condition to this)
    }
}

open class FinalState<T, O>(stateTag: T) : MutableState<T>(stateTag) {
    internal var getOutput: ((str: String) -> O)? = null
    internal fun getOutput(str: String): O {
        return getOutput?.invoke(str) ?: error("No output function")
    }

    fun output(block: (str: String) -> O) {
        getOutput = block
    }
}