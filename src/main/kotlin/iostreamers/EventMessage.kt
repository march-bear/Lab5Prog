package iostreamers

class EventMessage {
    companion object {
        fun message(text: String, color: TextColor = TextColor.DEFAULT): String {
            return "${color.code}${text}${TextColor.DEFAULT.code}"
        }

        fun printMessage(message: String? = null) {
            println(message ?: "")
        }

        fun inputPrompt(text: String = "", delimiter: String = ": ") {
            print("${TextColor.GREEN.code}$text$delimiter${TextColor.DEFAULT.code}")
        }

        fun interactiveModeMessage() {
            println("░▐██▒██▄░▒█▌▒█▀█▀█░▐█▀▀▒▐█▀▀▄─░▄█▀▄─░▐█▀█▒█▀█▀█░▐██▒▐▌▒▐▌░▐█▀▀     ▒▐██▄▒▄██▌▒▐█▀▀█▌░▐█▀█▄░▐█▀▀\n" +
                    "─░█▌▒▐█▒█▒█░░░▒█░░░▐█▀▀▒▐█▒▐█░▐█▄▄▐█░▐█──░░▒█░░─░█▌░▒█▒█░░▐█▀▀     ░▒█░▒█░▒█░▒▐█▄▒█▌░▐█▌▐█░▐█▀▀\n" +
                    "░▐██▒██░▒██▌░▒▄█▄░░▐█▄▄▒▐█▀▄▄░▐█─░▐█░▐█▄█░▒▄█▄░░▐██░▒▀▄▀░░▐█▄▄     ▒▐█░░░░▒█▌▒▐██▄█▌░▐█▄█▀░▐█▄▄")
        }

        fun oops(): String {
            return ("                :;                        :░¡.    .. '\".        ,▒▒▒▒▒▒▓▒▒╫╣░▒▒░░╫▓▒░▒▒░░░░░░,:¡.\n" +
                    "                      ╥@@┐              -,,',  `'`              ╙▓▒▓▓▒╫▓▓▒▓╣▒▓▓▒▒▓▓▒░▒▒▒░░▒▒▒░░░¡┌\n" +
                    "                     `└░░└,,,¿░░,..:,:',''`\".   ,                 `░▒▒▓█▓▒▓▌╜░╜▒▒▓▓▒ ╙╜└   j▒░░▒░░,\n" +
                    "          .          .,┌╓╖¡\",¡░░\"¡░░*'┐'┌     .¡, .`     `           ╙▀█▓▓██▄∩¡░▒▓█▒:     ┌╫▒▒▒▒▒░¡.\n" +
                    "         ▓▓,     ```''''j▓▌░\"\"└\"''`:¡\"\":\"`    `'r'\"┌,,.`,.'            └└└└▀▀└`  ``        `' `\"\"└'┌\n" +
                    "        .▒▒¡'         `,░▒░░¡,'`` ``░\"''``    `¡░,┘\"ª░?¼╕'   .            ╖;,,,.,╓╓,╓╓┌,.,,,. ,┌¡,.░\n" +
                    "  `     ░░▒¡┌         `┌'\"┘░▄▄   `,';░=,.``'╓@@╫╣▒ß░░░░░┌.¡░\"             ▐▓▒▒▒▒▓██▓████▓████▓▓▓▓▒\"░\n" +
                    "        ░▒╜\"¡          \",  ;██ `  '»└,¡` .╓████▓▓╬▒▒░░░░░░¡;,,.          '▀▓▒▒▒▒▓███████▓████▓██▓▒=\"\n" +
                    "`` ...,,░▒░░░¡;;;;;;;;;¡░░░╫▓¡,,...,¡'..;g▓████▓╣▓▒▒▒░░░░░░░░░░░¡,       v¿▒▒▒▒▒▓██▓████▓████▓██▓▒..\n" +
                    "░░░░░░░▒▒▒▒▒▒▒▒▒▒▒▒▒░▒▒░░░░░░░░░┬¡¡'╓░░▒▒▒████▓▓▓Ñ▒▒▒▒░░░░░░░░░░░░¡.      ²░┘░░▒███▓█████████▓██▓▒░▒\n" +
                    "░░░░░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒░░░░░░░░\"\"'└▒▒▓██████▓▒▓▒▒▒▒▒░░░░░░░░░░░░└¡      :'   `╣████████████████▒\"╙\n" +
                    "└└**░░░░╜╜▒╜╜▒▒▒▒╜▒▒▒▒▒░░░░░░░░░░'` #▒▓████▓▒░░░╙╙╜░▒░░▒░░░░░░░░░░¡¡`    ¡¡¡¡,,;▒▒▒▒▓▓▓╢╢▓▓▓▒▓▓▒▒▒!¡\n" +
                    "╣╬╬╬╬▓▓▓▓▓█████▓▓▓▓▓▓█████▄▄▄▄▄▄▄φ╦~▒▒██████▓▓▓▓╫▓@░░▒▒▒░░░└\"\"\"\"└░░░~    ║▒▒▒▒▒▒▒▒▒▒▒░░░¡¡¡¡┌,~.`\n" +
                    "░░░░░░░▒▒▒▒▒▒▒░░░░░░░░░░░░░░░░░░░▌▒░▒▓████▓▓▓▒▒░▒╫▓▓▓▓▒░░░░░░░░,.\"░░;    ▒▒▒╢▒▒╫▓▓▓▒▒▒░░░░░░░░¡┌~,.\n" +
                    "░░░░░▒▒▒▒▒▒▒▒▒▒▒▒▒░░░░░░░░░░░░░░j█▓▒░████████▌ ⌐░░░░█▌░░└└└░░░░░░¡░░'   :░▒▒▒▒╣▒▒▓▓▓▒░'    ```\n" +
                    "░░░░░▒▒▒▒▒▒▒░░░░░░░░░░░░░░░░░░░░▐▌▒▒▓████▓██▓▒╓░░m▒██░░░░\"┌⌐.`\"¡└░░░` `,░¡└░▒▒▒╢╣▒▒▒▓▓▌╖          .p\n" +
                    "░░░░░░░░░░░░░░░░░░░░░░░░░░░└└└└¡▐▌█▒█████▓▓▓▓▒▒▒▓███░░░░░¡¡:.=¡░░░░'.~░░░░░░░░▒▒▒▒▒▒▒▒╢▓▓φ,       ▐█\n" +
                    "''\"\"\"└└└└└└╠▒█████@█▌██'░⌐', ``└░████████▓▓▓▓▓╣╢██▓▒░░░░░░░░░░░░░└░ :|░░░░░░░░└░▒▒▒▒▒▒░░▒▒╣N,     ▐█\n" +
                    "   ```````'░▐█████▌█▌██ ░~,:,,.┌░░█████████▓▓▒░▄██▓░░░░░░░░░░░░░░┌¡ ░██▄└░░░░░░░└░▒▒░░░░░░▒▒▒╖g▓Ñ░▒▀\n" +
                    "`.....````'░▐▓████░▒h▒▒ ░`'\"'::░░░▓████████▌░▄▓▓@▓█▒▒░░░░░░░░░░░░¡¡░░┘▀██▄└░░░└░░¡¡░░░░░░░░░░▒╫▓▓▒░r\n" +
                    ",┌¡┌¡¡┌┌¡,┌¡║▒████~▒=░░ ░~'¡`┌~░░░║██████▓▒▓█▓╣╢▀╜░░¡¡░░░░░░░░░░░¡░░\"\"¡¡▀██▄¿░░└░░└¡¡└└░░░░░░░▒▓███▄\n" +
                    "':┌┌¡└¡¡╘░¡¡▒▒█▌▓█┌░⌐░░'░¡:░.┌~░░░'▓████▓▒└░▒▄▄╓,    '\"└░░░░░░░░░¡\",,'\"\"\"┌▀██▌/└└└\"\"\"\"\"\"└└└\"\"` └▒███\n" +
                    ":┌~┌¡¡¡¡└└░░▒▒▓░▒▒!░¡░░┌░░└░~┌┌░░``j█▓▓█░▄▄g╖@▒▒▒░░░¡~,  '└░░░░░¡\"~~,~~~\"┌:~╙▀▀▒░\",'.       .;░░▒▒▓█\n" +
                    "░░¡░░░¡¡¡¡░░░░▒=░░░░¡░░¡└░¡¡`¡¡░░``j█▒█▒╫▓███▓▒╜░░░░░░¡¡,, \"░░░░¡\"~\"~::~\"┌~,~~:.....``..`.`''└░░░░▒▓\n" +
                    "░░▒▒▒▒▒▒░░░░░▒▒┌░░░░¡░░¡░░¡┌~░░░▒`'j█▓▒▓▓▓██▓▒▒░,`   '┌└¡¡¡`░¡░\"\"\":\"~::~'~\"\"~::''`'`'''`'`.`'`'\"░░░▒\n" +
                    "░░▒▒▒▒▒▒▒▒░▒▒▒▒▒▒░▒░░░░└░░░¡┌░░░░~'▐███▓▒▒▓█▓▒░░¡¡¡~┌¡└░░░░└':¡~:~~~:~',''::~~~'`':''`',,,,''`.''\"└░\n" +
                    "▒▒▒╫▓▓▓▓▒░░▒▒▒▓▒▒▒▒▒▒░░░░░░░¡░░░░,┌█████▓▓Ñ▒░░░░¡~~\".\"\"'`..,┌¡~:~:┌~:~~,``'''':''',''''''''''''''``'\n" +
                    "▒▒▒▒▒▒╢╢▒░░▒▒╢▒▒▒▒▒▒▒▒▒╢▒▒╣▒╜▒▒▒╜░███████▓▓╣▒▒░░░,,,,,:¡┌┌¡░!¡┌~:` `'\"~~~'''',~'':'''''''```'',,'``\n" +
                    "░░└░░░░░░¡└░║╣░▒▒░▒░░▒▒▒▒░░┘\"`,'▄▓███████▓▓▓▒▒░░░░░¡¡¡¡░░░└└!¡¡:~       `'':,:,,''`''''''''``'`''``\n" +
                    "└\"~└░░░░░¡└└▒▒▒▒▒▒╢▒░░╙╙└'.``,\",`▀██████▓██▓▒▒▒░░░░░░░░░░░└¡└░¡'            ':,:~:~,`'`',`'''``'```\n" +
                    "'''''\"¡╘└┌¡¡▒▒░╢░┘└`````'.'`.'~' '██████▓███▒░░░░░░░░░░░░░░░└\"                  '':\"~'~~````'''``\n" +
                    "    `''\"\"\";¡└```   ```'`.''.~':,` ▐█████▓▒▓▓▒░░░░▒▒▒░░░░░░░░`                      `,,',`''''''`\n" +
                    "       `,¡\"''`   ``````'`'`'''',, '▀████▓▒▒▒▒░░▒▓█▒▒░░░░░░\"                           ',''''````\n" +
                    "`'\"*░░░\"~\"~'`.```.``.''``'`''.'':. '└▒▓█▓▒░░░▒▓██▒▒▒░░░²`                                `\n" +
                    "       ```,..``'`` `.~`.'',''`'`.',.`¡▄▒░▒░░░╫█▓▒╢╨┘\"```                `\n" +
                    "           ````,` `,'`.'''''''```:`▄██████`.`¡ç¡   ,,, ;;   ,;;`        `\n" +
                    "               ```:'`'','`''``,'`'`███w███W▐██████ ███▀███ ██████       `.\n" +
                    "                   `''`'``.'``'`.,`███░███▒███∩███~███ ███ ████▌╜        `\n" +
                    "                    `'~::.,.,```.``███∩███Ü███w███⌐███ ███ ▄▌████⌐      ╓╓╓,,,╓,╓=╓,,,,,,,,.....   .\n" +
                    "                        ```'\"':.,»'▀██████¡▀██▄███ ███▄███ ██████╛     .▒K⌠╖]╓╥╓▒╓├╓╙╓░]▒,?,,,;┌[\n" +
                    "                           `  ``'\"░\"░▀▀▀░'``┘▀▀▀░``███»▀▀,  ╜▀▀▀:          `\n" +
                    "                   ...............,~~¡¡¡└¡┌┌┌┌~,......,,,.,,.................\n" +
                    "┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌]▓▓▒▒▓▓▌▌▒ß▒▓▒▒Ñ▒╫▌▒▓▒▓▓▓∩\n" +
                    "\n" +
                    "Oops, что-то конкретно пошло нет. Скоро мы это пофиксим!\n" +
                    "До тех пор не делайте то, что вы сейчас сделали.\n" +
                    "Спасибо, если доверяете нам!\n" +
                    "\n" +
                    "Почта для связи со службой поддержки: razmech24@tals.ya")
        }
    }
}