// Estrutura inicial da árvore
let estruturaCompleta = {
  "id": 1,
  "tipo": "pasta",
  "nome": "Root",
  "carregado": false,
  "subpastas": [],
  "arquivos": []
};

//-- Função apenas para atualizar a estrutura completa conforme o recebimento do fetch quando o usuário clica em uma pasta
function atualizarEstruturaCompleta(estruturaCompleta, estruturaRetornoBackEnd) {

  function encontrarEAtualizarPasta(estruturaCompleta, estruturaRetornoBackEnd) {
    // Se encontramos a pasta, atualizamos os dados
    if (estruturaCompleta.id === estruturaRetornoBackEnd.id && estruturaCompleta.tipo === estruturaRetornoBackEnd.tipo) {
      estruturaCompleta.subpastas = Array.isArray(estruturaRetornoBackEnd.subpastas) ? estruturaRetornoBackEnd.subpastas : []; //-- estruturaRetornoBackEnd.subpastas;
      estruturaCompleta.arquivos = Array.isArray(estruturaRetornoBackEnd.arquivos) ? estruturaRetornoBackEnd.arquivos : []; //-- estruturaRetornoBackEnd.arquivos;
      estruturaCompleta.carregado = true; 
      return true;
    }

    // Caso contrário, verificamos as subpastas
    if (Array.isArray(estruturaCompleta.subpastas)) {
      for (let subpasta of estruturaCompleta.subpastas) {
        // Chamada recursiva para verificar subpastas
        if (encontrarEAtualizarPasta(subpasta, estruturaRetornoBackEnd)) {
          return true;
        }
      }
    }

    return false;
  }

  // Chama a função recursiva para buscar e atualizar a estrutura completa, depois atualiza a interface com esta estrutura
  encontrarEAtualizarPasta(estruturaCompleta, estruturaRetornoBackEnd);
  atualizarInterface(estruturaCompleta);
}

function renderizarArvore(estrutura) {
  const ul = document.createElement("ul");

  if (estrutura.id === 1) {
    const liRoot = document.createElement("li");
    liRoot.classList.add("folder", "root-folder");
    liRoot.setAttribute("data-id", estrutura.id);

    const spanRoot = document.createElement("span");
    spanRoot.classList.add("folder-name");
    spanRoot.textContent = estrutura.nome;

    spanRoot.onclick = function () {
      if (estrutura.carregado) {
        fetchPastasArquivos(estrutura.id, estrutura.tipo); // Carrega os dados da pasta root
      } else {
        /*
        const novaEstrutura = estrutura;
        novaEstrutura.subpastas = [];
        novaEstrutura.arquivos = [];
        atualizarEstruturaCompleta(estrutura, novaEstrutura);
        */
      }
    };

    liRoot.appendChild(spanRoot);

    ul.appendChild(liRoot);
  }

  // Renderiza subpastas
  if (estrutura.subpastas && estrutura.subpastas.length > 0) {
    estrutura.subpastas.forEach(subpasta => {
      const li = document.createElement("li");
      li.classList.add("folder");
      li.setAttribute("data-id", subpasta.id);

      const span = document.createElement("span");
      span.classList.add("folder-name");
      span.textContent = subpasta.nome;

      span.onclick = function () {
        if (!subpasta.carregado) {
          fetchPastasArquivos(subpasta.id, subpasta.tipo);
        }
      };

      li.appendChild(span);

      if (subpasta.carregado) {
        li.appendChild(renderizarArvore(subpasta));
      }

      ul.appendChild(li);
    });
  }

  // Renderiza arquivos
  if (estrutura.arquivos && estrutura.arquivos.length > 0) {
    estrutura.arquivos.forEach(arquivo => {
      const li = document.createElement("li");
      li.classList.add("file");
      li.textContent = arquivo.nome;
      ul.appendChild(li);
    });
  }

  return ul;
}

// Função para atualizar a árvore de pastas na interface
function atualizarInterface(estruturaCompleta) {
  const sidebar = document.querySelector(".home-sidebar");
  sidebar.innerHTML = "";  // Limpa a árvore antes de renderizar

  const tree = renderizarArvore(estruturaCompleta);
  sidebar.appendChild(tree);
}

// Função para fazer o fetch ao clicar em uma pasta
function fetchPastasArquivos(id, tipo) {
  fetch(`http://localhost:8081/WisdomBase/montapastasarquivos?id=${id}&tipo=${tipo}`)
    .then(response => response.json())
    .then(data => {
      // Atualiza a estrutura com os dados recebidos
      console.log("Dados da pasta recebidos:", data);
      atualizarEstruturaCompleta(estruturaCompleta, data);
    })
    .catch(error => {
      console.error("Erro ao carregar pastas e arquivos:", error);
    });
}

window.onload = function() {
  // Renderiza a árvore inicial
  atualizarEstruturaCompleta(estruturaCompleta, estruturaCompleta);
};



/*
  // Simulando a resposta do backend para a pasta root
  let respostaBackendRoot = {
    "id": 1,
    "tipo": "pasta",
    "nome": "root",
    "carregado": false,
    "subpastas": [
      {"id": 2, "tipo": "pasta", "nome": "Contratos", "carregado": false, "subpastas": [], "arquivos": []},
      {"id": 6, "tipo": "pasta", "nome": "Projetos", "carregado": false, "subpastas": [], "arquivos": []}
    ],
    "arquivos":[
      {"id": 4, "tipo": "arquivo", "nome": "SenhasImportantes"},
      {"id": 5, "tipo": "arquivo", "nome": "PostmanEmJava"}
    ]
  };

  // Renderiza a árvore inicial
  atualizarEstruturaCompleta(estruturaCompleta, respostaBackendRoot);
  console.log("Após atualizar 'root':", JSON.stringify(estruturaCompleta, null, 2));

  // Simulando a resposta do backend para a pasta "Contratos"
  let respostaBackendContratos = {
    "id": 2,
    "tipo": "pasta",
    "nome": "Contratos",
    "carregado": false,
    "subpastas": [
      {"id": 23, "tipo": "pasta", "nome": "Financeiro", "carregado": false, "subpastas": [], "arquivos": []},
      {"id": 44, "tipo": "pasta", "nome": "Comercio", "carregado": false, "subpastas": [], "arquivos": []}
    ],
    "arquivos":[
      {"id": 33, "tipo": "arquivo", "nome": "Gmails cadastrados"}
    ]
  };

  // Atualiza a pasta "Contratos" e suas subpastas
  atualizarEstruturaCompleta(estruturaCompleta, respostaBackendContratos);
  console.log("Após atualizar 'Contratos':", JSON.stringify(estruturaCompleta, null, 2));

  // Simulando a resposta do backend para a pasta "Comercio"
  let respostaBackendComercio = {
    "id": 44,
    "tipo": "pasta",
    "nome": "Comercio",
    "carregado": false,
    "subpastas": [
      {"id": 29, "tipo": "pasta", "nome": "Hoje", "carregado": false, "subpastas": [], "arquivos": []},
      {"id": 37, "tipo": "pasta", "nome": "Amanha", "carregado": false, "subpastas": [], "arquivos": []}
    ],
    "arquivos":[
      {"id": 70, "tipo": "arquivo", "nome": "Qualquer coisa"}
    ]
  };

  // Atualiza a pasta "Contratos" e suas subpastas
  atualizarEstruturaCompleta(estruturaCompleta, respostaBackendComercio);
  console.log("Após atualizar 'Comercio':", JSON.stringify(estruturaCompleta, null, 2));
  */