('use strict')
document.getElementById('searchInput').addEventListener('input', onSearchType);

function onSearchType() {
    const query = this.value;
    console.log(`Search query: ${query}`);  // Лог для проверки
    fetch(`/searchBooks?query=${query}`)
        .then(response => {
            console.log('Response received:', response);  // Лог для проверки
            return response.json();
        })
        .then(data => {
            console.log('Data received:', data);  // Лог для проверки
            const resultsDiv = document.getElementById('results');
            resultsDiv.innerHTML = '';
            data.forEach((book, index) => {
                const bookBlock = document.createElement('div');
                bookBlock.className = 'book-block';
                bookBlock.textContent = `${index + 1}. ${book.name}`;
                bookBlock.addEventListener('click', async function () {
                    openReader("getBookEpubFile.epub?id=" + book.id);
                });
                resultsDiv.appendChild(bookBlock);
            });
        })
        .catch(error => {
            console.error('Error:', error);  // Лог для проверки ошибок
        });
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

// Закрытие модального окна
document.querySelector('.close').addEventListener('click', function () {
    document.getElementById('myModal').style.display = "none";
});

document.querySelector('.close-reader').addEventListener('click', function () {
    document.getElementById('readerModal').style.display = "none";
});

document.getElementById('searchButton').addEventListener('click', function () {
    onSearchType();
})

window.onclick = function (event) {
    if (event.target == document.getElementById('myModal')) {
        document.getElementById('myModal').style.display = "none";
    }
    if (event.target == document.getElementById('readerModal')) {
        document.getElementById('readerModal').style.display = "none";
    }
};

window.onload = onSearchType;