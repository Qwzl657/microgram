'use strict';

function initSubscribe() {


    const subscribeForm = document.querySelector('.subscribe-form');

    if (subscribeForm) {

        subscribeForm.addEventListener('submit', handleSubscribeSubmit);
    }
}

async function handleSubscribeSubmit(event) {

    event.preventDefault();

    const form = event.target;

    const username = form.dataset.username;


    const csrfToken = document.querySelector('meta[name="_csrf"]')
        .getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')
        .getAttribute('content');


    const button = form.querySelector('.subscribe-btn');


    button.setAttribute('disabled', 'true');

    try {

        const response = await fetch(`/users/${username}/subscribe`, {
            method: 'POST',
            headers: {
                [csrfHeader]: csrfToken
            }
        });

        if (response.ok) {

            updateSubscribeButton(button);
            updateFollowersCounter(button);
        } else {
            console.warn('Ошибка подписки:', response.status);
        }

    } catch (error) {

        console.error('Ошибка запроса подписки:', error);
    } finally {

        button.removeAttribute('disabled');
    }
}


function updateSubscribeButton(button) {


    const isFollowing = button.dataset.following === 'true';

    if (isFollowing) {

        button.innerText = 'Подписаться';
        button.classList.remove('btn-outline-secondary');
        button.classList.add('btn-primary');
        // Обновляем состояние
        button.dataset.following = 'false';
    } else {

        button.innerText = 'Отписаться';
        button.classList.remove('btn-primary');
        button.classList.add('btn-outline-secondary');
        button.dataset.following = 'true';
    }
}


function updateFollowersCounter(button) {

    const isFollowing = button.dataset.following === 'true';


    const counter = document.querySelector('.followers-count');

    if (counter) {
        const current = parseInt(counter.innerText) || 0;

        if (isFollowing) {

            counter.innerText = Math.max(0, current - 1);
        } else {

            counter.innerText = current + 1;
        }
    }
}