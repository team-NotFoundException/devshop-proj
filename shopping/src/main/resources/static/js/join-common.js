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
        const response = await fetch(`/username-check?username=${username}`);
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

async function callSendApi() {
    let email = document.querySelector("#email").value;

    if (!email) {
        alert("이메일을 입력해주세요");
        return
    }

    try {
        let response = await fetch("/user/email/send", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({email: email})
        });

        let result = await response.json();

        if (response.ok) {
            alert("인증번호가 메일로 전송되었어요. 메일을 확인해주세요.");
            document.querySelector("#code-box").style.display = "block";
        } else {
            alert(result.message || "이메일 전송 실패");
        }
    } catch (e) {
        console.error("파싱 에러: ", e);
        alert("서버 통신 중 오류" );

    }
}

async function callVerifyApi() {
    let email = document.querySelector("#email").value;
    let code = document.querySelector("#code").value;

    if (!code) {
        alert("인증번호를 입력해주세요");
        return
    }

    try {
        let response = await fetch("/user/email/verify", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({email: email, code: code})
        });

        let result = await response.json();
        let msgBox = document.querySelector("#msg");
        const checkButton = document.querySelector(".checkBtn");

        if (response.ok) {
            msgBox.innerHTML = "<span style='color: green'>인증되었습니다.</span>"
            document.querySelector("#isEmailAuth").value = true;
            document.querySelector("#email").readOnly = true;

            checkButton.disabled = true;
            checkButton.style.opacity = "0.6";
            checkButton.innerHTML = "인증 완료";

            document.querySelector("#code").readOnly = true;
        } else {
            msgBox.innerHTML = "<span style='color: red'>인증번호가 틀렸습니다.</span>"
            document.querySelector("#isEmailAuth").value = false;

            checkButton.disabled = false;
        }
    } catch (e) {
        console.log(e);
        alert("네트워크 오류 발생");
    }
}