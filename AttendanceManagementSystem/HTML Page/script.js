const signUpBtnLink = document.querySelector('.signUpBtn-link');
const signInBtnLink = document.querySelector('.signInBtn-link');
const wrapper = document.querySelector('.wrapper');
// const Admin = document.querySelector('.Admin');
// const Staff = document.querySelector('.Staff');
// const Student = document.querySelector('.Student');

// Admin.addEventListener('click', () => {
    
//     wrapper.classList.toggle('active');
// });

// Staff.addEventListener('click', () => {
    
//     wrapper.classList.toggle('active');
// });

// Student.addEventListener('click', () => {
    
//     wrapper.classList.toggle('active');
// });

signUpBtnLink.addEventListener('click', () => {
    
    wrapper.classList.toggle('active');
    
});

signInBtnLink.addEventListener('click', () => {
    
    wrapper.classList.toggle('active');
    
});

