async function loadBook() {
    const urlParams = new URLSearchParams(window.location.search);
    const name= urlParams.get('bookName');
    const authors = urlParams.get('bookAuthors')
    const description = urlParams.get('bookDescription');
    const bookId = urlParams.get('bookId')
    document.getElementById('title').innerText = name;
    document.getElementById('authors').innerText = authors;
    document.getElementById('description').innerText = description;
    document.getElementById('readBtn').addEventListener("click", () =>{
        const url = `html/bookReadingPage.html?id=${bookId}`;
        window.open(url,'_blank');
    });
}
window.onload = loadBook;