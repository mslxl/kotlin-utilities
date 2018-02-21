package io.github.mslxl.utilities.graphics

import io.github.mslxl.utilities.num.Counter
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage


@Suppress("MemberVisibilityCanBePrivate")
open class Typewriter<PaperType : Paper>(private val paperSupport: PaperSupportDevice<PaperType>) {

    /**
     * 当前页
     */
    protected lateinit var paper: PaperType

    /**
     * 当前位置
     */
    protected var posX = 0
    @Suppress("unused")
    val positionX
        get() = posX
    protected var posY = 0
    @Suppress("unused")
    val positionY
        get() = posY
    /**
     * 当前行可用 横向像素
     */
    val availableWidth get() = lineWidth - posX
    /**
     * 当前页可用 纵向像素
     */
    val availableHeight get() = paperHeight - posY

    val lineWidth get() = paper.width - paper.margin.right
    val paperHeight get() = paper.height - paper.margin.bottom

    /**
     *  间距
     */
    var fontSpace = 2
    var lineSpace = 5

    /**
     * 下次位置变化时 下移行数
     */
    private var needNextLineNumber = Counter()

    var font = Font(Font.MONOSPACED, Font.PLAIN, 12)
    var color = Color.BLACK!!

    private val currentPageNumberCounter = Counter(0)
    @Suppress("unused")
    val currentPageNumber
        get() = currentPageNumberCounter.count

    /**
     * 上一字符的高度
     */
    private var lastCharHeight = 0
    private var lastCharWidth = 0
    /**
     * 重置位置到左上角
     */
    private fun resetPos() {
        resetPosX()
        resetPosY()
        needNextLineNumber.inc()
    }

    private fun resetPosX() {
        posX = paper.margin.left
        lastCharWidth = 0
    }

    private fun resetPosY() {
        posY = paper.margin.top
        lastCharWidth = 0
    }

    /**
     * 进纸 （ 换新纸
     */
    private fun feedPaper() {
        paper = paperSupport.feed()
        needNextLineNumber = Counter()
        resetPos()
        currentPageNumberCounter.inc()
        lastCharHeight = 0
    }

    /**
     * 根据大小和样式获取字体对象
     */
    protected fun getFont(size: Float = -1F, style: Int = -1): Font {
        var ft = font
        ft = if (size < 0) ft else ft.deriveFont(size)
        ft = if (style < 0) ft else ft.deriveFont(size)
        return ft
    }

    /**
     * 检查并移动位置( 仅能操作单字符，否则会计算错误
     * 返回是否需要重绘
     */
    protected fun checkPos(width: Int, height: Int): Boolean {
        while (needNextLineNumber.count > 0) {
            nextLineImmediately(lastCharHeight)
            // 覆盖原本用来别处使用时储存的 lastCharHeight
            lastCharHeight = height
            needNextLineNumber.dec()
        }
        if (lastCharWidth > availableWidth) {
            nextLineImmediately(height)
        }
        var needRepaint = false
        moveRight(lastCharWidth)

        if (height >= availableHeight) {
            flush()
            needRepaint = true
        }



        if (width > 0) {
            lastCharWidth = width
        }
        return needRepaint
    }

    /**
     * 下一字符时换一行 ，高度为下一字符高度
     */
    fun nextLine() {
        needNextLineNumber.inc()
    }

    /**
     * 立即换一行 默认为下一字符的高度
     */
    @JvmOverloads
    fun nextLineImmediately(height: Int = lastCharHeight) {
        posY += (height + lineSpace)
        resetPosX()
        lastCharHeight = height
        lastCharWidth = 0
    }

    /**
     * 立即右移
     */
    fun moveRight(width: Int) {
        posX += (width + fontSpace)
    }

    /**
     * 打印 Char
     */
    @JvmOverloads
    fun print(char: Char, size: Float = -1F, style: Int = -1) {
        if (char == '\n') {
            nextLine()
            return
        }

        paper.graphics.let {
            it.font = getFont(size, style)
            it.color = color
            val metrics = it.fontMetrics
            val charWidth = metrics.charWidth(char)
            val charHeight = metrics.height
            if (checkPos(charWidth, charHeight))
                print(char, size, style)
            it.drawString(char.toString(), posX, posY)
        }

    }

    @JvmOverloads
    fun print(text: String, size: Float = -1F, style: Int = -1) {
        text.forEach {
            print(it, size, style)
        }
    }
    @JvmOverloads
    fun println(text: String = "", size: Float = -1F, style: Int = -1) {
        print(text + "\n", size, style)
    }

    @JvmOverloads
    fun insertImage(image: BufferedImage, zoom: Boolean = true) {
        var targetWidth = image.width
        var targetHeight = image.height
        var targetImage = image

        if (zoom) {
            if (availableHeight < targetHeight || availableWidth < targetWidth) {
                val heightDifference = targetHeight - availableHeight
                val widthDifference = targetWidth - availableWidth
                val proportion = if (heightDifference > widthDifference) {
                    // According height
                    availableHeight.toFloat() / targetHeight
                } else {
                    // According width
                    availableWidth.toFloat() / targetWidth
                }

                targetHeight = (targetHeight * proportion).toInt()
                targetWidth = (targetWidth * proportion).toInt()
                if (targetHeight <= 0 || targetWidth <= 0){
                    flush()
                    insertImage(image, zoom)
                    return
                }
                targetImage = targetImage.scale(targetWidth, targetHeight)
            }
        }
        checkPos(0, targetHeight)
        paper.graphics.drawImage(targetImage, posX, posY, null)
        nextLine()
        nextLine()
    }

    /**
     * 大概就是这样的一个效果
     * [left] = "title "
     * [fillChar] = '-'
     * [right] = "10"
     *
     * title ------------------------- 10
     */
    fun fillLine(left: String, fillChar: Char = '-', right: String, size: Float = -1F, style: Int = -1) {
        if ('\n' in left || '\n' in right) {
            error("can not fill line with \\n")
        }
        paper.graphics.let {
            it.font = getFont(size, style)
            it.color = color
            val metrics = it.fontMetrics
            checkPos(0, metrics.height)
            print(left, size, style)
            val rightWidth = metrics.stringWidth(right) + right.length * fontSpace
            if (rightWidth < availableWidth) {
                val stepLong = metrics.charWidth(fillChar) + fontSpace
                while (availableWidth - stepLong > rightWidth) {
                    print(fillChar, size, style)
                }
            }
            print(right, size, style)
            nextLine()
        }
    }

    /**
     * 用 [string] 来填充本行
     */
    fun fillLine(string: String, size: Float = -1F, style: Int = -1) {
        if ('\n' in string) {
            error("can not fill line with \\n")
        }
        paper.graphics.let {
            it.font = getFont(size, style)
            it.color = color
            val metrics = it.fontMetrics
            checkPos(0, metrics.height)
            val stringWidth = metrics.stringWidth(string) + string.length * fontSpace
            while (availableWidth > stringWidth) {
                print(string)
            }
            nextLine()
        }
    }

    /**
     * 直接输出当前页
     */
    fun flush() {
        paperSupport.output(paper)
        feedPaper()
    }

    @Suppress("unused")
    fun reboot() {
        needNextLineNumber.default()
        currentPageNumberCounter.default()
        feedPaper()
    }

    init {
        feedPaper()
    }
}