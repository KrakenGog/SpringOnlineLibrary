const form = document.querySelector('form');
const resultsDiv = document.getElementById('results');

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
            const bookDiv = document.createElement('div');
            bookDiv.className = 'book';
            const authorsJS = book.authors;
            const authorNames = authorsJS.map(authors => authors.name);
            const authorsStk = authorNames.join(', ')
            bookDiv.innerHTML = `<p> <strong>${book.name}</strong> (${authorsStk})</p> `;

            const readButton = document.createElement('button');
            readButton.textContent = 'читать';
            readButton.setAttribute('data-bookName', book.name);
            readButton.setAttribute('data-bookAuthors', authorsStk);
            readButton.setAttribute('data-bookDescription',book.description);
            readButton.setAttribute('epubName', book.epub );//хз как это должно работать
            /*     document.getElementById('readButton').onclick = function() {
                     window.location.href = 'book.html?bookId=1'; // Укажите нужный URL
                 };*/
            /* старая функция которая открывала книгу в новой вкладке readButton.addEventListener('click',  function () {
                const url = `book.html?bookId=${book.id}&bookName=${book.name}&bookAuthors=${authorsStk}&bookDescription=${book.description}`;
                window.open(url,'_blank','width=800px,height=600px');
            });*/

            /*новая функция которая используется для открытия модальгого окна*/
            readButton.onclick = function(event) {
                event.preventDefault();
                const bookName = readButton.getAttribute('data-bookName');
                const bookAuthors = readButton.getAttribute('data-bookAuthors');
                const bookDescription = readButton.getAttribute('data-bookDescription');
                const bookEpub = readButton.getAttribute('epubName');
                // Обновление содержимого модального окна
                document.getElementById("bookAbout").textContent = bookName+' ('+bookAuthors+')';
                //document.getElementById("authors").textContent = bookAuthors;
                document.getElementById("description").textContent = bookDescription;

                // Показываем модальное окно
                document.getElementById("modalWin").style.display = "block";
            };

// Закрытие модального окна
            function closeModal() {
                document.getElementById("modalWin").style.display = "none";
            }
            document.getElementById('closeModal').onclick = closeModal;

            document.querySelector('.modal_background').onclick = function(event) {
                // Закрываем модальное окно, только если кликнули на фон
                if (event.target === this) {
                    closeModal();
                }
            };
            //document.getElementById('readBook').onclick = openReader(epubName);

            bookDiv.append(readButton);
            resultsDiv.appendChild(bookDiv);
        });
    } else {
        resultsDiv.innerHTML = '<p>Книги не найдены.</p>';
    }
});

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

