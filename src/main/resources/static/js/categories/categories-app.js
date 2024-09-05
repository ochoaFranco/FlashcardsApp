const getCategories = async () => {
    const url = 'http://127.0.0.1:8080/categories'
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        console.log(data);
        
        displayCategories(data);
    } catch(error) {
        console.log('There has been a problem with your fetch operation', error);
    }
}

// Display categories and add delete and edit btns.
const displayCategories = (categories) => {
    const categoryList = document.getElementById("category-list");
    
    // Clear the existing content
    categoryList.innerHTML = '';

    categories.forEach(category => {
        const div = document.createElement('div');
        div.className = 'category-item';
        div.innerHTML = `
            <div class = "category-header"> 
                <h4>${category.categoryName}</h4>
            </div>
            <button class="btn btn-edit" data-category-id="${category.id}">Edit</button>
            <button class="btn btn-delete" data-category-id="${category.id}">Delete</button>
        `;
        const hr = document.createElement('hr');
        categoryList.appendChild(hr);
        categoryList.appendChild(div)
    });

    // Add event listeners to edit button
    document.querySelectorAll('.btn-edit').forEach(button => {
        button.addEventListener('click', () => {
            const categoryId = button.getAttribute('data-category-id');
            editCategory(categoryId);
        });
    });

    // Add event listeners to delete button
    document.querySelectorAll('.btn-delete').forEach(button => {
        button.addEventListener('click', () => {
            const categoryId = button.getAttribute('data-category-id');
            deleteCategory(categoryId);
        });
    });
}

// show the form for creating a new category.
const showCreateCategoryForm = () => {
    document.getElementById('category-form').style.display = 'block';
    resetCategoryForm();
}

// Reset the form fields
const resetCategoryForm = () => {
    document.getElementById('categoryId').value = '';
    document.getElementById('categoryName').value = '';
};


// Save a new or updated category
const saveCategory = async () => {
    const categoryId = document.getElementById('categoryId').value;
    const categoryName = document.getElementById('categoryName').value;

    const method = categoryId ? 'PUT' : 'POST';
    const url = categoryId ? `http://127.0.0.1:8080/categories/edit/${categoryId}` : 'http://127.0.0.1:8080/categories/create';

    try {
        const response = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ categoryName })
        });

        if (!response.ok) throw new Error('Failed to save category');

        await getCategories(); // Refresh the category list.
        document.getElementById('category-form').style.display = 'none'; // Hide the form
    } catch (error) {
        console.error('Error saving category', error);
    }
};

// Edit an existing category
const editCategory = async (categoryId) => {
    try {
        const response = await fetch(`http://127.0.0.1:8080/categories/${categoryId}`);
        if (!response.ok) throw new Error('Failed to fetch category');

        const category = await response.json();
        document.getElementById('categoryId').value = category.id;
        document.getElementById('categoryName').value = category.categoryName;
        document.getElementById('category-form').style.display = 'block'; // Show the form
    } catch (error) {
        console.error('Error fetching category', error);
    }
};

// Delete a category.
const deleteCategory = async (categoryId) => {
    try {
        const response = await fetch(`http://127.0.0.1:8080/categories/delete/${categoryId}`, {
            method: 'DELETE',
        });
        if (!response.ok)
            console.log('an error has occurred');
        else
            alert('Category deleted successfully');
        getCategories();
    } catch(error) {
        alert('there has been an error');
    }
}

// Show the form for creating a new category when "Create New category" button is clicked
document.getElementById('create-category-btn').addEventListener('click', () => {
    showCreateCategoryForm();
});

// Handle the form submission for saving a new or updated category
document.getElementById('category-form').addEventListener('submit', (event) => {
    event.preventDefault(); // Prevent the default form submission
    saveCategory(); // Call the saveDeck function to save the new deck
});

window.onload = getCategories;