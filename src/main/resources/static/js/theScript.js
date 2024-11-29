const form = document.querySelector('form');
const resultsDiv = document.getElementById('results');

form.addEventListener('submit', async (event) => {
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
            const authorNames = authorsJS.map(authors=>authors.name);
            const authorsStk = authorNames.join(', ')
            bookDiv.innerHTML = `<p> <strong>${book.name}</strong> (${authorNames})</p> <div><button>Добавить</button><button>Читать</button></div>`;

            resultsDiv.appendChild(bookDiv);
        });
    } else {
        resultsDiv.innerHTML = '<p>Книги не найдены.</p>';
    }
});