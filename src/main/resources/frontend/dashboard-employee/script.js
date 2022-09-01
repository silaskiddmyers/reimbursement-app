let reimbsContainer = document.getElementById("reimbs-container");
let user;
let reimbs;

window.addEventListener("load", async () => {
    
    let response = await fetch("/api/session");
    
    let responseBody = await response.json();
    
    if(!responseBody.successful){
        window.location = "../";
    }
    if(responseBody.data.userRole == "Manager")
    {
        await fetch("/api/session", { method: "DELETE" });
        window.location = "../";
    }

    user = responseBody.data;

    reimbs = await getAllReimbs(user.username);
    displayReimbs(reimbs);
});


async function getAllReimbs(retrieveParam){
    let retrieveType = 2;
    let response = await fetch(`/api/reimbursement/retrieveType=${retrieveType}&retrieveParam=${retrieveParam}`, {method: "GET"});

    let responseBody = await response.json();

    return responseBody.data;
}


let logoutBtn = document.getElementById("logout-btn");
logoutBtn.addEventListener("click", async () => {
    await fetch("/api/session", { method: "DELETE" });
    window.location = "../";
});


function displayReimbs(reimbs){
    console.log(reimbs);
    reimbs.forEach(reimb => {

        let reimbElem = document.createElement("div");
        reimbElem.className = "reimbursement bg-dark";
        reimbElem.id = `reimbursement-${reimb.reimbId}`;

        let reimbNameContElem = document.createElement("div");
        reimbNameContElem.className = "reimbursement-container w-75";

        let reimbNameElem = document.createElement("div");
        reimbNameElem.className = "reimbursement-name text-light";
        reimbNameElem.innerText = `${reimb.reimbSubmitted.substring(0,19)} $${reimb.amount} \t\t ${reimb.reimbType}: ${reimb.description}`;  
        
        let reimbStatusElem = document.createElement("div");
        if(reimb.reimbStatus == 'Pending') {
            reimbStatusElem.className = "reimbursement-status bg-warning";
        } else if(reimb.reimbStatus == 'Approved') {
            reimbStatusElem.className = "reimbursement-status bg-success text-light";
        } else {
            reimbStatusElem.className = "reimbursement-status bg-danger text-light";
        }

        reimbStatusElem.innerText = `${reimb.reimbStatus !=  'Pending' ? `${reimb.reimbResolved.substring(0, 19)}` : ""}`;
        

        reimbElem.appendChild(reimbNameContElem);
        reimbElem.appendChild(reimbStatusElem);
        reimbNameContElem.appendChild(reimbNameElem);
        
        reimbsContainer.appendChild(reimbElem);
    });
}


let addReimbFormElem = document.getElementById("add-reimbursement-form");
    addReimbFormElem.addEventListener("submit", async (event) => {
    event.preventDefault();

    let description = document.getElementById("description");
    let amount = document.getElementById("amount");
    let reimbType = document.getElementById("reimbType");
    let response = await fetch("/api/reimbursement", {
        method: "POST",
        body: JSON.stringify({
            "amount": parseFloat(amount.value),
            "description": description.value,
            "reimbType": reimbType.value
        })
    })
    
    let session = await fetch("/api/session", {method: "GET"});
    let sessionBody = await session.json();
    reimbsContainer.innerHTML = "";
    console.log(sessionBody);
    reimbs = await getAllReimbs(sessionBody.data.username);
    displayReimbs(reimbs);

})
