document.getElementById('author-search').addEventListener('input', searchAuthors);
document.getElementById('add-book-form').addEventListener('submit', addBook);

async function searchAuthors(event) {
    const query = event.target.value;
    if (query.length < 3) {
        document.getElementById('author-results').innerHTML = '';
        return;
    }

    const response = await fetch(`/searchAuthors?query=${encodeURIComponent(query)}`);
    const authors = await response.json();

    const authorResultsDiv = document.getElementById('author-results');
    authorResultsDiv.innerHTML = '';

    authors.forEach(author => {
        const authorCheckbox = document.createElement('input');
        authorCheckbox.type = 'checkbox';
        authorCheckbox.id = `author-${author.id}`;
        authorCheckbox.value = author.id;

        const authorLabel = document.createElement('label');
        authorLabel.htmlFor = `author-${author.id}`;
        authorLabel.textContent = author.name;

        authorResultsDiv.appendChild(authorCheckbox);
        authorResultsDiv.appendChild(authorLabel);
        authorResultsDiv.appendChild(document.createElement('br'));
    });
}

async function addBook(event) {
    event.preventDefault();

    const formData = new FormData();
    formData.append('name', document.getElementById('book-name').value);
    formData.append('description', document.getElementById('book-description').value);

    const selectedAuthors = [];
    document.querySelectorAll('#author-results input[type="checkbox"]:checked').forEach(checkbox => {
        selectedAuthors.push(checkbox.value);
    });
    formData.append('authorsId', JSON.stringify(selectedAuthors));

    const bookFile = document.getElementById('book-file').files[0];
    formData.append('file', bookFile);

    const response = await fetch('/addBook', {
        method: 'POST',
        body: formData
    });

    if (response.ok) {

        alert('Книга успешно добавлена!');
    } else {
    alert(response.json().data);
        alert('Ошибка при добавлении книги.');
    }
}
