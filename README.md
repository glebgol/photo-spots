## Перед запуском android приложения
1. установите backend репозиторий [https://github.com/glebgol/photo-spots-core](https://github.com/glebgol/photo-spots-core.git)
2. запустите - `docker-compose up -d` (необходим установленный docker-compose)
3. запустите - `mvn spring-boot:run` (необходим установленный maven и java 17)
4. в `~\AppData\Local\Android\Sdk\platform-tools` запустить команду `./adb reverse tcp:8080 tcp:8080`, должно напечатать в консоль `8080`

## Использованные технологии
Kotlin, Coroutines/Flow, Gradle, Github Actions CI/CD, Dagger Hilt, Jetpack Compose, Retrofit, Coil, Room, CameraX, Google Play Services Location, Osmdroid maps, Clean Architecture

## Функционал приложения
- создание меток на карте с фотографией и текущим местоположением пользователя
- просмотр всех меток на интерактивной карте
- поиск меток по названию
- просмотр деталей меток
- частичный офлайн доступ
- сохранение в локальную бд избранных меток
- интеграция с другими приложениями карт
