const getWordDetails = async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const wordId = urlParams.get('id');

    if (!wordId) return;

    try {
        const response = await fetch(`http://127.0.0.1:8080/words/${wordId}`);
        // check if the response was ok.s
        if (!response.ok)
            throw new Error('Network response was not ok');
        // Display data.
        const word = await response.json();
        displayWordDetails(word);
    } catch(error) {
        console.error('There was a problem with fetching word details:', error)
    }
};

const displayWordDetails = (word) => {
    const wordDetails = document.getElementById('word-details');
    wordDetails.innerHTML = `
        <h2>${word.wordName}</h2>
        <p><strong>Meaning:</strong> ${word.meaning}</p>
          <p><strong>Last review:</strong> ${word.lastReview ? word.lastReview : '-'}</p>
        <p><strong>Next review:</strong> ${word.nextReviewDue ? word.nextReviewDue : '-'}</p>
    `;
};

// Fetch and display word details on page load
window.onload = getWordDetails;