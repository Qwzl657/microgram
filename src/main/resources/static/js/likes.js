'use strict';
function initLikes() {


    const likeForms = document.querySelectorAll('.like-form');


    for (let i = 0; i < likeForms.length; i++) {
        const form = likeForms[i];


        form.addEventListener('submit', handleLikeSubmit);
    }
}


async function handleLikeSubmit(event) {


    event.preventDefault();


    const form = event.target;


    const postId = form.dataset.postId;


    const csrfToken = document.querySelector('meta[name="_csrf"]')
        .getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')
        .getAttribute('content');

    try {

        const response = await fetch(`/posts/${postId}/like`, {
            method: 'POST',
            headers: {

                [csrfHeader]: csrfToken
            }
        });

        if (response.ok) {

            updateLikeButton(form);
        } else {

            console.warn('Ошибка при лайке:', response.status);
        }

    } catch (error) {

        console.error('Ошибка запроса лайка:', error);
    }
}


function updateLikeButton(form) {

    const icon = form.querySelector('.like-icon');

    const isLiked = icon.classList.contains('bi-heart-fill');


    const card = form.closest('.post-actions');
    const counter = card
        ? card.querySelector('.like-count')
        : null;

    if (isLiked) {

        icon.classList.remove('bi-heart-fill', 'text-danger');
        icon.classList.add('bi-heart');


        if (counter) {
            const current = parseInt(counter.innerText) || 0;
            counter.innerText = Math.max(0, current - 1);
        }
    } else {

        icon.classList.remove('bi-heart');
        icon.classList.add('bi-heart-fill', 'text-danger');

        if (counter) {
            const current = parseInt(counter.innerText) || 0;
            counter.innerText = current + 1;
        }
    }
}