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
function lightTheme() {
    document.getElementById('btn1').hidden = false;
    document.getElementById('btn2').hidden = true;
    document.querySelector('html').classList.add('light');
    document.querySelector('html').classList.remove('dark');
    setTheme('light');
}

function darkTheme() {
    document.getElementById('btn1').hidden = true;
    document.getElementById('btn2').hidden = false;
    document.querySelector('html').classList.add('dark');
    document.querySelector('html').classList.remove('light');
    setTheme('dark');
}

//Apply system theme
function systemTheme() {
    if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
        darkTheme();
    } else {
        lightTheme();
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