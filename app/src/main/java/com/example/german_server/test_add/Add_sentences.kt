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
                SentenceEntity(sentence = "Morgen fahre ich nach Berlin.", translation = "Завтра я еду в Берлин.", description = "Präsens с будущим значением. morgen на 1 → fahre на 2 → ich на 3."),
                SentenceEntity(sentence = "Gestern habe ich meine Oma besucht.", translation = "Вчера я навестил бабушку.", description = "Perfekt. haben на 2, Partizip II besucht в конце."),
                SentenceEntity(sentence = "Jetzt lese ich ein interessantes Buch.", translation = "Сейчас я читаю интересную книгу.", description = "Präsens. jetzt на 1 → lese на 2 → ich на 3."),
                SentenceEntity(sentence = "Heute Abend gehe ich ins Kino.", translation = "Сегодня вечером я иду в кино.", description = "heute Abend — обстоятельство времени на 1 → gehe на 2."),
                SentenceEntity(sentence = "Gestern hat es den ganzen Tag geregnet.", translation = "Вчера целый день шёл дождь.", description = "Perfekt с haben. es — формальное подлежащее."),
                SentenceEntity(sentence = "Nächste Woche fliege ich nach Spanien.", translation = "На следующей неделе я лечу в Испанию.", description = "nächste Woche на 1 → fliege на 2 → ich на 3 (инверсия)."),
                SentenceEntity(sentence = "Am Montag habe ich einen Termin.", translation = "В понедельник у меня встреча.", description = "Am Montag — время на 1 → habe на 2 → ich на 3."),
                SentenceEntity(sentence = "Vor zwei Jahren war ich in Deutschland.", translation = "Два года назад я был в Германии.", description = "Präteritum. vor zwei Jahren — время."),
                SentenceEntity(sentence = "Heute ist das Wetter fantastisch.", translation = "Сегодня погода фантастическая.", description = "heute на 1 → ist на 2 → das Wetter на 3."),
                SentenceEntity(sentence = "Im Sommer schwimmen wir im See.", translation = "Летом мы плаваем в озере.", description = "im Sommer — время на 1 → schwimmen на 2."),
                SentenceEntity(sentence = "Gestern Abend haben wir lange gesprochen.", translation = "Вчера вечером мы долго разговаривали.", description = "Perfekt. gestern Abend → haben на 2 → gesprochen в конце."),
                SentenceEntity(sentence = "Nach der Arbeit gehe ich einkaufen.", translation = "После работы я иду за покупками.", description = "nach der Arbeit — время на 1 → gehe на 2."),
                SentenceEntity(sentence = "Bald kommt der Winter.", translation = "Скоро придёт зима.", description = "bald на 1 → kommt на 2 → der Winter на 3."),
                SentenceEntity(sentence = "Immer trinke ich morgens Kaffee.", translation = "Всегда утром я пью кофе.", description = "immer на 1 → trinke на 2 → ich на 3."),
                SentenceEntity(sentence = "Oft besuche ich meine Eltern.", translation = "Часто я навещаю родителей.", description = "oft на 1 → besuche на 2 → ich на 3."),
                SentenceEntity(sentence = "Letztes Jahr habe ich Deutsch gelernt.", translation = "В прошлом году я учил немецкий.", description = "Perfekt. letztes Jahr → habe на 2 → gelernt в конце."),
                SentenceEntity(sentence = "Manchmal koche ich am Wochenende.", translation = "Иногда я готовлю на выходных.", description = "manchmal на 1 → koche на 2 → ich на 3."),
                SentenceEntity(sentence = "Heute Nachmittag treffe ich meine Freunde.", translation = "Сегодня днём я встречаю друзей.", description = "heute Nachmittag на 1 → treffe на 2 → ich на 3."),
                SentenceEntity(sentence = "Übermorgen habe ich frei.", translation = "Послезавтра у меня выходной.", description = "übermorgen на 1 → habe на 2 → ich на 3."),
                SentenceEntity(sentence = "Am Wochenende schlafe ich lange.", translation = "На выходных я долго сплю.", description = "am Wochenende на 1 → schlafe на 2 → ich на 3."),

