//For edit profile page
let input = document.getElementById('image_input');
let profile_pic = document.getElementById('profile_preview');
input.addEventListener('change', (event) => {
    document.getElementById('imgHide').hidden = true;
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = () => {
        profile_pic.src = reader.result;
    };
    reader.readAsDataURL(file);
});