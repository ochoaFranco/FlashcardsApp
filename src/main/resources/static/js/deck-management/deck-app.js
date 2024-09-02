const getDecks = async () => {
    const url = 'http://127.0.0.1:8080/decks'
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        console.log(data);
        
        displayDecks(data);
    } catch(error) {
        console.log('There has been a problem with your fetch operation', error);
    }
}
// Display decks and add delete and edit btns.
const displayDecks = (decks) => {
    const deckList = document.getElementById("deck-list");
    deckList.innerHTML = '';
    console.log(decks);
    
    decks.forEach(deck => {
        const div = document.createElement('div');
        div.className = 'deck-item';
        div.innerHTML = `
            <div class = "deck-header"> 
                <h4><a href="desk-description.html?id=${deck.deckId}">${deck.deckName}</a> (${deck.words.length} words)</h4>
            </div>
            <button class="btn btn-edit" data-deck-id="${deck.deckId}">Edit</button>
            <button class="btn btn-delete" data-deck-id="${deck.deckId}">Delete</button>
        `;
        const hr = document.createElement('hr');
        deckList.appendChild(hr);
        deckList.appendChild(div)
    });

    // Add event listeners to edit button
    document.querySelectorAll('.btn-edit').forEach(button => {
        button.addEventListener('click', () => {
            const deckId = button.getAttribute('data-deck-id');
            editDeck(deckId);
        });
    });

    // Add event listeners to delete button
    document.querySelectorAll('.btn-delete').forEach(button => {
        button.addEventListener('click', () => {
            const deckId = button.getAttribute('data-deck-id');
            deleteCategory(deckId);
        });
    });
}


// Delete a deck.
const deleteCategory = async (deckId) => {
    try {
        const response = await fetch(`http://127.0.0.1:8080/decks/delete/${deckId}`, {
            method: 'DELETE',
        });
        if (!response.ok)
            console.log('an error has occurred');
        alert('Deck deleted successfully');
        getDecks();
    } catch(error) {
        alert('there has been an error');
    }
}


window.onload = getDecks;