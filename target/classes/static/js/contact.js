//Base url
// const baseUrl = "http://localhost:3030";

//Using Fetch API
let baseUrl;
const getBaseUrl = async () => {
    try {
        const response = await fetch("/api/baseUrl");
        const data = await response.json();
        baseUrl = data.baseUrl;
    } catch (e) {
        console.log(e);
    }
}
getBaseUrl();

//View contact
const loadContactInfo = async (id) => {
    //Fetching data from api
    try {
        const response = await fetch(`${baseUrl}/api/showContact/${id}`);
        const data = await response.json();
        if (data.pic === "user-default.png") {
            data.pic = "/images/user-default.png";
        }
        document.getElementById('contact_image_preview2').src = data.pic;
        document.getElementById('name').innerHTML = data.name;
        document.getElementById('email').innerHTML = data.email;
        document.getElementById('phone').innerHTML = data.phone;
        document.getElementById('address').innerHTML = data.address;
        document.getElementById('link1').innerHTML = data.facebook;
        document.getElementById('link2').innerHTML = data.instagram;
    } catch (error) {
        console.log(error.message);
    }
};

//Delete Contact using fetch api by calling ApiController
const deleteContactClicked = (id) => {
    const btn = document.getElementById('delete-contact');
    btn.addEventListener("click", async () => {
        try {
            const response = await fetch(`${baseUrl}/api/deleteContact/${id}`);
            if (response.ok) {
                window.location.reload(true);
            } else {
                console.error("Failed to delete contact.");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    });
};

//Delete contact using sweet alert
// const deleteContactClicked = (id) => {
//     Swal.fire({
//         title: "Are you sure?",
//         text: "You want to delete this contact!",
//         icon: "warning",
//         showCancelButton: true,
//         confirmButtonColor: "#d33",
//         cancelButtonColor: "#808080",
//         confirmButtonText: "Yes, delete it!"
//     }).then((result) => {
//         if (result.isConfirmed) {
//             const url = `${baseUrl}/user/contacts/deleteContact/${id}`;
//             // window.location.reload(url);
//             window.location.href = url;
//         }
//     });
// }


//Export Data to Excel format
const exportData = () => {
    TableToExcel.convert(document.getElementById("contact-table"), {
        name: "contacts.xlsx",
        sheet: {
            name: "Sheet 1"
        }
    });
};

//Display searched contacts while typing
let searchInput = document.getElementById("search-contact");
searchInput.onchange = function () {
    //To do
}