package io.github.mslxl.uitlities.graphics

class PaperSupportDevice private constructor() {
    companion object {
        operator fun invoke(block: PaperSupportDevice.() -> Unit) = PaperSupportDevice().apply(block)
    }

    internal var feed: () -> Paper = {
        error("No feed")
    }
    internal var output: (Paper) -> Unit = {

    }

    fun feed(block: () -> Paper) {
        feed = block
    }

    fun output(block: (paper: Paper) -> Unit) {
        output = block
    }
}