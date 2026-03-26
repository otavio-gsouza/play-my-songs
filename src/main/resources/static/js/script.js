// --- 1. AO CARREGAR A PÁGINA ---
// Garante que os estilos do banco preencham o ComboBox assim que o site abrir
window.onload = () => {
    carregarEstilos();
};

// --- 2. FUNÇÃO PARA BUSCAR ESTILOS DO MONGO (GET) ---
async function carregarEstilos() {
    const selectEstilo = document.getElementById('estilo');

    try {
        const resposta = await fetch('http://localhost:8080/apis/get-all-styles');

        if (!resposta.ok) throw new Error("Erro ao buscar estilos");

        const estilos = await resposta.json();

        // Limpa o "Carregando..." e mantém a opção padrão instrucional
        selectEstilo.innerHTML = '<option value="" disabled selected>Selecione o Estilo...</option>';

        // Preenche o Select com os dados vindos do Java
        estilos.forEach(estilo => {
            const option = document.createElement('option');
            // ATENÇÃO: Verifique se no seu Java o atributo é 'nome' ou 'descricao'
            option.value = estilo.nome;
            option.textContent = estilo.nome;
            selectEstilo.appendChild(option);
        });

    } catch (error) {
        console.error("Erro:", error);
        selectEstilo.innerHTML = '<option value="">Erro ao carregar estilos</option>';
    }
}

// --- 3. FUNÇÃO DE FAZER UPLOAD (POST) ---
async function fazerUpload() {
    const formData = new FormData();

    const fileInput = document.getElementById('file');
    const titulo = document.getElementById('titulo').value;
    const artista = document.getElementById('artista').value;
    const estilo = document.getElementById('estilo').value;

    // Validação básica
    if (!fileInput.files[0] || !titulo || !artista || !estilo) {
        alert("Preencha todos os campos e selecione o arquivo MP3!");
        return;
    }

    formData.append("file", fileInput.files[0]);
    formData.append("titulo", titulo);
    formData.append("artista", artista);
    formData.append("estilo", estilo);

    try {
        const resposta = await fetch('http://localhost:8080/apis/music-upload', {
            method: 'POST',
            body: formData
        });

        if (resposta.ok) {
            alert("Música cadastrada com sucesso! 🎉");
            document.getElementById('formUpload').reset();
            // Opcional: recarrega a busca para mostrar a nova música
            buscarMusicas();
        } else {
            const msg = await resposta.text();
            alert("Erro: " + msg);
        }
    } catch (e) {
        alert("Erro de conexão com o servidor.");
    }
}

// --- 4. FUNÇÃO DE BUSCA (GET) ---
async function buscarMusicas() {
    const termo = document.getElementById('txtBusca').value;
    const container = document.getElementById('listaMusicas');

    container.innerHTML = "<p style='color:#1DB954'>Buscando...</p>";

    try {
        const url = `http://localhost:8080/apis/get-musics-by-keyword?termo=${encodeURIComponent(termo)}`;
        const resposta = await fetch(url);
        const musicas = await resposta.json();

        container.innerHTML = "";

        if (musicas.length === 0) {
            container.innerHTML = "<p>Nenhuma música encontrada. 🍌</p>";
            return;
        }

        musicas.forEach(musica => {
            const card = document.createElement('div');
            card.className = 'card-musica';
            card.innerHTML = `
                <h3>${musica.titulo}</h3>
                <p><strong>Artista:</strong> ${musica.artista}</p>
                <p><em>Estilo: ${musica.estilo}</em></p>
                <audio controls>
                    <source src="/uploads/${musica.musicFileName}" type="audio/mpeg">
                    Seu navegador não suporta áudio.
                </audio>
            `;
            container.appendChild(card);
        });

    } catch (e) {
        container.innerHTML = "<p style='color:red'>Erro ao carregar músicas.</p>";
    }
}