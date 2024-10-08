document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const deckId = urlParams.get('id');
    
    if (deckId) {
        fetchFlashcards(deckId);
    } else {
        console.error('No deck ID provided');
    }
});


let currentCard = 0;
let flashcards = [];

const fetchFlashcards = async (deckId) => {
    const url = `http://127.0.0.1:8080/decks/${deckId}/flashcards`;
    try {
        const response = await fetch(url);
        if (!response.ok) throw new Error('Network response was not ok');
        flashcards = await response.json();
        console.log(flashcards);

        const flashcardSection = document.getElementById('flashcard-section');
        const noCardsMessage = document.getElementById('no-cards-message');

        if (flashcards.length > 0) {
            noCardsMessage.style.display = 'none'; // Hide the no-cards message
            showCard(currentCard); // Initialize with the first card
            document.getElementById('reveal-answer').addEventListener('click', revealAnswer);
            document.getElementById('next-card').addEventListener('click', nextCard);
            document.getElementById('easy-btn').addEventListener('click', () => rateDifficulty("normal"));
            document.getElementById('medium-btn').addEventListener('click', () => rateDifficulty("difficult"));
            document.getElementById('hard-btn').addEventListener('click', () => rateDifficulty("hard"));
        } else {
            noCardsMessage.style.display = 'block'; // Show the no-cards message
            document.getElementById('reveal-answer').style.display = 'none';
            document.getElementById('next-card').style.display = 'none';
            document.getElementById('difficulty-rating').style.display = 'none';
        }
    } catch (error) {
        console.error('Error fetching flashcards:', error);
    }
};

 
const showCard = (index) => {
    if (index < 0 || index >= flashcards.length) {
        console.error('Invalid card index');
        return;
    }

    const flashcardElement = document.getElementById('flashcard');
    const answerElement = document.getElementById('flashcard-answer');
    const ratingElement = document.getElementById('difficulty-rating');

    flashcardElement.textContent = flashcards[index].wordName;
    answerElement.textContent = flashcards[index].meaning;
    answerElement.style.display = 'none';
    ratingElement.style.display = 'none';
};


const revealAnswer = () => {
    document.getElementById('flashcard-answer').style.display = 'block';
    document.getElementById('difficulty-rating').style.display = 'block';
};


const nextCard = () => {
    if (currentCard < flashcards.length - 1) {
        currentCard++;
        showCard(currentCard);
    } else {
        clearFlashcardContent();
        hideControls();
        document.getElementById('no-cards-message').style.display = 'block'; // Display the message
    }
};

const clearFlashcardContent = () => {
    // Clear the flashcard content
    document.getElementById('flashcard').textContent = '';
    document.getElementById('flashcard-answer').textContent = '';
};

const hideControls = () => {
    // Function to hide all controls when no cards are left
    document.getElementById('reveal-answer').style.display = 'none';
    document.getElementById('next-card').style.display = 'none';
    document.getElementById('difficulty-rating').style.display = 'none';
};

const rateDifficulty = async (difficulty) => {
    const wordId = flashcards[currentCard].wordId;
    const url = `http://127.0.0.1:8080/words/edit/difficulty/${wordId}?difficulty=${difficulty}`;
    try {
        const response = await fetch(url, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            console.log('Difficulty updated successfully');
            nextCard(); // Move to the next card after updating difficulty
        } else {
            console.error('Failed to update difficulty');
        }
    } catch (error) {
        console.error('Error updating difficulty:', error);
    }
};