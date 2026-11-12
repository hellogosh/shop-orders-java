# Итоговый проект «Заказы магазина»

Учебный проект для итоговой встречи курса «Программирование на Java для тестировщиков» (11.11). Задание — в файле `Задание — итоговый практикум.md` в корне проекта.

Проект устроен так же, как трекер задач, с которым вы работали девять встреч: модели, сервисы, файл с данными, база H2, учебный стаб с API и страницей, задачи Gradle по тегам. Предметная область другая — заказы интернет-магазина.

## Что уже есть

| Где | Что |
|---|---|
| `презентации` | готовый production-код: `OrderService`, `DiscountCalculator`, `OrderWorkflow`, `OrderFileReader`, `OrderLineParser`, `OrderIdValidator`. Скопируете в задании 1 |
| `src/test/java/training/shop/stub/` | учебный стаб магазина `OrderStub` и `OrderStubExtension` — не меняйте |
| `src/test/java/training/shop/config/` | `TestConfig` и `ConfigProvider`: `config().baseUrl()`, `config().dbUrl()` |
| `src/test/java/training/shop/tests/EnvironmentTest.java` | готовый тест: проект открылся и запускается |
| `src/test/resources/orders.txt` | пять заказов для задания 5 |
| `src/test/resources/test.properties` | адрес стаба и базы |
| `build.gradle` | зависимости и задачи `smokeTest`, `dbTest`, `apiTest`, `uiTest` |

## Что пишете вы

- Модели в `src/main/java/training/shop/models/`: `OrderStatus` и `Order` (задание 1).
- Модель ответа API в `src/test/java/training/shop/api/`: `OrderResponse` (задание 7).
- Все тесты.

## Пакеты

```text
src/main/java/training/shop/
├── models/      Order, OrderStatus — пишете сами
└── services/    копируете из заготовки/services
src/test/java/training/shop/
├── api/         OrderResponse и API-тесты
├── builders/    OrderBuilder — дополнительное задание
├── config/      готово
├── db/          тест базы
├── stub/        готово, не трогать
├── tests/       unit-тесты и тест файла
└── ui/          UI smoke
src/test/resources/
├── orders.txt
└── test.properties
```

## Стаб магазина

Адрес — `http://localhost:8090` (у трекера был 8080, поэтому проекты не мешают друг другу). Стаб поднимается сам, если над тестовым классом стоит `@ExtendWith(OrderStubExtension.class)`.

| Запрос | Ответ |
|---|---|
| `GET /orders/ORD-1` | `200`, `{"id":"ORD-1","customer":"Ivanov","amount":2500,"status":"PAID"}` |
| `GET /orders/ORD-2` | `200`, `{"id":"ORD-2","customer":"Petrova","amount":18000,"status":"SHIPPED"}` |
| `GET /orders/ORD-999` (любой другой номер) | `404`, `{"error":"Order not found","id":"ORD-999"}` |
| страница `/orders/ORD-1` в браузере | элементы `data-testid`: `order-id`, `order-customer`, `order-amount`, `order-status` |

Посмотреть стаб руками: `./gradlew runStub`, затем открыть `http://localhost:8090/orders/ORD-1`. Остановить — Ctrl+C.

## Команды

```bash
./gradlew clean test   # всё, кроме UI
./gradlew smokeTest    # тесты с @Tag("smoke"), кроме UI
./gradlew dbTest       # тесты с @Tag("db")
./gradlew apiTest      # тесты с @Tag("api")
./gradlew uiTest       # тесты с @Tag("ui") — только отдельной командой
```

Повторный запуск без изменений кода печатает `UP-TO-DATE` без строк `PASSED` — запускайте с `clean`. Браузер Playwright тот же, что и для трекера: скачивать не нужно.

## Итог

<!-- Задание 9: впишите сюда команды и число тестов каждой задачи -->
