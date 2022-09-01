let reimbsContainer = document.getElementById("reimbs-container");
let user;
let reimbs;

window.addEventListener("load", async () => {

    let response = await fetch("/api/session");

    let responseBody = await response.json();

    if (!responseBody.successful) {
        window.location = "../";
    }
    if(responseBody.data.userRole == "Employee")
    {
        await fetch("/api/session", { method: "DELETE" });
        window.location = "../";
    }

    user = responseBody.data;

    reimbs = await getAllReimbs(document.getElementById("retrieve-param").value);
    displayReimbs(reimbs);
});


async function getAllReimbs(retrieveParam) {
    let retrieveType = document.getElementById("retrieveTypeRadio").checked == true ? 0 : 1;
    console.log(retrieveType);
    let response = await fetch(`/api/reimbursement/retrieveType=${retrieveType}&retrieveParam=${retrieveParam}`, { method: "GET" });

    let responseBody = await response.json();

    return responseBody.data;
}


let logoutBtn = document.getElementById("logout-btn");
logoutBtn.addEventListener("click", async () => {
    await fetch("/api/session", { method: "DELETE" });
    window.location = "../";
});


let updateBtn = document.getElementById("select-by-button");
updateBtn.addEventListener("click", async () => {
    reimbsContainer.innerHTML = "";
    reimbs = await getAllReimbs(document.getElementById("retrieve-param").value);
    displayReimbs(reimbs);
})

function displayReimbs(reimbs) {
    console.log(reimbs);
    reimbs.forEach(reimb => {
        console.log("printing reimbursements");

        let reimbElem = document.createElement("div");
        reimbElem.className = "reimbursement bg-dark";
        reimbElem.id = `reimbursement-${reimb.reimbId}`;

        let reimbNameContElem = document.createElement("div");
        reimbNameContElem.className = "reimbursement-container w-50";


        resolveContainer = document.createElement("div");
        resolveContainer.className = "resolve-container w-25";
        resolveButtonApprove = document.createElement("button");
        resolveButtonApprove.className = "btn btn-outline-success";
        resolveButtonDeny = document.createElement("button");
        resolveButtonDeny.className = "btn btn-outline-danger";

        resolveButtonApprove.addEventListener("click", async () => {
            let response = await fetch(`/api/reimbursement/reimbId=${reimb.reimbId}&newStatus=2`, { method: "PATCH" });
            reimbsContainer.innerHTML = "";
            reimbs = await getAllReimbs(document.getElementById("retrieve-param").value);
            displayReimbs(reimbs);
        })

        resolveButtonDeny.addEventListener("click", async () => {
            let response = await fetch(`/api/reimbursement/reimbId=${reimb.reimbId}&newStatus=3`, { method: "PATCH" });
            reimbsContainer.innerHTML = "";
            reimbs = await getAllReimbs(document.getElementById("retrieve-param").value);
            displayReimbs(reimbs);
        })

        let reimbNameElem = document.createElement("div");
        reimbNameElem.className = "reimbursement-name text-light";
        reimbNameElem.innerText = `${reimb.reimbSubmitted.substring(0, 19)} $${reimb.amount} \t\t ${reimb.reimbType}: ${reimb.description}`;

        let reimbStatusElem = document.createElement("div");
        if (reimb.reimbStatus == 'Pending') {
            reimbStatusElem.className = "reimbursement-status bg-warning";
        } else if (reimb.reimbStatus == 'Approved') {
            reimbStatusElem.className = "reimbursement-status bg-success text-light";
        } else {
            reimbStatusElem.className = "reimbursement-status bg-danger text-light";
        }

        reimbStatusElem.innerText = `${reimb.reimbStatus != 'Pending' ? `${reimb.reimbResolved.substring(0, 19)}` : ""}`;


        resolveContainer.appendChild(resolveButtonApprove);
        resolveContainer.appendChild(resolveButtonDeny);


        reimbElem.appendChild(reimbNameContElem);
        reimbElem.appendChild(resolveContainer);
        reimbElem.appendChild(reimbStatusElem);

        reimbNameContElem.appendChild(reimbNameElem);

        reimbsContainer.appendChild(reimbElem);

        if(reimb.reimbStatus != "Pending") {
            reimbElem.removeChild(resolveContainer);
        }
    });
}
