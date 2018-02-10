@file:Suppress("unused")

package io.github.mslxl.utilities.log

@JvmOverloads
fun codeLocation(upOffset: Int = 0): CodeLocation {
    try {
        // 丢个错误定位，反正你们看不见错误信息对吧
        throw RuntimeException("(<ゝω·)☆ 诶嘿")
    } catch (e: Exception) {
        val element = e.stackTrace[upOffset + 2]
        return CodeLocation(element.className, element.fileName, element.methodName, element.lineNumber)
    }
}

// 谁调用了 调用 whoCalledMe方法 的方法
fun whoCalledMe() = codeLocation(1)

fun whoCalledMyParent() = codeLocation(2)
fun whoCalledMyGrandparent() = codeLocation(3)

@Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")
class CodeLocation(val className: String,
                   val fileName: String,
                   val methodName: String,
                   val lineNumber: Int) {
    // IDEA 可以直接解析这个链接并跳转
    val intellijLink = "$className.$methodName($fileName:$lineNumber)"

    override fun toString(): String {
        return intellijLink
    }

    fun printLocation(tag: String = "CodeLocation") {
        intellijLink.log(tag)
    }
}