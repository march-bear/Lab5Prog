class EventMessage {
    companion object {
        private const val redColor: String = "\u001b[31m"
        private const val greenColor: String = "\u001b[32m"
        private const val yellowColor: String = "\u001b[33m"
        private const val blueColor: String = "\u001b[34m"
        private const val defaultColor: String = "\u001b[39m"

        fun commandNotFound() {
            println("${redColor}Введенная команда не поддерживается${defaultColor}")
        }

        fun redMessage(message: String) {
            print("${redColor}${message}${defaultColor}")
        }

        fun yellowMessage(message: String) {
            print("${yellowColor}${message}${defaultColor}")
        }

        fun blueMessage(message: String) {
            print("${blueColor}${message}${defaultColor}")
        }

        fun greenMessage(message: String) {
            print("${greenColor}${message}${defaultColor}")
        }

        fun defaultMessage(message: String) {
            print(message)
        }
        fun redMessageln(message: String) {
            println("${redColor}${message}${defaultColor}")
        }

        fun yellowMessageln(message: String) {
            println("${yellowColor}${message}${defaultColor}")
        }

        fun blueMessageln(message: String) {
            println("${blueColor}${message}${defaultColor}")
        }

        fun greenMessageln(message: String) {
            println("${greenColor}${message}${defaultColor}")
        }

        fun defaultMessageln(message: String) {
            println(message)
        }

        fun inputPrompt(message: String = "", delimiter: String = ": ") {
            print("$greenColor$message$delimiter$defaultColor")
        }
    }
}