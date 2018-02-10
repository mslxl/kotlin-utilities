package io.github.mslxl.utilities.graphics

interface PaperSupportDevice<E : Paper> {
    fun feed(): E

    fun output(paper: E)
}