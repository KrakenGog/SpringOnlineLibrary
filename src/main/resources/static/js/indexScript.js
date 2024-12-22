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
            readButton.onclick = function(event) {
                const bookId = readButton.getAttribute("bookId");
                const bookInfoResponse = await fetch(`/getBookInfo?id=${encodeURIComponent(bookId)}`);

                const bookDiv = document.createElement('div');
                bookDiv.className = 'book-info-div';


                // Обновление содержимого модального окна
                document.getElementById("bookAbout").textContent = bookName+' ('+bookAuthors+')';
                //document.getElementById("authors").textContent = bookAuthors;
                document.getElementById("description").textContent = bookDescription;

                // Показываем модальное окно
                document.getElementById("modalWin").style.display = "block";
            };

            
            //document.getElementById('readBook').onclick = openReader(epubName);

            bookDiv.append(readButton);
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

