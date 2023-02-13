package iostreamers

import TextColor

class EventMessage {
    companion object {
        fun message(message: String, color: TextColor = TextColor.DEFAULT) {
            print("${color.code}${message}${TextColor.DEFAULT.code}")
        }

        fun messageln(message: String, color: TextColor = TextColor.DEFAULT) {
            println("${color.code}${message}${TextColor.DEFAULT.code}")
        }

        fun inputPrompt(message: String = "", delimiter: String = ": ") {
            print("${TextColor.GREEN.code}$message$delimiter${TextColor.DEFAULT.code}")
        }

        fun interactiveModeWasActivated() {
            println("███─█──█─███─███─████─████─████─███─███─█─█─███\n" +
                    "─█──██─█──█──█───█──█─█──█─█──█──█───█──█─█─█\n" +
                    "─█──█─██──█──███─████─████─█─────█───█──█─█─███\n" +
                    "─█──█──█──█──█───█─█──█──█─█──█──█───█──███─█\n" +
                    "███─█──█──█──███─█─█──█──█─████──█──███──█──███\n" +
                    "\n" +
                    "\n" +
                    "█───█─████─████──███───────────██\n" +
                    "██─██─█──█─█──██─█────────██─────██\n" +
                    "█─█─█─█──█─█──██─███──────────────██\n" +
                    "█───█─█──█─█──██─█────────██─────██\n" +
                    "█───█─████─████──███───────────██\n" +
                    "\n")
        }

        fun interactiveMode() {
            println("░▐██▒██▄░▒█▌▒█▀█▀█░▐█▀▀▒▐█▀▀▄─░▄█▀▄─░▐█▀█▒█▀█▀█░▐██▒▐▌▒▐▌░▐█▀▀     ▒▐██▄▒▄██▌▒▐█▀▀█▌░▐█▀█▄░▐█▀▀\n" +
                    "─░█▌▒▐█▒█▒█░░░▒█░░░▐█▀▀▒▐█▒▐█░▐█▄▄▐█░▐█──░░▒█░░─░█▌░▒█▒█░░▐█▀▀     ░▒█░▒█░▒█░▒▐█▄▒█▌░▐█▌▐█░▐█▀▀\n" +
                    "░▐██▒██░▒██▌░▒▄█▄░░▐█▄▄▒▐█▀▄▄░▐█─░▐█░▐█▄█░▒▄█▄░░▐██░▒▀▄▀░░▐█▄▄     ▒▐█░░░░▒█▌▒▐██▄█▌░▐█▄█▀░▐█▄▄")
        }
    }
}