package com.wisdom.controllers;

import com.wisdom.models.Arquivo;
import com.wisdom.models.POJO.ArquivoResponse;
import com.wisdom.models.POJO.PastaResponse;
import com.wisdom.models.Pasta;
import com.wisdom.models.POJO.PastasArquivosResponse;
import com.wisdom.repositories.ArquivoRepository;
import com.wisdom.repositories.PastaRepository;
import com.wisdom.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/montapastasarquivos")
public class MontaPastasArquivosController extends HttpServlet {
    private final PastaRepository pastaRepository = new PastaRepository();
    private final ArquivoRepository arquivoRepository = new ArquivoRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String tipo = request.getParameter("tipo");

        try {
            if (tipo.equals("pasta")) {
                Pasta pasta = pastaRepository.recuperarPastaViaId(id);
                if (pasta != null) {

                    // Recuperando subpastas e arquivos
                    List<Pasta> pastasFilhas = pastaRepository.recuperarPastasFilhasViaIdPai(id);
                    List<Arquivo> arquivos = arquivoRepository.recuperarArquivosViaIdPasta(id);

                    // Mapeando as pastas e arquivos para suas respectivas respostas simplificadas
                    List<PastaResponse> pastaResponses = pastasFilhas
                            .stream()
                            .map(p -> new PastaResponse(
                                    p.getId(),
                                    "pasta",
                                    p.getNome()
                            ))
                            .collect(Collectors.toList());

                    List<ArquivoResponse> arquivoResponses = arquivos
                            .stream()
                            .map(a -> new ArquivoResponse(
                                    a.getId(),
                                    "arquivo",
                                    a.getNome()
                            ))
                            .collect(Collectors.toList());

                    PastasArquivosResponse responsePOJO = new PastasArquivosResponse(
                            pasta.getId(),
                            "pasta",
                            pasta.getNome(),
                            false,
                            pastaResponses,
                            arquivoResponses
                    );

                    // Retornar a resposta no formato JSON
                    response.setContentType("application/json");
                    response.getWriter().write(JsonUtils.toJson(responsePOJO));

                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Pasta não encontrada.\"}");
                }
            } else if (tipo.equals("arquivo")) {
                Arquivo arquivo = arquivoRepository.recuperarArquivoViaId(id);
                if (arquivo != null) {

                    // Adicionar o código para lidar com o arquivo, como já estava mencionado:
                    /*
                    response.setContentType(arquivo.getTipo());
                    response.setContentLength((int) arquivo.getTamanho());
                    response.getOutputStream().write(arquivo.getConteudo());
                    */

                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Arquivo não encontrado.\"}");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Tipo inválido\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro interno do servidor. Tente novamente mais tarde.\"}");
        }
    }
}
