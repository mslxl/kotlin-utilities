package io.github.mslxl.uitlities.graphics

interface PaperSupportDevice<E : Paper> {
    fun feed(): E

    fun output(paper: E)
}