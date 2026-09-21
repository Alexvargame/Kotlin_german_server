package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.entities.SentenceEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Add_sentences(private val context: Context) {

    fun add_sentences() {
        val db = AppDatabase.getInstance(context)
        val sentenceDao = db.sentenceDao()

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("ADD_SENTENCES", "started")

            val sentences = listOf(
                // === ПРОСТЫЕ СО ВРЕМЕНЕМ (20) ===
                SentenceEntity(sentence = "Morgens trinke ich immer Kaffee.", translation = "По утрам я всегда пью кофе.", description = "morgens на 1 → trinke на 2 → ich на 3 (инверсия)."),
                SentenceEntity(sentence = "Abends sehe ich gern fern.", translation = "По вечерам я охотно смотрю телевизор.", description = "abends на 1 → sehe на 2 → ich на 3."),
                SentenceEntity(sentence = "Nachmittags mache ich Sport.", translation = "Днём я занимаюсь спортом.", description = "nachmittags на 1 → mache на 2 → ich на 3."),
                SentenceEntity(sentence = "Gestern war ich beim Arzt.", translation = "Вчера я был у врача.", description = "Präteritum. gestern на 1 → war на 2 → ich на 3."),
                SentenceEntity(sentence = "Vorgestern habe ich sie getroffen.", translation = "Позавчера я её встретил.", description = "Perfekt. vorgestern → habe на 2 → getroffen в конце."),
                SentenceEntity(sentence = "Nächsten Monat ziehe ich um.", translation = "В следующем месяце я переезжаю.", description = "nächsten Monat на 1 → ziehe на 2 → ich на 3."),
                SentenceEntity(sentence = "Letzte Woche war ich krank.", translation = "На прошлой неделе я был болен.", description = "Präteritum. letzte Woche на 1 → war на 2 → ich на 3."),
                SentenceEntity(sentence = "Heute Morgen habe ich verschlafen.", translation = "Сегодня утром я проспал.", description = "Perfekt. heute Morgen → habe на 2 → verschlafen в конце."),
                SentenceEntity(sentence = "In einer Stunde kommt der Bus.", translation = "Через час придёт автобус.", description = "in einer Stunde на 1 → kommt на 2 → der Bus на 3."),
                SentenceEntity(sentence = "Seit drei Jahren lerne ich Deutsch.", translation = "Три года я учу немецкий.", description = "seit + Dat. seit drei Jahren на 1 → lerne на 2."),
                SentenceEntity(sentence = "Bis morgen muss ich das erledigen.", translation = "До завтра я должен это сделать.", description = "bis morgen на 1 → muss на 2 → ich на 3."),
                SentenceEntity(sentence = "Während des Urlaubs lese ich viel.", translation = "Во время отпуска я много читаю.", description = "während + Gen. На 1 месте → lese на 2."),
                SentenceEntity(sentence = "Nach dem Frühstück gehe ich zur Arbeit.", translation = "После завтрака я иду на работу.", description = "nach + Dat. На 1 месте → gehe на 2."),
                SentenceEntity(sentence = "Vor dem Essen wasche ich mir die Hände.", translation = "Перед едой я мою руки.", description = "vor + Dat. Возвратный глагол с Dativ (mir)."),
                SentenceEntity(sentence = "Zwischen den Feiertagen arbeite ich nicht.", translation = "Между праздниками я не работаю.", description = "zwischen + Dat (время). nicht в конце."),
                SentenceEntity(sentence = "Am Anfang war es schwierig.", translation = "В начале было трудно.", description = "am Anfang на 1 → war на 2 → es на 3."),
                SentenceEntity(sentence = "Zum Schluss hat er noch etwas gesagt.", translation = "В конце он ещё что-то сказал.", description = "zum Schluss на 1 → hat на 2 → er на 3."),
                SentenceEntity(sentence = "Im Herbst fallen die Blätter.", translation = "Осенью падают листья.", description = "im Herbst на 1 → fallen на 2 → die Blätter на 3."),
                SentenceEntity(sentence = "Im Winter fahre ich Ski.", translation = "Зимой я катаюсь на лыжах.", description = "im Winter на 1 → fahre на 2 → ich на 3."),
                SentenceEntity(sentence = "Im Frühling blühen die Blumen.", translation = "Весной цветут цветы.", description = "im Frühling на 1 → blühen на 2 → die Blumen на 3."),

// === СЛОЖНОСОЧИНЁННЫЕ (40) ===
                SentenceEntity(sentence = "Ich frage mich, ob das stimmt.", translation = "Я спрашиваю себя, правда ли это.", description = "ob — косвенный вопрос, stimmt в конце."),
                SentenceEntity(sentence = "Er will wissen, wann der Zug fährt.", translation = "Он хочет знать, когда едет поезд.", description = "wann — косвенный вопрос, fährt в конце."),
                SentenceEntity(sentence = "Sie erklärt mir, wie das funktioniert.", translation = "Она объясняет мне, как это работает.", description = "wie — косвенный вопрос, funktioniert в конце."),
                SentenceEntity(sentence = "Ich verstehe nicht, warum er so reagiert.", translation = "Я не понимаю, почему он так реагирует.", description = "warum — косвенный вопрос, reagiert в конце."),
                SentenceEntity(sentence = "Weißt du, wo er wohnt?", translation = "Ты знаешь, где он живёт?", description = "wo — косвенный вопрос, wohnt в конце."),
                SentenceEntity(sentence = "Ich habe gehört, dass sie heiraten wird.", translation = "Я слышал, что она выйдет замуж.", description = "dass — придаток, Futur I: wird в конце."),
                SentenceEntity(sentence = "Er hat gesagt, dass er morgen anruft.", translation = "Он сказал, что позвонит завтра.", description = "dass — придаток, anruft в конце (отделяемая приставка)."),
                SentenceEntity(sentence = "Ich bin sicher, dass wir das schaffen.", translation = "Я уверен, что мы справимся.", description = "dass — придаток, schaffen в конце."),
                SentenceEntity(sentence = "Sie glaubt, dass alles gut wird.", translation = "Она верит, что всё будет хорошо.", description = "dass — придаток, wird в конце."),
                SentenceEntity(sentence = "Ich hoffe, dass du bald gesund wirst.", translation = "Я надеюсь, что ты скоро поправишься.", description = "dass — придаток, wirst в конце."),
                SentenceEntity(sentence = "Ich komme nicht, weil ich keine Zeit habe.", translation = "Я не приду, потому что у меня нет времени.", description = "weil — придаток, habe в конце."),
                SentenceEntity(sentence = "Er ist traurig, weil sie ihn verlassen hat.", translation = "Он грустный, потому что она его оставила.", description = "weil — придаток, Perfekt: hat в конце."),
                SentenceEntity(sentence = "Wir müssen gehen, weil der Film anfängt.", translation = "Мы должны идти, потому что фильм начинается.", description = "weil — придаток, anfängt в конце."),
                SentenceEntity(sentence = "Sie lernt viel, weil sie Ärztin werden will.", translation = "Она много учится, потому что хочет стать врачом.", description = "weil — придаток, will в конце."),
                SentenceEntity(sentence = "Ich bleibe im Bett, weil ich mich krank fühle.", translation = "Я остаюсь в постели, потому что чувствую себя больным.", description = "weil — придаток, возвратный глагол: fühle в конце."),
                SentenceEntity(sentence = "Obwohl es kalt ist, gehe ich spazieren.", translation = "Хотя холодно, я иду гулять.", description = "obwohl — придаток на 1 месте → главное: gehe на 2."),
                SentenceEntity(sentence = "Obwohl er müde war, hat er weitergearbeitet.", translation = "Хотя он устал, он продолжал работать.", description = "obwohl — придаток, weitergearbeitet в конце главного."),
                SentenceEntity(sentence = "Wenn es morgen regnet, bleiben wir zu Hause.", translation = "Если завтра будет дождь, мы останемся дома.", description = "wenn — придаток условия, regnet в конце."),
                SentenceEntity(sentence = "Wenn du willst, können wir ins Kino gehen.", translation = "Если хочешь, мы можем пойти в кино.", description = "wenn — придаток, willst в конце."),
                SentenceEntity(sentence = "Als ich jung war, reiste ich viel.", translation = "Когда я был молодым, я много путешествовал.", description = "als — однократное прошлое, war в конце."),
                SentenceEntity(sentence = "Als er nach Hause kam, war sie schon weg.", translation = "Когда он пришёл домой, она уже ушла.", description = "als — придаток, kam в конце. Главное: war на 2."),
                SentenceEntity(sentence = "Nachdem er gegessen hatte, ging er schlafen.", translation = "После того как он поел, он пошёл спать.", description = "nachdem — придаток, Plusquamperfekt: hatte в конце."),
                SentenceEntity(sentence = "Bevor wir fahren, müssen wir tanken.", translation = "Перед тем как ехать, мы должны заправиться.", description = "bevor — придаток, fahren в конце."),
                SentenceEntity(sentence = "Seitdem ich hier wohne, fühle ich mich wohl.", translation = "С тех пор как я здесь живу, я чувствую себя хорошо.", description = "seitdem — придаток, wohne в конце."),
                SentenceEntity(sentence = "Solange es nicht regnet, bleiben wir draußen.", translation = "Пока не идёт дождь, мы остаёмся снаружи.", description = "solange — придаток, regnet в конце."),
                SentenceEntity(sentence = "Ich lerne Deutsch, damit ich in Deutschland arbeiten kann.", translation = "Я учу немецкий, чтобы мочь работать в Германии.", description = "damit — придаток цели, kann в конце."),
                SentenceEntity(sentence = "Er spricht langsam, damit alle ihn verstehen.", translation = "Он говорит медленно, чтобы все его понимали.", description = "damit — придаток, verstehen в конце."),
                SentenceEntity(sentence = "Ich habe keine Lust, aber ich muss trotzdem gehen.", translation = "У меня нет желания, но я всё равно должен идти.", description = "aber — сочинит. союз, порядок не меняется."),
                SentenceEntity(sentence = "Er ist reich, aber nicht glücklich.", translation = "Он богат, но не счастлив.", description = "aber — сочинит. союз, порядок прямой."),
                SentenceEntity(sentence = "Sie mag Hunde, ich mag lieber Katzen.", translation = "Она любит собак, я больше люблю кошек.", description = "Без союза, просто два предложения через запятую."),
                SentenceEntity(sentence = "Ich habe viel gelernt, deshalb habe ich die Prüfung bestanden.", translation = "Я много учился, поэтому сдал экзамен.", description = "deshalb на 1 → habe на 2 → ich на 3 (инверсия)."),
                SentenceEntity(sentence = "Es war spät, deswegen bin ich nach Hause gegangen.", translation = "Было поздно, поэтому я пошёл домой.", description = "deswegen на 1 → bin на 2 → ich на 3."),
                SentenceEntity(sentence = "Er hat nichts gesagt, trotzdem habe ich verstanden.", translation = "Он ничего не сказал, несмотря на это я понял.", description = "trotzdem на 1 → habe на 2 → ich на 3."),
                SentenceEntity(sentence = "Sie ist sehr beschäftigt, dennoch hilft sie mir immer.", translation = "Она очень занята, тем не менее она мне всегда помогает.", description = "dennoch на 1 → hilft на 2 → sie на 3."),
                SentenceEntity(sentence = "Ich habe gefragt, aber niemand hat geantwortet.", translation = "Я спросил, но никто не ответил.", description = "aber — сочинит. союз, Perfekt: hat + geantwortet."),
                SentenceEntity(sentence = "Wir können entweder nach Italien oder nach Griechenland fahren.", translation = "Мы можем поехать либо в Италию, либо в Грецию.", description = "entweder ... oder — альтернативный союз."),
                SentenceEntity(sentence = "Er spricht nicht nur Deutsch, sondern auch Englisch.", translation = "Он говорит не только на немецком, но и на английском.", description = "nicht nur ... sondern auch — двойной союз."),
                SentenceEntity(sentence = "Ich mag sowohl Musik als auch Theater.", translation = "Я люблю как музыку, так и театр.", description = "sowohl ... als auch — двойной союз."),
                SentenceEntity(sentence = "Weder ich noch meine Familie waren dort.", translation = "Ни я, ни моя семья там не были.", description = "weder ... noch — отрицательный союз."),
                SentenceEntity(sentence = "Je mehr ich lerne, desto besser verstehe ich.", translation = "Чем больше я учу, тем лучше понимаю.", description = "je ... desto — порядок: придаток с lerne в конце, главное начинается с desto."),

// === С ОТРИЦАНИЕМ (20) ===
                SentenceEntity(sentence = "Ich kenne ihn nicht.", translation = "Я его не знаю.", description = "nicht в конце — отрицает действие."),
                SentenceEntity(sentence = "Sie hat mir nichts erzählt.", translation = "Она мне ничего не рассказала.", description = "nichts — ничего. Perfekt: hat + erzählt."),
                SentenceEntity(sentence = "Wir haben niemanden gesehen.", translation = "Мы никого не видели.", description = "niemanden — Akk от niemand."),
                SentenceEntity(sentence = "Er ist nie zu spät gekommen.", translation = "Он никогда не опаздывал.", description = "nie — никогда. Perfekt: ist + gekommen."),
                SentenceEntity(sentence = "Ich habe kein Interesse daran.", translation = "У меня нет к этому интереса.", description = "kein перед Interesse (ср.р.) → kein."),
                SentenceEntity(sentence = "Das ist keine gute Idee.", translation = "Это не хорошая идея.", description = "kein перед gute Idee (ж.р.) → keine."),
                SentenceEntity(sentence = "Er hat keinen Mut.", translation = "У него нет мужества.", description = "kein перед Mut (м.р.) → keinen. Akk."),
                SentenceEntity(sentence = "Sie haben keine Chance.", translation = "У них нет шанса.", description = "kein перед Chance (ж.р.) → keine."),
                SentenceEntity(sentence = "Ich habe kein Auto mehr.", translation = "У меня больше нет машины.", description = "kein + mehr — отрицание с 'больше не'."),
                SentenceEntity(sentence = "Das passt mir überhaupt nicht.", translation = "Это мне совсем не подходит.", description = "überhaupt nicht — усиленное отрицание."),
                SentenceEntity(sentence = "Ich kann das gar nicht verstehen.", translation = "Я этого вообще не могу понять.", description = "gar nicht — усиленное отрицание."),
                SentenceEntity(sentence = "Er ist überhaupt nicht müde.", translation = "Он совсем не устал.", description = "überhaupt nicht — усиленное отрицание."),
                SentenceEntity(sentence = "Wir haben nichts zu verlieren.", translation = "Нам нечего терять.", description = "nichts + zu + Infinitiv."),
                SentenceEntity(sentence = "Ich habe weder Zeit noch Lust.", translation = "У меня нет ни времени, ни желания.", description = "weder ... noch — двойное отрицание."),
                SentenceEntity(sentence = "Das ist nicht mein Fehler.", translation = "Это не моя ошибка.", description = "nicht перед mein Fehler — отрицает именно ошибку."),
                SentenceEntity(sentence = "Sie kommt heute Abend nicht.", translation = "Она не придёт сегодня вечером.", description = "nicht в конце — отрицает действие."),
                SentenceEntity(sentence = "Ich habe ihm nie vertraut.", translation = "Я ему никогда не доверял.", description = "nie — никогда. Partizip: vertraut."),
                SentenceEntity(sentence = "Niemand weiß die Antwort.", translation = "Никто не знает ответа.", description = "niemand — никто. Глагол в утвердительной форме."),
                SentenceEntity(sentence = "Ich habe nirgendwo einen Platz gefunden.", translation = "Я нигде не нашёл места.", description = "nirgendwo — нигде. Perfekt."),
                SentenceEntity(sentence = "Das macht überhaupt keinen Sinn.", translation = "Это вообще не имеет смысла.", description = "kein + überhaupt — усиление отрицания."),

// === DATIV + AKKUSATIV (20) ===
                SentenceEntity(sentence = "Ich gebe meinem Vater das Geld.", translation = "Я даю отцу деньги.", description = "Dat meinem Vater + Akk das Geld."),
                SentenceEntity(sentence = "Sie schickt ihrer Schwester ein Paket.", translation = "Она отправляет сестре посылку.", description = "Dat ihrer Schwester + Akk ein Paket."),
                SentenceEntity(sentence = "Er kauft seiner Frau einen Ring.", translation = "Он покупает жене кольцо.", description = "Dat seiner Frau + Akk einen Ring."),
                SentenceEntity(sentence = "Wir zeigen den Gästen die Stadt.", translation = "Мы показываем гостям город.", description = "Dat den Gästen + Akk die Stadt."),
                SentenceEntity(sentence = "Ich bringe meinem Freund ein Bier.", translation = "Я приношу другу пиво.", description = "Dat meinem Freund + Akk ein Bier."),
                SentenceEntity(sentence = "Sie erklärt den Kindern das Spiel.", translation = "Она объясняет детям игру.", description = "Dat den Kindern + Akk das Spiel."),
                SentenceEntity(sentence = "Er verspricht mir seine Hilfe.", translation = "Он обещает мне свою помощь.", description = "Dat mir + Akk seine Hilfe."),
                SentenceEntity(sentence = "Ich leihe dir mein Auto.", translation = "Я одалживаю тебе свою машину.", description = "Dat dir + Akk mein Auto."),
                SentenceEntity(sentence = "Sie empfiehlt uns ein gutes Restaurant.", translation = "Она рекомендует нам хороший ресторан.", description = "Dat uns + Akk ein gutes Restaurant."),
                SentenceEntity(sentence = "Er schenkt seiner Tochter eine Puppe.", translation = "Он дарит дочери куклу.", description = "Dat seiner Tochter + Akk eine Puppe."),
                SentenceEntity(sentence = "Ich schulde ihm noch Geld.", translation = "Я ещё должен ему денег.", description = "Dat ihm + Akk Geld."),
                SentenceEntity(sentence = "Sie sagt ihrem Mann die Wahrheit.", translation = "Она говорит мужу правду.", description = "Dat ihrem Mann + Akk die Wahrheit."),
                SentenceEntity(sentence = "Wir wünschen euch eine gute Reise.", translation = "Мы желаем вам хорошей поездки.", description = "Dat euch + Akk eine gute Reise."),
                SentenceEntity(sentence = "Er beantwortet mir die Frage.", translation = "Он отвечает мне на вопрос.", description = "Dat mir + Akk die Frage."),
                SentenceEntity(sentence = "Ich schreibe meiner Tante eine Karte.", translation = "Я пишу тёте открытку.", description = "Dat meiner Tante + Akk eine Karte."),
                SentenceEntity(sentence = "Sie bringt ihrem Chef einen Kaffee.", translation = "Она приносит начальнику кофе.", description = "Dat ihrem Chef + Akk einen Kaffee."),
                SentenceEntity(sentence = "Er zeigt mir seinen neuen Computer.", translation = "Он показывает мне свой новый компьютер.", description = "Dat mir + Akk seinen neuen Computer."),
                SentenceEntity(sentence = "Wir schenken den Kindern Süßigkeiten.", translation = "Мы дарим детям сладости.", description = "Dat den Kindern + Akk Süßigkeiten (мн.ч.)."),
                SentenceEntity(sentence = "Ich empfehle Ihnen dieses Buch.", translation = "Я рекомендую Вам эту книгу.", description = "Dat Ihnen (вежл.) + Akk dieses Buch."),
                SentenceEntity(sentence = "Er erzählt seiner Freundin die Geschichte.", translation = "Он рассказывает подруге историю.", description = "Dat seiner Freundin + Akk die Geschichte.")
            )

            sentenceDao.insertAll(sentences)
            Log.d("ADD_SENTENCES", "Добавлено: ${sentences.size}")
        }
    }
}