// === СЛОЖНОСОЧИНЁННЫЕ (40) ===
                SentenceEntity(sentence = "Ich glaube, dass er morgen kommt.", translation = "Я верю, что он придёт завтра.", description = "dass — придаток, глагол kommt в конце."),
                SentenceEntity(sentence = "Sie sagt, dass sie müde ist.", translation = "Она говорит, что устала.", description = "dass — придаток, ist в конце."),
                SentenceEntity(sentence = "Er kommt nicht, weil er krank ist.", translation = "Он не придёт, потому что болен.", description = "weil — придаток, ist в конце."),
                SentenceEntity(sentence = "Ich bleibe hier, weil ich arbeiten muss.", translation = "Я остаюсь здесь, потому что должен работать.", description = "weil — придаток, muss в конце."),
                SentenceEntity(sentence = "Wir gehen spazieren, obwohl es regnet.", translation = "Мы идём гулять, хотя идёт дождь.", description = "obwohl — придаток, regnet в конце."),
                SentenceEntity(sentence = "Ich lerne viel, damit ich die Prüfung bestehe.", translation = "Я много учусь, чтобы сдать экзамен.", description = "damit — придаток цели, bestehe в конце."),
                SentenceEntity(sentence = "Wenn ich Zeit habe, besuche ich dich.", translation = "Если у меня будет время, я навещу тебя.", description = "wenn — придаток условия. Главное: besuche на 2."),
                SentenceEntity(sentence = "Als ich klein war, wohnte ich in München.", translation = "Когда я был маленьким, я жил в Мюнхене.", description = "als — придаток (однократное прошлое), wohnte на 2."),
                SentenceEntity(sentence = "Er ist klug, aber faul.", translation = "Он умный, но ленивый.", description = "aber — сочинит. союз, порядок не меняется."),
                SentenceEntity(sentence = "Ich mag Kaffee, aber sie mag Tee.", translation = "Я люблю кофе, но она любит чай.", description = "aber — сочинит. союз, порядок прямой."),
                SentenceEntity(sentence = "Wir können ins Kino gehen oder zu Hause bleiben.", translation = "Мы можем пойти в кино или остаться дома.", description = "oder — сочинит. союз, порядок прямой."),
                SentenceEntity(sentence = "Ich habe Hunger, denn ich habe nicht gefrühstückt.", translation = "Я голоден, потому что не завтракал.", description = "denn — сочинит. союз, порядок не меняется."),
                SentenceEntity(sentence = "Er ist sehr müde, deshalb geht er früh ins Bett.", translation = "Он очень устал, поэтому рано ложится спать.", description = "deshalb на 1 → geht на 2 → er на 3 (инверсия)."),
                SentenceEntity(sentence = "Es regnet stark, deswegen bleiben wir zu Hause.", translation = "Сильно идёт дождь, поэтому мы остаёмся дома.", description = "deswegen на 1 → bleiben на 2 → wir на 3."),
                SentenceEntity(sentence = "Ich habe kein Geld, trotzdem kaufe ich das Auto.", translation = "У меня нет денег, несмотря на это я покупаю машину.", description = "trotzdem на 1 → kaufe на 2 → ich на 3."),
                SentenceEntity(sentence = "Sie ist krank, trotzdem geht sie zur Arbeit.", translation = "Она больна, несмотря на это идёт на работу.", description = "trotzdem на 1 → geht на 2 → sie на 3."),
                SentenceEntity(sentence = "Ich denke, dass du recht hast.", translation = "Я думаю, что ты прав.", description = "dass — придаток, hast в конце."),
                SentenceEntity(sentence = "Er weiß, dass sie in Berlin wohnt.", translation = "Он знает, что она живёт в Берлине.", description = "dass — придаток, wohnt в конце."),
                SentenceEntity(sentence = "Ich hoffe, dass das Wetter morgen besser ist.", translation = "Я надеюсь, что завтра погода лучше.", description = "dass — придаток, ist в конце."),
                SentenceEntity(sentence = "Sie fragt, ob ich Zeit habe.", translation = "Она спрашивает, есть ли у меня время.", description = "ob — придаток (косвенный вопрос), habe в конце."),
                SentenceEntity(sentence = "Ich weiß nicht, ob er kommt.", translation = "Я не знаю, придёт ли он.", description = "ob — придаток, kommt в конце."),
                SentenceEntity(sentence = "Er lernt Deutsch, weil er in Deutschland studieren will.", translation = "Он учит немецкий, потому что хочет учиться в Германии.", description = "weil — придаток, will в конце."),
                SentenceEntity(sentence = "Wir bleiben zu Hause, weil es zu kalt ist.", translation = "Мы остаёмся дома, потому что слишком холодно.", description = "weil — придаток, ist в конце."),
                SentenceEntity(sentence = "Ich kann nicht kommen, weil ich arbeiten muss.", translation = "Я не могу прийти, потому что должен работать.", description = "weil — придаток, muss в конце."),
                SentenceEntity(sentence = "Er ist nicht gekommen, weil er krank war.", translation = "Он не пришёл, потому что был болен.", description = "weil — придаток, war в конце."),
                SentenceEntity(sentence = "Sie hat den Test bestanden, obwohl sie wenig gelernt hat.", translation = "Она сдала тест, хотя мало училась.", description = "obwohl — придаток, hat в конце."),
                SentenceEntity(sentence = "Ich rufe dich an, sobald ich angekommen bin.", translation = "Я тебе позвоню, как только приеду.", description = "sobald — придаток времени, bin в конце."),
                SentenceEntity(sentence = "Bevor ich schlafe, lese ich ein Buch.", translation = "Перед тем как спать, я читаю книгу.", description = "bevor — придаток времени, schlafe в конце."),
                SentenceEntity(sentence = "Nachdem ich gegessen hatte, ging ich spazieren.", translation = "После того как я поел, я пошёл гулять.", description = "nachdem — придаток, hatte в конце. Plusquamperfekt."),
                SentenceEntity(sentence = "Ich mag sowohl Kaffee als auch Tee.", translation = "Я люблю как кофе, так и чай.", description = "sowohl ... als auch — двойной союз."),
                SentenceEntity(sentence = "Weder er noch sie kommt heute.", translation = "Ни он, ни она не придут сегодня.", description = "weder ... noch — отрицательный двойной союз."),
                SentenceEntity(sentence = "Entweder gehen wir ins Kino oder wir bleiben zu Hause.", translation = "Или мы идём в кино, или остаёмся дома.", description = "entweder ... oder — альтернативный союз."),
                SentenceEntity(sentence = "Ich lerne Deutsch, außerdem lerne ich Englisch.", translation = "Я учу немецкий, кроме того учу английский.", description = "außerdem — наречие-связка, инверсия."),
                SentenceEntity(sentence = "Er ist sehr intelligent, allerdings auch sehr faul.", translation = "Он очень умный, однако и очень ленивый.", description = "allerdings — наречие, порядок не меняется после союза."),
                SentenceEntity(sentence = "Ich habe viel gearbeitet, daher bin ich müde.", translation = "Я много работал, поэтому устал.", description = "daher на 1 → bin на 2 → ich на 3 (инверсия)."),
                SentenceEntity(sentence = "Sie spricht sowohl Deutsch als auch Französisch.", translation = "Она говорит как на немецком, так и на французском.", description = "sowohl ... als auch — порядок слов прямой."),
                SentenceEntity(sentence = "Ich gehe nicht zur Party, sondern bleibe zu Hause.", translation = "Я не иду на вечеринку, а остаюсь дома.", description = "nicht ... sondern — коррекция, порядок прямой."),
                SentenceEntity(sentence = "Er hat nicht nur Deutsch, sondern auch Englisch gelernt.", translation = "Он выучил не только немецкий, но и английский.", description = "nicht nur ... sondern auch — двойной союз."),
                SentenceEntity(sentence = "Ich weiß, dass er in Wien wohnt und arbeitet.", translation = "Я знаю, что он живёт и работает в Вене.", description = "dass — придаток с двумя глаголами в конце."),
                SentenceEntity(sentence = "Sie sagt, dass sie morgen kommen wird.", translation = "Она говорит, что придёт завтра.", description = "dass — придаток, Futur I: wird в самом конце."),

