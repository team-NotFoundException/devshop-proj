let isUsernameValid = false;

/* 아이디 중복 체크 */
async function usernameCheck() {
    const username = document.querySelector("#username").value;
    const msgElement = document.querySelector("#usernameMsg");

    if (username.length < 5 || username.length > 12) {
        msgElement.innerText = "아이디는 5~12자 사이여야 합니다.";
        msgElement.style.color = "red";
        isUsernameValid = false;
        return;
    }

    try {
        const response = await fetch(`/user/username-check?username=${username}`);
        if (response.ok) {
            msgElement.innerText = "사용 가능한 아이디입니다.";
            msgElement.style.color = "#2ecc71";
            isUsernameValid = true;
        } else {
            msgElement.innerText = "이미 사용 중인 아이디입니다.";
            msgElement.style.color = "#e74c3c";
            isUsernameValid = false;
        }
    } catch (e) {
        console.error(e);
        isUsernameValid = false;
    }
}

/* form submit 공통 검증 */
function handleFormSubmit() {
    if (!isUsernameValid) {
        alert("아이디 중복 확인을 해주세요.");
        return false;
    }
    clearAddressStorage();
    return true;
}

/* 주소 localStorage 제거 */
function clearAddressStorage() {
    const keys = [
        "tempZipNo",
        "tempRoadAddr",
        "tempAddrDetail",
        "tempRoadAddrPart2",
        "tempJibunAddr"
    ];
    keys.forEach(key => localStorage.removeItem(key));
}

/* 페이지 로드 시 주소 복원 */
window.addEventListener("DOMContentLoaded", () => {
    const mapping = {
        zipNo: "tempZipNo",
        roadAddr: "tempRoadAddr",
        addrDetail: "tempAddrDetail",
        roadAddrPart2: "tempRoadAddrPart2",
        jibunAddr: "tempJibunAddr"
    };

    for (const id in mapping) {
        const value = localStorage.getItem(mapping[id]);
        if (value && document.getElementById(id)) {
            document.getElementById(id).value = value;
        }
    }

    const usernameInput = document.querySelector("#username");
    if (usernameInput) {
        usernameInput.addEventListener("input", () => {
            isUsernameValid = false;
            document.querySelector("#usernameMsg").innerText = "다시 확인해주세요.";
            document.querySelector("#usernameMsg").style.color = "orange";
        });
    }
});
