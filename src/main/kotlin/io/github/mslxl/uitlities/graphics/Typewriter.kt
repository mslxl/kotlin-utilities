package io.github.mslxl.uitlities.graphics

import io.github.mslxl.uitlities.log.log
import io.github.mslxl.uitlities.num.Counter
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage


open class Typewriter<PaperType : Paper>(private val paperSupport: PaperSupportDevice<PaperType>) {

    /**
     * 当前页
     */
    protected lateinit var paper: PaperType

    /**
     * 当前位置
     */
    protected var posX = 0
    val positionX get() = posX
    protected var posY = 0
    val positionY get() = posY
    /**
     * 当前行可用 横向像素
     */
    val availableWidth get() = paper.width - paper.margin.right - posX
    /**
     * 当前页可用 纵向像素
     */
    val availableHeight get() = paper.height - paper.margin.bottom - posY


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
    val currentPageNumber get() = currentPageNumberCounter.count

    /**
     * 上一字符的高度
     */
    private var lastCharHeight = 0

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
    }

    private fun resetPosY() {
        posY = paper.margin.top
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
    private fun getFont(size: Float = -1F, style: Int = -1): Font {
        var ft = font
        ft = if (size < 0) ft else ft.deriveFont(size)
        ft = if (style < 0) ft else ft.deriveFont(size)
        return ft
    }

    /**
     * 检查并移动位置( 仅能操作单字符，否则会计算错误
     */
    private fun checkPos(width: Int, height: Int) {
        while (needNextLineNumber.count > 0) {
            nextLineImmediately(height)
            needNextLineNumber.dec()
        }
        if (width < availableWidth) {
            moveRight(width)
        } else {
            if (height < availableHeight) {
                nextLineImmediately(height)
            } else {
                flush()
            }
        }
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
    fun nextLineImmediately(height: Int = lastCharHeight) {
        posY += (height + lineSpace)
        resetPosX()
        lastCharHeight = height
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
            checkPos(charWidth, charHeight)
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
    fun println(text: String = "", size: Float = -1F, style: Int = -1) = print(text + "\n", size, style)

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
                targetImage = targetImage.scale(targetWidth, targetHeight)
            }
        }
        nextLineImmediately()
        "$targetWidth X $targetHeight".log()
        paper.graphics.drawImage(targetImage, posX, posY, null)
        posY += targetHeight
        nextLine()
    }

    /**
     * 直接输出当前页
     */
    fun flush() {
        paperSupport.output(paper)
        feedPaper()
    }

    init {
        feedPaper()
    }
}
