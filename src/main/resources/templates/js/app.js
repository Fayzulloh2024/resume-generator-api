
function addEducation() {
    const table = document.getElementById('education-table');
    const row = table.insertRow();
    row.innerHTML = `
            <td><input type="text" required></td>
            <td><input type="date" required></td>
            <td><input type="date" required></td>
        `;
}

function addExperience() {
    const table = document.getElementById('experience-table');
    const row = table.insertRow();
    row.innerHTML = `
            <td><input type="text" required></td>
            <td><input type="date" required></td>
            <td><input type="date" required></td>
        `;
}

document.getElementById('photo').addEventListener('change', function(event) {
    const reader = new FileReader();
    reader.onload = function() {
        document.getElementById('photoPreview').src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
});

function submitResume() {
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const dateOfBirth = document.getElementById('dateOfBirth').value;

    const educations = [];
    const educationRows = document.getElementById('education-table').rows;
    for (let i = 0; i < educationRows.length; i++) {
        const cells = educationRows[i].cells;
        educations.push({
            institution: cells[0].children[0].value,
            fromDate: cells[1].children[0].value,
            toDate: cells[2].children[0].value,
        });
    }

    const experiences = [];
    const experienceRows = document.getElementById('experience-table').rows;
    for (let i = 0; i < experienceRows.length; i++) {
        const cells = experienceRows[i].cells;
        experiences.push({
            company: cells[0].children[0].value,
            fromDate: cells[1].children[0].value,
            toDate: cells[2].children[0].value,
        });
    }

    const skills = document.getElementById('skills').value.split(',');

    const photo = document.getElementById('photo').files[0];
    const reader = new FileReader();
    reader.onload = function(event) {
        const imageBase64 = event.target.result.split(',')[1];

        const resume = {
            firstName,
            lastName,
            dateOfBirth,
            educations,
            experiences,
            skills,
        };

        fetch('http://localhost:8080/api/resumes/pdf', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ resume, imageBase64 }),
        })
            .then(response => response.blob())
            .then(blob => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.style.display = 'none';
                a.href = url;
                a.download = 'resume.pdf';
                document.body.appendChild(a);
                a.click();
                window.URL.revokeObjectURL(url);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };
    reader.readAsDataURL(photo);
}