// === С ОТРИЦАНИЕМ (20) ===
                SentenceEntity(sentence = "Ich habe keinen Bruder.", translation = "У меня нет брата.", description = "kein перед Bruder (м.р.) → keinen. Akk."),
                SentenceEntity(sentence = "Sie hat kein Auto.", translation = "У неё нет машины.", description = "kein перед Auto (ср.р.) → kein. Akk."),
                SentenceEntity(sentence = "Wir haben kein Geld.", translation = "У нас нет денег.", description = "kein перед Geld (ср.р.) → kein."),
                SentenceEntity(sentence = "Er hat keine Freundin.", translation = "У него нет девушки.", description = "kein перед Freundin (ж.р.) → keine."),
                SentenceEntity(sentence = "Ich trinke keinen Kaffee.", translation = "Я не пью кофе.", description = "kein перед Kaffee (м.р.) → keinen. Akk."),
                SentenceEntity(sentence = "Das ist nicht mein Problem.", translation = "Это не моя проблема.", description = "nicht перед mein Problem — отрицает именно проблему."),
                SentenceEntity(sentence = "Er arbeitet heute nicht.", translation = "Он сегодня не работает.", description = "nicht в конце — отрицает действие."),
                SentenceEntity(sentence = "Ich verstehe dich nicht.", translation = "Я тебя не понимаю.", description = "nicht в конце — отрицает действие."),
                SentenceEntity(sentence = "Wir gehen heute nicht ins Kino.", translation = "Мы сегодня не идём в кино.", description = "nicht перед обстоятельством — отрицает поход в кино."),
                SentenceEntity(sentence = "Sie ist nicht zu Hause.", translation = "Её нет дома.", description = "nicht перед zu Hause — отрицает место."),
                SentenceEntity(sentence = "Ich habe nicht genug Zeit.", translation = "У меня недостаточно времени.", description = "nicht перед genug — отрицает количество."),
                SentenceEntity(sentence = "Er ist nicht so klug, wie er denkt.", translation = "Он не так умён, как думает.", description = "nicht so ... wie — сравнение с отрицанием."),
                SentenceEntity(sentence = "Ich habe noch nie Berlin besucht.", translation = "Я никогда не был в Берлине.", description = "nie — никогда, усиливает отрицание."),
                SentenceEntity(sentence = "Niemand hat mir geholfen.", translation = "Никто мне не помог.", description = "niemand — никто. Глагол в утвердительной форме."),
                SentenceEntity(sentence = "Ich habe nichts gesagt.", translation = "Я ничего не сказал.", description = "nichts — ничего."),
                SentenceEntity(sentence = "Er kommt nirgendwohin.", translation = "Он никуда не идёт.", description = "nirgendwohin — никуда."),
                SentenceEntity(sentence = "Das macht keinen Sinn.", translation = "Это не имеет смысла.", description = "kein перед Sinn (м.р.) → keinen."),
                SentenceEntity(sentence = "Ich habe keine Ahnung.", translation = "Я не имею понятия.", description = "kein перед Ahnung (ж.р.) → keine."),
                SentenceEntity(sentence = "Sie hat keine Zeit für mich.", translation = "У неё нет времени для меня.", description = "kein перед Zeit (ж.р.) → keine."),
                SentenceEntity(sentence = "Wir haben keine Kinder.", translation = "У нас нет детей.", description = "kein перед Kinder (мн.ч.) → keine."),

