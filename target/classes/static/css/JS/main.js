const signUpA = document.getElementById('signUp');
const signInA = document.getElementById('signIn');
const container = document.getElementById('container');

signUpA.addEventListener('click', () =>
container.classList.add('right-panel-active'));

signInA.addEventListener('click', () =>
container.classList.remove('right-panel-active'));