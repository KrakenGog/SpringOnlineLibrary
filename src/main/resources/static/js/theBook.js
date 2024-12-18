async function loadBook() {
    const urlParams = new URLSearchParams(window.location.search);
    const bookId = urlParams.get('bookId');

    const response = await fetch(`books/${bookId}.json`);
    const book = await response.json();

    const authorsJS = book.authors;
    const authorNames = authorsJS.map(authors => authors.name);
    const authorsStk = authorNames.join(', ')

    document.getElementById('title').innerText = book.name;
    document.getElementById('authors').innerText = authorsStk;
    /*document.getElementById('description').innerText = book.description; замкомментировано пока у книг не появится описание*/
}