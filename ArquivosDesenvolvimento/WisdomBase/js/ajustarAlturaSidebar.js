function ajustarAlturaSidebar() {
  const windowHeight = window.innerHeight;
  const content = document.querySelector('.home-content');
  const breadcrumb = document.querySelector('.breadcrumb-container');
  const contentRect = content.getBoundingClientRect();

  // Altura real do conteúdo da direita (com scroll incluso)
  const contentHeight = content.offsetHeight;

  // Se o header ocupa 120px e o breadcrumb 100px, por exemplo, o contentTop será 220px.
  const breadcrumbHeight = breadcrumb ? breadcrumb.offsetHeight : 0;
  const contentTop = contentRect.top + breadcrumbHeight;

  let limiteAltura;

  if (contentHeight + contentTop <= windowHeight) {
    // Conteúdo da direita cabe na tela → usar altura da janela
    limiteAltura = windowHeight - contentTop - 10;
  } else {
    // Conteúdo da direita é maior → usar a altura do conteúdo
    limiteAltura = contentHeight;
  }

  // Define a altura máxima da sidebar com base na lógica acima
  const sidebar = document.querySelector('.home-sidebar');
  sidebar.style.maxHeight = `${limiteAltura}px`;
}

// Roda quando a página carrega e também quando o usuário redimensiona a tela
window.addEventListener('load', ajustarAlturaSidebar);
window.addEventListener('resize', ajustarAlturaSidebar);
