//Base url
// const baseUrl = "http://localhost:3030";

//Using Fetch API
const baseUrl = async () => {
    try {
        const response = await fetch("/api/baseUrl");
        const data = await response.json();
        return data.baseUrl;
    } catch (e) {
        console.log(e);
    }
}

//Using Promise
/*
const loadContactInfo = (id) => {
    //Fetching data from api
    fetch(`http://localhost:3030/api/showContact/${id}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
        }).catch(error => {
            console.log(error.message);
        });
};
*/

//OR

//Edit contact Using async, await
const loadContactInfo = async (id) => {
    //Fetching data from api
    try {
        const response = await fetch(`${baseUrl}/api/showContact/${id}`);
        const data = await response.json();
        if (data.pic === "user-default.png") {
            data.pic = "/images/user-default.png";
        }
        console.log(data);
        document.getElementById('contact_image_preview2').src = data.pic;
        document.getElementById('name').value = data.name;
        document.getElementById('email').value = data.email;
        document.getElementById('phone').value = data.phone;
        document.getElementById('address').value = data.address;
        document.getElementById('link1').value = data.facebook;
        document.getElementById('link2').value = data.instagram;
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