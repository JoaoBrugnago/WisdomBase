// Estrutura inicial da árvore
let estruturaCompleta = {
  "id": 1,
  "tipo": "pasta",
  "nome": "Root",
  "carregado": false,
  "caminho": [],
  "subpastas": [],
  "arquivos": []
};

let estruturaCarregamentoInicial = {
  "id": 9999,
  "tipo": "testes de sistema"
};

function encontrarEAtualizarPasta(estruturaCompleta, estruturaRetornoBackEnd) {
  if (estruturaCompleta.id === estruturaRetornoBackEnd.id && estruturaCompleta.tipo === estruturaRetornoBackEnd.tipo) {
    estruturaCompleta.subpastas = Array.isArray(estruturaRetornoBackEnd.subpastas) ? estruturaRetornoBackEnd.subpastas : [];
    estruturaCompleta.arquivos = Array.isArray(estruturaRetornoBackEnd.arquivos) ? estruturaRetornoBackEnd.arquivos : [];
    estruturaCompleta.carregado = !estruturaCompleta.carregado; 
    return true;
  }

  if (Array.isArray(estruturaCompleta.subpastas)) {
    for (let subpasta of estruturaCompleta.subpastas) {
      if (encontrarEAtualizarPasta(subpasta, estruturaRetornoBackEnd)) {
        return true;
      }
    }
  }

  return false;
}

function encontrarEFecharPasta(estrutura, id) {
  if (estrutura.id === id) {
    estrutura.carregado = false;
    estrutura.subpastas = []; 
    estrutura.arquivos = [];
    return true;
  }

  if (Array.isArray(estrutura.subpastas)) {
    for (let subpasta of estrutura.subpastas) {
      if (encontrarEFecharPasta(subpasta, id)) {
        return true;
      }
    }
  }

  return false;
}

function criarElemento(tag, classes = [], atributos = {}, conteudo = "") {
  const elemento = document.createElement(tag);
  classes.forEach(cls => elemento.classList.add(cls));
  Object.keys(atributos).forEach(attr => elemento.setAttribute(attr, atributos[attr]));
  if (conteudo) elemento.innerHTML = conteudo;
  return elemento;
}

function criarItem(nome, id, carregado, onClickHandler) {
  const li = criarElemento("li", ["folder"], { "data-id": id });
  const arrow = criarElemento("span", ["arrow"], {}, carregado ? "⏶" : "⏷");
  const span = criarElemento("span", ["folder-name"], {}, nome);
  
  span.onclick = onClickHandler;
  arrow.onclick = onClickHandler;
  
  li.appendChild(arrow);
  li.appendChild(span);
  
  return li;
}

function renderizarArvore(estrutura) {
  const ul = criarElemento("ul");

  if (estrutura.id === 1) {
    const rootItem = criarItem(estrutura.nome, estrutura.id, estrutura.carregado, function () {
      if (!estrutura.carregado) {
        fetchPastasArquivos(estrutura.id, estrutura.tipo);
      } else {
        encontrarEFecharPasta(estruturaCompleta, estrutura.id);
        atualizarInterface(estruturaCompleta);
      }
    });
    ul.appendChild(rootItem);
  }

  if (estrutura.subpastas?.length) {
    estrutura.subpastas.forEach(subpasta => {
      const subItem = criarItem(subpasta.nome, subpasta.id, subpasta.carregado, function () {
        if (!subpasta.carregado) {
          fetchPastasArquivos(subpasta.id, subpasta.tipo);
        } else {
          encontrarEFecharPasta(estruturaCompleta, subpasta.id);
          atualizarInterface(estruturaCompleta);
        }
      });

      if (subpasta.carregado) {
        subItem.appendChild(renderizarArvore(subpasta));
      }

      ul.appendChild(subItem);
    });
  }

  if (estrutura.arquivos?.length) {
    estrutura.arquivos.forEach(arquivo => {
      const fileItem = criarElemento("li", ["file"], {}, `📄 ${arquivo.nome}`);
      ul.appendChild(fileItem);
    });
  }

  return ul;
}

function atualizarInterface(estruturaCompleta) {
  const sidebar = document.querySelector(".home-sidebar");
  sidebar.innerHTML = "";  // Limpa a árvore antes de renderizar

  const tree = renderizarArvore(estruturaCompleta);
  sessionStorage.setItem("estruturaCompleta", JSON.stringify(estruturaCompleta));
  sidebar.appendChild(tree);
}

function atualizarEstruturaCompleta(estruturaCompleta, estruturaRetornoBackEnd) {
  encontrarEAtualizarPasta(estruturaCompleta, estruturaRetornoBackEnd);
  atualizarInterface(estruturaCompleta);
}

function atualizarBreadcrumb(estruturaRetornoBackEnd) {
  const breadcrumb = document.querySelector('.breadcrumb-container');
  breadcrumb.innerHTML = "";

  const array = estruturaRetornoBackEnd.caminho;
  sessionStorage.setItem("caminhoCompleto", JSON.stringify(estruturaRetornoBackEnd));
  
  if (Array.isArray(array)) {
    array.forEach((item, index) => {
      const span = criarElemento("span", ["breadcrumb-text"], {}, item);
      breadcrumb.appendChild(span);
  
      if (index < array.length - 1) {
        const separador = criarElemento("span", ["breadcrumb-separator"], {}, "  >  ");
        breadcrumb.appendChild(separador);
      }
    });
  }
}

function fetchPastasArquivos(id, tipo) {
  fetch(`http://localhost:8081/WisdomBase/montapastasarquivos?id=${id}&tipo=${tipo}`)
    .then(response => response.json())
    .then(data => {
      atualizarEstruturaCompleta(estruturaCompleta, data);
      ajustarAlturaSidebar();
      atualizarBreadcrumb(data);
    })
    .catch(error => {
      console.error("Erro ao carregar pastas e arquivos:", error);
    });
}

window.onload = function() {
  const estruturaSalva = sessionStorage.getItem("estruturaCompleta");
  if (estruturaSalva) {
    estruturaCompleta = JSON.parse(estruturaSalva);
    atualizarInterface(estruturaCompleta);
  } else {
    atualizarEstruturaCompleta(estruturaCompleta, estruturaCarregamentoInicial);
  }

  const caminhoSalvo = sessionStorage.getItem("caminhoCompleto");
  atualizarBreadcrumb(JSON.parse(caminhoSalvo));
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