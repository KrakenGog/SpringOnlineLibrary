async function loadBook() {
    const urlParams = new URLSearchParams(window.location.search);
    const name= urlParams.get('bookName');
    const authors = urlParams.get('bookAuthors')
    const description = urlParams.get('bookDescription')

    document.getElementById('title').innerText = name;
    document.getElementById('authors').innerText = authors;
    document.getElementById('description').innerText = description;
}
window.onload = loadBook;