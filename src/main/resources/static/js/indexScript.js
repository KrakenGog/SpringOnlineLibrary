const form = document.getElementById('search-form')
const resultsDiv = document.getElementById('search-form__results');
const modalWindow = document.getElementById("modal-window");
const contentPlace = document.getElementById("content-place");

form.oninput = () => searchBooks();

async function searchBooks(){


    const formData = new FormData(form);
    const query = formData.get('query');

    if (typeof query != 'string') {
        resultsDiv.innerHTML = '<p>Книги не найдены.</p>';
        return;
    }

    // Отправка запроса на сервер
    const response = await fetch(`/searchBooks?query=${encodeURIComponent(query)}`);
    const data = await response.json();

    // Очистка предыдущих результатов
    resultsDiv.innerHTML = '';

    // Отображение результатов
    if (data.length > 0) {
        data.forEach(book => resultsDiv.append(createBookDiv(book)));
    } else {
        resultsDiv.innerHTML = '<p>Книги не найдены.</p>';
    }
}

function createBookDiv(book) {
    // Отображение результатов
    const bookDiv = document.createElement('div');
    bookDiv.className = 'book-div';
    const authorsStr = book.authors
        .map(author => author.name)
        .join(', ');
    bookDiv.innerHTML = `<p> <strong>${book.name}</strong> (${authorsStr})</p> `;

    // Добавление кнопки для перехода к описанию книги
    const readButton = document.createElement('button');
    readButton.innerText = 'Инфо';
    readButton.onclick = (event) => showBookInfo(event, book.id);

    // Закидываем кнопку
    bookDiv.append(readButton);

    return bookDiv;
}

async function showBookInfo(event, bookId) {
    event.preventDefault();

    // Запрашиваем информацию о книге у сервера
    const bookInfoResponse = await fetch(`/getBookInfo?id=${encodeURIComponent(bookId)}`);
    const bookInfo = await bookInfoResponse.json();

    // Загружаем шаблон book-info-content
    const response = await fetch("book-info-content.html");
    contentPlace.innerHTML = await response.text();
    const bookInfoContent = contentPlace.querySelector("#book-info-content");

    // Выделяем нужные поля в шаблоне
    const nameHtml = bookInfoContent.querySelector("#name");
    const authorsDivHtml = bookInfoContent.querySelector("#authors");
    const descriptionHtml = bookInfoContent.querySelector("#description");
    const reviewsDivHtml = bookInfoContent.querySelector("#reviews");
    const readButton = bookInfoContent.querySelector("#read-button");

    // Обновление содержимого модального окна
    // Название книги
    nameHtml.textContent = bookInfo.name;
    // Список авторов
    bookInfo.authors.forEach(author => authorsDivHtml.append(createAuthorP(author)));
    // Описание
    descriptionHtml.textContent = bookInfo.description;
    // Кнопочка добавления отзыва
    const addReviewButton = document.createElement('button');
    addReviewButton
    // Список отзывов
    bookInfo.reviews.forEach(review => reviewsDivHtml.append(createReviewDiv(review)));
    // Кнопка для чтения
    readButton.onclick = (event) => readBook(event, bookId);

    // Показываем модальное окно
    modalWindow.style.display = "block";
}

function createAuthorP(author) {
    const authorP = document.createElement('p');
    authorP.className = "book-info-content__text";

    authorP.innerHTML = author.name;

    // На будущее сохранил id автора
    authorP.setAttribute("authorId", author.id);

    return authorP;
}

async function createReviewDiv(review) {
    // Временный объект, чтобы получить в использование объект из reviewDiv.html
    const bufferDiv = document.createElement('div');

    // Лутаем html код нашего reviewDiv
    const response = await fetch("reviewDiv.html");
    bufferDiv.innerHTML = await response.text();

    // Достаём наш объект из буфера
    const reviewDiv = bufferDiv.querySelector("#reviewDiv");

    // Выделяем поля в шаблоне
    const dateHtml = reviewDiv.querySelector("#date");
    const userNameHtml = reviewDiv.querySelector("#user-name");
    const markHtml = reviewDiv.querySelector("#mark");
    const textHtml = reviewDiv.querySelector("#text");


    // Заполняем поля
    dateHtml.textContent = review.date;
    userNameHtml.textContent = review.user.name;
    markHtml.textContent = review.mark;
    textHtml.textContent = review.text;


    // На будущее сохраню id отзыва
    reviewDiv.setAttribute("reviewId", review.id);

    // На будущее сохраню id пользователя
    userNameHtml.setAttribute("userId", review.user.id);

    return reviewDiv;
}

async function readBook(event, bookId) {
    event.preventDefault();

    const epubUrl = await fetch(`getBookEpubFile.epub?${encodeURIComponent(bookId)}`);
    openReader(epubUrl);
}

// Закрытие модального окна
function closeModal() {
    modalWindow.style.display = "none";
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

