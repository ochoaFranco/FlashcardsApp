const getDeckDetails = async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const deckId = urlParams.get('id');

    if (!deckId) return;

    try {
        const response = await fetch(`http://127.0.0.1:8080/decks/${deckId}`);
        // check if the response was ok.s
        if (!response.ok)
            throw new Error('Network response was not ok');
        // Display data.
        const deck = await response.json();
        displayDeckDetails(deck);
    } catch(error) {
        console.error('There was a problem with fetching deck details:', error)
    }
};

const displayDeckDetails = (deck) => {
    const deckDetails = document.getElementById('deck-details');
    let wordsHTML = '';
    // check if there are words associated with the deck.
    if (deck.words && deck.words.length > 0) {
        deck.words.forEach(word => {
            wordsHTML += `<a href="word-description.html?id=${word.wordId}"> <p>${word.wordName}</a> - ${word.meaning}</p>`
        });
    } else {
        wordsHTML = `<p>No words available for this deck.</p>`;
    }

    deckDetails.innerHTML = `
        <h2>${deck.deckName}</h2>
        <p><strong>Description:</strong> ${deck.description}</p>
        <h3>Words</h3>
        <div>${wordsHTML}</div>
    `;
};

// Fetch and display deck details on page load
window.onload = getDeckDetails;