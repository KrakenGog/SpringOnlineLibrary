const form = document.getElementById('search-form')
const resultsDiv = document.getElementById('search-form__results');

form.addEventListener('input', async (event) => {
    event.preventDefault(); // Отменяем стандартное поведение формы

    const formData = new FormData(form);
    const query = formData.get('query');

    // Отправка запроса на сервер
    const response = await fetch(`/searchBooks?query=${encodeURIComponent(query)}`);
    const data = await response.json();

    // Очистка предыдущих результатов
    resultsDiv.innerHTML = '';

    // Отображение результатов
    if (data.length > 0) {
        data.forEach(book => {
            // Отображение результатов
            const bookDiv = document.createElement('div');
            bookDiv.className = 'book-div';
            const authorsJS = book.authors;
            const authorNames = authorsJS.map(authors => authors.name);
            const authorsStr = authorNames.join(', ')
            bookDiv.innerHTML = `<p> <strong>${book.name}</strong> (${authorsStr})</p> `;

            // Добавление кнопки для перехода к описанию книги
            const readButton = document.createElement('button');
            readButton.textContent = 'читать';
            readButton.setAttribute("bookId", book.id);
            
            // Обработка события нажатия на кнопку
            readButton.onclick = async function (event) {
                event.preventDefault();
                // Запрашиваем информацию о книге у сервера
                const bookId = readButton.getAttribute("bookId");
                const bookInfoResponse = await fetch(`/getBookInfo?id=${encodeURIComponent(bookId)}`);
                const bookInfo = await bookInfoResponse.json();

                // Определяем модальное окно и его содержимое
                const modalWindow = document.getElementById("modal-window");
                const contentPlace = document.getElementById("content-place");


                // Загружаем шаблон book-info-content
                const response = await fetch("book-info-content.html");
                contentPlace.innerHTML = await response.text();
                const bookInfoContent = contentPlace.getById("book-info-content");

                // Выделяем нужные поля в шаблоне
                const nameHtml = bookInfoContent.getElementById("name");
                const authorsDivHtml = bookInfoContent.getElementById("authors");
                const descriptionHtml = bookInfoContent.getElementById("description");
                const reviewsDivHtml = bookInfoContent.getElementById("reviews");
                const readButton = bookInfoContent.getElementById("read-button");

                // Обновление содержимого модального окна
                // Название книги
                nameHtml.textContent = bookInfo.name;
                // Список авторов
                bookInfo.authors.forEach(author => {
                    const authorP = document.createElement('p');
                    authorP.className = "book-info-content__text";

                    authorP.textContent = author.name;

                    // На будущее сохранил id автора
                    authorP.setAttribute("authorId", author.id);

                    authorsDivHtml.append(authorP);
                });
                // Описание
                descriptionHtml.textContent = bookInfo.description;
                // Список отзывов
                for (const review of bookInfo.reviews) {
                    const bufferDiv = document.createElement('reviewDiv.html');
                    const response = await fetch("reviewDiv.html");
                    bufferDiv.innerHTML = await response.text();
                    const reviewDiv = bufferDiv.getElementById("reviewDiv");

                    // На будущее сохраню id
                    reviewDiv.setAttribute("reviewId", review.id);

                    // Выделяем поля в шаблоне
                    let dateHtml = reviewDiv.getElementById("date");
                    let userNameHtml = reviewDiv.getElementById("user-name");
                    let markHtml = reviewDiv.getElementById("mark");
                    let textHtml = reviewDiv.getElementById("text");

                    // Заполняем поля
                    dateHtml = review.date;
                    userNameHtml = review.user.name;
                    markHtml = review.mark;
                    textHtml = review.text;

                    // Сохраним id пользователя на будущее
                    userNameHtml.setAttribute("userId", review.user.id);

                    // Закидываем готовый отзыв в список
                    reviewsDivHtml.append(bufferDiv);
                }
                // Кнопка для чтения
                readButton.setAttribute("bookId", bookId);
                readButton.onclick = async function (event) {
                    const bookId = readButton.getAttribute("bookId");
                    event.preventDefault();
                    const epubUrl = await fetch(`getBookEpubFile.epub?${encodeURIComponent(bookId)}`);
                    openReader(epubUrl);
                }

                // Показываем модальное окно
                modalWindow.display = "block";
            };

            // Закидываем кнопку
            bookDiv.append(readButton);

            // Закидываем готовое поле книги
            resultsDiv.append(bookDiv);
        });
    } else {
        resultsDiv.innerHTML = '<p>Книги не найдены.</p>';
    }
});

// Закрытие модального окна
function closeModal() {
    document.getElementById("modal-window").style.display = "none";
}

function openReader(epubUrl) {
    const elem = document.getElementById('reader');
    elem.innerHTML = elem.innerText || elem.textContent;
    document.getElementById('readerModal').style.display = "block";
    const book = ePub(epubUrl);
    const rendition = book.renderTo("reader", {
        spread: "always",
        width: "100%",
        height: "100%"
    });
    rendition.display();
}


document.getElementById('closeRead').addEventListener('click', function () {
    document.getElementById('readerModal').style.display = "none";
});

