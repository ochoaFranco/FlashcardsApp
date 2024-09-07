const getWords = async () => {
    const url = 'http://127.0.0.1:8080/words'
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        console.log(data);
        
        displayWords(data);
    } catch(error) {
        console.log('There has been a problem with your fetch operation', error);
    }
}

// Display words and add delete and edit btns.
const displayWords = (words) => {
    const wordList = document.getElementById("word-list");
    wordList.innerHTML = '';
    console.log(words);
    
    words.forEach(word => {
        const div = document.createElement('div');
        div.className = 'word-item';
        div.innerHTML = `
             <div class="word-header"> 
                <h4><a href="word-description.html?id=${word.wordId}" class="word-link">${word.wordName}</a> (${word.deckNames.length} decks)</h4>
            </div>
            <button class="btn btn-edit" data-word-id="${word.wordId}">Edit</button>
            <button class="btn btn-delete" data-word-id="${word.wordId}">Delete</button>
        `;
        const hr = document.createElement('hr');
        wordList.appendChild(hr);
        wordList.appendChild(div)
    });

    // Add event listeners to edit button
    document.querySelectorAll('.btn-edit').forEach(button => {
        button.addEventListener('click', () => {
            const wordId = button.getAttribute('data-word-id');
            editWord(wordId);
        });
    });

    // Add event listeners to delete button
    document.querySelectorAll('.btn-delete').forEach(button => {
        button.addEventListener('click', () => {
            const wordId = button.getAttribute('data-word-id');
            deleteWord(wordId);
        });
    });
}

// show the form for creating a new word.
const showCreateWordForm = () => {
    document.getElementById('word-form').style.display = 'block';
    resetWordForm();
}

// Reset the form fields
const resetWordForm = () => {
    document.getElementById('wordId').value = '';
    document.getElementById('wordName').value = '';
    document.getElementById('meaning').value = '';
};

// Save a new or updated word
const saveWord = async () => {
    const wordId = document.getElementById('wordId').value;
    const wordName = document.getElementById('wordName').value;
    const meaning = document.getElementById('meaning').value;
    const method = wordId ? 'PUT' : 'POST';
    const url = wordId ? `http://127.0.0.1:8080/words/edit/${wordId}` : 'http://127.0.0.1:8080/words/create';

    try {
        const response = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ wordName, meaning })
        });

        if (!response.ok) throw new Error('Failed to save word');

        await getWords(); // Refresh the word list.
        document.getElementById('word-form').style.display = 'none'; // Hide the form
    } catch (error) {
        console.error('Error saving word', error);
    }
};

// Edit an existing word
const editWord = async (wordId) => {
    try {
        const response = await fetch(`http://127.0.0.1:8080/words/${wordId}`);
        if (!response.ok) throw new Error('Failed to fetch word');

        const word = await response.json();
        document.getElementById('wordId').value = word.wordId;
        document.getElementById('wordName').value = word.wordName;
        document.getElementById('meaning').value = word.meaning;
        document.getElementById('word-form').style.display = 'block'; // Show the form
    } catch (error) {
        console.error('Error fetching word', error);
    }
};


// Delete a word.
const deleteWord = async (wordId) => {
    try {
        const response = await fetch(`http://127.0.0.1:8080/words/delete/${wordId}`, {
            method: 'DELETE',
        });
        if (!response.ok)
            console.log('an error has occurred');
        else
            alert('Word deleted successfully');
        getWords();
    } catch(error) {
        alert('there has been an error');
    }
}

// Show the form for creating a new word when "Create New word" button is clicked
document.getElementById('create-word-btn').addEventListener('click', () => {
    showCreateWordForm();
});

// Handle the form submission for saving a new or updated word
document.getElementById('word-form').addEventListener('submit', (event) => {
    event.preventDefault(); // Prevent the default form submission
    saveWord(); // Call the saveDeck function to save the new deck
});

window.onload = getWords;