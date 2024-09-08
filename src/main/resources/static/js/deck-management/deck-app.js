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
    
    decks.forEach(deck => {
        const div = document.createElement('div');
        div.className = 'deck-item';
        div.innerHTML = `
             <div class="deck-header"> 
                <h4><a href="deck-description.html?id=${deck.deckId}" class="deck-link">${deck.deckName}</a>  (${deck.words.length} words)</h4>
            </div>
            <button class="btn btn-edit" data-deck-id="${deck.deckId}">Edit</button>
            <button class="btn btn-delete" data-deck-id="${deck.deckId}">Delete</button>
            <button class="btn btn-words" data-deck-id="${deck.deckId}">Add words</button>
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
            deleteDeck(deckId);
        });
    });

    // Add event listeners to "Add words" button
    document.querySelectorAll('.btn-words').forEach(button => {
        button.addEventListener('click', async () => {
            const deckId = button.getAttribute('data-deck-id');
            await showAddWordForm(deckId);
        });
    });
}

// function for displaying the form when click add words.
const showAddWordForm = async (deckId) => {
    document.getElementById('wordDeckId').value = deckId;
    document.getElementById('add-word-form').style.display = 'block'; // Show the form
    await fetchCategories(); // Fetch and populate the categories
};


// fetch categories from the server.
const fetchCategories = async () => {
    try {
        const response = await fetch('http://127.0.0.1:8080/categories'); 
        if (!response.ok) throw new Error('Failed to fetch categories');

        const categories = await response.json();
        const categorySelect = document.getElementById('category');
        categorySelect.innerHTML = ''; // Clear any existing options

        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.categoryId;
            option.textContent = category.categoryName;
            categorySelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error fetching categories', error);
    }
};

// show the form for creating a new deck.
const showCreateDeckForm = () => {
    document.getElementById('deck-form').style.display = 'block';
    resetDeckForm();
}

// Reset the form fields
const resetDeckForm = () => {
    document.getElementById('deckId').value = '';
    document.getElementById('deckName').value = '';
    document.getElementById('description').value = '';
};

// Save a new or updated deck
const saveDeck = async () => {
    const deckId = document.getElementById('deckId').value;
    const deckName = document.getElementById('deckName').value;
    const description = document.getElementById('description').value;
    const method = deckId ? 'PUT' : 'POST';
    const url = deckId ? `http://127.0.0.1:8080/decks/edit/${deckId}` : 'http://127.0.0.1:8080/decks/create';

    try {
        const response = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ deckName, description })
        });

        if (!response.ok) throw new Error('Failed to save deck');

        await getDecks(); // Refresh the list of decks
        document.getElementById('deck-form').style.display = 'none'; // Hide the form
    } catch (error) {
        console.error('Error saving deck', error);
    }
};

// Add a word to an existing deck.
const addWordToDeck = async () => {
    const deckId = document.getElementById('wordDeckId').value;
    const wordId = document.getElementById('word').value;

    try {
        const response = await fetch(`http://127.0.0.1:8080/decks/assign-word/${deckId}?wordId=${wordId}`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
        });

        if (!response.ok) throw new Error('Failed to add word to deck');
        
        alert('Word added successfully!');
        document.getElementById('add-word-form').style.display = 'none'; // Hide the form
        await getDecks(); // Refresh the list of decks
    } catch (error) {
        console.error('Error adding word to deck', error);
    }
};

// Populate categories and words dynamically
async function populateWords() {
    try {
        const wordsResponse = await fetch('http://127.0.0.1:8080/words'); 

        if (!wordsResponse.ok) throw new Error('Failed to fetch data');

        const words = await wordsResponse.json();

        const wordSelect = document.getElementById('word');

        // Populate words 
        wordSelect.innerHTML = words.map(word => `<option value="${word.wordId}">${word.wordName}</option>`).join('');

    } catch (error) {
        console.error('Error fetching categories or words', error);
    }
}


// Edit an existing deck
const editDeck = async (deckId) => {
    try {
        const response = await fetch(`http://127.0.0.1:8080/decks/${deckId}`);
        if (!response.ok) throw new Error('Failed to fetch deck');

        const deck = await response.json();
        document.getElementById('deckId').value = deck.deckId;
        document.getElementById('deckName').value = deck.deckName;
        document.getElementById('description').value = deck.description;
        document.getElementById('deck-form').style.display = 'block'; // Show the form
    } catch (error) {
        console.error('Error fetching deck', error);
    }
};

// Delete a deck.
const deleteDeck = async (deckId) => {
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

// Show the form for creating a new deck when "Create New Deck" button is clicked
document.getElementById('create-deck-btn').addEventListener('click', () => {
    showCreateDeckForm();
});

// Handle the form submission for saving a new or updated deck
document.getElementById('deckForm').addEventListener('submit', (event) => {
    event.preventDefault(); // Prevent the default form submission
    saveDeck(); // Call the saveDeck function to save the new deck
});

// Handle the "Add Word" Form Submission
document.getElementById('wordForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    await addWordToDeck();
});




window.onload = async () => {
    await getDecks();
    await populateWords();
};
