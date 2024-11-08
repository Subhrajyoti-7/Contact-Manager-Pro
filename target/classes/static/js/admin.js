//For add contact page
let input_tag = document.getElementById('image_file_input');
let img = document.getElementById('contact_image_preview');

img.hidden = true;
input_tag.addEventListener('change', (event) => {
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = () => {
        img.hidden = false;
        img.src = reader.result;
    };
    reader.readAsDataURL(file);
});

//for edit contact page
let input_tag2 = document.getElementById('image_file_input2');
let img2 = document.getElementById('contact_image_preview2');

input_tag2.addEventListener('change', (event) => {
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = () => {
        img2.src = reader.result;
    };
    reader.readAsDataURL(file);
});