// === DATIV + AKKUSATIV (20) ===
                SentenceEntity(sentence = "Ich schenke meiner Mutter Blumen.", translation = "Я дарю маме цветы.", description = "Dat meiner Mutter (ж.р.) + Akk Blumen. Порядок: Dat → Akk."),
                SentenceEntity(sentence = "Er gibt dem Kind einen Apfel.", translation = "Он даёт ребёнку яблоко.", description = "Dat dem Kind + Akk einen Apfel."),
                SentenceEntity(sentence = "Sie zeigt ihrem Freund das Haus.", translation = "Она показывает другу дом.", description = "Dat ihrem Freund + Akk das Haus."),
                SentenceEntity(sentence = "Wir bringen den Eltern Geschenke.", translation = "Мы приносим родителям подарки.", description = "Dat den Eltern (мн.ч.) + Akk Geschenke."),
                SentenceEntity(sentence = "Der Lehrer erklärt den Schülern die Grammatik.", translation = "Учитель объясняет ученикам грамматику.", description = "Dat den Schülern + Akk die Grammatik."),
                SentenceEntity(sentence = "Ich leihe meinem Bruder mein Fahrrad.", translation = "Я одалживаю брату свой велосипед.", description = "Dat meinem Bruder + Akk mein Fahrrad."),
                SentenceEntity(sentence = "Sie kauft ihrem Sohn ein Buch.", translation = "Она покупает сыну книгу.", description = "Dat ihrem Sohn + Akk ein Buch."),
                SentenceEntity(sentence = "Er schickt seiner Freundin eine Nachricht.", translation = "Он отправляет подруге сообщение.", description = "Dat seiner Freundin + Akk eine Nachricht."),
                SentenceEntity(sentence = "Ich gebe dir mein Wort.", translation = "Я даю тебе слово.", description = "Dat dir (местоимение) + Akk mein Wort."),
                SentenceEntity(sentence = "Sie empfiehlt mir dieses Restaurant.", translation = "Она рекомендует мне этот ресторан.", description = "Dat mir + Akk dieses Restaurant."),
                SentenceEntity(sentence = "Wir wünschen dir viel Glück.", translation = "Мы желаем тебе много удачи.", description = "Dat dir + Akk viel Glück."),
                SentenceEntity(sentence = "Der Vater zeigt seinen Kindern den Garten.", translation = "Отец показывает детям сад.", description = "Dat seinen Kindern + Akk den Garten."),
                SentenceEntity(sentence = "Ich schreibe meiner Oma einen Brief.", translation = "Я пишу бабушке письмо.", description = "Dat meiner Oma + Akk einen Brief."),
                SentenceEntity(sentence = "Sie bringt dem Gast einen Kaffee.", translation = "Она приносит гостю кофе.", description = "Dat dem Gast + Akk einen Kaffee."),
                SentenceEntity(sentence = "Er verkauft seinem Nachbarn sein Auto.", translation = "Он продаёт соседу свою машину.", description = "Dat seinem Nachbarn + Akk sein Auto."),
                SentenceEntity(sentence = "Wir erzählen den Kindern eine Geschichte.", translation = "Мы рассказываем детям историю.", description = "Dat den Kindern + Akk eine Geschichte."),
                SentenceEntity(sentence = "Ich danke dir für deine Hilfe.", translation = "Я благодарю тебя за помощь.", description = "danken + Dat (dir) + für + Akk (deine Hilfe)."),
                SentenceEntity(sentence = "Sie hilft ihrem Vater im Garten.", translation = "Она помогает отцу в саду.", description = "helfen + Dat (ihrem Vater)."),
                SentenceEntity(sentence = "Er antwortet dem Lehrer auf die Frage.", translation = "Он отвечает учителю на вопрос.", description = "antworten + Dat (dem Lehrer) + auf + Akk."),
                SentenceEntity(sentence = "Ich gratuliere dir zum Geburtstag.", translation = "Я поздравляю тебя с днём рождения.", description = "gratulieren + Dat (dir) + zu + Dat (Geburtstag).")
            )

            sentenceDao.insertAll(sentences)
            Log.d("ADD_SENTENCES", "Добавлено: ${sentences.size}")
        }
    }
}
