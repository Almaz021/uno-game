## Семестровая работа

## Сетевое приложение с графическим интерфейсом


## Игра UNO

## Действия
- **Пользователь открывает приложение, регистрируется/входит в аккаунт**
- **Авторизованный пользователь может начать игру (ожидает подключения второго пользователя) / посмотреть статистику (сколько игр сыграл, сколько выиграл, процент побед)**
- **После подключения второго пользователя начинается игра**

## Игра
- **Карты для упрощенной версии (т.е. от 0 до 7, четыре цвета, без спец. карт)**
- **Игроки по очереди ходят**
- **Выигрывает тот, у кого осталась одна карта и который нажал на кнопку 'UNO!'**


## Таблицы

### Пользователи
- **id**
- **name**
- **password**
- **all_games**
- **win_games**

### История игр (M2M)
- **id**
- **id_player1**
- **id_player2**
- **id_winner**
