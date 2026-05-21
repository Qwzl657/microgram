'use strict';

function initImagePreview() {

    const imageInput = document.getElementById('image-input');
    const previewContainer = document.getElementById('image-preview');

    if (!imageInput || !previewContainer) return;

    imageInput.addEventListener('change', function (event) {

        const file = event.target.files[0];

        if (!file) {
            previewContainer.hidden = true;
            return;
        }

        if (!file.type.startsWith('image/')) {
            alert('Пожалуйста, выберите изображение');
            imageInput.value = '';
            previewContainer.hidden = true;
            return;
        }

        const reader = new FileReader();

        reader.addEventListener('load', function (e) {

            const img = previewContainer.querySelector('img');

            if (img) {
                img.src = e.target.result;
            }

            previewContainer.hidden = false;
        });

        reader.readAsDataURL(file);
    });
}

function initDescriptionCounter() {

    const textarea = document.getElementById('description-input');
    const counter = document.getElementById('description-counter');

    if (!textarea || !counter) return;

    const maxLength = 2200;

    textarea.addEventListener('input', function () {

        const current = textarea.value.length;
        const remaining = maxLength - current;

        counter.innerText = remaining;

        if (remaining < 100) {
            counter.classList.add('text-danger');
            counter.classList.remove('text-muted');
        } else {
            counter.classList.remove('text-danger');
            counter.classList.add('text-muted');
        }
    });
}

function initDeleteConfirm() {


    const deleteForms = document.querySelectorAll('.delete-form');

    for (let i = 0; i < deleteForms.length; i++) {
        const form = deleteForms[i];


        form.addEventListener('submit', function (event) {

            event.preventDefault();

            const message = form.dataset.confirm
                || 'Вы уверены что хотите удалить?';

            if (confirm(message)) {

                form.submit();
            }
        });
    }
}