import java.io.InputStream
import java.util.*


/**
 * Класс для управления входным потоком для объекта класса Scanner
 */
class ScannerController {
    private var inputStream: InputStream? = null
    private var inputString: String? = null
    var scanner: Scanner = Scanner(System.`in`)
        private set

    constructor(inputStream: InputStream) {
        setInputStream(inputStream)
    }

    constructor(inputString: String) {
        setInputString(inputString)
    }

    fun setInputStream(inputStream: InputStream) {
        this.inputStream = inputStream
        this.inputString = null
        this.scanner = Scanner(inputStream)
    }

    fun setInputString(inputString: String) {
        this.inputStream = null
        this.inputString = inputString
        this.scanner = Scanner(inputString)
    }

    fun getInputStream() = inputStream
    fun getInputString() = inputString
}