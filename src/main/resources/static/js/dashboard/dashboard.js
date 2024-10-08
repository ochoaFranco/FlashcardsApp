document.addEventListener('DOMContentLoaded', () => {
    fetchDashboardData();
});

function fetchDashboardData() {
    let url = "http://localhost:8080/decks"
    
    // fetching decks.
    fetch(url)
        .then(response => response.json())
        .then(data => {
            updateDecks(data);
            updateDeckOverview(data);
        })
        .catch(error => console.error('Error fetching decks', error));
    
    // fetching categories.
    url = "http://localhost:8080/categories"
    fetch(url)
        .then(response => response.json())
        .then(data => {
            updateCategories(data)
            updateCategoryOverview(data)
        })
        .catch(error => console.error('Error fetching categories', error));
}

function updateDecks(decks) {
    const decksList = document.getElementById('decks');
    decksList.innerHTML = ''; // Clear existing data

    decks.forEach(deck => {
        const li = document.createElement('li');
        // Use innerHTML to insert HTML content
        li.innerHTML = `<a href="deck-description.html?id=${deck.deckId}" class="deck-link">${deck.deckName}</a> <a href="study-deck.html?id=${deck.deckId}" class="deck-study-link">Study</a>`;
        decksList.appendChild(li);
    });
}


function updateCategories(categories) {
    const categoriesList = document.getElementById('categories');
    categoriesList.innerHTML = ''; // Clear existing data

    categories.forEach(category => {
        const li = document.createElement('li');
        li.textContent = `${category.categoryName}`;
        categoriesList.appendChild(li);
    });
}

function updateDeckOverview(deck) {
    document.getElementById('total-decks').textContent = deck.length; // Total number of decks
}

function updateCategoryOverview(categories) {
    document.getElementById('total-categories').textContent = categories.length;
}