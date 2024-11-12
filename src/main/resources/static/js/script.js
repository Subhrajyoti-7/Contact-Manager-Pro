/**
    This script handles the theme button functionality.
    It applies the selected theme to the webpage and saves it to local storage.
    The theme is then applied when the page is loaded.
 */

//Set theme to local storage
function setTheme(theme) {
    localStorage.setItem('theme', theme);
}

//Get theme from local storage
function getTheme() {
    let theme = localStorage.getItem('theme');
    return theme ? theme : 'light';
}

//Apply theme
function applyTheme() {
    let currentTheme = getTheme();
    if (currentTheme == 'light') {
        document.getElementById('btn1').hidden = true;
        document.getElementById('btn2').hidden = false;
        document.querySelector('html').classList.add('dark');
        document.querySelector('html').classList.remove('light');
        setTheme('dark');
    } else {
        document.getElementById('btn1').hidden = false;
        document.getElementById('btn2').hidden = true;
        document.querySelector('html').classList.add('light');
        document.querySelector('html').classList.remove('dark');
        setTheme('light');
    }
}

window.onload = function () {
    let currentTheme = getTheme();
    if (currentTheme == 'light') {
        document.getElementById('btn1').hidden = false;
        document.getElementById('btn2').hidden = true;
        document.querySelector('html').classList.add('light');
    } else {
        document.getElementById('btn1').hidden = true;
        document.getElementById('btn2').hidden = false;
        document.querySelector('html').classList.add('dark');
    }
}