'use strict';



function initLiveSearch() {


    const searchInput = document.getElementById('search-input');
    const resultsContainer = document.getElementById('search-results');

    if (!searchInput || !resultsContainer) return;

    let debounceTimer = null;


    searchInput.addEventListener('input', function () {


        const query = searchInput.value.trim();

        if (query.length === 0) {

            resultsContainer.innerHTML = '';
            resultsContainer.hidden = true;
            return;
        }

        if (query.length < 2) return;

        if (debounceTimer) {
            clearTimeout(debounceTimer);
        }


        debounceTimer = setTimeout(function () {
            fetchSearchResults(query, resultsContainer);
        }, 400);
    });

    document.addEventListener('click', function (event) {

        if (!searchInput.contains(event.target)
            && !resultsContainer.contains(event.target)) {
            resultsContainer.hidden = true;
        }
    });
}

async function fetchSearchResults(query, container) {

    try {
        container.innerHTML = '<div class="p-2 text-muted">Поиск...</div>';
        container.hidden = false;

        const response = await fetch(
            `/api/users/search?q=${encodeURIComponent(query)}`
        );

        if (!response.ok) {
            throw new Error('Ошибка запроса: ' + response.status);
        }

        const users = await response.json();


        renderSearchResults(users, container);

    } catch (error) {
        console.error('Ошибка поиска:', error);
        container.innerHTML =
            '<div class="p-2 text-danger">Ошибка поиска</div>';
    }
}

function renderSearchResults(users, container) {


    container.innerHTML = '';


    if (!users || users.length === 0) {
        container.innerHTML =
            '<div class="p-2 text-muted">Ничего не найдено</div>';
        container.hidden = false;
        return;
    }


    for (let i = 0; i < users.length; i++) {
        const user = users[i];


        const item = document.createElement('a');


        item.setAttribute('href', '/users/' + user.username);
        item.setAttribute('class',
            'list-group-item list-group-item-action ' +
            'd-flex align-items-center gap-2 py-2');


        const avatarSrc = user.avatar
            ? '/avatars/' + user.avatar
            : '/avatars/default.png';

        item.innerHTML =
            '<img src="' + avatarSrc + '" ' +
            'style="width:32px;height:32px;' +
            'border-radius:50%;object-fit:cover;" ' +
            'alt="avatar">' +
            '<div>' +
            '<div class="fw-bold small">' + escapeHtml(user.username) + '</div>' +
            '<div class="text-muted small">' + escapeHtml(user.name || '') + '</div>' +
            '</div>';

        container.appendChild(item);
    }

    container.hidden = false;
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.appendChild(document.createTextNode(text));
    return div.innerHTML;
}